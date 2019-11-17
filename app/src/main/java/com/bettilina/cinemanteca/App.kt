package com.bettilina.cinemanteca

import android.app.Activity
import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import com.bettilina.cinemanteca.data.controller.RetrofitController
import com.bettilina.cinemanteca.inject.databaseModule
import com.bettilina.cinemanteca.inject.movieModule
import com.bettilina.cinemanteca.inject.networkingModule
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.lang.ref.WeakReference

class App: Application() {

    private val retrofitController: RetrofitController by inject()
    private val sharedPreferences: SharedPreferences by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(movieModule, networkingModule, databaseModule))
        }

        AndroidThreeTen.init(this)

        listenActivitiesCallbacks()
    }

    private fun listenActivitiesCallbacks(){
        registerActivityLifecycleCallbacks(Lifecycle())
    }

    inner class Lifecycle : ActivityLifecycleCallbacks{

        override fun onActivityStarted(activity: Activity) {
            activity.let{
                currentActivity = WeakReference(it)
            }
        }

        override fun onActivityResumed(activity: Activity) {
            activity.let{
                currentActivity = WeakReference(it)
            }
        }

        override fun onActivityDestroyed(activity: Activity) {
            if (activity == currentActivity.get()) {
                currentActivity.clear()
            }
        }

        override fun onActivityPaused(activity: Activity) {}

        override fun onActivitySaveInstanceState(activity: Activity, p1: Bundle) {}

        override fun onActivityStopped(activity: Activity) {}

        override fun onActivityCreated(activity: Activity, p1: Bundle?) {}
    }

    companion object{
        var currentActivity = WeakReference<Activity>(null)

        var apiKey = "6c6054e6ecc7a9dc40e88fac1a10685e"
        var imgSrcBasePath = "https://image.tmdb.org/t/p/original"
        var imgSrcBasePath500 = "https://image.tmdb.org/t/p/w500"
    }
}