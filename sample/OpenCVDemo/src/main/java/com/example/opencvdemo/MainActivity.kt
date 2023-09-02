package com.example.opencvdemo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnOriginal = findViewById<Button>(R.id.btn_original)

        val btnGray = findViewById<Button>(R.id.btn_grey)
        val btnLighten = findViewById<Button>(R.id.btn_lighten)
        val btnSharpen = findViewById<Button>(R.id.btn_sharpen)

        val btnBlur = findViewById<Button>(R.id.btn_blur)
        val btnBlurGaussian = findViewById<Button>(R.id.btn_blur_gaussian)
        val btnBlurMedian = findViewById<Button>(R.id.btn_blur_median)
        val btnBlurBilateral = findViewById<Button>(R.id.btn_blur_bilateral)
        val btnFilter2D = findViewById<Button>(R.id.btn_filter2D)

        val btnErode = findViewById<Button>(R.id.btn_erode)
        val btnDilate = findViewById<Button>(R.id.btn_dilate)

        val ivPreview = findViewById<ImageView>(R.id.iv_preview)

        var bitmap = BitmapFactory.decodeResource(this.resources,R.drawable.pic_1)

        btnOriginal.setOnClickListener {
            bitmap = BitmapFactory.decodeResource(this.resources,R.drawable.pic_1)
            ivPreview.setImageBitmap(bitmap)
        }

        btnGray.setOnClickListener {
            bitmap = OpenCVUtils.greyBitmap(bitmap)
            ivPreview.setImageBitmap(bitmap)
        }
        btnLighten.setOnClickListener {
            bitmap = OpenCVUtils.lightenBitmap(bitmap)
            ivPreview.setImageBitmap(bitmap)
        }
        btnSharpen.setOnClickListener {
            bitmap = OpenCVUtils.sharpenBitmap(bitmap)
            ivPreview.setImageBitmap(bitmap)
        }

        btnBlur.setOnClickListener {
            bitmap = OpenCVUtils.blurBitmap(bitmap,20,1)
            ivPreview.setImageBitmap(bitmap)
        }
        btnBlurGaussian.setOnClickListener {
            bitmap = OpenCVUtils.blurBitmap(bitmap,20,2)
            ivPreview.setImageBitmap(bitmap)
        }
        btnBlurMedian.setOnClickListener {
            bitmap = OpenCVUtils.blurBitmap(bitmap,20,3)
            ivPreview.setImageBitmap(bitmap)
        }
        btnBlurBilateral.setOnClickListener {
            bitmap = OpenCVUtils.blurBitmap(bitmap,20,4)
            ivPreview.setImageBitmap(bitmap)
        }
        btnFilter2D.setOnClickListener {
            bitmap = OpenCVUtils.filter2DBitmap(bitmap,20)
            ivPreview.setImageBitmap(bitmap)
        }

        btnErode.setOnClickListener {
            bitmap = OpenCVUtils.erodeBitmap(bitmap,20)
            ivPreview.setImageBitmap(bitmap)
        }
        btnDilate.setOnClickListener {
            bitmap = OpenCVUtils.dilateBitmap(bitmap,20)
            ivPreview.setImageBitmap(bitmap)
        }

        ivPreview.setImageBitmap(bitmap)


    }

}