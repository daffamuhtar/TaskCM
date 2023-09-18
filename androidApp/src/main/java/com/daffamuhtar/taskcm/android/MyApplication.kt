package com.daffamuhtar.taskcm.android

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.tasks.await

val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "localStore")
class MyApplication : Application() {
//    launchCatching {
//        val token = Firebase.messaging.token.await()
//        Log.d("FCM token:", token)
//    }

    override fun onCreate() {
        super.onCreate()


        createNotificationChannel()
    }

    private fun createNotificationChannel(){
        val name = "JetpackPushNotification"
        val description ="Jetpack Push Notification"
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        // create notif chn
        val channel = NotificationChannel("Global",name, importance)
        channel.description = description

        //  get notif manager
        val notificationManager : NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        notificationManager.createNotificationChannel(channel)
    }
}