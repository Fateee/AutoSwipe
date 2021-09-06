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
import com.shanhu.autoswipe.util.CollectionUtils
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
                                val strValue = s?.toString() ?:""
                                when(key1) {
                                    "他人主页链接" -> {
                                        Consts.TARGET_PROFILE_LINKS_LIST = CollectionUtils.string2Array(strValue)
                                        Consts.KV?.encode(Consts.TARGET_PROFILE_LINKS,strValue)
                                    }
                                    "转发人主页" -> {
                                        Consts.FORWARD_PROFILE_LINKS_VALUE = strValue
                                        Consts.KV?.encode(Consts.FORWARD_PROFILE_LINKS_KEY,strValue)
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
                        holder.configTwo.editConfig.addTextChangedListener(object :TextWatcher{
                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                            }

                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            }

                            override fun afterTextChanged(s: Editable?) {
                                val strValue = s?.toString() ?:""
                                when(key1) {
                                    "关注数量" -> {
                                        AutoGetPacketService.TOTAL_FOLLOW_COUNT = s?.toString()?.toInt()?:100
                                        Consts.KV?.encode(Consts.TOTAL_FOLLOW_COUNT,AutoGetPacketService.TOTAL_FOLLOW_COUNT)
                                    }
                                    "转发总人数" -> {
                                        Consts.TOTAL_FORWARD_COUNT = s?.toString()?.toInt()?:100
                                        Consts.KV?.encode(Consts.TOTAL_FORWARD_COUNT_KEY,Consts.TOTAL_FORWARD_COUNT?:100)
                                    }
                                    "每次转发人数" -> {
                                        Consts.PER_FORWARD_COUNT = s?.toString()?.toInt()?:15
                                        Consts.KV?.encode(Consts.PER_FORWARD_COUNT_KEY,Consts.PER_FORWARD_COUNT?:15)
                                    }
                                    "间隔秒数" -> {
                                        Consts.PER_FORWARD_DELAY = s?.toString()?.toInt()?:30
                                        Consts.KV?.encode(Consts.PER_FORWARD_DELAY_KEY,Consts.PER_FORWARD_DELAY?:30)
                                    }
                                    "转发内容关键字" -> {
                                        Consts.FORWARD_WORD = strValue
                                        Consts.KV?.encode(Consts.FORWARD_WORD_KEY,strValue)
                                    }
                                    "附带留言" -> {
                                        Consts.FORWARD_WORD_TIP = strValue
                                        Consts.KV?.encode(Consts.FORWARD_WORD_TIP_KEY,strValue)
                                    }
                                }
                            }
                        })
                    }
                    2->{
                        holder.configThree.visibility = View.VISIBLE
                        holder.configThree.tip.text = key1
                        holder.configThree.editConfig.setText(value1)
                        holder.configThree.editConfig.addTextChangedListener(object :TextWatcher{
                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                            }

                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            }

                            override fun afterTextChanged(s: Editable?) {
                                val strValue = s?.toString() ?:""
                                when(key1) {
                                    "关注间隔秒数" -> {
                                        AutoGetPacketService.FOLLOW_DELAY = s?.toString()?.toInt()?:100
                                        Consts.KV?.encode(Consts.FOLLOW_DELAY,AutoGetPacketService.FOLLOW_DELAY)
                                    }
                                }
                            }
                        })
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
                    5->{
                        holder.configSix.visibility = View.VISIBLE
                        holder.configSix.tip.text = key1
                        holder.configSix.editConfig.setText(value1)
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
        val configSix: View = view.findViewById(R.id.configSix)

//        override fun toString(): String {
//            return super.toString() + " '" + contentView.text + "'"
//        }
    }
}