package com.lee.easytodo.util

import android.content.ClipData

/**
 * @package com.lee.easytodo.util
 * @author zhangjianfei
 * @create 2019-08-21.
 * @function
 */
object ClipDataWrapper {


    const val IDEA_NEW = "idea_new"
    const val IDEA = "idea"
    const val IDEA_REMOVE = "idea_remove"

    const val TODOO_NEW = "todo_new"
    const val TODOO = "todo"
    const val TODOO_REMOVE = "todo_remove"

    const val MATERIAL_TYPE_IDEA = 1
    const val MATERIAL_TYPE_TODO = 2

    @JvmStatic
    fun isAdd(label: String, type: Int): Boolean {
        when (type) {
            MATERIAL_TYPE_IDEA -> {
                when (label) {
                    IDEA_NEW     -> return true
                    IDEA         -> return false
                    IDEA_REMOVE  -> return false
                    TODOO_NEW    -> return false
                    TODOO        -> return true
                    TODOO_REMOVE -> return false
                }
            }
            MATERIAL_TYPE_TODO -> {
                when (label) {
                    IDEA_NEW     -> return false
                    IDEA         -> return true
                    IDEA_REMOVE  -> return false
                    TODOO_NEW    -> return true
                    TODOO        -> return false
                    TODOO_REMOVE -> return false
                }
            }
        }
        return false
    }

    @JvmStatic
    fun isRemove(label: String, type: Int): Boolean {
        when (type) {
            MATERIAL_TYPE_IDEA -> {
                when (label) {
                    IDEA_NEW     -> return false
                    IDEA         -> return false
                    IDEA_REMOVE  -> return true
                    TODOO_NEW    -> return false
                    TODOO        -> return false
                    TODOO_REMOVE -> return false
                }
            }
            MATERIAL_TYPE_TODO -> {
                when (label) {
                    IDEA_NEW     -> return false
                    IDEA         -> return false
                    IDEA_REMOVE  -> return false
                    TODOO_NEW    -> return false
                    TODOO        -> return false
                    TODOO_REMOVE -> return true
                }
            }
        }
        return false
    }

    @JvmStatic
    fun newClipData(type: Int, material: String): ClipData {
        var label = ""
        when (type) {
            MATERIAL_TYPE_IDEA -> label = IDEA_NEW
            MATERIAL_TYPE_TODO -> label = TODOO_NEW
        }
        return ClipData(label, arrayOf(""), ClipData.Item(material))
    }

    @JvmStatic
    fun dragClipData(type: Int, clipData: ClipData): ClipData {
        when (type) {
            MATERIAL_TYPE_IDEA -> return ClipData(IDEA, arrayOf(""), clipData.getItemAt(0))
            MATERIAL_TYPE_TODO -> return ClipData(TODOO, arrayOf(""), clipData.getItemAt(0))
            else               -> return clipData
        }
    }

}