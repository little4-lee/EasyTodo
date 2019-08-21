package com.lee.easytodo.presenter


import android.os.Handler
import android.os.Looper
import android.os.Message
import com.lee.easytodo.activity.MainActivity
import com.lee.easytodo.util.logD
import com.lee.easytodo.view.calendarview.OnDraggedAboveListener

/**
 * @package com.lee.easytodo.presenter
 * @author zhangjianfei
 * @create 2019-08-18.
 * @function
 */
class MainActivityPresenter : BasePersenter{

    private var handler: Handler? = null
    private var mainActivity: MainActivity? = null

    constructor(mainActivity: MainActivity) {
        this.mainActivity = mainActivity
    }

    override fun load() {
        mainActivity?.initView()
        mainActivity?.initAction()
    }


    fun onCreateMaterialByText() {
        mainActivity?.showTextInputUI()
    }

    fun onCreateMaterialByVoice() {
        mainActivity?.showVoiceInputUI()
    }

    fun onCreatedMaterialByText() {
        var material = mainActivity?.popMaterialText()
        logD("pop Material by text: $material")
        mainActivity?.dismissTextInputUI()
    }

    fun onCreatedMaterialByVoice() {
        var material = mainActivity?.popMaterialVoice()
        logD("pop Material by voice: $material")
        //暂不保存声音文件
//        var materialFilePath = mainActivity.pop
        mainActivity?.dismissVoiceInputUI()
    }

    fun loadTextInputView() {
        mainActivity?.initTextInputView()
        mainActivity?.initTextInputAction()
    }

    fun loadVoiceInputView() {
        mainActivity?.initVoiceInputView()
        mainActivity?.initVoiceInputAction()
    }

    fun sendEmptyMessageDelay (messageType: Int, delay: Long) {
        if (handler == null) {
            initHandler()
        }

        handler?.sendEmptyMessageDelayed(messageType, delay)
    }

    fun cancelInputUI () {
        mainActivity?.dismissVoiceInputUI()
        mainActivity?.dismissTextInputUI()
    }

    fun cancel(): Boolean {
        if (mainActivity?.isShowingInputView()!!) {
            cancelInputUI()
            return true
        } else {
            return false
        }
    }

    fun onScroll(direction: OnDraggedAboveListener.Direction) {
        when (direction) {
            OnDraggedAboveListener.Direction.LEFT -> mainActivity?.scrollLeft()
            OnDraggedAboveListener.Direction.RIGHT -> mainActivity?.scrollRight()
        }
    }

    private fun initHandler() {
        handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message?) {
                when (msg?.what) {
                    MESSAGE_TYPE_END_VOICE_INPUT -> {
                        onCreatedMaterialByVoice()
                    }
                    else -> {
                        logD("unknown message type: ${msg?.what} in MainActivityPresenter handler")
                    }
                }
            }
        }
    }


    override fun onDestroy() {
        handler = null
        mainActivity = null
    }


    companion object {
        const val message_key = "main_message_key"
        const val MESSAGE_TYPE_END_VOICE_INPUT  = 1
    }


}