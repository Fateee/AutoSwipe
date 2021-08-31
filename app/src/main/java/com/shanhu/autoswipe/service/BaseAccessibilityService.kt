package com.shanhu.autoswipe.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.accessibilityservice.GestureDescription
import android.accessibilityservice.GestureDescription.StrokeDescription
import android.app.ActivityManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Path
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import android.view.accessibility.AccessibilityNodeInfo
import com.shanhu.autoswipe.MainApplication
import java.util.*
import kotlin.math.abs

/**
 * @author donghailong
 */
open class BaseAccessibilityService : AccessibilityService(), IAccessbilityAction {
    /**
     *
     */
    private val mManager: AccessibilityManager? = null
    private val gestureCallback: GestureResultCallback? = null
    override fun onAccessibilityEvent(event: AccessibilityEvent) {}
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.e(TAG, "onStartCommand=")
        return START_STICKY
    }

    override fun onStart(intent: Intent, startId: Int) {
        Log.e(TAG, "onStart=")
        super.onStart(intent, startId)
    }

    override fun onServiceConnected() {
        Log.e(TAG, "onServiceConnected=")
        super.onServiceConnected()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy=")
    }

    override fun onInterrupt() {
        Log.e(TAG, "onInterrupt=")
        onServiceDisconnected()
    }

    open fun onServiceDisconnected() {}

    override fun performBack() {
        performGlobalAction(GLOBAL_ACTION_BACK)
    }

    override fun performScrollUp() {
        performGlobalAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
    }

    override fun performScrollDown() {
        performGlobalAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD)
    }

    var path = Path()
    var sd: StrokeDescription? = null
    var gesture: GestureDescription? = null
    var random = Random()

    @JvmOverloads
    fun dispatchGesture(up: Int, name: String = "") {
        scroll(up)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val randomTime = getRandomNum(400, 700)
            Log.e(TAG, "swipe now randomTime  $randomTime")
            sd = StrokeDescription(path, 0, randomTime.toLong())
            gesture = GestureDescription.Builder().addStroke(sd!!).build()
            //先横滑
            dispatchGesture(gesture!!, null, null)
            path.reset()
        } else {
            if (TextUtils.isEmpty(name)) return
            val accessibilityNodeInfo = rootInActiveWindow
            click(accessibilityNodeInfo, name)
        }
    }

    private fun scroll(direction: Int) {
        when(direction) {
            0 -> {
                scrollUp(true)
            }
            1 -> {
                scrollLeft(true)
            }
        }
    }

    private fun scrollUp(up: Boolean) {
        val randomX1 = getRandomDouble(0.33, 0.75)
        val randomY1 = getRandomDouble(0.79, 0.92)
        val randomX2 = getRandomDouble(0.35, 0.80)
        val randomY2 = getRandomDouble(0.05, 0.18)
        Log.e(TAG, "dispatchGesture  $randomX1  $randomY1  $randomX2 $randomY2")
        if (realWidth == 0 || realHeight == 0) {
            getPingMuSize(applicationContext)
        }
        val x1 = realWidth * randomX1
        val y1 = realHeight * randomY1
        val controlX = getRandomNum(100, 150) + x1
        val controlY = y1 - getRandomNum(100, 400)
        val x2 = realWidth * randomX2
        val y2 = realHeight * randomY2
        if (up) {
            path.moveTo(x1.toFloat(), y1.toFloat())
            path.quadTo(controlX.toFloat(), controlY.toFloat(), x2.toFloat(), y2.toFloat())
        } else {
            path.moveTo(x2.toFloat(), y2.toFloat())
            path.quadTo(controlX.toFloat(), controlY.toFloat(), x1.toFloat(), y1.toFloat())
        }

//        path.lineTo((float) x2, (float) y2);
        Log.e(TAG, "dispatchGesture x1 = $x1 y1 = $y1 controlx = $controlX controly = $controlY x2 = $x2 y2 = $y2")
    }

    private fun scrollLeft(up: Boolean) {
        val randomX1 = getRandomDouble(0.33, 0.75)
        val randomY1 = getRandomDouble(0.79, 0.92)
        val randomX2 = getRandomDouble(0.35, 0.80)
        val randomY2 = getRandomDouble(0.05, 0.18)
        Log.e(TAG, "dispatchGesture  $randomX1  $randomY1  $randomX2 $randomY2")
        if (realWidth == 0 || realHeight == 0) {
            getPingMuSize(applicationContext)
        }
        val x1 = realWidth * randomY1
        val y1 = realHeight * randomX1
        val controlX = getRandomNum(100, 150) + x1
        val controlY = y1 - getRandomNum(100, 150)
        val x2 = realWidth * randomY2
        val y2 = realHeight * randomX2
        if (up) {
            path.moveTo(x1.toFloat(), y1.toFloat())
            path.quadTo(controlX.toFloat(), controlY.toFloat(), x2.toFloat(), y2.toFloat())
        } else {
            path.moveTo(x2.toFloat(), y2.toFloat())
            path.quadTo(controlX.toFloat(), controlY.toFloat(), x1.toFloat(), y1.toFloat())
        }

//        path.lineTo((float) x2, (float) y2);
        Log.e(TAG, "dispatchGesture x1 = $x1 y1 = $y1 controlx = $controlX controly = $controlY x2 = $x2 y2 = $y2")
    }

    fun click(paramAccessibilityNodeInfo: AccessibilityNodeInfo?, name: String) {
        if (paramAccessibilityNodeInfo == null) return
        val text = paramAccessibilityNodeInfo.text
        if (!TextUtils.isEmpty(text)) {
            if (name.equals(text.toString(), ignoreCase = true)) {
                if (paramAccessibilityNodeInfo.isClickable) {
                    paramAccessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                } else {
                    val accessibilityNodeInfo1 = paramAccessibilityNodeInfo.parent
                    val accessibilityNodeInfo2 = accessibilityNodeInfo1.parent
                    Log.e(TAG, "--parent widget------------ " + accessibilityNodeInfo1.className + accessibilityNodeInfo1.isClickable)
                    Log.e(TAG, "--parent1 widget------------ " + accessibilityNodeInfo2.className + accessibilityNodeInfo2.isClickable)
                    if (accessibilityNodeInfo1.isClickable) {
                        Log.e(TAG, "-- widget------------" + paramAccessibilityNodeInfo.childCount)
                        accessibilityNodeInfo1.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    }
                }
                Log.e(TAG, "--text widget----------------------------" + paramAccessibilityNodeInfo.text)
                return
            }
        }
        for (b1 in 0 until paramAccessibilityNodeInfo.childCount) {
            if (paramAccessibilityNodeInfo.getChild(b1) != null) click(
                paramAccessibilityNodeInfo.getChild(
                    b1
                ), name
            )
        }
    }

    override fun performViewClick(nodeInfo: AccessibilityNodeInfo?) {
        var nodeInfo: AccessibilityNodeInfo? = nodeInfo ?: return
        while (nodeInfo != null) {
            if (nodeInfo.isClickable) {
                Log.e(TAG, "--is MainLooper-----" + (Looper.getMainLooper() == Looper.myLooper()))
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                break
            }
            nodeInfo = nodeInfo.parent
        }
    }

    fun performViewClickByRect(nodeInfo: AccessibilityNodeInfo?){
        if (nodeInfo != null) {
            val rectScreen = Rect()
            val path = Path()
            nodeInfo.getBoundsInScreen(rectScreen)
            Log.e(TAG, "adb点击位置: " + rectScreen.left + "," + rectScreen.top + "," + rectScreen.right + "," + rectScreen.bottom);

            val parentScreen = Rect()
            nodeInfo.getBoundsInParent(parentScreen)
            Log.e(TAG, "adb点击位置: " + parentScreen.left + "," + parentScreen.top + "," + parentScreen.right + "," + parentScreen.bottom);

//            var y = rectScreen.bottom
//            if (rectScreen.bottom < 0) {
//                y = abs(rectScreen.bottom)
//            }
//            path.moveTo(rectScreen.left.toFloat(), y.toFloat())
//            val builder = GestureDescription.Builder()
//            val gestureDescription = builder.addStroke(StrokeDescription(path,0,100)).build()
//            dispatchGesture(gestureDescription,object : GestureResultCallback() {
//                override fun onCompleted(gestureDescription: GestureDescription?) {
//                    super.onCompleted(gestureDescription)
//                    Log.e(TAG,"single click finish.....")
//                }
//
//                override fun onCancelled(gestureDescription: GestureDescription?) {
//                    super.onCancelled(gestureDescription)
//                    Log.e(TAG,"single click onCancelled.....")
//                }
//            },null)
        }
    }

