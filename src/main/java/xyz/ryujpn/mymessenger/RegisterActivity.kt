package xyz.ryujpn.mymessenger

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import xyz.ryujpn.mymessenger.ui.login.LoginActivity
import java.util.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        SignUpButton.setOnClickListener {
            performRegister()
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


    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {

            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

            circleImageView.setImageBitmap(bitmap)
            photobutton.alpha = 0f

        }
    }

    private fun performRegister() {
        val userEmailText = emailEditText.text.toString()
        val userPasswordText = passEditText.text.toString()

        Log.d("RegisterActivity", userEmailText)
        Log.d("RegisterActivity", userPasswordText)

        if (userEmailText.isEmpty() || userPasswordText.isEmpty()) {
            Toast.makeText(this, "メールとパスワードを入力してください", Toast.LENGTH_SHORT).show()
            return
        }
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(userEmailText, userPasswordText)
                .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        baseContext, "ユーザー登録完了",
                        Toast.LENGTH_SHORT
                    ).show()
                    uploadImageToFirebaseStorage()
                }
            }
    }


    private fun uploadImageToFirebaseStorage() {
        if (selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        Log.d("RegisterActivity", filename)
        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("RegisterActivity", "UploadSuccess//")
                ref.downloadUrl.addOnSuccessListener {
                    saveUserToFirebaseDatabase(it.toString())
                    Log.d("RegisterActivity", "UploadSuccess")
                }
            }
            .addOnFailureListener {
                Log.d("RegisterActivity", "faileduploadimagefile")
            }
    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user = User(uid, nameEditText.text.toString(), profileImageUrl)

        ref.setValue(user)
            .addOnSuccessListener {
                val intent = Intent(this, DrawerMainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
    }
}
//
//class User(val uid: String, val username: String, val profileImageUrl: String){
//    constructor() : this("","", "")
//}
