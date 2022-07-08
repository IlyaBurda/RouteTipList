package com.example.routetiplist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.routetiplist.Registration.Companion.isEmailValid
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Login : AppCompatActivity() {
    lateinit var inputEmail: EditText
    lateinit var inputPassword: EditText
    lateinit var buttonLog: Button
    lateinit var progressBar: ProgressBar
    private lateinit var mAuth: FirebaseAuth
    var mUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        inputEmail = findViewById(R.id.input_email_log)
        inputPassword = findViewById(R.id.input_password_log)
        buttonLog = findViewById(R.id.login_button)
        mAuth = FirebaseAuth.getInstance()
        mUser = mAuth.currentUser
        progressBar = findViewById(R.id.progressBar_log)
        progressBar.visibility = View.INVISIBLE

        buttonLog.setOnClickListener {
            perforLogin()
        }
    }

    private fun perforLogin() {
        var email = inputEmail.text.toString()
        var password = inputPassword.text.toString()

        if (!isEmailValid(email)) {
            inputEmail.error = "Enter context email"
        } else if (password.isEmpty() || password.length < 6) {
            inputPassword.error = "Enter proper password"
        } else {
            progressBar.visibility = View.VISIBLE
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                progressBar.visibility = View.INVISIBLE
                sendUserToNextActivity()
                Toast.makeText(
                    applicationContext,
                    "Login Successful",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(
                    applicationContext,
                    "${it.exception}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun sendUserToNextActivity() {
        intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
