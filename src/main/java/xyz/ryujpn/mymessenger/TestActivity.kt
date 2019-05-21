package xyz.ryujpn.mymessenger

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        val user = intent.getStringExtra("user_name")
        val userEmail = findViewById<TextView>(R.id.userEmail)
        userEmail.text = user.toString()
    }
}
