package com.lee.easytodo.application

import android.app.Application
import com.lee.easytodo.lifecycle.EasyTodoActivityCallbacks

/**
 * @package com.lee.easytodo.application
 * @author zhangjianfei
 * @create 2019-08-18.
 * @function
 */
class App: Application(){

    private lateinit var activityCallbacks: ActivityLifecycleCallbacks
    override fun onCreate() {
        super.onCreate()

        initActivityLifeCycle()
        initFragmentLifeCycle()
    }

    private fun initFragmentLifeCycle() {

    }

    private fun initActivityLifeCycle() {
        activityCallbacks = EasyTodoActivityCallbacks()
        registerActivityLifecycleCallbacks(activityCallbacks)
    }

}