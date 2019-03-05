package yunnuo.baseframe.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hwk
 *         项目通用工具类
 */

@SuppressLint("SimpleDateFormat")
public class CommonUtil {
    public static final Pattern sDateTimePattern = Pattern.compile("(\\d4)(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})");
    public static long timeInterval = 0;

    /**
     * 得到设备屏幕的宽度
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static String getFormatTime(Long time) {
        StringBuffer str = new StringBuffer("订单有效时间: ");
        if (time / 60 != 0) {
            str.append(time / 60 + "分");
        }
        str.append(time % 60 + "秒");

        return str.toString();
    }

    /**
     * 得到设备屏幕的高度
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 得到设备的密度
     */
    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 把密度转换为像素
     */
    public static int dip2px(Context context, float dp) {
        final float scale = getScreenDensity(context);
        return (int) (dp * scale + 0.5);
    }

    public static float dip2pxF(Context context, float dp) {
        final float scale = getScreenDensity(context);
        return (dp * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param context （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param context （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 去除标题栏
     *
     * @param mActivity
     */
    public static void requestNotTitleBar(final Activity mActivity) {
        mActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 全屏
     *
     * @param mActivity
     */
    public static void requestFullscreen(final Activity mActivity) {
        final Window window = mActivity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        window.requestFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 动态设置ListView组建的高度
     */
    public static void setListViewHeightBasedOnChildren(ListView listView, Adapter adapter) {
        Adapter listAdapter = (Adapter) listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()));
        listView.setLayoutParams(params);
    }

    public static void setLvHeightBasedOnChildren(ListView listView, Adapter adapter, Context context, int padding) {
        Adapter listAdapter = (Adapter) listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += CommonUtil.dip2px(context, padding) + listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()));
        listView.setLayoutParams(params);
    }

