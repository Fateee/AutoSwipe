package com.shanhu.autoswipe

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.util.Log

/**
 * 默认的Activity生命周期监听器。自动维护一个Activity栈。
 */
class DefaultActivityLifecycle : ActivityLifecycleCallbacks {
    //判断app是否是前台
    //    static int startedActivityCount = 0;
    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        Log.i(TAG, "onActivityCreated " + MainApplication.mAppContext?.topActivity + " " + activity)
        //        ActivityStackHelper.add(activity);
        MainApplication.mAppContext?.topActivity = activity
    }

    override fun onActivityStarted(activity: Activity?) {
        Log.i(TAG, "onActivityResumed " + MainApplication.mAppContext?.topActivity + " " + activity)
        //        startedActivityCount++;
    }

    override fun onActivityResumed(activity: Activity?) {
        Log.i(TAG, "onActivityResumed " + MainApplication.mAppContext?.topActivity + " " + activity)
        MainApplication.mAppContext?.topActivity = activity
    }

    override fun onActivityPaused(activity: Activity?) {}
    override fun onActivityStopped(activity: Activity?) {
        Log.i(TAG, "onActivityStopped " + MainApplication.mAppContext?.topActivity + " " + activity)
        //        startedActivityCount--;
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}
    override fun onActivityDestroyed(activity: Activity?) {
        Log.i(TAG, "onActivityDestroyed " + MainApplication.mAppContext?.topActivity + " " + activity)
        //        ActivityStackHelper.pop(activity);
    }
    //    public static boolean isAppForeground(){
    //        return startedActivityCount > 0;
    //    }
    companion object {
        private const val TAG = "DefaultActivityLifecycl"
    }
}