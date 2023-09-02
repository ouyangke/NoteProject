package com.example.opencvdemo

import android.graphics.Bitmap

class OpenCVUtils {
    /**
     * openCV工具类
     */
    companion object {
        init {
            System.loadLibrary("opencv_utils")
        }

        external fun bitmap2Mat(bitmap: Bitmap)

        external fun greyBitmap(bitmap: Bitmap): Bitmap

        external fun lightenBitmap(bitmap: Bitmap): Bitmap

        external fun sharpenBitmap(bitmap: Bitmap): Bitmap

        external fun blurBitmap(bitmap: Bitmap, kernelLength: Int, type: Int): Bitmap

        external fun filter2DBitmap(bitmap: Bitmap, kernelSize: Int): Bitmap

        external fun erodeBitmap(bitmap: Bitmap, kernelSize: Int): Bitmap

        external fun dilateBitmap(bitmap: Bitmap, kernelSize: Int): Bitmap
    }
}