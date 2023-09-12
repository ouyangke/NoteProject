package com.example.uvcdemo

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        const val REQUEST_CODE_CAMERA = 1
    }

    private var cameraManager: CameraManager? = null

    private lateinit var sv1: SurfaceView
    private var device1: CameraDevice? = null
    private var previewRequestBuilder1: CaptureRequest.Builder? = null
    private var cameraSession1: CameraCaptureSession? = null
    private var handlerThread1: HandlerThread? = null
    private var handler1: Handler? = null

    private lateinit var sv2: SurfaceView
    private var device2: CameraDevice? = null
    private var previewRequestBuilder2: CaptureRequest.Builder? = null
    private var cameraSession2: CameraCaptureSession? = null
    private var handlerThread2: HandlerThread? = null
    private var handler2: Handler? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sv1 = findViewById(R.id.sv_1)
        sv2 = findViewById(R.id.sv_2)

        if (PackageManager.PERMISSION_GRANTED == ActivityCompat
                .checkSelfPermission(this, Manifest.permission.CAMERA)) {
            //已授权
            init()
        } else {
            //未授权
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CODE_CAMERA)
        }



    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode === REQUEST_CODE_CAMERA) {
            for (i in permissions.indices) {
                if (grantResults[i] === PackageManager.PERMISSION_GRANTED) {
                    //已授权
                    init()
                } else {
                    //未授权
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun init(){
        //初始化相机
        initCamera()

        sv1.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                openCamera1()
            }

            override fun surfaceChanged(
                holder: SurfaceHolder, format: Int, width: Int, height: Int
            ) = Unit

            override fun surfaceDestroyed(holder: SurfaceHolder) = Unit

        })

        sv2.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                openCamera2()
            }

            override fun surfaceChanged(
                holder: SurfaceHolder, format: Int, width: Int, height: Int
            ) = Unit

            override fun surfaceDestroyed(holder: SurfaceHolder) = Unit

        })
    }

    /**
     * 初始化相机
     */
    private fun initCamera() {
        //获取相机服务
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager?
        cameraManager?.let {
            //获取相机列表
            val cameraIdList = it.cameraIdList

        }
        handlerThread1 = HandlerThread("handlerThread1")
        handlerThread1?.start()
        handler1 = Handler(Looper.getMainLooper())

        handlerThread2 = HandlerThread("handlerThread2")
        handlerThread2?.start()
        handler2 = Handler(Looper.getMainLooper())
    }

    /**
     * 打开相机
     */
    @SuppressLint("MissingPermission")
    private fun openCamera1() {
        cameraManager?.openCamera("0", object : CameraDevice.StateCallback() {

            override fun onOpened(camera: CameraDevice) {
                device1 = camera
                createCameraPreviewSession1()
            }

            override fun onDisconnected(camera: CameraDevice) {

            }

            override fun onError(camera: CameraDevice, error: Int) {

            }
        }, handler1)

    }

    @SuppressLint("MissingPermission")
    private fun openCamera2() {
        cameraManager?.openCamera("1", object : CameraDevice.StateCallback() {

            override fun onOpened(camera: CameraDevice) {
                device2 = camera
                createCameraPreviewSession2()
            }

            override fun onDisconnected(camera: CameraDevice) {

            }

            override fun onError(camera: CameraDevice, error: Int) {

            }
        }, handler2)

    }

    private fun createCameraPreviewSession1() {
        try {
            previewRequestBuilder1 = device1?.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            val previewSurface1 = sv1.holder.surface
            previewRequestBuilder1?.addTarget(previewSurface1)
            device1?.createCaptureSession(
                mutableListOf(previewSurface1), object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(session: CameraCaptureSession) {
                        try {
                            cameraSession1 = session
                            previewRequestBuilder1?.set(
                                CaptureRequest.CONTROL_AF_MODE,
                                CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
                            )
                            val captureRequest1: CaptureRequest? = previewRequestBuilder1?.build()
                            if (captureRequest1 != null) {
                                cameraSession1?.setRepeatingRequest(captureRequest1, null, handler1)
                            }
                        } catch (e: CameraAccessException) {
                            e.printStackTrace()
                        }
                    }

                    override fun onConfigureFailed(session: CameraCaptureSession) {

                    }
                }, handler1
            )
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun createCameraPreviewSession2() {
        try {
            previewRequestBuilder2 = device2?.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            val previewSurface2 = sv2.holder.surface
            previewRequestBuilder2?.addTarget(previewSurface2)
            device2?.createCaptureSession(
                mutableListOf(previewSurface2), object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(session: CameraCaptureSession) {
                        try {
                            cameraSession2 = session
                            previewRequestBuilder2?.set(
                                CaptureRequest.CONTROL_AF_MODE,
                                CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
                            )
                            val captureRequest2: CaptureRequest? = previewRequestBuilder2?.build()
                            if (captureRequest2 != null) {
                                cameraSession2?.setRepeatingRequest(captureRequest2, null, handler2)
                            }
                        } catch (e: CameraAccessException) {
                            e.printStackTrace()
                        }
                    }

                    override fun onConfigureFailed(session: CameraCaptureSession) {

                    }
                }, handler2
            )
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }
}