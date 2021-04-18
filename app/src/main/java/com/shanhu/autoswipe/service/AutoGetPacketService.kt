package com.shanhu.autoswipe.service

import android.content.*
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.shanhu.autoswipe.MainApplication
import com.shanhu.autoswipe.util.ForegroundAppUtil
import com.shanhu.autoswipe.util.RunningTaskUtil
import com.shanhu.autoswipe.util.SpUtil

/**
 * @author donghailong
 */
class AutoGetPacketService : BaseAccessibilityService() {
    private val TIK_TOK = "com.zhiliaoapp.musically"
    private var swipeRandomTime: Int = 15
    private var isSwiped = true
    var startVideo = false
    private var taskUtil = RunningTaskUtil()
    var stringArray:Array<String> = arrayOf("so cute", "cute", "awe","....","wawaha","hhhhh","cool","so cool","awesome","sooo cute",
            "omg","oh my god","omgomg","oh my god","omgoh my god","oh no","oh...","...no",".yep.","ohmy")
    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
//            handler.removeCallbacksAndMessages(null);
        }
    }

    inner class ClickRunable : Runnable {
        var id: String? = null
        override fun run() {
            val node = findViewByViewId(id!!)
            node?.let {
                performViewClick(it)
            }
        }
    }
    inner class EditRunable : Runnable {
        var id: String? = null
        override fun run() {
            val node = findViewByViewId(id!!)
            if (node != null) {
                val randomIndex = getRandomNum(0, 19)
                pasteText(node, stringArray[randomIndex])
                performViewClick(node)
            }
        }
    }

    fun pasteText(node: AccessibilityNodeInfo?, text: String?) {
        val arguments = Bundle()
        arguments.putString(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text)
        node?.performAction(AccessibilityNodeInfoCompat.ACTION_SET_TEXT, arguments)
    }

    private val followBtRunable = ClickRunable()
    private val praiseBtRunable = ClickRunable()
    private val commentBtRunable = ClickRunable()
    private val commentEditBtRunable = EditRunable()
    private val commentSendBtRunable = ClickRunable()
    private val commentClsoeBtRunable = ClickRunable()
    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val tempMsg = Message.obtain()
            when (msg.what) {
                AUTO_RANDOM_PLAY -> {
//                    var componentName = ForegroundAppUtil.getForegroundActivityName()
//                    Log.e(TAG, "componentName $componentName")
//                    if (componentName?.equals(TIK_TOK) == true) {
//                        dispatchGesture(true, "小视频")
//                    }
                    dispatchGesture(true, "小视频")
                    isSwiped = true
                    if (SpUtil.getInstace().getBoolean(MainApplication.AUTO_PLAY, false)) {
                        val randomTime = getRandomNum(2, 5)
                        postDelayed({ swipeDelay() }, (randomTime*1000).toLong())
                    } else {
                        startVideo = false
                    }
                }
            }
            System.gc()
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        val pkg = event.packageName
        Log.i(TAG, "event pkg:" + pkg + " event type:" + event.eventType)
        if (TextUtils.isEmpty(pkg) || startVideo) return
        if (pkg == "com.shanhu.autoswipe") return
        when (pkg.toString()) {
            TIK_TOK -> {
                startVideo = SpUtil.getInstace().getBoolean(MainApplication.AUTO_PLAY, false)
                if (startVideo) {
                    swipeDelay()
                }
            }
        }
    }

    override fun onServiceDisconnected() {
        Log.e(TAG, "onServiceDisconnected")
    }

    private fun swipeDelay() {
        handler.removeCallbacksAndMessages(null)
        if (isSwiped) {
            isSwiped = false
            if (swipeNow()) {
                handler.sendEmptyMessage(AUTO_RANDOM_PLAY)
            } else {
                swipeRandomTime = SpUtil.getInstace().getString(MainApplication.TIME_SET, "15").toInt()
                swipeRandomTime = getRandomNum(swipeRandomTime-2, swipeRandomTime + 6)
                Log.e(TAG, "swipeRandomTime: $swipeRandomTime")
                followPraiseComment()
                handler.sendEmptyMessageDelayed(AUTO_RANDOM_PLAY, (swipeRandomTime * 1000).toLong())
            }
        }
    }

    private fun swipeNow(): Boolean {
        var ret = true
        val textview = findViewByViewId("com.zhiliaoapp.musically:id/adc")
        val content = textview?.text
        Log.e(TAG, "content str: $content")
        if (content.isNullOrEmpty()) return ret
        val keywords = SpUtil.getInstace().getString(MainApplication.KEYWORDS_SET, "")
        val keyList = keywords?.split(" ")
        if (keyList!= null) {
            val all = SpUtil.getInstace().getBoolean(MainApplication.INCLUDE_ALL, false)
            loop@ for (keyword in keyList) {
                if (all) {
                    if (content.contains(keyword,true)) {
                        ret = false
                    } else {
                        ret = true
                        break@loop
                    }
                } else {
                    if (content.contains(keyword,true)) {
                        ret = false
                        break@loop
                    }
                }
            }
        }
        return ret
    }

    private fun followPraiseComment() {
        var followDelay = 0.0
        val followId = SpUtil.getInstace().getString(MainApplication.FOLLOW_KEY, MainApplication.DEFAULT_FOLLOW_ID)
        if (followId != null) {
            handler.removeCallbacks(followBtRunable)
            val randomFollow = getRandomNum(0, 10)
            Log.e(TAG, "randomFollow == $randomFollow")
            val followRate = SpUtil.getInstace().getString(MainApplication.KEY_FOLLOW, "8").toInt()
            if (randomFollow > followRate) {
                followBtRunable.id = followId
                followDelay = getRandomDouble(1.00, 4.00)
                Log.e(TAG, "followDelay == $followDelay")
                handler.postDelayed(followBtRunable, (followDelay * 1000).toLong())
            }
        }
        var praiseDelay = 0.0
        val praiseId = SpUtil.getInstace().getString(MainApplication.PRAISE_KEY, MainApplication.DEFAULT_PRAISE_ID)
        if (praiseId != null) {
            handler.removeCallbacks(praiseBtRunable)
            val randomPraise = getRandomNum(0, 10)
            Log.e(TAG, "randomPraise == $randomPraise")
            val niceRate = SpUtil.getInstace().getString(MainApplication.KEY_NICE, "4").toInt()
            if (randomPraise > niceRate) {
                praiseBtRunable.id = praiseId
                praiseDelay = getRandomDouble(1.00, 4.00)
                Log.e(TAG, "praiseDelay == $praiseDelay")
                handler.postDelayed(praiseBtRunable, (praiseDelay * 1000).toLong())
            }
        }
        val commentId = SpUtil.getInstace().getString(MainApplication.COMMENT_KEY, MainApplication.DEFAULT_COMMENT_ID)
        if (commentId != null) {
            handler.removeCallbacks(commentBtRunable)
            handler.removeCallbacks(commentEditBtRunable)
            handler.removeCallbacks(commentSendBtRunable)
            handler.removeCallbacks(commentClsoeBtRunable)
            val randomComment = getRandomNum(0, 10)
            Log.e(TAG, "randomComment == $randomComment")
            val commentRate = SpUtil.getInstace().getString(MainApplication.KEY_COMMENT, "7").toInt()
            if (randomComment > commentRate) {
                val actionDelay = getRandomDouble(1.00, 3.00)
                val totoalDelay = followDelay+praiseDelay+actionDelay
                if (swipeRandomTime- totoalDelay> 7) {
                    Log.e(TAG, "commentDelay == $actionDelay totoalDelay == $totoalDelay")
                    commentBtRunable.id = commentId
                    handler.postDelayed(commentBtRunable, (totoalDelay * 1000).toLong())
                    val commentEditBtId = SpUtil.getInstace().getString(MainApplication.COMMENT_EDIT_KEY, MainApplication.DEFAULT_EDIT_ID)
                    commentEditBtRunable.id = commentEditBtId
                    val randomDelay1 = getRandomDouble(2.90, 3.50)
                    handler.postDelayed(commentEditBtRunable, ((totoalDelay+randomDelay1) * 1000).toLong())

                    val commentSendBtId = SpUtil.getInstace().getString(MainApplication.COMMENT_SEND_KEY, MainApplication.DEFAULT_SEND_ID)
                    commentSendBtRunable.id = commentSendBtId
                    val randomDelay2 = getRandomDouble(4.00, 4.50)
                    handler.postDelayed(commentSendBtRunable, ((totoalDelay+randomDelay2) * 1000).toLong())

                    val commentCloseBtId = SpUtil.getInstace().getString(MainApplication.COMMENT_CLOSE_KEY, MainApplication.COMMENT_CLOSE_ID)
                    commentClsoeBtRunable.id = commentCloseBtId
                    val randomDelay3 = getRandomDouble(5.00, 5.50)
                    handler.postDelayed(commentClsoeBtRunable, ((totoalDelay+randomDelay3) * 1000).toLong())
                }
            }
        }
    }

    override fun onInterrupt() {}

    companion object {
        private const val TAG = "AutoGetPacketService"
        const val AUTO_RANDOM_PLAY = 902
    }
}