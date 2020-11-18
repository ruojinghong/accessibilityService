package com.demacia.garen.utils;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import java.io.File;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LOG {
    private static final SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss");
    public static String logfile = null;
    public static OnLOGListener mListener;

    public interface OnLOGListener {
        void onLog(String str);
    }

    /* renamed from: e */
    public static void m10e(String str) {
        synchronized (LOG.class) {
            try {
                Log.e("wzt", "" + str);
                tryWriteToLogFile("" + str);
                if (mListener != null) {
                    mListener.onLog(str);
                }
            } catch (Throwable th) {
                Class<LOG> cls = LOG.class;
                throw th;
            }
        }
    }

    public static void toast(Context context, String str) {
        m11v("toast : " + str);
        if (SysUtils.isDebug()) {
            Toast.makeText(context, "" + str, Toast.LENGTH_SHORT).show();
        }
    }

    private static void tryWriteToLogFile(String str) {
        if (!TextUtils.isEmpty(logfile)) {
            Date date = new Date();
            String format2 = String.format("%s.%03d %04d-%04d : %s\r\n", new Object[]{format.format(date), Long.valueOf(date.getTime() % 1000), Long.valueOf(Looper.getMainLooper().getThread().getId()), Long.valueOf(Thread.currentThread().getId()), str});
            try {
                File file = new File(logfile);
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                randomAccessFile.seek(file.length());
                randomAccessFile.write(format2.getBytes(Charset.forName("UTF-8")));
                randomAccessFile.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: v */
    public static void m11v(String str) {
        synchronized (LOG.class) {
            try {
                Log.v("wzt", "" + str);
                tryWriteToLogFile("" + str);
                if (mListener != null) {
                    mListener.onLog(str);
                }
            } catch (Throwable th) {
                Class<LOG> cls = LOG.class;
                throw th;
            }
        }
    }
}
