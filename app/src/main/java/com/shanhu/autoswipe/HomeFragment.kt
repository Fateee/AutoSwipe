package com.shanhu.autoswipe

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import com.lzf.easyfloat.EasyFloat
import com.lzf.easyfloat.enums.ShowPattern
import com.lzf.easyfloat.enums.SidePattern
import com.lzf.easyfloat.interfaces.OnPermissionResult
import com.lzf.easyfloat.permission.PermissionUtils
import com.shanhu.autoswipe.dummy.DummyContent
import com.shanhu.autoswipe.service.AutoGetPacketService
import com.shanhu.autoswipe.util.Jump2AppUtil

/**
 * A fragment representing a list of Items.
 */
class HomeFragment : Fragment() {

    var logTv: TextView? = null
    var scrollView: ScrollView? = null

    var logReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            addLogToTv(Consts.TEMP_STR)
        }
    }

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home_list, container, false)
        view.findViewById<View>(R.id.auto_random_play).setOnClickListener {
            openAccessSetting()
        }
        activity?.registerReceiver(logReceiver, IntentFilter(Consts.LOG_ACTION))
        view.findViewById<View>(R.id.start).setOnClickListener {
            context?.let {
                if (PermissionUtils.checkPermission(it)) {
                    showFloatTaskView(it,view)
                } else {
                    PermissionUtils.requestPermission(activity!!, object : OnPermissionResult{
                        override fun permissionResult(isOpen: Boolean) {
                            if (isOpen) {
                                showFloatTaskView(it,view)
                            }
                        }
                    })
                }
            }
            Jump2AppUtil.Jump2App(context,AutoGetPacketService.TIK_TOK)
        }
        val list = view.findViewById<RecyclerView>(R.id.list)
        // Set the adapter
        if (list is RecyclerView) {
            with(list) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyItemRecyclerViewAdapter(DummyContent.ITEMS)
            }
        }
        return view
    }

    private fun showFloatTaskView(context: Context, view: View?) {
        EasyFloat.with(context).setLayout(R.layout.view_auto_log).setShowPattern(ShowPattern.ALL_TIME)
            .setTag("ab").setLocation(80,80).show()
        view?.postDelayed({
            logTv = EasyFloat.getAppFloatView("ab")?.findViewById(R.id.logTv)
            scrollView = EasyFloat.getAppFloatView("ab")?.findViewById(R.id.scrollView)
            logTv?.text = "开始执行任务\n"
            if (AutoGetPacketService.autoGetTheirFans) {
                AutoGetPacketService.followedCount = 0
                AutoGetPacketService.autoGetTheirFansJobFlag = 0
            }
        },5000)
    }

    /**
     * 打开辅助功能设置
     */
    fun openAccessSetting() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun addLogToTv(str: String?) {
        logTv?.let {
            it.text = it.text?.toString()+str+"\n"
        }
        scrollView?.fullScroll(ScrollView.FOCUS_DOWN)
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.unregisterReceiver(logReceiver)
    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}