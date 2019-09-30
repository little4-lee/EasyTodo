package com.lee.easytodo.fragment


import android.content.ClipData
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lee.easytodo.R
import com.lee.easytodo.fragment.base.BaseFragment
import com.lee.easytodo.presenter.IdeaPoolFragmentPersenter
import com.lee.easytodo.util.DragActionInteractor
import com.lee.easytodo.util.logD
import com.lee.easytodo.view.drag.InteractDragEventRecyclerView


/**
 * A fragment show all the ideas
 * recorded by user
 *
 */
class IdeaPoolFragment : BaseFragment() {

    private lateinit var presenter: IdeaPoolFragmentPersenter
    private lateinit var recyclerViewIdeaPool: InteractDragEventRecyclerView

    override fun initView(root: View) {
        recyclerViewIdeaPool = root.findViewById(R.id.recyclerViewIdeaPool)
    }

    override fun onActivityInteraction(clipData: ClipData) {
        //TODO add to data set
        //TODO notify
        //TODO animation
    }

    override fun initAction() {
        recyclerViewIdeaPool.dragActionInteractor = DragActionInteractor.Companion.Builder()
            .isInteractStayEdgeLong(false)
            .isInteractDrop(true)
            .isInteractLocationChange(true)
            .onDragInteractListener(object : DragActionInteractor.OnDragActionListener {
                override fun onDrop(x: Float, y: Float, clipData: ClipData) {
                    logD("ideas drop: $clipData")
                }

                override fun onLocationChanged(x: Float, y: Float) {
                    logD("ideas location x:$x, y:$y")
                }

                override fun onStayEdgeLong(direction: DragActionInteractor.DIRECTION) {}

            })
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_idea_pool_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter = IdeaPoolFragmentPersenter(this)
        presenter.load(view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }


}
