package com.example.faultgenlib

class NativeLib {
    external fun genInvalidWrite()
    external fun genStackOverflow()
    external fun genSigAbort()

    companion object {
        init {
            System.loadLibrary("faultgenlib")
        }
    }
}
