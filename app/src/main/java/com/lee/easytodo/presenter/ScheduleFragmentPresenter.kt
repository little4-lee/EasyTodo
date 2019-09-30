package com.lee.easytodo.presenter

import android.view.View
import com.lee.easytodo.fragment.ScheduleFragment

/**
 * @com www.weicheche.cn
 * @package com.lee.easytodo.presenter
 * @author zhangjianfei
 * @create 2019-08-21.
 * @function
 */
class ScheduleFragmentPresenter : BaseFragmentPresenter {

    private val scheduleFragment: ScheduleFragment

    constructor(scheduleFragment: ScheduleFragment) {
        this.scheduleFragment = scheduleFragment
    }

    override fun load(root: View) {
        scheduleFragment.initView(root)
        scheduleFragment.initAction()
    }

}