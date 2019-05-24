package xyz.ryujpn.mymessenger

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val buttonSignUp = findViewById<Button>(R.id.SignUpButton)
        val buttonLogin = findViewById<Button>(R.id.LoginButton)

        // SignUp
        buttonSignUp.setOnClickListener {
            var isValid = true
            // 入力した文字列を変数に格納
            val emailEditText = findViewById<EditText>(R.id.emailEditText)
            val emailText = emailEditText.text.toString()
            if (emailText.isEmpty()) {
                emailEditText.error = "メールアドレスを入力してください"
                isValid = false
            }

            val passEditText = findViewById<EditText>(R.id.passEditText)
            val passText = passEditText.text.toString()
            if (passText.isEmpty()) {
                passEditText.error = "Passを入力してください"
                isValid = false
            }


            if(isValid) {
                auth.createUserWithEmailAndPassword(emailText, passText)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                baseContext, "SignUp 成功",
                                Toast.LENGTH_SHORT
                            ).show()
                            login()
                        } else {
                            if(passText.isEmpty() || passText.length < 6){
                                passEditText.error = "Passを入力してください"
                            }
                            if(emailText.isEmpty())

                            Toast.makeText(
                                baseContext, "SignUp 失敗",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }


        // Login
        buttonLogin.setOnClickListener {
            var isValid = true
            // 入力した文字列を変数に格納
            val emailEditText = findViewById<EditText>(R.id.emailEditText)
            val emailText = emailEditText.text.toString()
            if (emailText.isEmpty()) {
                emailEditText.error = "メールアドレスを入力してください"
                isValid = false
            }

            val passEditText = findViewById<EditText>(R.id.passEditText)
            val passText = passEditText.text.toString()
            if (passText.isEmpty()) {
                passEditText.error = "6文字以上のPassを入力してください"
                isValid = false
            }
            if(isValid || passText.length < 6) {
                auth?.signInWithEmailAndPassword(emailText, passText)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                baseContext, "Login 成功",
                                Toast.LENGTH_SHORT
                            ).show()
                            login()
                        } else {
                            Toast.makeText(
                                baseContext, "Login 失敗",
                                Toast.LENGTH_SHORT
                            ).show()
                            passEditText.error = "6文字以上のPassを入力してください"
                        }

                        // ...
                    }
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        updateUI(currentUser)
    }

    fun login(){
        val user = auth.currentUser
        val intent = Intent(this,DrawerMainActivity::class.java)
        intent.putExtra("user_name", user?.email)
        startActivity(intent)
    }



}