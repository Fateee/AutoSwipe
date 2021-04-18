package com.shanhu.autoswipe.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.shanhu.autoswipe.MainApplication;

public class SpUtil {
    private static final String SP= "diy_sp";

    private SpUtil() {
    }


    private static class SpManager {
        private static final SpUtil instace = new SpUtil();
    }

    private static SharedPreferences mSp = null;

    public static SpUtil getInstace() {
        if (mSp == null) {
            synchronized (SpUtil.class) {
                if (mSp == null) {
                    mSp = MainApplication.mAppContext.getSharedPreferences(SP, Context.MODE_PRIVATE);
                }
            }
        }
        return SpManager.instace;
    }

    /**
     * 保存数据
     *
     * @param key   键
     * @param value 值
     */
    public void save(String key, Object value) {

        if (value instanceof String) {
            mSp.edit().putString(key, (String) value).apply();
        } else if (value instanceof Boolean) {
            mSp.edit().putBoolean(key, (Boolean) value).apply();
        } else if (value instanceof Integer) {
            mSp.edit().putInt(key, (Integer) value).apply();
        }
    }

    // 读取String类型数据
    public String getString(String key, String defValue) {
        return mSp.getString(key, defValue);
    }

    // 读取boolean类型数据
    public boolean getBoolean(String key, boolean defValue) {
        return mSp.getBoolean(key, defValue);
    }

    // 读取int类型数据
    public int getInt(String key, int defValue) {
        return mSp.getInt(key, defValue);
    }

    public void remove(String key) {
        mSp.edit().remove(key).apply();
    }

    public void clear(String key) {
        mSp.edit().clear().apply();
    }


}
