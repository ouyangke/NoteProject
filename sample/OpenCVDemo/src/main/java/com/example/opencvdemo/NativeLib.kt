package com.example.opencvdemo

class NativeLib {

    /**
     * A native method that is implemented by the 'opencvdemo' native library,
     * which is packaged with this application.
     */
    external fun bitmap2Grey(pixels: IntArray?, w: Int, h: Int): IntArray?

    companion object {
        // Used to load the 'opencvdemo' library on application startup.
        init {
            System.loadLibrary("opencvdemo")
        }
    }
}