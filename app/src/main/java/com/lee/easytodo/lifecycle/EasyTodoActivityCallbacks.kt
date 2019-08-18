package com.lee.easytodo.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.lee.easytodo.util.logD

open class EasyTodoActivityCallbacks: Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        logD("${activity?.localClassName}--$activity--onCreate")
    }

    override fun onActivityStarted(activity: Activity?) {
        logD("${activity?.localClassName}--$activity--onStart")
    }

    override fun onActivityResumed(activity: Activity?) {
        logD("${activity?.localClassName}--$activity--onResume")
    }

    override fun onActivityPaused(activity: Activity?) {
        logD("${activity?.localClassName}--$activity--pause")
    }

    override fun onActivityStopped(activity: Activity?) {
        logD("${activity?.localClassName}--$activity--onStop")
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        logD("${activity?.localClassName}--$activity--onSaveInstanceState")
        logD(outState.toString())
    }

    override fun onActivityDestroyed(activity: Activity?) {
        logD("${activity?.localClassName}--$activity--onDestroy")
    }
}