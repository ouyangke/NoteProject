package com.oyke.jni

class NativeUtil {

    companion object{
        init {
            System.loadLibrary("native")
        }
    }

    external fun getName()

}