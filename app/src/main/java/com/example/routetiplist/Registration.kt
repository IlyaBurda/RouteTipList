package com.example.routetiplist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class Registration : AppCompatActivity() {
    lateinit var inputEmail: EditText
    lateinit var inputPassword: EditText
    lateinit var inputConfirmPassword: EditText
    lateinit var buttonReg: Button
    lateinit var progressBar: ProgressBar
    private lateinit var mAuth: FirebaseAuth
    var mUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        inputEmail = findViewById(R.id.input_email)
        inputPassword = findViewById(R.id.input_password)
        inputConfirmPassword = findViewById(R.id.input_confirm_password)
        buttonReg = findViewById(R.id.register_button)
        mAuth = FirebaseAuth.getInstance()
        mUser = mAuth.currentUser
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE

        buttonReg.setOnClickListener {
            perforAuth()
        }
    }

    private fun perforAuth() {
        var email = inputEmail.text.toString()
        var password = inputPassword.text.toString()
        var confirmPassword = inputConfirmPassword.text.toString()

        if (!isEmailValid(email)) {
            inputEmail.error = "Enter context email"
        } else if (password.isEmpty() || password.length < 6) {
            inputPassword.error = "Enter proper password"
        } else if (password != confirmPassword) {
            inputConfirmPassword.error = "Password not match Both field"
        } else {
            progressBar.visibility = View.VISIBLE

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    progressBar.visibility = View.INVISIBLE
                    sendUserToNextActivity()
                    Toast.makeText(
                        applicationContext,
                        "Registration Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(applicationContext, "${it.exception}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun sendUserToNextActivity() {
        intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun isEmailValid(email: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }
}