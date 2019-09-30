package com.lee.easytodo.presenter

import android.view.View
import com.lee.easytodo.fragment.IdeaPoolFragment

/**
 * @com www.weicheche.cn
 * @package com.lee.easytodo.presenter
 * @author zhangjianfei
 * @create 2019-08-21.
 * @function
 */
class IdeaPoolFragmentPersenter : BaseFragmentPresenter {

    private val ideaPoolFragment: IdeaPoolFragment

    constructor(ideaPoolFragment: IdeaPoolFragment) {
        this.ideaPoolFragment = ideaPoolFragment
    }


    override fun load(root: View) {
        ideaPoolFragment.initView(root)
        ideaPoolFragment.initAction()
    }
}