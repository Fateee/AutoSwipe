package com.shanhu.autoswipe

import android.app.Activity
import android.app.Application

class MainApplication : Application() {

    var topActivity: Activity? = null

    override fun onCreate() {
        super.onCreate()
        mAppContext = this
        registerActivityLifecycleCallbacks(DefaultActivityLifecycle())
    }

    companion object {

        @JvmField
        var AUTO_PLAY: String = "AUTO_PLAY"
        @JvmField
        var INCLUDE_ALL: String = "INCLUDE_ALL"


        @JvmField
        var PRAISE_KEY: String = "PRAISE_KEY"
        const val DEFAULT_PRAISE_ID: String = "com.zhiliaoapp.musically:id/aev"

        @JvmField
        var FOLLOW_KEY: String = "FOLLOW_KEY"
        const val DEFAULT_FOLLOW_ID: String = "com.zhiliaoapp.musically:id/axn"

        @JvmField
        var COMMENT_KEY: String = "COMMENT_KEY"
        const val DEFAULT_COMMENT_ID: String = "com.zhiliaoapp.musically:id/a52"

        var COMMENT_EDIT_KEY: String = "COMMENT_EDIT_KEY"
        const val DEFAULT_EDIT_ID: String = "com.zhiliaoapp.musically:id/a56"

        var COMMENT_SEND_KEY: String = "COMMENT_SEND_KEY"
        const val DEFAULT_SEND_ID: String = "com.zhiliaoapp.musically:id/a5r"

        var COMMENT_CLOSE_KEY: String = "COMMENT_CLOSE_KEY"
        const val COMMENT_CLOSE_ID: String = "com.zhiliaoapp.musically:id/n9"

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