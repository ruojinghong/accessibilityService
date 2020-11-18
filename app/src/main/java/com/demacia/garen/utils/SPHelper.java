package com.demacia.garen.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SPHelper {
    public static boolean getBoolean(Context context, String str, boolean z) {
        return context.getSharedPreferences(context.getPackageName(), 0).getBoolean(str, z);
    }

    public static float getFloat(Context context, String str, float f) {
        return context.getSharedPreferences(context.getPackageName(), 0).getFloat(str, f);
    }

    public static int getInt(Context context, String str, int i) {
        return context.getSharedPreferences(context.getPackageName(), 0).getInt(str, i);
    }

    public static long getLong(Context context, String str, long j) {
        return context.getSharedPreferences(context.getPackageName(), 0).getLong(str, j);
    }

    public static String getString(Context context, String str, String str2) {
        return context.getSharedPreferences(context.getPackageName(), 0).getString(str, str2);
    }

    public static void putBoolean(Context context, String str, boolean z) {
        SharedPreferences.Editor edit = context.getSharedPreferences(context.getPackageName(), 0).edit();
        edit.putBoolean(str, z);
        edit.apply();
    }

    public static void putFloat(Context context, String str, float f) {
        SharedPreferences.Editor edit = context.getSharedPreferences(context.getPackageName(), 0).edit();
        edit.putFloat(str, f);
        edit.apply();
    }

    public static void putInt(Context context, String str, int i) {
        SharedPreferences.Editor edit = context.getSharedPreferences(context.getPackageName(), 0).edit();
        edit.putInt(str, i);
        edit.apply();
    }

    public static void putLong(Context context, String str, long j) {
        SharedPreferences.Editor edit = context.getSharedPreferences(context.getPackageName(), 0).edit();
        edit.putLong(str, j);
        edit.apply();
    }

    public static void putString(Context context, String str, String str2) {
        SharedPreferences.Editor edit = context.getSharedPreferences(context.getPackageName(), 0).edit();
        edit.putString(str, str2);
        edit.apply();
    }

    public static void remove(Context context, String str) {
        SharedPreferences.Editor edit = context.getSharedPreferences(context.getPackageName(), 0).edit();
        edit.remove(str);
        edit.apply();
    }
}
