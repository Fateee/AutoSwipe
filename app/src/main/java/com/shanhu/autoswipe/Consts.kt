package com.shanhu.autoswipe

import com.tencent.mmkv.MMKV

object Consts {
    var TEMP_STR: String? = ""
    val LOG_ACTION: String = "LOG_ACTION"
    var KV: MMKV? = null
    val TOTAL_FOLLOW_COUNT = "TOTAL_FOLLOW_COUNT"
    val FOLLOW_DELAY = "FOLLOW_DELAY"
    val FOLLOWED_SET_KEY = "FOLLOWED_SET_KEY"
    var FOLLOWED_SET = HashSet<String?>()
    val TARGET_PROFILE_LINKS = "TARGET_PROFILE_LINKS"
//    var TARGET_PROFILE_LINKS_VALUE : String? = ""
    var TARGET_PROFILE_LINKS_LIST :Array<String>? = emptyArray()

    var FORWARD_PROFILE_LINKS_KEY = "FORWARD_PROFILE_LINKS_KEY"
    var FORWARD_PROFILE_LINKS_VALUE = "https://vm.tiktok.com/ZMRDC7DpP/"

    val TOTAL_FORWARD_COUNT_KEY = "TOTAL_FORWARD_COUNT_KEY"
    var TOTAL_FORWARD_COUNT : Int? = 15
    val PER_FORWARD_COUNT_KEY = "PER_FORWARD_COUNT_KEY"
    var PER_FORWARD_COUNT : Int? = 15
    val PER_FORWARD_DELAY_KEY = "PER_FORWARD_DELAY_KEY"
    var PER_FORWARD_DELAY : Int? = 30

    val FORWARD_WORD_KEY = "FORWARD_WORD_KEY"
    var FORWARD_WORD = "Dear"

    val FORWARD_WORD_TIP_KEY = "FORWARD_WORD_TIP_KEY"
    var FORWARD_WORD_TIP = ""

    var autoForwardMsgs = false
    var autoForwardMsgsJobFlag = -1 //-1 未开始 0点击开始 1已开始 2已结束
}