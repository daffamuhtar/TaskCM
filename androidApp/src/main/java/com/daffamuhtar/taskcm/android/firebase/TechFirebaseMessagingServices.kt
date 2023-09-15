package com.daffamuhtar.taskcm.android.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.app.NotificationCompat
import com.daffamuhtar.taskcm.android.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class TechFirebaseMessagingServices : FirebaseMessagingService() {

//    override fun onMessageReceived(message: RemoteMessage) {
//        super.onMessageReceived(message)
//
//        println("CloudMessage - From ${message.from}")
//        Log.v("CloudMessage", "From ${message.from}" )
//
//        Toast.makeText(applicationContext, "Masuk nih", Toast.LENGTH_SHORT).show()
//
//        if (message.data.isNotEmpty()){
//            Log.v("CloudMessage", "Message Data ${message.data}")
//        }
//
//        message.data.let {
//            Log.v("CloudMessage", "Message Notification Body ${it["body"]}")
//
//        }
//
//        if(message.notification != null){
//            Log.v("CloudMessage", "Notification ${message.notification}")
//            Log.v("CloudMessage", "Notification ${message.notification!!.title}")
//            Log.v("CloudMessage", "Notification ${message.notification!!.body}")
//
//        }
//    }
//
//    override fun onNewToken(token: String) {
//        super.onNewToken(token)
//        Toast.makeText(applicationContext, "main "+token, Toast.LENGTH_SHORT).show()
//        GlobalScope.launch {
//            saveGMCToken(token)
//        }
//    }
//
//    //Save GMC token
//    private suspend fun saveGMCToken(token: String) {
//        val gckTokenKey = stringPreferencesKey("gmc_token")
//        baseContext.dataStore.edit { pref ->
//            pref[gckTokenKey] = token
//        }
//    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let { message ->
            sendNotification(message)
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    private fun sendNotification(message: RemoteMessage.Notification) {
        val name = "JetpackPushNotification"
        val description ="Jetpack Push Notification"
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        val channelId = this.getString(R.string.default_notification_cannel_id)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(message.title)
            .setContentText(message.body)
            .setSmallIcon(R.drawable.baseline_assignment_turned_in_24)
            .setAutoCancel(true)

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            val channel = NotificationChannel(channelId, name, importance)
            manager.createNotificationChannel(channel)

        manager.notify(Random.nextInt(), notificationBuilder.build())
    }
}