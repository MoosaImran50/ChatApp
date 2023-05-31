package com.example.assignment1
import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class ChatAPP: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }

}