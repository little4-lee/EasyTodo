package com.lee.easytodo.lifecycle

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.lee.easytodo.util.logD

class EasyTodoFragmentCallbacks : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        logD("${f.javaClass}--$f--onFragmentAttached")
    }

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        logD("${javaClass}--$f--onFragmentCreated")
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
        logD("${javaClass}--$f--onFragmentDetached")
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        logD("${javaClass}--$f--onFragmentDestroyed")
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        logD("${javaClass}--$f--onFragmentPaused")
    }

    override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {
        logD("${javaClass}--$f--onFragmentPreAttached")
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        logD("${javaClass}--$f--onFragmentResumed")
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
        logD("${javaClass}--$f--onFragmentStarted")
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
        logD("${javaClass}--$f--onFragmentStopped")
    }

    override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
        logD("${javaClass}--$f--onFragmentViewCreated")
    }
}
