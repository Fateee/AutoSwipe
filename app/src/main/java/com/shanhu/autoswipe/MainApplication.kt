package com.shanhu.autoswipe

import android.app.Activity
import android.app.Application
import com.shanhu.autoswipe.util.SpUtil

class MainApplication : Application() {

    var topActivity: Activity? = null


    override fun onCreate() {
        super.onCreate()
        mAppContext = this
        registerActivityLifecycleCallbacks(DefaultActivityLifecycle())
        CONTENT_ID = DEFAULT_CONTENT_ID
        FOLLOW_ID = SpUtil.getInstace().getString(FOLLOW_KEY, DEFAULT_FOLLOW_ID)
        PRAISE_ID = SpUtil.getInstace().getString(PRAISE_KEY, DEFAULT_PRAISE_ID)
        COMMENT_ID = SpUtil.getInstace().getString(COMMENT_KEY, DEFAULT_COMMENT_ID)
        EDITEXT_ID = SpUtil.getInstace().getString(COMMENT_EDIT_KEY, DEFAULT_EDIT_ID)
        SEND_ID = SpUtil.getInstace().getString(COMMENT_SEND_KEY, DEFAULT_SEND_ID)
        CLOSE_ID = SpUtil.getInstace().getString(COMMENT_CLOSE_KEY, COMMENT_CLOSE_ID)

        COMMENT_RATE = SpUtil.getInstace().getString(KEY_COMMENT, "7").toInt()
        NICE_RATE = SpUtil.getInstace().getString(MainApplication.KEY_NICE, "4").toInt()
        FOLLOW_RATE = SpUtil.getInstace().getString(MainApplication.KEY_FOLLOW, "8").toInt()

        KEYWORDS = SpUtil.getInstace().getString(MainApplication.KEYWORDS_SET, "")
        INCLUDE_ALL_VALUE = SpUtil.getInstace().getBoolean(MainApplication.INCLUDE_ALL, false)
    }

    companion object {

        var CONTENT_ID: String? = null
        var FOLLOW_ID: String? = null
        var PRAISE_ID: String? = null
        var COMMENT_ID: String? = null
        var EDITEXT_ID: String? = null
        var SEND_ID: String? = null
        var CLOSE_ID: String? = null

        var COMMENT_RATE : Int = 7
        var NICE_RATE : Int = 4
        var FOLLOW_RATE : Int = 8

        var KEYWORDS: String? = ""
        var INCLUDE_ALL_VALUE : Boolean = false

        const val DEFAULT_CONTENT_ID: String = "com.zhiliaoapp.musically:id/adr"

        @JvmField
        var AUTO_PLAY: String = "AUTO_PLAY"
        @JvmField
        var INCLUDE_ALL: String = "INCLUDE_ALL"


        @JvmField
        var PRAISE_KEY: String = "PRAISE_KEY"
        const val DEFAULT_PRAISE_ID: String = "com.zhiliaoapp.musically:id/af_"

        @JvmField
        var FOLLOW_KEY: String = "FOLLOW_KEY"
        const val DEFAULT_FOLLOW_ID: String = "com.zhiliaoapp.musically:id/ay_"

        @JvmField
        var COMMENT_KEY: String = "COMMENT_KEY"
        const val DEFAULT_COMMENT_ID: String = "com.zhiliaoapp.musically:id/a5g"

        var COMMENT_EDIT_KEY: String = "COMMENT_EDIT_KEY"
        const val DEFAULT_EDIT_ID: String = "com.zhiliaoapp.musically:id/a5k"

        var COMMENT_SEND_KEY: String = "COMMENT_SEND_KEY"
        const val DEFAULT_SEND_ID: String = "com.zhiliaoapp.musically:id/a67"

        var COMMENT_CLOSE_KEY: String = "COMMENT_CLOSE_KEY"
        const val COMMENT_CLOSE_ID: String = "com.zhiliaoapp.musically:id/nf"

        const val RESET_TIME_ACTION: String = "RESET_TIME_ACTION"
        const val TIME_SET: String = "TIME_SET"
        const val KEYWORDS_SET: String = "KEYWORDS_SET"
        @JvmField
        var mAppContext: MainApplication? = null

        const val KEY_FOLLOW: String = "KEY_FOLLOW"
        const val KEY_NICE: String = "KEY_NICE"
        const val KEY_COMMENT: String = "KEY_COMMENT"
    }
}