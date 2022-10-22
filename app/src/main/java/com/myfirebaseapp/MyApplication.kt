package com.myfirebaseapp

import android.app.Application
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        //offline
        Firebase.database.setPersistenceEnabled(true)
    }
}