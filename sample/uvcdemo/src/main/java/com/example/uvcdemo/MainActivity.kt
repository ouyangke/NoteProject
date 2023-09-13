package com.example.uvcdemo

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.media.ImageReader
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import android.util.Range
import android.view.Surface
import android.view.TextureView
import android.view.TextureView.SurfaceTextureListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.util.Arrays

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        const val REQUEST_CODE_CAMERA = 1
    }

    private var cameraManager: CameraManager? = null

    private lateinit var sv1: TextureView
    private lateinit var tv1:TextView
    private var device1: CameraDevice? = null
    private var previewRequestBuilder1: CaptureRequest.Builder? = null
    private var cameraSession1: CameraCaptureSession? = null
    private var nv21Reader1: ImageReader? = null
    private var handlerThread1: HandlerThread? = null
    private var handler1: Handler? = null

    private lateinit var sv2: TextureView
    private lateinit var tv2:TextView
    private var device2: CameraDevice? = null
    private var previewRequestBuilder2: CaptureRequest.Builder? = null
    private var cameraSession2: CameraCaptureSession? = null
    private var nv21Reader2: ImageReader? = null
    private var handlerThread2: HandlerThread? = null
    private var handler2: Handler? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sv1 = findViewById(R.id.sv_1)
        tv1 = findViewById(R.id.tv_fps1)
        sv2 = findViewById(R.id.sv_2)
        tv2 = findViewById(R.id.tv_fps2)

        if (PackageManager.PERMISSION_GRANTED == ActivityCompat
                .checkSelfPermission(this, Manifest.permission.CAMERA)
        ) {
            //已授权
            init()
        } else {
            //未授权
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CODE_CAMERA
            )
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

    private fun init() {
        //初始化相机
        initCamera()

        sv1.surfaceTextureListener = object : SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {
                Log.e(TAG, "sv1:onSurfaceTextureAvailable：$width---$height")
                openCamera1()
            }

            override fun onSurfaceTextureSizeChanged(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {

            }

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                return true
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {

            }

        }

        sv2.surfaceTextureListener = object : SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {
                Log.e(TAG, "sv2:onSurfaceTextureAvailable：$width---$height")
                openCamera2()
            }

            override fun onSurfaceTextureSizeChanged(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {

            }

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                return true
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {

            }

        }
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
            for (cameraId in cameraIdList) {
                val character = cameraManager?.getCameraCharacteristics(cameraId)
                val ranges =
                    character?.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES)
                Log.e(TAG, "camera${cameraId}支持的FPS范围${Arrays.toString(ranges)}")
                val map = character?.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
                val outputSizes = map?.getOutputSizes(SurfaceTexture::class.java)
                Log.e(TAG, "camera${cameraId}支持的预览尺寸${Arrays.toString(outputSizes)}")
            }

        }
        handlerThread1 = HandlerThread("handlerThread1")
        handlerThread1?.start()
        handler1 = Handler(Looper.getMainLooper())
        nv21Reader1 = ImageReader.newInstance(640, 640, ImageFormat.YUV_420_888, 2)
        nv21Reader1?.setOnImageAvailableListener({
            val image = it.acquireLatestImage()
            getFps1()
            image.close()
        }, handler1)

        handlerThread2 = HandlerThread("handlerThread2")
        handlerThread2?.start()
        handler2 = Handler(Looper.getMainLooper())
        nv21Reader2 = ImageReader.newInstance(320, 240, ImageFormat.YUV_420_888, 2)
        nv21Reader2?.setOnImageAvailableListener({
            val image = it.acquireLatestImage()
            getFps2()
            image.close()
        }, handler2)
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
            previewRequestBuilder1?.set(
                CaptureRequest.CONTROL_AF_MODE,
                CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
            )
            previewRequestBuilder1?.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, Range(30,30))
            val surfaceTexture1 = sv1.surfaceTexture
            surfaceTexture1?.setDefaultBufferSize(640, 640)
            val previewSurface1 = Surface(surfaceTexture1)

            previewRequestBuilder1?.addTarget(previewSurface1)
            nv21Reader1?.surface?.let { previewRequestBuilder1?.addTarget(it) }

            device1?.createCaptureSession(
                mutableListOf(previewSurface1, nv21Reader1?.surface),
                object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(session: CameraCaptureSession) {
                        try {
                            cameraSession1 = session

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
                },
                handler1
            )
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun createCameraPreviewSession2() {
        try {
            previewRequestBuilder2 = device2?.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            previewRequestBuilder2?.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, Range(30,30))
            val surfaceTexture2 = sv2.surfaceTexture
            surfaceTexture2?.setDefaultBufferSize(320, 240)
            val previewSurface2 = Surface(surfaceTexture2)
            previewRequestBuilder2?.addTarget(previewSurface2)
            nv21Reader2?.surface?.let { previewRequestBuilder2?.addTarget(it) }
            device2?.createCaptureSession(
                mutableListOf(previewSurface2,nv21Reader2?.surface), object : CameraCaptureSession.StateCallback() {
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

    private var mLastTime1: Long = 0
    private var fps1 = 0
    private var ifps1: Int = 0
    private fun getFps1() {
        val now = System.currentTimeMillis()
        ifps1++
        if (now > mLastTime1 + 1000) {
            mLastTime1 = now
            fps1 = ifps1
            tv1.text = "fps1:$fps1"
            ifps1 = 0
        }
    }

    private var mLastTime2: Long = 0
    private var fps2 = 0
    private var ifps2: Int = 0
    private fun getFps2() {
        val now = System.currentTimeMillis()
        ifps2++
        if (now > mLastTime2 + 1000) {
            mLastTime2 = now
            fps2 = ifps2
            tv2.text = "fps2:$fps2"
            ifps2 = 0
        }
    }

}