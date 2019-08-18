package com.lee.easytodo.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lee.easytodo.R
import com.lee.easytodo.fragment.base.BaseFragment


/**
 * A fragment show all the ideas
 * recorded by user
 *
 */
class IdeaPoolFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_idea_pool_layout, container, false)
    }


}
