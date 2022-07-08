package com.example.routetiplist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class Profile : AppCompatActivity() {
    lateinit var name: EditText
    lateinit var email: EditText
    lateinit var yourCity: EditText
    lateinit var startDay: EditText
    lateinit var endDay: EditText
    lateinit var submitChanges: Button
    lateinit var changePassword: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        name = findViewById(R.id.name_profile)
        email = findViewById(R.id.email_profile)
        yourCity = findViewById(R.id.yourCity_profile)
        startDay = findViewById(R.id.startDay_profile)
        endDay = findViewById(R.id.endDay_profile)
        submitChanges = findViewById(R.id.submitChanges_button)
        changePassword = findViewById(R.id.changePassword_button)

    }
}