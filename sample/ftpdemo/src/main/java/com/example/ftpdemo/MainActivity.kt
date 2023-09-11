package com.example.ftpdemo


import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textview)

        val ip: String = getIp()
        if (TextUtils.isEmpty(ip)) {
            textView.text = "获取不到IP，请连接网络"
        } else {
            val str = """
                请在IE浏览器上输入网址访问FTP服务
                ftp://$ip:2221
                账号:admin
                密码:123456
                """.trimIndent()
            textView.text = str
        }

        startService(Intent(this, FtpService::class.java))

    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, FtpService::class.java))
    }

    fun getIp(): String {
        //获取wifi服务
        val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled) {
            wifiManager.isWifiEnabled = true
        }
        val wifiInfo = wifiManager.connectionInfo
        val ipAddress = wifiInfo.ipAddress
        return intToIp(ipAddress)
    }


    private fun intToIp(i: Int): String {
        return (i and 0xFF).toString() + "." + (i shr 8 and 0xFF) + "." + (i shr 16 and 0xFF) + "." + (i shr 24 and 0xFF)
    }
}