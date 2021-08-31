package com.shanhu.autoswipe

import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.shanhu.autoswipe.dummy.DummyContent.DummyItem
import com.shanhu.autoswipe.service.AutoGetPacketService
import com.shanhu.autoswipe.util.SpUtil
import kotlinx.android.synthetic.main.item_config.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 */
class MyItemRecyclerViewAdapter2(
    private val values: MutableList<HashMap<String, LinkedHashMap<String, String>>>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter2.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_config, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        item.entries.forEachIndexed { index, mutableEntry ->
            val key = mutableEntry.key
            holder.title.text = key
            val value = mutableEntry.value as LinkedHashMap<String, String>

            value.entries.forEachIndexed { j, mutableEntrys ->
                val key1 = mutableEntrys.key
                val value1 = mutableEntrys.value
                when(j) {
                    0->{
                        holder.configOne.tip.text = key1
                        holder.configOne.editConfig.setText(value1)
                        holder.configOne.editConfig.addTextChangedListener(object :TextWatcher{
                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                            }

                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            }

                            override fun afterTextChanged(s: Editable?) {
                                when(key1) {
                                    "关注数量" -> {
                                        AutoGetPacketService.TOTAL_FOLLOW_COUNT = s?.toString()?.toInt()?:100
                                        Consts.KV?.encode(Consts.TOTAL_FOLLOW_COUNT,AutoGetPacketService.TOTAL_FOLLOW_COUNT)
                                    }
                                    "间隔秒数" -> {
                                        AutoGetPacketService.FOLLOW_DELAY = s?.toString()?.toInt()?:100
                                        Consts.KV?.encode(Consts.FOLLOW_DELAY,AutoGetPacketService.FOLLOW_DELAY)
                                    }
                                }
                            }
                        })
                    }
                    1->{
                        holder.configTwo.tip.text = key1
                        holder.configTwo.editConfig.setText(value1)
                    }
                    2->{
                        holder.configThree.visibility = View.VISIBLE
                        holder.configThree.tip.text = key1
                        holder.configThree.editConfig.setText(value1)
                    }
                    3->{
                        holder.configFour.visibility = View.VISIBLE
                        holder.configFour.tip.text = key1
                        holder.configFour.editConfig.setText(value1)
                    }
                    4->{
                        holder.configFive.visibility = View.VISIBLE
                        holder.configFive.tip.text = key1
                        holder.configFive.editConfig.setText(value1)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val configOne: View = view.findViewById(R.id.configOne)
        val configTwo: View = view.findViewById(R.id.configTwo)
        val configThree: View = view.findViewById(R.id.configThree)
        val configFour: View = view.findViewById(R.id.configFour)
        val configFive: View = view.findViewById(R.id.configFive)

//        override fun toString(): String {
//            return super.toString() + " '" + contentView.text + "'"
//        }
    }
}