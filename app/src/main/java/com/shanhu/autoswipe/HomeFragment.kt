package com.shanhu.autoswipe

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shanhu.autoswipe.dummy.DummyContent
import com.shanhu.autoswipe.service.AutoGetPacketService
import com.shanhu.autoswipe.util.Jump2AppUtil

/**
 * A fragment representing a list of Items.
 */
class HomeFragment : Fragment() {

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
        view.findViewById<View>(R.id.start).setOnClickListener {
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

    /**
     * 打开辅助功能设置
     */
    fun openAccessSetting() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
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