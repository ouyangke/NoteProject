package com.oyke.cpp

class NativeLib {

    /**
     * A native method that is implemented by the 'cpp' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'cpp' library on application startup.
        init {
            System.loadLibrary("cpp")
        }
    }
}