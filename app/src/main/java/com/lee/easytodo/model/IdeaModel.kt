package com.lee.easytodo.model

import android.os.SystemClock

class IdeaModel{
    var id: String
    var content: String
    var timestamp: Long

    private constructor(id:String, content: String, timestamp: Long) {
        this.id = id
        this.content = content
        this.timestamp = timestamp
    }

    companion object {
        @JvmStatic
        fun newInstance(content: String, userId: String): IdeaModel {
            var timestamp = SystemClock.currentThreadTimeMillis()
            var ideaId = userId + timestamp
            return IdeaModel(ideaId, content, timestamp)
        }
    }

}