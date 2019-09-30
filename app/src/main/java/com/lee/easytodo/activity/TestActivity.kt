package com.lee.easytodo.activity

import android.animation.ObjectAnimator
import android.os.Bundle
import com.lee.easytodo.R
import com.lee.easytodo.activity.base.BaseActivity
import com.lee.easytodo.view.animation.TextGeneratingView

class TestActivity : BaseActivity() {

    private lateinit var animateView: TextGeneratingView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_layout)

        animateView = findViewById(R.id.animateView)
    }

    override fun onResume() {
        super.onResume()

        ObjectAnimator.ofFloat(animateView, "progress", 0f, 100f).setDuration(1000).start()
    }
}
