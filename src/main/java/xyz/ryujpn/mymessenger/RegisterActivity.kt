package xyz.ryujpn.mymessenger

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import xyz.ryujpn.mymessenger.ui.login.LoginActivity
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userNameEditText= findViewById<EditText>(R.id.nameregister)

        val userEmailEditText = findViewById<EditText>(R.id.emailEditText)

        val userPasswordEditText = findViewById<EditText>(R.id.passEditText)

        val registerButton = findViewById<Button>(R.id.SignUpButton)

        registerButton.setOnClickListener {
            val userNameText =  userNameEditText.text.toString()
            val userEmailText = userEmailEditText.text.toString()
            val userPasswordText = userPasswordEditText.text.toString()

            var isValid = true
            Log.d("Ryu",userNameText)

            // UserName
            if(userNameText.isEmpty()){
                userNameEditText.error = "名前を入力してください"
                isValid = false
            }
            // UserEmail
            if (userEmailText.isEmpty()) {
                userEmailEditText.error = "メールアドレスを入力してください"
                isValid = false
            }
            // UserPassword
            if (userPasswordText.isEmpty()) {
                userPasswordEditText.error = "パスワードを入力してください"
                isValid = false
            }

            auth = FirebaseAuth.getInstance()
            if(isValid) {
                auth.createUserWithEmailAndPassword(userEmailText, userPasswordText)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                baseContext, "ユーザー登録完了",
                                Toast.LENGTH_SHORT
                            ).show()
                            uploadImageToFirebaseStorage()
                            login()
                        } else {
                            if(userPasswordText.isEmpty() || userPasswordText.length < 6){
                                passEditText.error = "パスワードを入力してください"
                            }
                            if(userEmailText.isEmpty())
                            Toast.makeText(
                                baseContext, "ユーザー登録に失敗しました",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
        photobutton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
        // already have account...
        val loginIntentButton = findViewById<Button>(R.id.intentSignup)
        loginIntentButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun uploadImageToFirebaseStorage(){
        if(selectedPhotoUri == null) return
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
//                ref.downloadUrl.addOnSuccessListener {
//
////                    saveUserToFirebaseDatabase(it.toString())
//                }
//            }
//            .addOnFailureListener{
//
            }
    }
    private fun saveUserToFirebaseDatabase(profileImageUri: String){
//        val uid = FirebaseAuth.getInstance().uid ?: ""
//        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
//        val user = User(uid, user.text.toString(), profileImageUri)
    }

    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0&& resultCode == Activity.RESULT_OK && data != null){
            selectedPhotoUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

            val bitmapDrawable = BitmapDrawable(bitmap)
            photobutton.alpha = 0f
            photobutton.setBackgroundDrawable(bitmapDrawable)
        }
    }

    public override fun onStart() {
        super.onStart()
    }

    private fun login(){
        val intent = Intent(this, DrawerMainActivity::class.java)
        startActivity(intent)
    }



}