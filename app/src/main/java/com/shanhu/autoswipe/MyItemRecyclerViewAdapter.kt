package com.shanhu.autoswipe

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView

import com.shanhu.autoswipe.dummy.DummyContent.DummyItem
import com.shanhu.autoswipe.service.AutoGetPacketService


class MyItemRecyclerViewAdapter(
    private val values: List<DummyItem>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_home, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.itemName.text = item.content
        when(item.content) {
            "批量关注他人粉丝" -> {
                holder.itemName.isChecked = AutoGetPacketService.autoGetTheirFans
                holder.itemName.setOnCheckedChangeListener { _, isChecked ->
                    AutoGetPacketService.autoGetTheirFans = isChecked
                }
            }
            "批量转发聊天内容" -> {
                holder.itemName.isChecked = Consts.autoForwardMsgs
                holder.itemName.setOnCheckedChangeListener { _, isChecked ->
                    Consts.autoForwardMsgs = isChecked
                }
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: CheckBox = view.findViewById(R.id.itemName)
        val config: ImageView = view.findViewById(R.id.config)

//        override fun toString(): String {
//            return super.toString() + " '" + contentView.text + "'"
//        }
    }
}