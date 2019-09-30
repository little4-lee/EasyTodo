package com.lee.easytodo.util

import android.content.Context
import android.util.Log
import com.lee.easytodo.BuildConfig


//----------------------log--------------------------
var logInRelease = false
//const val LOG_TAG = "easy_todo_tag"
const val LOG_TAG = "lee_view"

fun logV(log: String) {
    if (BuildConfig.DEBUG || logInRelease) {
        Log.v(LOG_TAG, log)
    }
}

fun logV(tag: String, log: String) {
    if (BuildConfig.DEBUG || logInRelease) {
        Log.v(tag, log)
    }
}

fun logD(log: String) {
    if (BuildConfig.DEBUG || logInRelease) {
        Log.d(LOG_TAG, log)
    }
}

fun logD(tag: String, log: String) {
    if (BuildConfig.DEBUG || logInRelease) {
        Log.d(tag, log)
    }
}

fun logE(log: String) {
    if (BuildConfig.DEBUG || logInRelease) {
        Log.e(LOG_TAG, log)
    }
}

fun logE(tag: String, log: String) {
    if (BuildConfig.DEBUG || logInRelease) {
        Log.e(tag, log)
    }
}

fun logW(log: String) {
    if (BuildConfig.DEBUG || logInRelease) {
        Log.w(LOG_TAG, log)
    }
}

fun logW(tag: String, log: String) {
    if (BuildConfig.DEBUG || logInRelease) {
        Log.w(tag, log)
    }
}

//----------------------log--------------------------

//----------------------size--------------------------

fun dipToPx(context: Context, dpValue: Float): Float {
    val scale = context.resources.displayMetrics.density
    return dpValue * scale + 0.5f
}
//----------------------size--------------------------


//----------------------hash--------------------------

//----------------------hash--------------------------

