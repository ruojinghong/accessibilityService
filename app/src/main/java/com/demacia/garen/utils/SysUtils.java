package com.demacia.garen.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
// import android.support.p000v4.app.ActivityCompat;
// import android.support.p000v4.content.FileProvider;
// import android.support.p000v4.p002os.EnvironmentCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.os.EnvironmentCompat;

import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.util.Locale;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.TELEPHONY_SERVICE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.content.pm.PackageManager.GET_ACTIVITIES;

public class SysUtils {
    private static String onlyId;

    public static String buildType() {
        return "release";
    }

    public static int dp2px(float f) {
        return (int) ((Resources.getSystem().getDisplayMetrics().density * f) + 0.5f);
    }

    public static String getAndroidId(Context context) {
        return Settings.System.getString(context.getContentResolver(), "android_id");
    }

    public static PackageInfo getApkPackageInfo(Context context, String str) {
        PackageInfo packageArchiveInfo = context.getPackageManager().getPackageArchiveInfo(str, GET_ACTIVITIES);
        if (packageArchiveInfo != null) {
            return packageArchiveInfo;
        }
        return null;
    }

    public static JSONObject getDeviceInfo(Context context) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ((ActivityManager) context.getSystemService(ACTIVITY_SERVICE)).getMemoryInfo(memoryInfo);
        JSONObject jSONObject = new JSONObject(true);
        jSONObject.put("screenWidth", (Object) Integer.valueOf(screenWidth(context)));
        jSONObject.put("screenHeight", (Object) Integer.valueOf(screenHeight(context)));
        jSONObject.put("px2dp", (Object) Integer.valueOf(px2dp((float) screenWidth(context))));
        jSONObject.put("px2sp", (Object) Integer.valueOf(px2sp((float) screenWidth(context))));
        jSONObject.put("totalSpace", (Object) Long.valueOf(Environment.getExternalStorageDirectory().getTotalSpace()));
        jSONObject.put("freeSpace", (Object) Long.valueOf(Environment.getExternalStorageDirectory().getFreeSpace()));
        jSONObject.put("totalMem", (Object) Long.valueOf(memoryInfo.totalMem));
        jSONObject.put("availMem", (Object) Long.valueOf(memoryInfo.availMem));
        jSONObject.put("BRAND", (Object) Build.BRAND);
        jSONObject.put("MODEL", (Object) Build.MODEL);
        jSONObject.put("DISPLAY", (Object) Build.DISPLAY);
        jSONObject.put("AndroidVersion", (Object) Build.VERSION.RELEASE);
        jSONObject.put("SDKVersion", (Object) Integer.valueOf(Build.VERSION.SDK_INT));
        jSONObject.put("Serial", (Object) Build.SERIAL);
        jSONObject.put("IMEI", (Object) getIMEI(context));
        jSONObject.put("AndroidId", (Object) getAndroidId(context));
        return jSONObject;
    }

    public static String getIMEI(Context context) {
        return ActivityCompat.checkSelfPermission(context, "android.permission.READ_PHONE_STATE") != 0 ? "" : ((TelephonyManager) context.getSystemService(TELEPHONY_SERVICE)).getDeviceId();
    }

    public static String getOnlyId(Context context) {
        if (TextUtils.isEmpty(onlyId)) {
            onlyId = getOnlyIdImpl(context);
        }
        return onlyId;
    }

    private static String getOnlyIdImpl(Context context) {
        if (!EnvironmentCompat.MEDIA_UNKNOWN.equals(Build.SERIAL)) {
            return ("D" + Build.SERIAL).toUpperCase().trim();
        } else if (!TextUtils.isEmpty(getIMEI(context))) {
            return ("I" + getIMEI(context)).toUpperCase().trim();
        } else {
            return ("A" + getAndroidId(context)).toUpperCase().trim();
        }
    }

    public static PackageInfo getPackageInfo(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static PackageInfo getPackageInfo(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static int getVersionCode(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo != null) {
            return packageInfo.versionCode;
        }
        return 0;
    }

    public static String getVersionName(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        return packageInfo != null ? packageInfo.versionName : "";
    }

    public static boolean isDebug() {
        return false;
    }

    public static boolean isHUAWEIP7L07() {
        return "HUAWEI P7-L07".equals(Build.MODEL);
    }

    public static boolean isHUAWEIVNSAL00() {
        return "HUAWEI VNS-AL00".equals(Build.MODEL);
    }

    public static boolean isMI4LTE() {
        return "MI 4LTE".equals(Build.MODEL);
    }

    public static void openFile(Context context, File file) {
        String lowerCase = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase(Locale.getDefault());
        Intent intent = new Intent();
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("android.intent.action.VIEW");
        if (Build.VERSION.SDK_INT >= 24) {
            Uri uriForFile = FileProvider.getUriForFile(context, context.getPackageName() + ".FileProvider", file);
            intent.addFlags(1);
            intent.setDataAndType(uriForFile, context.getContentResolver().getType(uriForFile));
        } else {
            intent.setDataAndType(Uri.fromFile(file), MimeTypeMap.getSingleton().getMimeTypeFromExtension(lowerCase));
        }
        context.startActivity(intent);
    }

    public static int px2dp(float f) {
        return (int) ((f / Resources.getSystem().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2sp(float f) {
        return (int) ((f / Resources.getSystem().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    public static int screenHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int screenWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int sp2px(float f) {
        return (int) ((Resources.getSystem().getDisplayMetrics().scaledDensity * f) + 0.5f);
    }
}
