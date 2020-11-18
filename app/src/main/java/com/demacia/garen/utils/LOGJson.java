package com.demacia.garen.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class LOGJson {

    public interface TraversalCallback {
        void callback(String str);
    }

    private static String getIndentStr(int i) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append("\t");
        }
        return sb.toString();
    }

    public static String getStr(String str) {
        return getStr(str, Integer.MAX_VALUE);
    }

    public static String getStr(String str, int i) {
        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            traversal(str, i, new TraversalCallback() {
                public void callback(String str) {
                    byteArrayOutputStream.write(str.getBytes(), 0, str.getBytes().length);
                    byteArrayOutputStream.write("\r\n".getBytes(), 0, "\r\n".getBytes().length);
                }
            });
            byteArrayOutputStream.close();
            return new String(byteArrayOutputStream.toByteArray(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void log(String str) {
        log(str, Integer.MAX_VALUE);
    }

    public static void log(String str, int i) {
        traversal(str, i, new TraversalCallback() {
            public void callback(String str) {
                LOG.m11v(str);
            }
        });
    }

    private static void traversal(String str, int i, TraversalCallback traversalCallback) {
        traversal(str, 0, true, i, traversalCallback);
    }

    private static void traversal(String str, int i, boolean z, int i2, TraversalCallback traversalCallback) {
        try {
            int jsonType = JsonHelper.getJsonType(str);
            String str2 = "";
            if (jsonType == 1) {
                JSONObject jSONObject = JsonHelper.getJSONObject(str);
                if (i >= i2) {
                    traversalCallback.callback(String.format("%s%s", new Object[]{getIndentStr(i), jSONObject.toString()}));
                    return;
                }
                traversalCallback.callback(String.format("%s%s", new Object[]{getIndentStr(i), "{"}));
                ArrayList<String> keys = JsonHelper.getKeys(jSONObject);
                int i3 = 0;
                while (i3 < keys.size()) {
                    String str3 = keys.get(i3);
                    String string = jSONObject.getString(str3);
                    if (JsonHelper.getJsonType(string) == 0) {
                        traversalCallback.callback(String.format("%s\"%s\":\"%s\"%s", new Object[]{getIndentStr(i + 1), str3, string, i3 == keys.size() + -1 ? "" : ","}));
                    } else {
                        int i4 = i + 1;
                        if (i4 >= i2) {
                            traversalCallback.callback(String.format("%s\"%s\":%s%s", new Object[]{getIndentStr(i4), str3, string, i3 == keys.size() + -1 ? "" : ","}));
                        } else {
                            traversalCallback.callback(String.format("%s\"%s\":", new Object[]{getIndentStr(i4), str3}));
                            traversal(string, i4, i3 == keys.size() + -1, i2, traversalCallback);
                        }
                    }
                    i3++;
                }
                String indentStr = getIndentStr(i);
                if (!z) {
                    str2 = ",";
                }
                traversalCallback.callback(String.format("%s%s%s", new Object[]{indentStr, "}", str2}));
            } else if (jsonType == 2) {
                JSONArray jSONArray = JsonHelper.getJSONArray(str);
                if (i >= i2) {
                    traversalCallback.callback(String.format("%s%s", new Object[]{getIndentStr(i), jSONArray.toString()}));
                    return;
                }
                traversalCallback.callback(String.format("%s%s", new Object[]{getIndentStr(i), "["}));
                int i5 = 0;
                while (i5 < jSONArray.size()) {
                    String string2 = jSONArray.getString(i5);
                    if (JsonHelper.getJsonType(string2) == 0) {
                        traversalCallback.callback(String.format("%s\"%s\"%s", new Object[]{getIndentStr(i + 1), string2, i5 == jSONArray.size() + -1 ? "" : ","}));
                    } else {
                        int i6 = i + 1;
                        if (i6 >= i2) {
                            traversalCallback.callback(String.format("%s%s%s", new Object[]{getIndentStr(i6), string2, i5 == jSONArray.size() + -1 ? "" : ","}));
                        } else {
                            traversal(string2, i6, i5 == jSONArray.size() + -1, i2, traversalCallback);
                        }
                    }
                    i5++;
                }
                traversalCallback.callback(String.format("%s%s%s", new Object[]{getIndentStr(i), "]", z ? str2 : ","}));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
