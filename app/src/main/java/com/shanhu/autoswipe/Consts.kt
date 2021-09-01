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
}