    public static void setListViewHeightBasedOnChildrens(ListView listView, Adapter adapter, Context context, int itemHeight) {
        if (adapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += CommonUtil.dip2px(context, itemHeight) + listView.getDividerHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        listView.setLayoutParams(params);
    }

    /**
     * 动态设置股票策略的高度
     */
    public static boolean setLvPorStra(ListView listView, Adapter adapter, Map<Integer, Boolean> map) {
        boolean a1 = false;
        try {
            Adapter listAdapter = (Adapter) listView.getAdapter();
            if (listAdapter != null) {
                int totalHeight = 0;
                int size = listAdapter.getCount();
                for (int i = 0; i < size; i++) {
                    boolean a = map.get(i);
                    View listItem = listAdapter.getView(i, null, listView);
                    listItem.measure(0, 0);
                    if (!a)
                        totalHeight += listItem.getMeasuredHeight();
                    else
                        totalHeight += listItem.getMeasuredHeight() * 3;
                }
                ViewGroup.LayoutParams params = listView.getLayoutParams();
                params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()));
                listView.setLayoutParams(params);
                a1 = true;
            }
        } catch (Exception e) {
            a1 = false;
            e.printStackTrace();
        }
        return a1;
    }

    public static void setListViewHeightBasedOnChildren(ExpandableListView listView) {
        // ListAdapter listAdapter = listView.getAdapter();
        ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                View listItem = listAdapter.getGroupView(i, true, null, listView);
                // getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + ((listAdapter.getGroupCount()) * 100);
        listView.setLayoutParams(params);
    }

    /**
     * 以默认的格式获取当前日期的字符串表示形式。
     *
     * @return 当前日期的字符串表示形式（yyyyMMdd）。
     */
    public static String getTodayDate() {
        return getFormatedDateTime("yyyy-MM-dd");
    }

    /**
     * 以指定的格式获取当前日期和时间的字符串表示形式。
     *
     * @param pattern 指定的格式
     * @return 当前日期和时间的字符串表示形式。
     */
    public static String getFormatedDateTime(String pattern) {
        return getFormatedDateTime(pattern, System.currentTimeMillis());
    }

    /**
     * 获取今日的日期格式 20141208
     */
    public static String getStockToday() {
        return getFormatedDateTime("yyyyMMdd", System.currentTimeMillis());
    }

    /**
     * 将日期格式 长变短
     */
    public static String formatLoToSho(String time) {
        String ti = null;
        try {
            SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1 = sd1.parse(time);
            SimpleDateFormat sd2 = new SimpleDateFormat("yyyy-MM-dd");
            ti = sd2.format(d1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ti;
    }

    /**
     * 将日期格式 长变短
     */
    public static String formatLoToSho(String time, String paOld, String paNew) {
        String ti = null;
        try {
            SimpleDateFormat sd1 = new SimpleDateFormat(paOld);
            Date d1 = sd1.parse(time);
            SimpleDateFormat sd2 = new SimpleDateFormat(paNew);
            ti = sd2.format(d1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ti;
    }

    public static int differentDaysByMillisecond(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            Date date2 = format.parse(time);
            Date date = new Date();
            int days = (int) ((date2.getTime() - date.getTime()) / (1000*3600*24));
            return days+1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 将日期格式 长变短
     */
    public static String getformatTimePoint(String oldTime, String serviceTime, String patter) {
        String ti = " ";
        try {
            SimpleDateFormat sd1 = new SimpleDateFormat(patter);
            Date d1 = sd1.parse(oldTime);
            long t1 = d1.getTime();
            if (serviceTime != null) {
                Long tm = Long.valueOf(serviceTime);
                t1 += tm * 60000;
            }
            Date d2 = new Date(t1);
            ti = sd1.format(d2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ti;
    }

    /**
     * 以指定的格式获取指定时间的字符串表示形式。
     *
     * @param pattern  指定的格式
     * @param dateTime 指定的时间
     * @return 指定时间的字符串表示形式。
     */
    public static String getFormatedDateTime(String pattern, long dateTime) {
        // DateFormat.format(pattern, dateTime).toString();

        SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
        return sDateFormat.format(new Date(dateTime + timeInterval));
    }

    /**
     * 以默认的格式获取当前日期的字符串表示形式。
     *
     * @return 当前日期的字符串表示形式（yyyy-MM-dd HH:mm:ss）。
     */
    public static String getTodayDate19() {
        return getFormatedDateTime("yyyy-MM-dd HH:mm:ss");
    }

    public static String getTodayDate21() {
        return getFormatedDateTime("yyyy-MM-dd");
    }

    public static String getTodayDate22() {
        return getFormatedDateTime("yyyyMMdd");
    }

    /**
     * 以默认的格式获取当前日期的字符串表示形式。
     *
     * @return 当前日期的字符串表示形式（yyyy-MM-dd HH:mm:ss）。
     */
    public static String getTodayDate20() {
        return getFormatedDateTime("MM-dd HH:mm:ss");
    }

    /**
     * 将String 类型的时间转换成long类型
     *
     * @param date
     * @return
     */
    public static long getLongTimeByString(String patter, String date) {
        long l = 0;
        try {
            DateFormat df = new SimpleDateFormat(patter);
            Date dt = df.parse(date);
            l = dt.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    /**
     * 比较第
     *
     * @param oldTime
     * @param newTime
     * @return
     */
    public static boolean guoqi(String oldTime, String newTime) {
        boolean a = false;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = df.parse(oldTime);
            Date d2 = df.parse(newTime);
            long t1 = d1.getTime();
            long t2 = d2.getTime();
            if (t2 > t1)
                a = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }

    /**
     * 将String 类型的时间转换成long类型
     *
     * @param date
     * @return
     */
    public static Date getDateByString(String patter, String date) {
        Date dt = new Date();
        try {
            DateFormat df = new SimpleDateFormat(patter);
            dt = df.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    /**
     * 弹窗提示文字
     *
     * @param msg 信息
     * @param c   上下文
     */
    public static void showToast(final String msg, Context c) {
        Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 读取本地文件中JSON字符串
     *
     * @param fileName assets中文件名
     * @param c        上下文
     * @return
     */
    public static String getJson(String fileName, Context c) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(c.getAssets().open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 检测网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }

        return false;
    }

    /**
     * 检查网络状态
     *
     * @param contexts
     * @return true 可用 false 不可用
     */

    public static boolean isNetworkAvailable(Context contexts) {
        ConnectivityManager connectivityManager = (ConnectivityManager) contexts
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * 获取手机的IMEI串号
     */
    public static String getPhoneImei(Context ctx) {
        TelephonyManager telephonyManager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        return imei;
    }

    /**
     * 检测Sdcard是否存在
     *
     * @return
     */
    public static boolean isExitsSdcard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    public static String getTopActivity(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

        if (runningTaskInfos != null)
            return runningTaskInfos.get(0).topActivity.getClassName();
        else
            return "";
    }

    /**
     * 验证邮箱地址是否正确
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    /**
     * 验证手机号码
     *
     * @param mobiles
     * @return [0-9]{5,9}
     */
    public static boolean isMobileNO(String mobiles) {
        boolean flag = false;
        try {
//            Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
            Pattern p = Pattern.compile("^(1)\\d{10}$");

            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static boolean isNum(String number) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^[0-9]{5}$");
            Matcher m = p.matcher(number);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 将String 类型的double数据转换成格式化后的百分比数据
     *
     * @param doublevalue
     * @return
     */
    public static String formatDoublePer(String doublevalue) {
        String format = "";
        if (!TextUtils.isEmpty(doublevalue)) {
            try {
                format = new DecimalFormat("#.##%").format(Double.parseDouble(doublevalue));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return format;
    }

    /**
     * 将String 类型的double数据转换成格式化后的百分比数据
     *
     * @param doublevalue
     * @return
     */
    public static String formatDoublePerZero(String doublevalue) {
        String format = "";
        if (!TextUtils.isEmpty(doublevalue)) {
            try {
                double s = Double.parseDouble(doublevalue) / 100;
                format = new DecimalFormat("#.##%").format(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return format;
    }

    public static String formatDoublePerZeroSame(String doublevalue) {
        String format = "";
        if (!TextUtils.isEmpty(doublevalue)) {
            try {
                double s = Double.parseDouble(doublevalue);
                format = new DecimalFormat("#.##%").format(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return format;
    }

    /**
     * 将String 类型的double数据转换成格式化后的百分比数据
     *
     * @param floatValue 将0.0256转化成float类型的2.6
     * @return
     */
    public static float formatFloat(String floatValue) {
        float data = 0f;
        if (!TextUtils.isEmpty(floatValue)) {
            try {
                DecimalFormat df = new DecimalFormat("0.00");
                float data1 = Float.parseFloat(floatValue);
                data = Float.parseFloat(df.format(data1 * 100));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    /**
     * 将String类型的数据格式化 0.00补上
     */
    public static String formatStringData(String str) {
        String format = "";
        if (!TextUtils.isEmpty(str)) {
            try {
                float f = Float.parseFloat(str);
                DecimalFormat df = new DecimalFormat("0.00");
                format = df.format(f);
                if (format.contains(".")) {
                    int ind = format.indexOf(".");
                    if (format.substring(ind).length() < 2) {
                        format += "0";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return format;
    }

    /**
     * 将String类型的数据格式化 0.00补上
     */
    public static String formatStringPercentData100(String str) {
        String format = "";
        if (!TextUtils.isEmpty(str)) {
            try {
                double f = Double.parseDouble(str) * 100;
                DecimalFormat df = new DecimalFormat("0.00");
                format = df.format(f);
                if (format.contains(".")) {
                    int ind = format.indexOf(".");
                    if (format.substring(ind).length() < 2) {
                        format += "0";
                    }
                }
                format += "%";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return format;
    }

    /**
     * 将String类型的数据格式化 0.00补上
     */
    public static String formatStringPercentData(String str) {
        String format = "";
        if (!TextUtils.isEmpty(str)) {
            try {
                float f = Float.parseFloat(str);
                DecimalFormat df = new DecimalFormat("0.00");
                format = df.format(f);
                if (format.contains(".")) {
                    int ind = format.indexOf(".");
                    if (format.substring(ind).length() < 2) {
                        format += "0";
                    }
                }
                format += "%";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return format;
    }

    /**
     * 将String类型的数据格式化 *100 0.00补上
     */
    public static String formatStringPercentData1(String str) {
        String format = "";
        if (!TextUtils.isEmpty(str)) {
            try {
                float f = Float.parseFloat(str) * 100;
                DecimalFormat df = new DecimalFormat("0.00");
                format = df.format(f);
                if (format.contains(".")) {
                    int ind = format.indexOf(".");
                    if (format.substring(ind).length() < 2) {
                        format += "0";
                    }
                }
                format += "%";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return format;
    }

    public static float formatFloat(float floatValue) {
        float data = 0f;
        try {
            DecimalFormat df = new DecimalFormat("0.00");
            data = Float.parseFloat(df.format(floatValue * 100));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static float formatFloatSame(float floatValue) {
        float data = 0f;
        try {
            DecimalFormat df = new DecimalFormat("0.00");
            data = Float.parseFloat(df.format(floatValue));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String formatFloatSame1(float floatValue) {
        String data = "";
        try {
            DecimalFormat df = new DecimalFormat("0.00");
            data = df.format(floatValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static double formatFloatSame(double floatValue) {
        double data = 0f;
        try {
            DecimalFormat df = new DecimalFormat("0.00");
            data = Double.parseDouble(df.format(floatValue));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 将位数多的数据四舍五入 格式化
     *
     * @param data
     * @return
     */
    public static float fourFiveData(float data) {
        float f1 = 0;
        try {
            BigDecimal b = new BigDecimal(data);
            f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f1;
    }

    /**
     * 将位数多的数据四舍五入 格式化
     *
     * @param data
     * @return
     */
    public static double fourFiveData(double data) {
        float f1 = 0;
        try {
            BigDecimal b = new BigDecimal(data);
            f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f1;
    }

    /**
     * 将3个数字进行比较 按照升序排列
     */
    public static float[] getFloatMax(float a, float b, float c) {
        float[] result = new float[3];
        result[0] = a;
        result[1] = b;
        result[2] = c;
        Arrays.sort(result);
        return result;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView, int padding, int num) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1) + padding * num);
        listView.setLayoutParams(params);
    }

    public static String getOrderData(String date) {
        String da = "";
        try {
            if (date != null) {// orderDate
                String da1 = date.substring(0, 10);
                da = formatLoToSho(da1, "yyyy/MM/dd", "yyyyMMdd");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return da;
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

//    public static String getIdentity(Context context) {
////        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
////        String identity = preference.getString("identity", null);
//        String identity = SPUtils.getString(context, "uuid", null);
//        if (identity == null) {
//            identity = java.util.UUID.randomUUID().toString();
////            preference.edit().putString("identity", identity);
//            SPUtils.setString(context, "uuid", identity);
//        }
//        return identity;
//    }

    public static String getUUID() {
        return java.util.UUID.randomUUID().toString();
    }

    /**
     * 获取IMEI，设备唯一编号
     * @param context
     * @return
     */
//    public static String getIMEI(Context context){
//        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        return telephonyManager.getDeviceId();
//    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        int versionCode = 1;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getAndroidDisplayVersion(Context context) {
        String androidDisplay = null;
        androidDisplay = android.os.Build.DISPLAY;
        return androidDisplay;
    }

    /**
     * 实现文本复制功能 add by wangqianzhou
     *
     * @param content
     */
    public static void copyText(String content, Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    /**
     * 实现粘贴功能 add by wangqianzhou
     *
     * @param context
     * @return
     */
    public static String paste(Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return cmb.getText().toString().trim();
    }

    /**
     * 设置 属性 防止异常 UnsupportedOperationException
     *
     * @param view
     */
    public static void setButtons(View view) {
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    // SDK1.5以上支持

    /**
     * 获取手机型号
     */
    public static String getModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机生厂商
     */
    public static String getMANUFACTURER() {
        return android.os.Build.MANUFACTURER;
    }

    /**
     * 打卡软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    public static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }

    public static String generateTag() {
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = "%s";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName);
        return tag;
    }

    // 全局数组
    private final static String[] strDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    public static String getMD5Code(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            //md.digest()该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

    /**
     * 外置存储卡的路径
     *
     * @return
     */
    public static String getExternalStorePath() {
        if (isExistExternalStore()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }

    /**
     * 是否有外存卡
     *
     * @return
     */
    public static boolean isExistExternalStore() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取当前应用程序的版本号。
     */
    public static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 根据传入的uniqueName获取硬盘缓存的路径地址。
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 返回图片存放目录
     *
     * @return
     */
    public static File getImagePathName(String IMESSAGE_IMAGE) {
        File directory = new File(IMESSAGE_IMAGE);
        if (!directory.exists() && !directory.mkdirs()) {
            return null;
        }
        return directory;
    }

    public static String formatNum(int time) {
        return time < 10 ? "0" + time : String.valueOf(time);
    }

    public static String formatMillisecond(int millisecond) {
        String retMillisecondStr;

        if (millisecond > 99) {
            retMillisecondStr = String.valueOf(millisecond / 10);
        } else if (millisecond <= 9) {
            retMillisecondStr = "0" + millisecond;
        } else {
            retMillisecondStr = String.valueOf(millisecond);
        }

        return retMillisecondStr;
    }

    /**
     * 判断当前网络是不是wifi
     *
     * @param context
     * @return
     */
    public static boolean isWifi(Context context) {
        boolean isWifi = false;
        try {
            ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectMgr.getActiveNetworkInfo();
            if (info != null && (info.getType() == ConnectivityManager.TYPE_WIFI)) {
                //在判断之前一定要进行的非空判断，如果没有任何网络连接info ==null
                isWifi = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isWifi;
    }

    public static String getFormatDistance(String distance) {
        int dis;
        try {
            dis = Integer.valueOf(distance);
            if (dis < 1000) {
                return dis + "m";
            } else {
                return dis / 1000 + "." + (dis % 1000 + 5) / 10 + "km";
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    //获得当前应用包名
    public static synchronized String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }
}
