package com.bettilina.cinemanteca.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bettilina.cinemanteca.presentation.view.main.MainActivity

class CinemantecaActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityToLaunch = Intent(this, MainActivity::class.java)
        startActivity(activityToLaunch)
        finish()
    }
}