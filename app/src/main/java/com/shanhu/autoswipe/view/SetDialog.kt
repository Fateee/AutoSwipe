package com.shanhu.autoswipe.view

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import com.shanhu.autoswipe.MainApplication
import com.shanhu.autoswipe.R
import com.shanhu.autoswipe.util.SpUtil

/**
 * Created by huyi on 19/5/9.
 */
class SetDialog : Dialog {
    private var defaultValue = "18"
    private var mType = 0
    private var editTimeEt: EditText? = null
    private var editBtnIdEt: EditText? = null
    private var contentIdEt: EditText? = null
    private var editCommentEtId: EditText? = null
    private var editCommentSendId: EditText? = null
    private var editCommentCloseId: EditText? = null
    private var mContext: Context

    constructor(context: Context, type: Int = 0) : super(context, R.style.bottom_dialog) {
        mContext = context
        mType = type
        initView()
    }

    /**
     * 刷新亮度
     */
    fun refreshView() {
        var idValue = ""
        when(mType) {
            0 -> {
                defaultValue = SpUtil.getInstace().getString(MainApplication.TIME_SET, "18")
            }
            1 -> {
                defaultValue = SpUtil.getInstace().getString(MainApplication.KEYWORDS_SET, "")
            }
            2 -> {
                defaultValue = SpUtil.getInstace().getString(MainApplication.KEY_FOLLOW, "8")
                idValue = SpUtil.getInstace().getString(MainApplication.FOLLOW_KEY, MainApplication.DEFAULT_FOLLOW_ID)
            }
            3 -> {
                defaultValue = SpUtil.getInstace().getString(MainApplication.KEY_NICE, "4")
                idValue = SpUtil.getInstace().getString(MainApplication.PRAISE_KEY, MainApplication.DEFAULT_PRAISE_ID)
            }
            4 -> {
                defaultValue = SpUtil.getInstace().getString(MainApplication.KEY_COMMENT, "7")
                idValue = SpUtil.getInstace().getString(MainApplication.COMMENT_KEY, MainApplication.DEFAULT_COMMENT_ID)

                val contentId = SpUtil.getInstace().getString(MainApplication.CONTENT_KEY, MainApplication.DEFAULT_CONTENT_ID)
                val commentEditId = SpUtil.getInstace().getString(MainApplication.COMMENT_EDIT_KEY, MainApplication.DEFAULT_EDIT_ID)
                val commentSendId = SpUtil.getInstace().getString(MainApplication.COMMENT_SEND_KEY, MainApplication.DEFAULT_SEND_ID)
                val commentCloseId = SpUtil.getInstace().getString(MainApplication.COMMENT_CLOSE_KEY, MainApplication.COMMENT_CLOSE_ID)

                findViewById<View>(R.id.commentIds).visibility = View.VISIBLE

                contentIdEt?.setText(contentId)
                contentIdEt?.text?.length?.let { contentIdEt?.setSelection(it) }

                editCommentEtId?.setText(commentEditId)
                editCommentEtId?.text?.length?.let { editCommentEtId?.setSelection(it) }

                editCommentSendId?.setText(commentSendId)
                editCommentSendId?.text?.length?.let { editCommentSendId?.setSelection(it) }

                editCommentCloseId?.setText(commentCloseId)
                editCommentCloseId?.text?.length?.let { editCommentCloseId?.setSelection(it) }
            }
        }

        editTimeEt?.setText(defaultValue)
        editTimeEt?.text?.length?.let { editTimeEt?.setSelection(it) }
        editBtnIdEt?.setText(idValue)
        editBtnIdEt?.text?.length?.let { editBtnIdEt?.setSelection(it) }
    }

    private fun initView() {
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_bright, null)
        setContentView(view)
        val params = window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT
        window?.attributes = params
        window?.setGravity(Gravity.CENTER)

        editTimeEt = findViewById<View>(R.id.edit_time) as EditText
        editBtnIdEt = findViewById<View>(R.id.btn_id) as EditText
        contentIdEt = findViewById<View>(R.id.content_id) as EditText
        editCommentEtId = findViewById<View>(R.id.edit_id) as EditText
        editCommentSendId = findViewById<View>(R.id.send_id) as EditText
        editCommentCloseId = findViewById<View>(R.id.close_id) as EditText
        refreshView()
        findViewById<View>(R.id.exit_iv).setOnClickListener { dismiss() }
        findViewById<View>(R.id.confirm_tv).setOnClickListener {
            val value = editTimeEt?.text?.toString()
            val idValue = editBtnIdEt?.text?.toString()
            when(mType) {
                0 -> {
//                    LocalBroadcastManager.getInstance(context).sendBroadcast(Intent(MainApplication.RESET_TIME_ACTION))
                    SpUtil.getInstace().save(MainApplication.TIME_SET, value)
                }
                1 -> {
                    SpUtil.getInstace().save(MainApplication.KEYWORDS_SET, value)
                    MainApplication.KEYWORDS = value
                }
                2 -> {
                    SpUtil.getInstace().save(MainApplication.KEY_FOLLOW, value)
                    MainApplication.FOLLOW_RATE = value?.toInt()?:8
                    SpUtil.getInstace().save(MainApplication.FOLLOW_KEY, idValue)
                    MainApplication.FOLLOW_ID = idValue
                }
                3 -> {
                    SpUtil.getInstace().save(MainApplication.KEY_NICE, value)
                    MainApplication.NICE_RATE = value?.toInt()?:4
                    SpUtil.getInstace().save(MainApplication.PRAISE_KEY, idValue)
                    MainApplication.PRAISE_ID = idValue
                }
                4 -> {
                    SpUtil.getInstace().save(MainApplication.KEY_COMMENT, value)
                    MainApplication.COMMENT_RATE = value?.toInt()?:7
                    SpUtil.getInstace().save(MainApplication.COMMENT_KEY, idValue)
                    MainApplication.COMMENT_ID = idValue

                    val contentId = contentIdEt?.text?.toString()
                    val editCommentEtId = editCommentEtId?.text?.toString()
                    val editCommentSendId = editCommentSendId?.text?.toString()
                    val editCommentCloseId = editCommentCloseId?.text?.toString()

                    SpUtil.getInstace().save(MainApplication.CONTENT_KEY, contentId)
                    MainApplication.CONTENT_ID = contentId

                    SpUtil.getInstace().save(MainApplication.COMMENT_EDIT_KEY, editCommentEtId)
                    MainApplication.EDITEXT_ID = editCommentEtId

                    SpUtil.getInstace().save(MainApplication.COMMENT_SEND_KEY, editCommentSendId)
                    MainApplication.SEND_ID = editCommentSendId

                    SpUtil.getInstace().save(MainApplication.COMMENT_CLOSE_KEY, editCommentCloseId)
                    MainApplication.CLOSE_ID = editCommentCloseId
                }
            }
            dismiss()
        }
    }

    fun dp2px(context: Context, dp: Float): Int {
        return Math.ceil((context.resources.displayMetrics.density * dp).toDouble())
            .toInt()
    }
}