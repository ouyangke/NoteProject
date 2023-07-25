package com.oyke.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class RemoteService : Service() {

    private val TAG = "RemoteService"

    override fun onCreate() {
        Log.e(TAG, "onCreate")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(TAG, "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        Log.e(TAG, "onBind")
        return KtvBinder()
    }

    override fun onRebind(intent: Intent?) {
        Log.e(TAG, "onRebind")
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.e(TAG, "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.e(TAG, "onDestroy")
        super.onDestroy()
    }


    private class KtvBinder : IKtvController.Stub() {

        override fun setPlay(play: String?) {
            Log.e("oyk", play + "")
        }

        override fun setPause(pause: String?) {
            Log.e("oyk", pause + "")
        }

        override fun add(arg1: Int, arg2: Int): Int {
            Log.e("oyk", "add")
            return arg1 + arg2;
        }

        override fun inKtvInfo(ktvData: KtvData?): String {
            //ktvData?.name = "serviceIn"
            //ktvData?.type = 4
            return "${ktvData?.name},${ktvData?.type}"
        }

        override fun outKtvInfo(ktvData: KtvData?): String {
            ktvData?.name = "serviceOut"
            ktvData?.type = 5
            return "${ktvData?.name},${ktvData?.type}"
        }

        override fun inOutKtvInfo(ktvData: KtvData?): String {
            //ktvData?.name = "serviceInOut"
            ktvData?.type = 6
            return "${ktvData?.name},${ktvData?.type}"
        }


    }
}