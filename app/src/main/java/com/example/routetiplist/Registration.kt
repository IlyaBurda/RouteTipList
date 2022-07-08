package com.example.routetiplist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.regex.Pattern

class Registration : AppCompatActivity() {
    lateinit var mInputName: EditText
    lateinit var mInputEmail: EditText
    lateinit var mInputPassword: EditText
    lateinit var mInputConfirmPassword: EditText
    lateinit var mButtonReg: Button
    lateinit var mProgressBar: ProgressBar

    lateinit var mAuth: FirebaseAuth
    private var mUser: FirebaseUser? = null
    private lateinit var userId: String
    private lateinit var fStore: FirebaseFirestore
//    private val TAG = Registration::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        mInputName = findViewById(R.id.input_name)
        mInputEmail = findViewById(R.id.input_email)
        mInputPassword = findViewById(R.id.input_password)
        mInputConfirmPassword = findViewById(R.id.input_confirm_password)
        mButtonReg = findViewById(R.id.register_button)
        mProgressBar = findViewById(R.id.progressBar_reg)
        mAuth = FirebaseAuth.getInstance()
        mUser = mAuth.currentUser
        fStore = FirebaseFirestore.getInstance()
        mProgressBar.visibility = View.INVISIBLE
        mButtonReg.setOnClickListener {
            perforAuth()
        }
    }

    private fun perforAuth() {
        var email = mInputEmail.text.toString()
        var password = mInputPassword.text.toString()
        var confirmPassword = mInputConfirmPassword.text.toString()
        var name = mInputName.text.toString().trim()

        if (name.isEmpty()) {
            mInputName.error = "This field is empty"
        } else if (!isEmailValid(email)) {
            mInputEmail.error = "Enter context email"
        } else if (password.isEmpty() || password.length < 6) {
            mInputPassword.error = "Enter proper password"
        } else if (password != confirmPassword) {
            mInputConfirmPassword.error = "Password not match Both field"
        } else {
            mProgressBar.visibility = View.VISIBLE

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    userId = mAuth.currentUser!!.uid
                    var ref: DocumentReference = fStore.collection("users").document(userId)
                    var user = hashMapOf(
                        "name" to name,
                        "email" to email
                    )
                    ref.set(user).addOnSuccessListener {
//                        Log.d(TAG, "onSuccess: user Profile is created for $userId")
                        Toast.makeText(applicationContext, "All is good", Toast.LENGTH_SHORT).show()
                    }
                    sendUserToNextActivity()
                    Toast.makeText(
                        applicationContext,
                        "Registration Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    mProgressBar.visibility = View.INVISIBLE
                } else {
                    Toast.makeText(
                        applicationContext,
                        "${it.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                    mProgressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun sendUserToNextActivity() {
        intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    companion object {
        fun isEmailValid(email: String): Boolean {
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

}