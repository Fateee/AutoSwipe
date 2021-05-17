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
        when(mType) {
            0 -> {
                defaultValue = SpUtil.getInstace().getString(MainApplication.TIME_SET, "18")
            }
            1 -> {
                defaultValue = SpUtil.getInstace().getString(MainApplication.KEYWORDS_SET, "")
            }
            2 -> {
                defaultValue = SpUtil.getInstace().getString(MainApplication.KEY_FOLLOW, "8")
            }
            3 -> {
                defaultValue = SpUtil.getInstace().getString(MainApplication.KEY_NICE, "4")
            }
            4 -> {
                defaultValue = SpUtil.getInstace().getString(MainApplication.KEY_COMMENT, "7")
            }
        }

        editTimeEt?.setText(defaultValue)
        editTimeEt?.text?.length?.let { editTimeEt?.setSelection(it) }
    }

    private fun initView() {
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_bright, null)
        setContentView(view)
        val params = window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = dp2px(context, 280f)
        window?.attributes = params
        window?.setGravity(Gravity.CENTER)

        editTimeEt = findViewById<View>(R.id.edit_time) as EditText
        refreshView()
        findViewById<View>(R.id.exit_iv).setOnClickListener { dismiss() }
        findViewById<View>(R.id.confirm_tv).setOnClickListener {
            val value = editTimeEt?.text?.toString()
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
                }
                3 -> {
                    SpUtil.getInstace().save(MainApplication.KEY_NICE, value)
                    MainApplication.NICE_RATE = value?.toInt()?:4
                }
                4 -> {
                    SpUtil.getInstace().save(MainApplication.KEY_COMMENT, value)
                    MainApplication.COMMENT_RATE = value?.toInt()?:7
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