//    fun mockDoubleClick(){
//        val path = Path().apply {
//            moveTo((realWidth/2).toFloat(), (realHeight/2).toFloat())
//        }
//        val secondPath = Path().apply {
//            moveTo((realWidth/2).toFloat(), (realHeight/2).toFloat())
//        }
////        val rightThenDownDrag = StrokeDescription(
////            path,
////            0L,
////            1,
////            true
////        ).apply {
////            continueStroke(secondPath, 1, 1, false)
////        }
//        val first = StrokeDescription(path,0,100,true)
//        val builder = GestureDescription.Builder()
//        val gestureDescription = builder.addStroke(first).build()
//        dispatchGesture(gestureDescription, object : GestureResultCallback() {
//            override fun onCompleted(gestureDescription: GestureDescription?) {
//                super.onCompleted(gestureDescription)
////                val path2 = Path()
////                path2.moveTo((realWidth/2).toFloat(), (realHeight/2).toFloat())
//                val builder2 = GestureDescription.Builder()
//                val gestureDescription2 = builder2.addStroke(StrokeDescription(secondPath,0,100)).build()
//                dispatchGesture(gestureDescription2,object : GestureResultCallback() {
//                    override fun onCompleted(gestureDescription: GestureDescription?) {
//                        super.onCompleted(gestureDescription)
//                        Log.e(TAG,"double click222 finish.....")
//                    }
//
//                    override fun onCancelled(gestureDescription: GestureDescription?) {
//                        super.onCancelled(gestureDescription)
//                        Log.e(TAG,"double click2222 onCancelled.....")
//                    }
//                },null)
//                Log.e(TAG,"double click finish.....")
//                path.close()
////                path2.close()
//            }
//
//            override fun onCancelled(gestureDescription: GestureDescription?) {
//                super.onCancelled(gestureDescription)
//                Log.e(TAG,"double click onCancelled.....")
//            }
//        },null)
//    }

    override fun findViewByText(text: String): AccessibilityNodeInfo? {
        return findViewByText(text, false)
    }

    override fun findViewByText(text: String, clickable: Boolean): AccessibilityNodeInfo? {
        val accessibilityNodeInfo = rootInActiveWindow ?: return null
        val nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByText(text)
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (nodeInfo in nodeInfoList) {
                if (nodeInfo != null && nodeInfo.isClickable == clickable) {
                    return nodeInfo
                }
            }
        }
        return null
    }

    override fun findViewByViewId(viewId: String): AccessibilityNodeInfo? {
        return findViewByViewId(viewId, true)
    }

    override fun findViewByViewId(viewId: String, clickable: Boolean): AccessibilityNodeInfo? {
        val accessibilityNodeInfo = rootInActiveWindow ?: return null
        val nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(viewId)
        if (MainApplication.CLOSE_ID?.equals(viewId,true) == true) {
            if (nodeInfoList.size == 2) {
                val nodeInfo = nodeInfoList[1]
                if (nodeInfo != null && nodeInfo.isClickable == clickable && nodeInfo.isVisibleToUser) {
                    return nodeInfo
                }
            }
        }
        if (nodeInfoList != null && nodeInfoList.isNotEmpty()) {
            for (nodeInfo in nodeInfoList) {
                if (nodeInfo != null && nodeInfo.isClickable == clickable && nodeInfo.isVisibleToUser) {
                    return nodeInfo
                }
            }
        }
        return null
    }

    fun findViewByViewIdNoClick(viewId: String?): AccessibilityNodeInfo? {
        val accessibilityNodeInfo = rootInActiveWindow ?: return null
        val nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(viewId)
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (nodeInfo in nodeInfoList) {
                if (nodeInfo != null) {
                    return nodeInfo
                }
            }
        }
        return null
    }

    fun findViewListByViewId(viewId: String?): List<AccessibilityNodeInfo>? {
        val accessibilityNodeInfo = rootInActiveWindow ?: return null
        return accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(viewId)
    }

    fun isButtonCheckedById(viewId: String?): Boolean {
        val rightRB = findViewByViewIdNoClick(viewId)
        return if (rightRB != null && rightRB.isChecked) {
            true
        } else {
            false
        }
    }

    fun isButtonCheckedByText(text: String): Boolean {
        val rightRB = findViewByRealText(text)
        return if (rightRB != null && (rightRB.isChecked || rightRB.isSelected)) {
            true
        } else {
            false
        }
    }

    fun findViewByRealText(text: String): AccessibilityNodeInfo? {
        val accessibilityNodeInfo = rootInActiveWindow ?: return null
        val nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByText(text)
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (nodeInfo in nodeInfoList) {
                if (nodeInfo != null && !TextUtils.isEmpty(nodeInfo.text) && text.equals(
                        nodeInfo.text.toString(),
                        ignoreCase = true
                    )
                ) {
                    return nodeInfo
                }
            }
        }
        return null
    }

    fun findViewByRealTextInList(text: String): AccessibilityNodeInfo? {
        val accessibilityNodeInfo = rootInActiveWindow ?: return null
        click(accessibilityNodeInfo, text)
        //        List<AccessibilityNodeInfo> nodeInfoList =
//                accessibilityNodeInfo.findAccessibilityNodeInfosByText(text);
//        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
//            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
//                if (nodeInfo != null && !TextUtils.isEmpty(nodeInfo.getText()) && text.equalsIgnoreCase(nodeInfo.getText().toString())) {
//                    return nodeInfo;
//                }
//            }
//        }
        return null
    }

    fun findViewByViewId(
        accessibilityNodeInfo: AccessibilityNodeInfo?,
        viewId: String?
    ): AccessibilityNodeInfo? {
        if (accessibilityNodeInfo == null) {
            return null
        }
        val nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(viewId)
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (nodeInfo in nodeInfoList) {
                if (nodeInfo != null) {
                    return nodeInfo
                }
            }
        }
        return null
    }

    override fun clickTextViewByText(text: String) {
        val accessibilityNodeInfo = rootInActiveWindow
        if (accessibilityNodeInfo == null) {
            Log.i(TAG, "accessibilityNodeInfo is null")
            return
        }
        val nodeInfoList = accessibilityNodeInfo
            .findAccessibilityNodeInfosByText(text)
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (nodeInfo in nodeInfoList) {
                if (nodeInfo != null) {
                    performViewClick(nodeInfo)
                    break
                }
            }
        }
    }

    override fun clickTextViewByViewId(viewId: String) {
        val accessibilityNodeInfo = rootInActiveWindow
        if (accessibilityNodeInfo == null) {
            Log.i(TAG, "accessibilityNodeInfo is null")
            return
        }
        val nodeInfoList = accessibilityNodeInfo
            .findAccessibilityNodeInfosByViewId(viewId)
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (nodeInfo in nodeInfoList) {
                if (nodeInfo != null) {
                    performViewClick(nodeInfo)
                    break
                }
            }
        }
    }

    fun getRoot(): AccessibilityNodeInfo? {
        return rootInActiveWindow
    }

    override fun performInputText(info: AccessibilityNodeInfo, text: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val arguments = Bundle()
            arguments.putCharSequence(
                AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                text
            )
            info.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", text)
            clipboard.setPrimaryClip(clip)
            info.performAction(AccessibilityNodeInfo.ACTION_FOCUS)
            info.performAction(AccessibilityNodeInfo.ACTION_PASTE)
        }
    }

    override fun checkAccessbilityEnabled(serviceName: String): Boolean {
        val accessibilityServices =
            mManager!!.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC)
        for (info in accessibilityServices) {
            if (info.id == serviceName) {
                return true
            }
        }
        return false
    }

    /**
     * 生成一个startNum 到 endNum之间的随机数(不包含endNum的随机数)
     *
     * @param startNum
     * @param endNum
     * @return
     */
    fun getRandomNum(startNum: Int, endNum: Int): Int {
        return if (endNum > startNum) {
            random.nextInt(endNum - startNum) + startNum
        } else 0
    }

    fun getRandomFloat(startNum: Int, endNum: Int): Int {
        return if (endNum > startNum) {
            random.nextInt(endNum - startNum) + startNum
        } else 0
    }

    val topActivity: String
        get() {
            var pkg = ""
            val am = getSystemService(ACTIVITY_SERVICE) as ActivityManager
            val cn = am.getRunningTasks(1)[0].topActivity
            pkg = cn!!.packageName
            Log.e("huyi", "pkg:$pkg")
            Log.e("huyi", "cls:" + cn.className)
            return pkg
        }

    companion object {
        private const val TAG = "BaseAccessibilityServic"
        fun getRandomDouble(min: Double, max: Double): Double {
//        double min = 0.1;//最小值
//        double max = 10;//总和
            val scl = 2 //小数最大位数
            val pow = Math.pow(10.0, scl.toDouble()).toInt() //指定小数位
            return Math.floor((Math.random() * (max - min) + min) * pow) / pow
        }

        var realWidth = 0
        var realHeight = 0
        fun getPingMuSize(mContext: Context): Float {
            val xdpi = mContext.resources.displayMetrics.xdpi
            val ydpi = mContext.resources.displayMetrics.ydpi
            val width = mContext.resources.displayMetrics.widthPixels
            val height = mContext.resources.displayMetrics.heightPixels
            realWidth = width
            realHeight = height
            // 这样可以计算屏幕的物理尺寸
            val width2 = width / xdpi * (width / xdpi)
            val height2 = height / ydpi * (width / xdpi)
            return Math.sqrt((width2 + height2).toDouble()).toFloat()
        }
    }
}