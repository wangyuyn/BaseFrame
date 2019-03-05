package yunnuo.baseframe.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Explain:  保存本地文件SharePregerences的工具类
 * Author: wy
 * Time: 2016/6/28 11:11
 */
public class SPUtils {
    private final static String CONFIG = "config";
    private static SharedPreferences sp;

    public static void setBoolean(Context context,String key,boolean defaultvalue) {
        if (sp == null) {
            sp = context.getSharedPreferences(CONFIG,Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key,defaultvalue).commit();
    }

    public static boolean getBoolean(Context context,String key,boolean defaultvalue) {
        if (sp == null) {
            sp = context.getSharedPreferences(CONFIG,Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key,defaultvalue);
    }

    public static void setString(Context context,String key,String defaultvalue) {
        if (sp == null) {
            sp = context.getSharedPreferences(CONFIG,Context.MODE_PRIVATE);
        }
        sp.edit().putString(key,defaultvalue).commit();
    }

    public static String getString(Context context,String key,String defaultvalue) {
        if (sp == null) {
            sp = context.getSharedPreferences(CONFIG,Context.MODE_PRIVATE);
        }
        return sp.getString(key,defaultvalue);
    }

    public static void setInt(Context context,String key,int defaultvalue) {
        if (sp == null) {
            sp = context.getSharedPreferences(CONFIG,Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key,defaultvalue).commit();

    }

    public static int getInt(Context context,String key,int defaultvalue) {
        if (sp == null) {
            sp = context.getSharedPreferences(CONFIG,Context.MODE_PRIVATE);
        }
        return sp.getInt(key,defaultvalue);
    }
}
