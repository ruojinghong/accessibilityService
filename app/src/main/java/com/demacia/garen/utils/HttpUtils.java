package com.demacia.garen.utils;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {
    private static OkHttpClient httpClient = null;
    /* access modifiers changed from: private */
    public static final List<DownloadInfo> mDownloadRunningList = new ArrayList();
    private static final ConcurrentLinkedQueue<DownloadInfo> mDownloadWaitQueue = new ConcurrentLinkedQueue<>();
    private static final int maxDownloadCount = 2;

    public static class DownloadInfo {
        /* access modifiers changed from: private */
        public File file;
        /* access modifiers changed from: private */
        public String httpUrl;
        /* access modifiers changed from: private */
        public OnDownloadListener listener;

        private DownloadInfo(String str, File file2, OnDownloadListener onDownloadListener) {
            this.httpUrl = str;
            this.file = file2;
            this.listener = onDownloadListener;
        }
    }

    public interface OnCallback {
        void onResponse(String str);
    }

    public interface OnDownloadListener {
        void onFail();

        void onProgress(int i);

        void onSuccess();

        void onWait();
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0079 A[SYNTHETIC, Splitter:B:21:0x0079] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x007e A[Catch:{ Exception -> 0x00f7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0083 A[Catch:{ Exception -> 0x00f7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0089  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00a4 A[SYNTHETIC, Splitter:B:34:0x00a4] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00a9 A[Catch:{ Exception -> 0x00b2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00ae A[Catch:{ Exception -> 0x00b2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00bf A[SYNTHETIC, Splitter:B:46:0x00bf] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00c4 A[Catch:{ Exception -> 0x00f2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00c9 A[Catch:{ Exception -> 0x00f2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00e9  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x00fa  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean doDownload(Context r8, String r9, File r10) {
        /*
            r5 = 0
            r1 = 0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "download : "
            r0.append(r2)
            r0.append(r9)
            java.lang.String r0 = r0.toString()
            com.lisyx.tap.utils.LOG.m11v(r0)
            r10.delete()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = r10.getAbsolutePath()
            r0.append(r2)
            java.lang.String r2 = ".dld"
            r0.append(r2)
            java.io.File r6 = new java.io.File
            java.lang.String r0 = r0.toString()
            r6.<init>(r0)
            r6.delete()
            okhttp3.OkHttpClient r0 = getHttpClient()     // Catch:{ Exception -> 0x00e4, all -> 0x00de }
            okhttp3.Request$Builder r2 = new okhttp3.Request$Builder     // Catch:{ Exception -> 0x00e4, all -> 0x00de }
            r2.<init>()     // Catch:{ Exception -> 0x00e4, all -> 0x00de }
            r2.url((java.lang.String) r9)     // Catch:{ Exception -> 0x00e4, all -> 0x00de }
            okhttp3.Request r2 = r2.build()     // Catch:{ Exception -> 0x00e4, all -> 0x00de }
            okhttp3.Call r0 = r0.newCall(r2)     // Catch:{ Exception -> 0x00e4, all -> 0x00de }
            okhttp3.Response r0 = r0.execute()     // Catch:{ Exception -> 0x00e4, all -> 0x00de }
            if (r0 == 0) goto L_0x00da
            boolean r2 = r0.isSuccessful()     // Catch:{ Exception -> 0x00d6, all -> 0x00d0 }
            if (r2 == 0) goto L_0x00da
            okhttp3.ResponseBody r2 = r0.body()     // Catch:{ Exception -> 0x00d6, all -> 0x00d0 }
            java.io.InputStream r2 = r2.byteStream()     // Catch:{ Exception -> 0x00d6, all -> 0x00d0 }
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00cd, all -> 0x00b9 }
            r4.<init>(r6)     // Catch:{ Exception -> 0x00cd, all -> 0x00b9 }
            r1 = 1024(0x400, float:1.435E-42)
            byte[] r1 = new byte[r1]     // Catch:{ Exception -> 0x0072 }
        L_0x0067:
            int r3 = r2.read(r1)     // Catch:{ Exception -> 0x0072 }
            if (r3 <= 0) goto L_0x00a1
            r7 = 0
            r4.write(r1, r7, r3)     // Catch:{ Exception -> 0x0072 }
            goto L_0x0067
        L_0x0072:
            r1 = move-exception
            r3 = r1
        L_0x0074:
            r3.printStackTrace()     // Catch:{ all -> 0x00ed }
            if (r4 == 0) goto L_0x007c
            r4.close()     // Catch:{ Exception -> 0x00f7 }
        L_0x007c:
            if (r2 == 0) goto L_0x0081
            r2.close()     // Catch:{ Exception -> 0x00f7 }
        L_0x0081:
            if (r0 == 0) goto L_0x00fa
            r0.close()     // Catch:{ Exception -> 0x00f7 }
            r3 = r5
        L_0x0087:
            if (r3 == 0) goto L_0x00e9
            r6.renameTo(r10)
        L_0x008c:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "success : "
            r0.append(r1)
            r0.append(r3)
            java.lang.String r0 = r0.toString()
            com.lisyx.tap.utils.LOG.m11v(r0)
            return r3
        L_0x00a1:
            r3 = 1
        L_0x00a2:
            if (r4 == 0) goto L_0x00a7
            r4.close()     // Catch:{ Exception -> 0x00b2 }
        L_0x00a7:
            if (r2 == 0) goto L_0x00ac
            r2.close()     // Catch:{ Exception -> 0x00b2 }
        L_0x00ac:
            if (r0 == 0) goto L_0x0087
            r0.close()     // Catch:{ Exception -> 0x00b2 }
            goto L_0x0087
        L_0x00b2:
            r0 = move-exception
            r1 = r3
        L_0x00b4:
            r0.printStackTrace()
            r3 = r1
            goto L_0x0087
        L_0x00b9:
            r3 = move-exception
            r5 = r0
            r4 = r1
            r6 = r2
        L_0x00bd:
            if (r4 == 0) goto L_0x00c2
            r4.close()     // Catch:{ Exception -> 0x00f2 }
        L_0x00c2:
            if (r6 == 0) goto L_0x00c7
            r6.close()     // Catch:{ Exception -> 0x00f2 }
        L_0x00c7:
            if (r5 == 0) goto L_0x00cc
            r5.close()     // Catch:{ Exception -> 0x00f2 }
        L_0x00cc:
            throw r3
        L_0x00cd:
            r3 = move-exception
            r4 = r1
            goto L_0x0074
        L_0x00d0:
            r2 = move-exception
            r5 = r0
            r4 = r1
            r3 = r2
            r6 = r1
            goto L_0x00bd
        L_0x00d6:
            r3 = move-exception
            r2 = r1
            r4 = r1
            goto L_0x0074
        L_0x00da:
            r3 = r5
            r4 = r1
            r2 = r1
            goto L_0x00a2
        L_0x00de:
            r0 = move-exception
            r5 = r1
            r4 = r1
            r3 = r0
            r6 = r1
            goto L_0x00bd
        L_0x00e4:
            r3 = move-exception
            r0 = r1
            r2 = r1
            r4 = r1
            goto L_0x0074
        L_0x00e9:
            r6.delete()
            goto L_0x008c
        L_0x00ed:
            r1 = move-exception
            r5 = r0
            r3 = r1
            r6 = r2
            goto L_0x00bd
        L_0x00f2:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x00cc
        L_0x00f7:
            r0 = move-exception
            r1 = r5
            goto L_0x00b4
        L_0x00fa:
            r3 = r5
            goto L_0x0087
        */
        throw new UnsupportedOperationException("Method not decompiled: HttpUtils.doDownload(android.content.Context, java.lang.String, java.io.File):boolean");
    }

    public static void doHttpGet(Context context, String str, final OnCallback onCallback1) {
        final Handler r1 = new Handler() {
            public void handleMessage(Message message) {
                OnCallback onCallback = onCallback1;
                if (onCallback != null) {
                    onCallback.onResponse((String) message.obj);
                }
            }
        };
        try {
            OkHttpClient httpClient2 = getHttpClient();
            Request.Builder builder = new Request.Builder();
            builder.url(str);
            httpClient2.newCall(builder.build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException iOException) {
                    r1.sendMessage(r1.obtainMessage(0, (Object) null));
                }

                public void onResponse(Call call, Response response) throws IOException {
                    if (response == null || !response.isSuccessful()) {
                        r1.sendMessage(r1.obtainMessage(0, (Object) null));
                        return;
                    }
                    r1.sendMessage(r1.obtainMessage(0, response.body().string()));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            r1.sendMessage(r1.obtainMessage(0, (Object) null));
        }
    }

    public static String doHttpPost(String str, String str2) {
        return doHttpPostImpl(str, RequestBody.create(MediaType.parse("application/json;charset=utf-8"), str2));
    }

    public static void doHttpPost(Context context, String str, String str2, OnCallback onCallback) {
        doHttpPostImpl(context, str, RequestBody.create(MediaType.parse("application/json;charset=utf-8"), str2), onCallback);
    }

    public static void doHttpPost(Context context, String str, Map<String, String> map, OnCallback onCallback) {
        if (map == null || map.size() <= 0) {
            doHttpPostImpl(context, str, (RequestBody) null, onCallback);
            return;
        }
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry next : map.entrySet()) {
            String str2 = (String) next.getKey();
            String str3 = (String) next.getValue();
            if (str3 == null) {
                str3 = "";
            }
            builder.add(str2, str3);
        }
        doHttpPostImpl(context, str, builder.build(), onCallback);
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0038 A[SYNTHETIC, Splitter:B:18:0x0038] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x004b A[SYNTHETIC, Splitter:B:30:0x004b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static String doHttpPostImpl(String r3, RequestBody r4) {
        /*
            r0 = 0
            okhttp3.OkHttpClient r1 = getHttpClient()     // Catch:{ Exception -> 0x0044, all -> 0x0041 }
            okhttp3.Request$Builder r2 = new okhttp3.Request$Builder     // Catch:{ Exception -> 0x0044, all -> 0x0041 }
            r2.<init>()     // Catch:{ Exception -> 0x0044, all -> 0x0041 }
            r2.url((java.lang.String) r3)     // Catch:{ Exception -> 0x0044, all -> 0x0041 }
            if (r4 == 0) goto L_0x0012
            r2.post(r4)     // Catch:{ Exception -> 0x0044, all -> 0x0041 }
        L_0x0012:
            okhttp3.Request r2 = r2.build()     // Catch:{ Exception -> 0x0044, all -> 0x0041 }
            okhttp3.Call r1 = r1.newCall(r2)     // Catch:{ Exception -> 0x0044, all -> 0x0041 }
            okhttp3.Response r2 = r1.execute()     // Catch:{ Exception -> 0x0044, all -> 0x0041 }
            if (r2 == 0) goto L_0x002e
            boolean r1 = r2.isSuccessful()     // Catch:{ Exception -> 0x0057, all -> 0x0034 }
            if (r1 == 0) goto L_0x002e
            okhttp3.ResponseBody r1 = r2.body()     // Catch:{ Exception -> 0x0057, all -> 0x0034 }
            java.lang.String r0 = r1.string()     // Catch:{ Exception -> 0x0057, all -> 0x0034 }
        L_0x002e:
            if (r2 == 0) goto L_0x0033
            r2.close()     // Catch:{ Exception -> 0x003c }
        L_0x0033:
            return r0
        L_0x0034:
            r0 = move-exception
            r1 = r0
        L_0x0036:
            if (r2 == 0) goto L_0x003b
            r2.close()     // Catch:{ Exception -> 0x0052 }
        L_0x003b:
            throw r1
        L_0x003c:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0033
        L_0x0041:
            r1 = move-exception
            r2 = r0
            goto L_0x0036
        L_0x0044:
            r1 = move-exception
            r2 = r0
        L_0x0046:
            r1.printStackTrace()     // Catch:{ all -> 0x004f }
            if (r2 == 0) goto L_0x0033
            r2.close()     // Catch:{ Exception -> 0x003c }
            goto L_0x0033
        L_0x004f:
            r0 = move-exception
            r1 = r0
            goto L_0x0036
        L_0x0052:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x003b
        L_0x0057:
            r1 = move-exception
            goto L_0x0046
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lisyx.tap.utils.HttpUtils.doHttpPostImpl(java.lang.String, okhttp3.RequestBody):java.lang.String");
    }

    public static void doHttpPostImpl(final Context context1, String str, RequestBody requestBody, final OnCallback onCallback1) {
        final Handler r1 = new Handler() {
            public void handleMessage(Message message) {
                if (message.what == 0) {
                    OnCallback onCallback = onCallback1;
                    if (onCallback != null) {
                        onCallback.onResponse((String) message.obj);
                        return;
                    }
                    return;
                }
                Context context = context1;
                Toast.makeText(context, message.what + " : " + ((String) message.obj), Toast.LENGTH_LONG).show();
                OnCallback onCallback2 = onCallback1;
                if (onCallback2 != null) {
                    onCallback2.onResponse((String) null);
                }
            }
        };
        try {
            OkHttpClient httpClient2 = getHttpClient();
            Request.Builder builder = new Request.Builder();
            builder.url(str);
            if (requestBody != null) {
                builder.post(requestBody);
            }
            httpClient2.newCall(builder.build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException iOException) {
                    r1.sendMessage(r1.obtainMessage(2, iOException.toString()));
                }

                public void onResponse(Call call, Response response) throws IOException {
                    if (response == null || !response.isSuccessful()) {
                        r1.sendMessage(r1.obtainMessage(1, "未返回数据"));
                        return;
                    }
                    r1.sendMessage(r1.obtainMessage(0, response.body().string()));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            r1.sendMessage(r1.obtainMessage(3, e.toString()));
        }
    }

    public static void download(Context context, String str, File file, OnDownloadListener onDownloadListener) {
        DownloadInfo downloadInfo = new DownloadInfo(str, file, onDownloadListener);
        if (mDownloadRunningList.size() >= 2) {
            mDownloadWaitQueue.offer(downloadInfo);
            if (onDownloadListener != null) {
                onDownloadListener.onWait();
                return;
            }
            return;
        }
        downloadImpl(context, downloadInfo);
    }

    private static void downloadImpl(final Context context, final DownloadInfo downloadInfo) {
        mDownloadRunningList.add(downloadInfo);
        if (downloadInfo.listener != null) {
            downloadInfo.listener.onProgress(0);
        }
        final Handler r1 = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message message) {
                if (message.what == 0) {
                    downloadInfo.file.delete();
                    HttpUtils.mDownloadRunningList.remove(downloadInfo);
                    HttpUtils.downloadOther(context);
                    if (downloadInfo.listener != null) {
                        downloadInfo.listener.onFail();
                    }
                } else if (message.what == 1) {
                    if (downloadInfo.listener != null) {
                        downloadInfo.listener.onProgress(((Integer) message.obj).intValue());
                    }
                } else if (message.what == 2) {
                    HttpUtils.mDownloadRunningList.remove(downloadInfo);
                    HttpUtils.downloadOther(context);
                    if (downloadInfo.listener != null) {
                        downloadInfo.listener.onSuccess();
                    }
                }
            }
        };
        try {
            OkHttpClient httpClient2 = getHttpClient();
            Request.Builder builder = new Request.Builder();
            builder.url(downloadInfo.httpUrl);
            httpClient2.newCall(builder.build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException iOException) {
                    Handler handler = r1;
                    handler.sendMessage(handler.obtainMessage(0));
                }

                /* JADX WARNING: Removed duplicated region for block: B:18:0x005c A[SYNTHETIC, Splitter:B:18:0x005c] */
                /* JADX WARNING: Removed duplicated region for block: B:21:0x0061 A[Catch:{ Exception -> 0x007a }] */
                /* JADX WARNING: Removed duplicated region for block: B:33:0x0084 A[SYNTHETIC, Splitter:B:33:0x0084] */
                /* JADX WARNING: Removed duplicated region for block: B:36:0x0089 A[Catch:{ Exception -> 0x009c }] */
                /* JADX WARNING: Removed duplicated region for block: B:50:? A[RETURN, SYNTHETIC] */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void onResponse(Call r9, Response r10) throws IOException {
                    /*
                        r8 = this;
                        r2 = 0
                        r0 = 0
                        if (r10 == 0) goto L_0x00a1
                        boolean r1 = r10.isSuccessful()
                        if (r1 == 0) goto L_0x00a1
                        okhttp3.ResponseBody r1 = r10.body()     // Catch:{ Exception -> 0x0095, all -> 0x0090 }
                        java.io.InputStream r1 = r1.byteStream()     // Catch:{ Exception -> 0x0095, all -> 0x0090 }
                        com.lisyx.tap.utils.HttpUtils$DownloadInfo r3 = r6     // Catch:{ Exception -> 0x008d, all -> 0x007f }
                        java.io.File r3 = r3.file     // Catch:{ Exception -> 0x008d, all -> 0x007f }
                        java.io.File r3 = r3.getParentFile()     // Catch:{ Exception -> 0x008d, all -> 0x007f }
                        com.lisyx.tap.utils.FsUtils.createFolder(r3)     // Catch:{ Exception -> 0x008d, all -> 0x007f }
                        java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x008d, all -> 0x007f }
                        com.lisyx.tap.utils.HttpUtils$DownloadInfo r4 = r6     // Catch:{ Exception -> 0x008d, all -> 0x007f }
                        java.io.File r4 = r4.file     // Catch:{ Exception -> 0x008d, all -> 0x007f }
                        r3.<init>(r4)     // Catch:{ Exception -> 0x008d, all -> 0x007f }
                        r2 = 1024(0x400, float:1.435E-42)
                        byte[] r2 = new byte[r2]     // Catch:{ Exception -> 0x004a }
                    L_0x002e:
                        int r4 = r1.read(r2)     // Catch:{ Exception -> 0x004a }
                        if (r4 <= 0) goto L_0x0065
                        r5 = 0
                        r3.write(r2, r5, r4)     // Catch:{ Exception -> 0x004a }
                        int r0 = r0 + r4
                        android.os.Handler r4 = r1     // Catch:{ Exception -> 0x004a }
                        android.os.Handler r5 = r1     // Catch:{ Exception -> 0x004a }
                        r6 = 1
                        java.lang.Integer r7 = java.lang.Integer.valueOf(r0)     // Catch:{ Exception -> 0x004a }
                        android.os.Message r5 = r5.obtainMessage(r6, r7)     // Catch:{ Exception -> 0x004a }
                        r4.sendMessage(r5)     // Catch:{ Exception -> 0x004a }
                        goto L_0x002e
                    L_0x004a:
                        r0 = move-exception
                    L_0x004b:
                        r0.printStackTrace()     // Catch:{ all -> 0x0099 }
                        android.os.Handler r0 = r1     // Catch:{ all -> 0x0099 }
                        android.os.Handler r2 = r1     // Catch:{ all -> 0x0099 }
                        r4 = 0
                        android.os.Message r2 = r2.obtainMessage(r4)     // Catch:{ all -> 0x0099 }
                        r0.sendMessage(r2)     // Catch:{ all -> 0x0099 }
                        if (r3 == 0) goto L_0x005f
                        r3.close()     // Catch:{ Exception -> 0x007a }
                    L_0x005f:
                        if (r1 == 0) goto L_0x0064
                        r1.close()     // Catch:{ Exception -> 0x007a }
                    L_0x0064:
                        return
                    L_0x0065:
                        android.os.Handler r0 = r1     // Catch:{ Exception -> 0x004a }
                        android.os.Handler r2 = r1     // Catch:{ Exception -> 0x004a }
                        r4 = 2
                        android.os.Message r2 = r2.obtainMessage(r4)     // Catch:{ Exception -> 0x004a }
                        r0.sendMessage(r2)     // Catch:{ Exception -> 0x004a }
                        r3.close()     // Catch:{ Exception -> 0x007a }
                        if (r1 == 0) goto L_0x0064
                        r1.close()     // Catch:{ Exception -> 0x007a }
                        goto L_0x0064
                    L_0x007a:
                        r0 = move-exception
                        r0.printStackTrace()
                        goto L_0x0064
                    L_0x007f:
                        r0 = move-exception
                        r3 = r2
                        r4 = r0
                    L_0x0082:
                        if (r3 == 0) goto L_0x0087
                        r3.close()     // Catch:{ Exception -> 0x009c }
                    L_0x0087:
                        if (r1 == 0) goto L_0x008c
                        r1.close()     // Catch:{ Exception -> 0x009c }
                    L_0x008c:
                        throw r4
                    L_0x008d:
                        r0 = move-exception
                        r3 = r2
                        goto L_0x004b
                    L_0x0090:
                        r0 = move-exception
                        r3 = r2
                        r1 = r2
                        r4 = r0
                        goto L_0x0082
                    L_0x0095:
                        r0 = move-exception
                        r1 = r2
                        r3 = r2
                        goto L_0x004b
                    L_0x0099:
                        r0 = move-exception
                        r4 = r0
                        goto L_0x0082
                    L_0x009c:
                        r0 = move-exception
                        r0.printStackTrace()
                        goto L_0x008c
                    L_0x00a1:
                        android.os.Handler r1 = r1
                        android.os.Message r0 = r1.obtainMessage(r0)
                        r1.sendMessage(r0)
                        goto L_0x0064
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.lisyx.tap.utils.HttpUtils.C04359.onResponse(okhttp3.Call, okhttp3.Response):void");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            r1.sendMessage(r1.obtainMessage(0));
        }
    }

    /* access modifiers changed from: private */
    public static void downloadOther(Context context) {
        DownloadInfo poll = mDownloadWaitQueue.poll();
        if (poll != null) {
            downloadImpl(context, poll);
        }
    }

    public static void downloadSetListener(DownloadInfo downloadInfo, OnDownloadListener onDownloadListener) {
        OnDownloadListener unused = downloadInfo.listener = onDownloadListener;
    }

    public static HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {
            public boolean verify(String str, SSLSession sSLSession) {
                return true;
            }
        };
    }

    private static OkHttpClient getHttpClient() {
        OkHttpClient okHttpClient;
        synchronized (HttpUtils.class) {
            try {
                if (httpClient == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.connectTimeout(10, TimeUnit.SECONDS);
                    builder.writeTimeout(60, TimeUnit.SECONDS);
                    builder.readTimeout(60, TimeUnit.SECONDS);
                    if (Build.VERSION.SDK_INT < 29) {
                        builder.sslSocketFactory(getSSLSocketFactory());
                    }
                    builder.hostnameVerifier(getHostnameVerifier());
                    builder.cookieJar(new CookieJar() {
                        private Map<String, List<Cookie>> cookiesMap = new HashMap();

                        public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                            List<Cookie> list = this.cookiesMap.get(httpUrl.host());
                            if (list == null) {
                                list = new ArrayList<>();
                            }
                            LOG.m11v(String.format("load cookie : %s : %s : %s", new Object[]{httpUrl.host(), Integer.valueOf(list.size()), list}));
                            return list;
                        }

                        public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                            LOG.m11v(String.format("save cookie : %s : %s : %s", new Object[]{httpUrl.host(), Integer.valueOf(list.size()), list}));
                            this.cookiesMap.put(httpUrl.host(), list);
                        }
                    });
                    httpClient = builder.build();
                }
                okHttpClient = httpClient;
            } catch (Throwable th) {
                Class<HttpUtils> cls = HttpUtils.class;
                throw th;
            }
        }
        return okHttpClient;
    }

    public static DownloadInfo getRunningDownloadInfo(File file) {
        for (DownloadInfo next : mDownloadRunningList) {
            if (next.file.getAbsolutePath().equals(file.getAbsolutePath())) {
                return next;
            }
        }
        return null;
    }

    public static SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext instance = SSLContext.getInstance("SSL");
            instance.init((KeyManager[]) null, getTrustManager(), new SecureRandom());
            return instance.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static TrustManager[] getTrustManager() {
        return new TrustManager[]{new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};
    }

    public static DownloadInfo getWaitDownloadInfo(File file) {
        Iterator<DownloadInfo> it = mDownloadWaitQueue.iterator();
        while (it.hasNext()) {
            DownloadInfo next = it.next();
            if (next.file.getAbsolutePath().equals(file.getAbsolutePath())) {
                return next;
            }
        }
        return null;
    }
}
