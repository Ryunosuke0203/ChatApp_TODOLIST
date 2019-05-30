package xyz.ryujpn.mymessenger

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val userRegisteredEmail = findViewById<EditText>(R.id.LoginemailText)
        val userRegisteredPassword = findViewById<EditText>(R.id.LoginpassText)

        val loginButton = findViewById<Button>(R.id.LoginButton)

        loginButton.setOnClickListener {
            val email = userRegisteredEmail.text.toString()
            val password = userRegisteredPassword.text.toString()

            var isValid = true

            // UserEmail
            if (email.isEmpty()) {
                userRegisteredEmail.error = "メールアドレスを入力してください"
                isValid = false
            }
            // UserPassword
            if (password.isEmpty()) {
                userRegisteredPassword.error = "パスワードを入力してください"
                isValid = false
            }

            if (isValid) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                baseContext, "ログインしました",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this, DrawerMainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                baseContext, "ログインできませんでした",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }

        }
        val registerIntentButton = findViewById<Button>(R.id.intentSignup)
        registerIntentButton.setOnClickListener {
//            val intent = Intent(this, RegisterActivity::class.java)
//            startActivity(intent)
            finish()
        }
    }
}