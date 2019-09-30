package com.lee.easytodo.model

import android.os.SystemClock

/**
 * @com www.weicheche.cn
 * @package com.lee.easytodo.model
 * @author zhangjianfei
 * @create 2019-08-18.
 * @function
 */
class ScheduleModel {
    var id: String
    var content: String
    var timestamp: Long

    //关联Idea
    var idea: IdeaModel? = null

    var level: Level = Level.NOT_IMPORTANT_NOT_EMERGENCY
    var progress: Progress = Companion.Progress.PERCENT_0

    private constructor(id: String, content: String, timestamp: Long) {
        this.id = id
        this.content = content
        this.timestamp = timestamp
    }


    companion object {
        @JvmStatic
        fun newInstance(content: String, userId: String): ScheduleModel {
            var timestamp = SystemClock.currentThreadTimeMillis()
            var scheduleId = userId + timestamp
            return ScheduleModel(scheduleId, content, timestamp)
        }

        @JvmStatic
        fun newInstance(ideaModel: IdeaModel, userId: String): ScheduleModel {
            var timestamp = SystemClock.currentThreadTimeMillis()
            var scheduleId = userId + timestamp
            return ScheduleModel(scheduleId, ideaModel.content, timestamp)
        }

        enum class Level {
            IMPORTANT_EMERGENCY, IMPORTANT_NOT_EMERGENCY, NOT_IMPORTANT_EMERGENCY, NOT_IMPORTANT_NOT_EMERGENCY
        }

        enum class Progress {
            PERCENT_0, PERCENT_25, PERCENT_50, PERCENT_75, PERCENT_100
        }
    }
}