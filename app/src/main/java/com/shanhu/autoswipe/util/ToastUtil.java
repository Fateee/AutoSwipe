package com.shanhu.autoswipe.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.shanhu.autoswipe.BuildConfig;
import com.shanhu.autoswipe.MainApplication;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

import static android.util.TypedValue.COMPLEX_UNIT_SP;


public class ToastUtil {
    private static Toast toast;//实现不管我们触发多少次Toast调用，都只会持续一次Toast显示的时长

    /**
     * 短时间显示Toast【居下】
     *
     * @param msg 显示的内容-字符串
     */
    public static void showShortToast(String msg) {
        try {
            if (MainApplication.mAppContext != null && !TextUtils.isEmpty(msg)) {
                if (toast == null) {
                    toast = Toast.makeText(MainApplication.mAppContext, msg, Toast.LENGTH_SHORT);
                    LinearLayout layout = (LinearLayout) toast.getView();
                    TextView tv = (TextView) layout.getChildAt(0);
                    tv.setTextSize(COMPLEX_UNIT_SP,19);
                } else {
                    toast.setText(msg);
                }
                //1、setGravity方法必须放到这里，否则会出现toast始终按照第一次显示的位置进行显示（比如第一次是在底部显示，那么即使设置setGravity在中间，也不管用）
                //2、虽然默认是在底部显示，但是，因为这个工具类实现了中间显示，所以需要还原，还原方式如下：
                toast.setGravity(Gravity.BOTTOM, 0, dip2px(MainApplication.mAppContext, 140));
                toast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Toast getToast(String msg) {
        try {
            if (MainApplication.mAppContext != null && !TextUtils.isEmpty(msg)) {
                if (toast == null) {
                    toast = Toast.makeText(MainApplication.mAppContext, msg, Toast.LENGTH_SHORT);
                    LinearLayout layout = (LinearLayout) toast.getView();
                    TextView tv = (TextView) layout.getChildAt(0);
                    tv.setTextSize(COMPLEX_UNIT_SP,19);
                } else {
                    toast.setText(msg);
                }
                //1、setGravity方法必须放到这里，否则会出现toast始终按照第一次显示的位置进行显示（比如第一次是在底部显示，那么即使设置setGravity在中间，也不管用）
                //2、虽然默认是在底部显示，但是，因为这个工具类实现了中间显示，所以需要还原，还原方式如下：
                toast.setGravity(Gravity.BOTTOM, 0, dip2px(MainApplication.mAppContext, 140));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toast;
    }

    /**
     * 短时间显示Toast【居中】
     *
     * @param msg 显示的内容-字符串
     */
    public static void showShortToastCenter(String msg) {
        if (MainApplication.mAppContext != null) {
            if (toast == null) {
                toast = Toast.makeText(MainApplication.mAppContext, msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * 短时间显示Toast【居上】
     *
     * @param msg 显示的内容-字符串
     */
    public static void showShortToastTop(String msg) {
        if (MainApplication.mAppContext != null) {
            if (toast == null) {
                toast = Toast.makeText(MainApplication.mAppContext, msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
        }
    }

    /**
     * 长时间显示Toast【居下】
     *
     * @param msg 显示的内容-字符串
     */
    public static void showLongToast(String msg) {
        if (MainApplication.mAppContext != null) {
            if (toast == null) {
                toast = Toast.makeText(MainApplication.mAppContext, msg, Toast.LENGTH_LONG);
            } else {
                toast.setText(msg);
            }
            toast.setGravity(Gravity.BOTTOM, 0, dip2px(MainApplication.mAppContext, 64));
            toast.show();
        }
    }

    /**
     * 长时间显示Toast【居中】
     *
     * @param msg 显示的内容-字符串
     */
    public static void showLongToastCenter(String msg) {
        if (MainApplication.mAppContext != null) {
            if (toast == null) {
                toast = Toast.makeText(MainApplication.mAppContext, msg, Toast.LENGTH_LONG);
            } else {
                toast.setText(msg);
            }
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * 长时间显示Toast【居上】
     *
     * @param msg 显示的内容-字符串
     */
    public static void showLongToastTop(String msg) {
        if (MainApplication.mAppContext != null) {
            if (toast == null) {
                toast = Toast.makeText(MainApplication.mAppContext, msg, Toast.LENGTH_LONG);
            } else {
                toast.setText(msg);
            }
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
        }
    }

    /*=================================常用公共方法============================*/
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static void show(String msg) {
        try {
//            getToast(msg);
//            hook(toast);
//            toast.show();
            showShortToast(msg);
        } catch (Exception e) {
            e.printStackTrace();
//            showShortToast(msg);
        }
    }
    public static void showDebug(@NotNull String msg) {
        if (BuildConfig.DEBUG) {
            showShortToast(msg);
        }
    }

    private static Field sFieldTN;
    private static Field sFieldTNHandler;

    static {
        try {
            sFieldTN = Toast.class.getDeclaredField("mTN");
            sFieldTN.setAccessible(true);
            sFieldTNHandler = sFieldTN.getType().getDeclaredField("mHandler");
            sFieldTNHandler.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void hook(Toast toast) {
        try {
            Object tn = sFieldTN.get(toast);
            Handler preHandler = (Handler) sFieldTNHandler.get(tn);
            sFieldTNHandler.set(tn, new SafelyHandlerWrapper(preHandler));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class SafelyHandlerWrapper extends Handler {
        private final Handler impl;

        SafelyHandlerWrapper(Handler impl) {
            this.impl = impl;
        }

        @Override
        public void dispatchMessage(Message msg) {
            try {
                super.dispatchMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void handleMessage(Message msg) {
            try {
                impl.handleMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    public static void showToast(@StringRes int id) {
//        showToast(MainApplication.mAppContext.getResources().getString(id), Toast.LENGTH_SHORT);
//    }
//
//    public static void showToast(@StringRes int id, int duration) {
//        showToast(MainApplication.mAppContext.getResources().getString(id), duration);
//    }
//
//    public static void showToast(@NonNull String msg) {
//        showToast(msg, Toast.LENGTH_SHORT);
//    }
//
//    public static void showToast(@NonNull String msg, int duration) {
//        Toast toast = Toast.makeText(MainApplication.mAppContext, msg, duration);
//        // 在调用Toast.show()之前处理:
//        hook(toast);
//        toast.show();
//    }
}
