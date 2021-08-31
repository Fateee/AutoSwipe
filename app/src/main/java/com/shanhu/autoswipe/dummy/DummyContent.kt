package com.shanhu.autoswipe.dummy

import com.shanhu.autoswipe.Consts
import com.shanhu.autoswipe.R
import com.shanhu.autoswipe.service.AutoGetPacketService
import java.util.ArrayList

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 */
object DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<DummyItem> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableList<HashMap<String,LinkedHashMap<String,String>>> = ArrayList()

    private val COUNT = 25

    init {
//        // Add some sample items.
//        for (i in 1..COUNT) {
//            addItem(createDummyItem(i))
//        }
        addItem(DummyItem(false, "自动养号"))
        addItem(DummyItem(false, "自动发布"))
        addItem(DummyItem(AutoGetPacketService.autoGetTheirFans, "批量关注他人粉丝"))
        addItem(DummyItem(false, "批量回关粉丝"))
        addItem(DummyItem(false, "批量私信粉丝(需互关)"))
        addItem(DummyItem(false, "批量转发聊天内容"))

        val items = LinkedHashMap<String,String>()
        items["关注数量"] = Consts.KV?.decodeInt(Consts.TOTAL_FOLLOW_COUNT,AutoGetPacketService.TOTAL_FOLLOW_COUNT).toString()
        items["间隔秒数"] = Consts.KV?.decodeInt(Consts.FOLLOW_DELAY,AutoGetPacketService.FOLLOW_DELAY).toString()
        val maps = HashMap<String,LinkedHashMap<String,String>>()
        maps["批量关注他人粉丝"] = items
        ITEM_MAP.add(maps)
//        ITEM_MAP["my_follow_back"] = items

        val itemss = LinkedHashMap<String,String>()
        itemss["转发总人数"] = "20"
        itemss["每次转发人数"] = "15"
        itemss["间隔秒数"] = "30"
        itemss["转发内容关键字"] = "Answer"
        itemss["附带留言"] = ""
        val mapss = HashMap<String,LinkedHashMap<String,String>>()
        mapss["批量转发聊天内容"] = itemss
        ITEM_MAP.add(mapss)
//        ITEM_MAP["my_forward"] = itemss
    }

    private fun addItem(item: DummyItem) {
        ITEMS.add(item)
//        ITEM_MAP.put(item.id, item)
    }

//    private fun createDummyItem(position: Int): DummyItem {
//        return DummyItem(position.toString(), "Item " + position, makeDetails(position))
//    }

//    private fun makeDetails(position: Int): String {
//        val builder = StringBuilder()
//        builder.append("Details about Item: ").append(position)
//        for (i in 0..position - 1) {
//            builder.append("\nMore details information here.")
//        }
//        return builder.toString()
//    }

    /**
     * A dummy item representing a piece of content.
     */
    data class DummyItem(val checked: Boolean, val content: String ) {
        override fun toString(): String = content
    }
}