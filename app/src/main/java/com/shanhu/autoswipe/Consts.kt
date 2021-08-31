package com.shanhu.autoswipe

import com.tencent.mmkv.MMKV

object Consts {
    var KV: MMKV? = null
    val TOTAL_FOLLOW_COUNT = "TOTAL_FOLLOW_COUNT"
    val FOLLOW_DELAY = "FOLLOW_DELAY"
    val FOLLOWED_SET_KEY = "FOLLOWED_SET_KEY"
    var FOLLOWED_SET = HashSet<String?>()

}