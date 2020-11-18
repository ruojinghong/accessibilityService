package com.demacia.garen.utils;

import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

public class NodeHelper {

    public interface OnEachCallback {
        void each(AccessibilityNodeInfo accessibilityNodeInfo, int i);
    }

    public interface OnFindCallback {
        boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4);
    }

    public static AccessibilityNodeInfo brotherNodeIndex(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        return getChildNode(accessibilityNodeInfo.getParent(), i);
    }

    public static AccessibilityNodeInfo brotherNodeOffset(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        return getChildNode(accessibilityNodeInfo.getParent(), getIndex(accessibilityNodeInfo) + i);
    }

    private static void eachNode(AccessibilityNodeInfo accessibilityNodeInfo, int i, OnEachCallback onEachCallback) {
        if (onEachCallback != null) {
            onEachCallback.each(accessibilityNodeInfo, i);
        }
        if (accessibilityNodeInfo != null && accessibilityNodeInfo.isVisibleToUser()) {
            for (int i2 = 0; i2 < accessibilityNodeInfo.getChildCount(); i2++) {
                eachNode(accessibilityNodeInfo.getChild(i2), i + 1, onEachCallback);
            }
        }
    }

    public static List<AccessibilityNodeInfo> findNodes(AccessibilityNodeInfo accessibilityNodeInfo, final OnFindCallback onFindCallback) {
        final ArrayList arrayList = new ArrayList();
        eachNode(accessibilityNodeInfo, 0, new OnEachCallback() {
            public void each(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
                if (accessibilityNodeInfo != null && accessibilityNodeInfo.isVisibleToUser()) {
                    if (onFindCallback.find(accessibilityNodeInfo, i, accessibilityNodeInfo.getClassName() != null ? accessibilityNodeInfo.getClassName().toString() : null, accessibilityNodeInfo.getText() != null ? accessibilityNodeInfo.getText().toString() : null, accessibilityNodeInfo.getContentDescription() != null ? accessibilityNodeInfo.getContentDescription().toString() : null, accessibilityNodeInfo.getViewIdResourceName())) {
                        arrayList.add(accessibilityNodeInfo);
                    }
                }
            }
        });
        return arrayList;
    }

    public static int getChildCount(AccessibilityNodeInfo accessibilityNodeInfo) {
        int i = 0;
        int i2 = 0;
        while (true) {
            int i3 = i;
            if (i3 >= accessibilityNodeInfo.getChildCount()) {
                return i2;
            }
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i3);
            if (child != null && child.isVisibleToUser()) {
                i2++;
            }
            i = i3 + 1;
        }
    }

    public static AccessibilityNodeInfo getChildNode(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        int i2 = -1;
        for (int i3 = 0; i3 < accessibilityNodeInfo.getChildCount(); i3++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i3);
            if (child != null && child.isVisibleToUser() && i == (i2 = i2 + 1)) {
                return child;
            }
        }
        return null;
    }

    public static int getIndex(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
        int i = -1;
        for (int i2 = 0; i2 < parent.getChildCount(); i2++) {
            AccessibilityNodeInfo child = parent.getChild(i2);
            if (child != null && child.isVisibleToUser()) {
                i++;
                if (accessibilityNodeInfo.equals(child)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static void printNode(AccessibilityNodeInfo accessibilityNodeInfo) {
        eachNode(accessibilityNodeInfo, 0, new OnEachCallback() {
            public void each(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
                if (accessibilityNodeInfo != null && accessibilityNodeInfo.isVisibleToUser()) {
                    if (accessibilityNodeInfo.isClickable()) {
                        LOG.m10e(CommonUtils.getIndentStr(i) + String.format("%s, %s, %s, %s", new Object[]{accessibilityNodeInfo.getClassName(), accessibilityNodeInfo.getText(), accessibilityNodeInfo.getContentDescription(), accessibilityNodeInfo.getViewIdResourceName()}));
                        return;
                    }
                    LOG.m11v(CommonUtils.getIndentStr(i) + String.format("%s, %s, %s, %s", new Object[]{accessibilityNodeInfo.getClassName(), accessibilityNodeInfo.getText(), accessibilityNodeInfo.getContentDescription(), accessibilityNodeInfo.getViewIdResourceName()}));
                }
            }
        });
    }
}
