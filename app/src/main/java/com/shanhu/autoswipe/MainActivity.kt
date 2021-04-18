package com.shanhu.autoswipe

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SwitchCompat
import com.shanhu.autoswipe.util.SpUtil
import com.shanhu.autoswipe.view.SetDialog

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var timeDialog : SetDialog? = null
    var keywordDialog : SetDialog? = null
    var followDialog : SetDialog? = null
    var niceDialog : SetDialog? = null
    var commentDialog : SetDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timeDialog = SetDialog(this, 0)
        keywordDialog = SetDialog(this, 1)
        followDialog = SetDialog(this, 2)
        niceDialog = SetDialog(this, 3)
        commentDialog = SetDialog(this, 4)

        val bt: SwitchCompat = findViewById(R.id.auto_random_play)
        val check = SpUtil.getInstace().getBoolean(MainApplication.AUTO_PLAY, false)
        bt.isChecked = check
        bt.setOnCheckedChangeListener { _, checked ->
            SpUtil.getInstace().save(MainApplication.AUTO_PLAY, checked)
        }

        val allCheck: SwitchCompat = findViewById(R.id.include_all)
        val all = SpUtil.getInstace().getBoolean(MainApplication.INCLUDE_ALL, false)
        allCheck.isChecked = all
        allCheck.setOnCheckedChangeListener { _, checked ->
            SpUtil.getInstace().save(MainApplication.INCLUDE_ALL, checked)
        }

        findViewById<AppCompatButton>(R.id.openAccess).setOnClickListener(this);
        findViewById<AppCompatButton>(R.id.time).setOnClickListener(this);
        findViewById<AppCompatButton>(R.id.keywords).setOnClickListener(this);
        findViewById<AppCompatButton>(R.id.followRate).setOnClickListener(this);
        findViewById<AppCompatButton>(R.id.niceRate).setOnClickListener(this);
        findViewById<AppCompatButton>(R.id.commentRate).setOnClickListener(this);
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.openAccess -> openAccessSetting()
            R.id.time -> {
                timeDialog?.refreshView()
                timeDialog?.show()
            }
            R.id.keywords -> {
                keywordDialog?.refreshView()
                keywordDialog?.show()
            }
            R.id.followRate -> {
                followDialog?.refreshView()
                followDialog?.show()
            }
            R.id.niceRate -> {
                niceDialog?.refreshView()
                niceDialog?.show()
            }
            R.id.commentRate -> {
                commentDialog?.refreshView()
                commentDialog?.show()
            }
        }
    }

    /**
     * 打开辅助功能设置
     */
    fun openAccessSetting() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        val follow = SpUtil.getInstace().getString(MainApplication.KEY_FOLLOW, "8")
        val nice = SpUtil.getInstace().getString(MainApplication.KEY_NICE, "4")
        val comment = SpUtil.getInstace().getString(MainApplication.KEY_COMMENT, "7")
        val followText = findViewById<AppCompatButton>(R.id.followRate)
        followText.text = "关注比率($follow/10)"
        val nicetext = findViewById<AppCompatButton>(R.id.niceRate)
        nicetext.text = "点赞比率($nice/10)"
        val commentText = findViewById<AppCompatButton>(R.id.commentRate)
        commentText.text = "评论比率($comment/10)"
    }
}