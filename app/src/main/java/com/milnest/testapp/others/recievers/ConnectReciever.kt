package com.milnest.testapp.others.recievers

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.NetworkInfo
import android.net.ConnectivityManager
import android.support.v4.app.NotificationCompat
import com.milnest.testapp.App
import com.milnest.testapp.R


class ConnectReciever : BroadcastReceiver() {
    private val INTERNET_CHANNEL_ID = 1
    private val notificationManager = App.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val notificationBuilder = NotificationCompat.Builder(App.context, "Main channel")
            .setSmallIcon(R.drawable.ic_network_on)
            .setContentTitle("Сведения о подключении")
            .setContentText("Выполнено подключение к интернету")
            .setAutoCancel(false)
            .setOngoing(true)

    override fun onReceive(p0: Context?, p1: Intent?) {
        if (isOnline()){
            notificationManager.notify(INTERNET_CHANNEL_ID, notificationBuilder.build())
        }
        else
            notificationManager.cancel(INTERNET_CHANNEL_ID)
    }

    fun isOnline() : Boolean{
        val cm = App.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return (netInfo != null && netInfo.isConnected)
    }
}