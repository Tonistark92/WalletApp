package com.iscoding.android.util

import android.util.Log

object AppLogger {
    private const val TAG = "CACHE_APP"

    var isDebuggable = BuildConfig.DEBUG

    fun d(message: String) {
        if (isDebuggable) {
            Log.d(TAG, message)
        }
    }

    fun i(message: String) {
        if (isDebuggable) {
            Log.i(TAG, message)
        }
    }

    fun e(message: String, throwable: Throwable? = null) {
        if (isDebuggable) {
            Log.e(TAG, message, throwable)
        }
    }

    fun w(message: String) {
        if (isDebuggable) {
            Log.w(TAG, message)
        }
    }
}