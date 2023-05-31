package com.example.assignment1

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate

class LoginActivty : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_login_activty)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        getSupportActionBar()?.hide()

        val nameText = findViewById<EditText>(R.id.nameField)
        val contactText = findViewById<EditText>(R.id.contactField)
        val LoginButton = findViewById<Button>(R.id.loginButton)
        var name: String
        var contact :String

        // onclick listener for adding contact button
        LoginButton.setOnClickListener {
            name = nameText.text.toString().trim()
            contact = contactText.text.toString().trim()
            if (name == "") {
                Toast.makeText(
                    this@LoginActivty,
                    "Name field can not be left blank!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if (contact == "") {
                Toast.makeText(
                    this@LoginActivty,
                    "Contact Number field can not be left blank!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else {
                val intent = Intent(this@LoginActivty, ContactActivity::class.java)
                intent.putExtra("NAME", name)
                intent.putExtra("CONTACT", contact)
                startActivity(intent)
            }

        }
    }
}