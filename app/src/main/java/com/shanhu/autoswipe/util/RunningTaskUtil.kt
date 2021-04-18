package com.shanhu.autoswipe.util

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.ComponentName
import android.content.Context
import android.util.Log
import com.shanhu.autoswipe.MainApplication
import java.util.*

class RunningTaskUtil {
    private val TAG = "RunningTaskUtil"

    private var mUsageStatsManager: UsageStatsManager = MainApplication.mAppContext?.applicationContext?.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    private var topComponentName: ComponentName? = null

    fun getTopRunningTasks(): ComponentName? {
        return getTopRunningTasksByEvent()
    }

    private fun getTopRunningTasksByEvent(): ComponentName? {
        val time = System.currentTimeMillis()
        val usageEvents: UsageEvents = mUsageStatsManager.queryEvents(time - 60 * 60 * 1000, time)
        var out: UsageEvents.Event
        val map: TreeMap<Long?, UsageEvents.Event?> = TreeMap()
        if (usageEvents != null) {
            while (usageEvents.hasNextEvent()) {
                out = UsageEvents.Event() //这里一定要初始化，不然getNextEvent会报空指针
                if (usageEvents.getNextEvent(out)) {
                    if (out != null) {
                        map[out.timeStamp] = out
                    } else {
                        Log.e(TAG, " out is NULL")
                    }
                } else {
                    Log.e(TAG, " usageEvents is unavailable")
                }
            }

            if (!map.isEmpty()) {
                //将keyset颠倒过来，让最近的排列在上面
                val keySets = map.navigableKeySet()
                val iterator: Iterator<*> = keySets.descendingIterator()
                while (iterator.hasNext()) {
                    val event = map[iterator.next()]
                    if (event!!.eventType == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                        //当遇到有app移动到前台时，就更新topComponentName
                        topComponentName = ComponentName(event.packageName, "")
                        break
                    }
                }
            }
        } else {
            Log.e(TAG, " usageEvents is null")
        }
        return topComponentName
    }
}