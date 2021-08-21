package com.shanhu.autoswipe

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.shanhu.autoswipe.dummy.DummyContent.DummyItem
import kotlinx.android.synthetic.main.item_config.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 */
class MyItemRecyclerViewAdapter2(
    private val values: MutableList<LinkedHashMap<String,String>>
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
            val value = mutableEntry.value
            when(index) {
                0->{
                    holder.configOne.tip.text = key
                    holder.configOne.editConfig.setText(value)
                }
                1->{
                    holder.configTwo.tip.text = key
                    holder.configTwo.editConfig.setText(value)
                }
                2->{
                    holder.configThree.visibility = View.VISIBLE
                    holder.configThree.tip.text = key
                    holder.configThree.editConfig.setText(value)
                }
                3->{
                    holder.configFour.visibility = View.VISIBLE
                    holder.configFour.tip.text = key
                    holder.configFour.editConfig.setText(value)
                }
                4->{
                    holder.configFive.visibility = View.VISIBLE
                    holder.configFive.tip.text = key
                    holder.configFive.editConfig.setText(value)
                }
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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