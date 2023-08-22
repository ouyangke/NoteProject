package com.oyke.client

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.oyke.service.IKtvController
import com.oyke.service.KtvData

class MainActivity : AppCompatActivity() {

    private var ktvController: IKtvController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvText = findViewById<TextView>(R.id.tv_text)
        val btnPlay = findViewById<Button>(R.id.btn_play)
        val btnPause = findViewById<Button>(R.id.btn_pause)
        val btnGetInfo = findViewById<Button>(R.id.btn_getInfo)

        btnPlay.setOnClickListener {
            ktvController?.setPlay("hi play")
        }
        btnPause.setOnClickListener {
            ktvController?.setPause("sorry pause")
        }
        btnGetInfo.setOnClickListener {

            val sum = ktvController?.add(1, 2)
            Log.d("oyk", "add:$sum")

            val ktvDataIn = KtvData()
            ktvDataIn.name = "clientIn"
            ktvDataIn.type = 1
            val inKtvInfo = ktvController?.inKtvInfo(ktvDataIn)
            Log.d("oyk","in:$inKtvInfo")

            val ktvDataOut = KtvData()
            ktvDataOut.name = "clientOut"
            ktvDataOut.type = 2
            val outKtvInfo = ktvController?.outKtvInfo(ktvDataOut)
            Log.d("oyk","out:$outKtvInfo")

            val ktvDataInOut = KtvData()
            ktvDataInOut.name = "clientInOut"
            ktvDataInOut.type = 3
            val inOutKtvInfo = ktvController?.inOutKtvInfo(ktvDataInOut)
            Log.d("oyk","inOut:$inOutKtvInfo")
        }

        bindService()
    }

    private fun bindService() {
        val intent = Intent()
        intent.setClassName("com.oyke.service", "com.oyke.service.RemoteService")
        applicationContext.bindService(intent, object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                Log.d("oyk", "onServiceConnected")
                ktvController = IKtvController.Stub.asInterface(service)
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                Log.d("oyk", "onServiceDisconnected")
            }

        }, Context.BIND_AUTO_CREATE)
    }
}