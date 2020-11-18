package com.demacia.garen.utils;

import android.util.Base64;
import okhttp3.internal.ws.RealWebSocket;

public class CommonUtils {
    public static byte[] base64Decode(String str) {
        return Base64.decode(str, 0);
    }

    public static String base64Encode(byte[] bArr) {
        return Base64.encodeToString(bArr, 0);
    }

    public static String formatSize(double d) {
        return formatSize(d, RealWebSocket.DEFAULT_MINIMUM_DEFLATE_SIZE);
    }

    public static String formatSize(double d, long j) {
        double d2 = (double) j;
        if (d < d2) {
            return ((int) d) + " 字节";
        }
        long j2 = j * j;
        double d3 = (double) j2;
        if (d < d3) {
            String format = String.format("%.1f", new Object[]{Double.valueOf(d / d2)});
            if (format.endsWith(".0")) {
                format = format.substring(0, format.length() - 2);
            }
            return format + " KB";
        }
        double d4 = (double) (j2 * j);
        if (d < d4) {
            String format2 = String.format("%.1f", new Object[]{Double.valueOf(d / d3)});
            if (format2.endsWith(".0")) {
                format2 = format2.substring(0, format2.length() - 2);
            }
            return format2 + " MB";
        }
        String format3 = String.format("%.1f", new Object[]{Double.valueOf(d / d4)});
        if (format3.endsWith(".0")) {
            format3 = format3.substring(0, format3.length() - 2);
        }
        return format3 + " GB";
    }

    public static String getIndentStr(int i) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append("\t");
        }
        return sb.toString();
    }
}
