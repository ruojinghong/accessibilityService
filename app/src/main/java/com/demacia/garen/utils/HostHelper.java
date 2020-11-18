package com.demacia.garen.utils;

import android.content.Context;
import android.text.TextUtils;

public class HostHelper {

    public interface OnHostCallback {
        void onResult(String str);
    }

    private static String hostFileAddress(Context context) {
        return String.format("http://qyqy-file.oss-cn-hangzhou.aliyuncs.com/host/%s--%s.txt", context.getPackageName(), SysUtils.buildType());
    }

    public static void requestHost(Context context, final OnHostCallback onHostCallback1) {
        String hostFileAddress = hostFileAddress(context);
        if (!TextUtils.isEmpty(hostFileAddress)) {
            if (SysUtils.isDebug()) {
                LOG.m11v("requestHost : " + hostFileAddress);
            }
            HttpUtils.doHttpGet(context, hostFileAddress, new HttpUtils.OnCallback() {
                public void onResponse(String str) {
                    if (!TextUtils.isEmpty(str)) {
                        OnHostCallback onHostCallback = onHostCallback1;
                        if (onHostCallback != null) {
                            onHostCallback.onResult(str);
                            return;
                        }
                        return;
                    }
                    OnHostCallback onHostCallback2 = onHostCallback1;
                    if (onHostCallback2 != null) {
                        onHostCallback2.onResult((String) null);
                    }
                }
            });
        } else if (onHostCallback1 != null) {
            onHostCallback1.onResult((String) null);
        }
    }
}
