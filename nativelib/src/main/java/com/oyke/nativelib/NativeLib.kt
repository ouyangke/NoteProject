package com.oyke.nativelib

import java.lang.Exception

class NativeLib {

    val father:Father = Child()

    val number:Int = 1
    /**
     * A native method that is implemented by the 'nativelib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    external fun newJavaData(): Long

    external fun callFatherMethod(): String

    external fun changeNumber()

    external fun getNativeString():String

    external fun getNativeStingUTF():String

    external fun getStringLength(string: String):Int

    external fun getStringUTFLength(stringUTF: String):Int

    external fun testThrowException()

    fun throwException(){
        throw NullPointerException("空指针异常")
    }

    fun stringFromJava(): String {
        return "java:普通方法"
    }


    companion object {

        @JvmStatic
        external fun staticStringFromJNI(): String

        @JvmStatic
        fun staticStringFromJava(): String {
            return "java:静态方法"
        }

        // Used to load the 'nativelib' library on application startup.
        init {
            System.loadLibrary("nativelib")
        }
    }
}

open class Father {
    override fun toString(): String {
        return "父类方法被调用"
    }
}

class Child : Father() {
    override fun toString(): String {
        return "子类方法被调用"
    }
}