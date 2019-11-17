package com.bettilina.cinemanteca.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.bettilina.cinemanteca.R
import com.bettilina.cinemanteca.data.helper.NetworkingManager
import com.bettilina.cinemanteca.presentation.view.main.MainActivity
import kotlinx.android.synthetic.main.activity_cinemanteca.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import kotlin.coroutines.CoroutineContext

class CinemantecaActivity: AppCompatActivity(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val networkingManager: NetworkingManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_cinemanteca)

        setViewData()
    }

    private fun setViewData(){
        if(networkingManager.isNetworkOnline()){
            txt_AppNameSplashScreen.text = getString(R.string.welcome_splash_screen)
            txt_SplashScreen.text = getString(R.string.with_internet_splash_screen)
            launch(Dispatchers.Main){
                delay(7500)
                val activityToLaunch = Intent(applicationContext, MainActivity::class.java)
                startActivity(activityToLaunch)
                finish()
            }
        } else {
            txt_AppNameSplashScreen.text = getString(R.string.welcome_splash_screen)
            txt_SplashScreen.text = getString(R.string.without_internet_splash_screen)
            launch(Dispatchers.Main){
                delay(7500)
                val activityToLaunch = Intent(applicationContext, MainActivity::class.java)
                startActivity(activityToLaunch)
                finish()
            }
        }


    }
}