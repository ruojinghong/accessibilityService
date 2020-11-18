package com.demacia.garen.utils;

import android.content.Context;
import java.lang.Thread;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    /* JADX WARNING: Removed duplicated region for block: B:25:0x00b9 A[SYNTHETIC, Splitter:B:25:0x00b9] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00c4 A[SYNTHETIC, Splitter:B:34:0x00c4] */
    /* JADX WARNING: Removed duplicated region for block: B:48:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:18:0x00aa=Splitter:B:18:0x00aa, B:27:0x00bc=Splitter:B:27:0x00bc} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void handleException(Throwable r9) {
        /*
            r8 = this;
            r2 = 0
            if (r9 != 0) goto L_0x0004
        L_0x0003:
            return
        L_0x0004:
            java.io.StringWriter r1 = new java.io.StringWriter
            r1.<init>()
            java.io.PrintWriter r3 = new java.io.PrintWriter
            r3.<init>(r1)
            r9.printStackTrace(r3)
            java.lang.Throwable r0 = r9.getCause()
        L_0x0015:
            if (r0 == 0) goto L_0x001f
            r0.printStackTrace(r3)
            java.lang.Throwable r0 = r0.getCause()
            goto L_0x0015
        L_0x001f:
            r3.close()
            java.lang.String r0 = r1.toString()
            java.lang.String r1 = android.os.Environment.getExternalStorageState()
            java.lang.String r3 = "mounted"
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x0003
            android.content.Context r1 = r8.mContext     // Catch:{ NameNotFoundException -> 0x00af }
            android.content.pm.PackageManager r1 = r1.getPackageManager()     // Catch:{ NameNotFoundException -> 0x00af }
            android.content.Context r3 = r8.mContext     // Catch:{ NameNotFoundException -> 0x00af }
            java.lang.String r3 = r3.getPackageName()     // Catch:{ NameNotFoundException -> 0x00af }
            r4 = 0
            android.content.pm.PackageInfo r1 = r1.getPackageInfo(r3, r4)     // Catch:{ NameNotFoundException -> 0x00af }
            java.lang.String r3 = "%s_%s[%s][%s]_%s.txt"
            r4 = 5
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ NameNotFoundException -> 0x00af }
            r5 = 0
            java.text.SimpleDateFormat r6 = new java.text.SimpleDateFormat     // Catch:{ NameNotFoundException -> 0x00af }
            java.lang.String r7 = "yyyy-MM-dd-HH-mm-ss"
            r6.<init>(r7)     // Catch:{ NameNotFoundException -> 0x00af }
            java.util.Date r7 = new java.util.Date     // Catch:{ NameNotFoundException -> 0x00af }
            r7.<init>()     // Catch:{ NameNotFoundException -> 0x00af }
            java.lang.String r6 = r6.format(r7)     // Catch:{ NameNotFoundException -> 0x00af }
            r4[r5] = r6     // Catch:{ NameNotFoundException -> 0x00af }
            r5 = 1
            android.content.Context r6 = r8.mContext     // Catch:{ NameNotFoundException -> 0x00af }
            java.lang.String r6 = r6.getPackageName()     // Catch:{ NameNotFoundException -> 0x00af }
            r4[r5] = r6     // Catch:{ NameNotFoundException -> 0x00af }
            r5 = 2
            int r6 = r1.versionCode     // Catch:{ NameNotFoundException -> 0x00af }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ NameNotFoundException -> 0x00af }
            r4[r5] = r6     // Catch:{ NameNotFoundException -> 0x00af }
            r5 = 3
            java.lang.String r1 = r1.versionName     // Catch:{ NameNotFoundException -> 0x00af }
            r4[r5] = r1     // Catch:{ NameNotFoundException -> 0x00af }
            r1 = 4
            android.content.Context r5 = r8.mContext     // Catch:{ NameNotFoundException -> 0x00af }
            java.lang.String r5 = com.lisyx.tap.utils.SysUtils.getOnlyId(r5)     // Catch:{ NameNotFoundException -> 0x00af }
            r4[r1] = r5     // Catch:{ NameNotFoundException -> 0x00af }
            java.lang.String r3 = java.lang.String.format(r3, r4)     // Catch:{ NameNotFoundException -> 0x00af }
            java.io.File r4 = new java.io.File     // Catch:{ NameNotFoundException -> 0x00af }
            java.io.File r1 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ NameNotFoundException -> 0x00af }
            java.lang.String r5 = "crash"
            r4.<init>(r1, r5)     // Catch:{ NameNotFoundException -> 0x00af }
            boolean r1 = r4.exists()     // Catch:{ NameNotFoundException -> 0x00af }
            if (r1 != 0) goto L_0x0093
            r4.mkdirs()     // Catch:{ NameNotFoundException -> 0x00af }
        L_0x0093:
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00bd, all -> 0x00b5 }
            java.io.File r5 = new java.io.File     // Catch:{ Exception -> 0x00bd, all -> 0x00b5 }
            r5.<init>(r4, r3)     // Catch:{ Exception -> 0x00bd, all -> 0x00b5 }
            r1.<init>(r5)     // Catch:{ Exception -> 0x00bd, all -> 0x00b5 }
            byte[] r0 = r0.getBytes()     // Catch:{ Exception -> 0x00d3, all -> 0x00d5 }
            r1.write(r0)     // Catch:{ Exception -> 0x00d3, all -> 0x00d5 }
            r1.close()     // Catch:{ IOException -> 0x00a9 }
            goto L_0x0003
        L_0x00a9:
            r0 = move-exception
        L_0x00aa:
            r0.printStackTrace()     // Catch:{ NameNotFoundException -> 0x00af }
            goto L_0x0003
        L_0x00af:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0003
        L_0x00b5:
            r0 = move-exception
        L_0x00b6:
            r1 = r2
        L_0x00b7:
            if (r1 == 0) goto L_0x00bc
            r1.close()     // Catch:{ IOException -> 0x00cb }
        L_0x00bc:
            throw r0     // Catch:{ NameNotFoundException -> 0x00af }
        L_0x00bd:
            r0 = move-exception
            r1 = r2
        L_0x00bf:
            r0.printStackTrace()     // Catch:{ all -> 0x00d0 }
            if (r1 == 0) goto L_0x0003
            r1.close()     // Catch:{ IOException -> 0x00c9 }
            goto L_0x0003
        L_0x00c9:
            r0 = move-exception
            goto L_0x00aa
        L_0x00cb:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ NameNotFoundException -> 0x00af }
            goto L_0x00bc
        L_0x00d0:
            r0 = move-exception
            r2 = r1
            goto L_0x00b6
        L_0x00d3:
            r0 = move-exception
            goto L_0x00bf
        L_0x00d5:
            r0 = move-exception
            goto L_0x00b7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lisyx.tap.utils.CrashHandler.handleException(java.lang.Throwable):void");
    }

    public void init(Context context) {
        this.mContext = context.getApplicationContext();
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void uncaughtException(Thread thread, Throwable th) {
        handleException(th);
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.mDefaultHandler;
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, th);
        }
    }
}
