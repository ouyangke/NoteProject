package com.example.ftpdemo

import android.app.Service
import android.content.Intent
import android.os.Environment
import android.os.IBinder
import android.widget.Toast
import org.apache.ftpserver.FtpServer
import org.apache.ftpserver.FtpServerFactory
import org.apache.ftpserver.ftplet.Authority
import org.apache.ftpserver.ftplet.FtpException
import org.apache.ftpserver.listener.ListenerFactory
import org.apache.ftpserver.usermanager.impl.BaseUser
import org.apache.ftpserver.usermanager.impl.WritePermission


class FtpService : Service() {
    private var server: FtpServer? = null
    private val user = "admin"
    private val password = "123456"
    private var rootPath: String? = null
    private val port = 2221

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        rootPath = "mnt/sdcard/Android"//Environment.getExternalStorageDirectory().absolutePath
        try {
            init()
            Toast.makeText(this, "启动ftp服务成功", Toast.LENGTH_SHORT).show()
        } catch (e: FtpException) {
            e.printStackTrace()
            Toast.makeText(this, "启动ftp服务失败", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        release()
        Toast.makeText(this, "关闭ftp服务", Toast.LENGTH_SHORT).show()
    }

    /**
     * 初始化
     *
     * @throws FtpException
     */
    @Throws(FtpException::class)
    fun init() {
        release()
        startFtp()
    }

    @Throws(FtpException::class)
    private fun startFtp() {
        val serverFactory = FtpServerFactory()

        //设置访问用户名和密码还有共享路径
        val baseUser = BaseUser()
        baseUser.name = user
        baseUser.password = password
        baseUser.homeDirectory = rootPath
        val authorities: MutableList<Authority> = ArrayList()
        authorities.add(WritePermission())
        baseUser.authorities = authorities
        serverFactory.userManager.save(baseUser)
        val factory = ListenerFactory()
        factory.port = port //设置端口号 非ROOT不可使用1024以下的端口
        serverFactory.addListener("default", factory.createListener())
        server = serverFactory.createServer()
        server?.start()
    }


    /**
     * 释放资源
     */
    private fun release() {
        stopFtp()
    }

    private fun stopFtp() {
        server?.stop()
        server = null
    }
}