package com.demacia.garen.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.demacia.garen.activity.ActivityMain;
import com.demacia.garen.tools.OperateDyBothGuan;
import com.demacia.garen.tools.OperateDyFenGuan;
import com.demacia.garen.tools.OperateDyLive;
import com.demacia.garen.tools.OperateDyPingXing;
import com.demacia.garen.tools.OperateDyQuGuan;
import com.demacia.garen.tools.OperateDyShiXing;
import com.demacia.garen.tools.OperateDyYangHao;
import com.demacia.garen.tools.OperateDyYingLiu;
import com.demacia.garen.tools.OperateBase;
import com.demacia.garen.utils.HttpUtils;
import com.demacia.garen.utils.LOG;
import com.demacia.garen.utils.NodeHelper;
import com.demacia.garen.utils.SPHelper;
import com.demacia.garen.utils.SysUtils;
import com.demacia.garen.view.Ball;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.view.accessibility.AccessibilityEvent.TYPE_VIEW_CLICKED;
import static android.view.accessibility.AccessibilityEvent.TYPE_VIEW_SCROLLED;
import static androidx.core.view.accessibility.AccessibilityNodeInfoCompat.ACTION_CLICK;

import static androidx.core.view.accessibility.AccessibilityNodeInfoCompat.ACTION_SET_TEXT;

public class DyAccessibilityService extends AccessibilityService {
    public static final int OperateType_DyBothGuan = 4;
    public static final int OperateType_DyFenGuan = 5;
    public static final int OperateType_DyLive = 8;
    public static final int OperateType_DyPingXing = 7;
    public static final int OperateType_DyQuGuan = 3;
    public static final int OperateType_DyShiXing = 6;
    public static final int OperateType_DyYangHao = 2;
    public static final int OperateType_DyYingLiu = 1;
    public static long lastCheckTime;
    /* access modifiers changed from: private */
    public OnWaitCallback callback = null;
    public long largeWaitTime = 4000;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            LOG.m11v("Action:" + intent.getAction());
            DyAccessibilityService.this.processReceiver(context, intent);
        }
    };
    private HashMap<Integer, OperateBase> operateBaseMap = new HashMap<>();
    private Handler waitHandler = new Handler();
    private Runnable waitRunnable = new Runnable() {
        public void run() {
//            DyAccessibilityService.check(DyAccessibilityService.this);
            if (DyAccessibilityService.this.callback != null) {
                DyAccessibilityService.this.callback.process();
            }
        }
    };

    public interface OnClickFunction {
        void click(AccessibilityNodeInfo accessibilityNodeInfo, String str);
    }

    public interface OnWaitCallback {
        void process();
    }

    public static String Action_Start(Context context) {
        return context.getPackageName() + "." + DyAccessibilityService.class.getName() + ".start";
    }

    public static String Action_Stop(Context context) {
        return context.getPackageName() + "." + DyAccessibilityService.class.getName() + ".stop";
    }

    public static String Action_test(Context context) {
        return context.getPackageName() + "." + DyAccessibilityService.class.getName() + ".test";
    }

    public static void check(final Context context) {
        if (Math.abs(System.currentTimeMillis() - lastCheckTime) > 3600000) {
            lastCheckTime = System.currentTimeMillis();
            HashMap hashMap = new HashMap();
            hashMap.put("mobileId", SysUtils.getOnlyId(context));
            hashMap.put("checkKey", SPHelper.getString(context, "code", ""));
            HttpUtils.doHttpPost(context, "http://loan.eco-ozg.cn/jy/jyMobileKey/check", (Map<String, String>) hashMap, (HttpUtils.OnCallback) new HttpUtils.OnCallback() {
                public void onResponse(String str) {
                    JSONObject parseObject;
                    if (!TextUtils.isEmpty(str) && (parseObject = JSONObject.parseObject(str, Feature.OrderedField)) != null && parseObject.getInteger("code").intValue() != 200) {
                        Toast.makeText(context, parseObject.getString(NotificationCompat.CATEGORY_MESSAGE), Toast.LENGTH_SHORT).show();
                        Ball.stopRunning();
                        Intent intent = new Intent(context, ActivityMain.class);
                        intent.putExtra("past", true);
                        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    private void clickNode(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        if (accessibilityNodeInfo != null && accessibilityNodeInfo.isVisibleToUser()) {
            if (accessibilityNodeInfo.isClickable()) {
                LOG.m11v("click " + str);
                accessibilityNodeInfo.performAction(ACTION_CLICK);
                return;
            }
            clickNode(accessibilityNodeInfo.getParent(), str);
        }
    }

    /* access modifiers changed from: private */
    public void processReceiver(Context context, Intent intent) {
        if (intent.getAction().equals(Action_test(context))) {
            NodeHelper.printNode(getRootInActiveWindow());
        } else if (intent.getAction().equals(Action_Start(context))) {
            start(intent.getIntExtra("operateType", 0));
        } else if (intent.getAction().equals(Action_Stop(context))) {
            stop();
        }
    }

    public boolean click(int i, int i2, GestureResultCallback gestureResultCallback) {
        LOG.m11v("click : " + i + ", " + i2);
        try {
            Path path = new Path();
            path.moveTo((float) i, (float) i2);
            if (Build.VERSION.SDK_INT >= 24) {
                dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0, 100)).build(), gestureResultCallback, (Handler) null);
                return true;
            }
            Toast.makeText(this, String.format("系统版本太低", new Object[0]), Toast.LENGTH_SHORT).show();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public AccessibilityNodeInfo clickIfFindOne(AccessibilityNodeInfo accessibilityNodeInfo, String str, NodeHelper.OnFindCallback onFindCallback, OnClickFunction onClickFunction) {
        LOG.m11v("to find " + str);
        List<AccessibilityNodeInfo> findNodes = NodeHelper.findNodes(accessibilityNodeInfo, onFindCallback);
        if (findNodes.size() == 1) {
            LOG.m11v("find " + str);
            AccessibilityNodeInfo accessibilityNodeInfo2 = findNodes.get(0);
            if (onClickFunction != null) {
                onClickFunction.click(accessibilityNodeInfo2, str);
                return accessibilityNodeInfo2;
            }
            clickNode(accessibilityNodeInfo2, str);
            return accessibilityNodeInfo2;
        }
        LOG.m10e(String.format("find %s error : size is %s", new Object[]{str, Integer.valueOf(findNodes.size())}));
        return null;
    }

    public AccessibilityNodeInfo clickIfFindOne(String str, NodeHelper.OnFindCallback onFindCallback, OnClickFunction onClickFunction) {
        return clickIfFindOne(getRootInActiveWindow(), str, onFindCallback, onClickFunction);
    }

    public OperateBase getOperateBase(int i) {
        if (!this.operateBaseMap.containsKey(Integer.valueOf(i))) {
            if (i == 1) {
                this.operateBaseMap.put(Integer.valueOf(i), new OperateDyYingLiu(this));
            } else if (i == 2) {
                this.operateBaseMap.put(Integer.valueOf(i), new OperateDyYangHao(this));
            } else if (i == 3) {
                this.operateBaseMap.put(Integer.valueOf(i), new OperateDyQuGuan(this));
            } else if (i == 4) {
                this.operateBaseMap.put(Integer.valueOf(i), new OperateDyBothGuan(this));
            } else if (i == 5) {
                this.operateBaseMap.put(Integer.valueOf(i), new OperateDyFenGuan(this));
            } else if (i == 6) {
                this.operateBaseMap.put(Integer.valueOf(i), new OperateDyShiXing(this));
            } else if (i == 7) {
                this.operateBaseMap.put(Integer.valueOf(i), new OperateDyPingXing(this));
            } else if (i == 8) {
                this.operateBaseMap.put(Integer.valueOf(i), new OperateDyLive(this));
            }
        }
        return this.operateBaseMap.get(Integer.valueOf(i));
    }

    public void inputNode(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
//        if (str.contains("@")) {
            ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("text", str));
//        }else{
//            ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("text", "???"));
//
//        }
        CharSequence txt = accessibilityNodeInfo.getText();
        if(txt == null) txt = "";
        Bundle arguments = new Bundle();
        arguments.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_SELECTION_START_INT, 0);
        arguments.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_SELECTION_END_INT, str.length());
        accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
//        accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_SET_SELECTION, arguments);
        accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_PASTE);


//        Bundle arguments = new Bundle();
//        arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, str);
//        accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
//        accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
    }

    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        // TODO: 2020/11/17
        if (accessibilityEvent.getEventType() == TYPE_VIEW_CLICKED || accessibilityEvent.getEventType() == TYPE_VIEW_SCROLLED) {
            LOG.m11v("------- onAccessibilityEvent : " + accessibilityEvent.toString());
        }
    }

    public void onInterrupt() {
        LOG.m11v("------- onInterrupt : " + getClass().getSimpleName());
    }

    /* access modifiers changed from: protected */
    public void onServiceConnected() {
        LOG.m11v("--------------------------- onServiceConnected : " + getClass().getSimpleName());
        super.onServiceConnected();
        Intent intent = new Intent();
        intent.setAction(ActivityMain.Action_Connected(this));
        sendBroadcast(intent);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Action_test(this));
        intentFilter.addAction(Action_Start(this));
        intentFilter.addAction(Action_Stop(this));
        registerReceiver(this.mReceiver, intentFilter);
    }

    public boolean onUnbind(Intent intent) {
        LOG.m11v("--------------------------- onUnbind : " + getClass().getSimpleName());
        Intent intent2 = new Intent();
        intent2.setAction(ActivityMain.Action_Unbind(this));
        sendBroadcast(intent2);
        unregisterReceiver(this.mReceiver);
        return super.onUnbind(intent);
    }

    public void setNodeText(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        Bundle bundle = new Bundle();
        bundle.putCharSequence(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, str);
        accessibilityNodeInfo.performAction(ACTION_SET_TEXT, bundle);
    }

    public boolean smooth(int i, int i2, int i3, int i4, int i5, GestureResultCallback gestureResultCallback) {
        LOG.m11v("smooth : " + i + ", " + i2 + ", " + i3 + ", " + i4 + ", " + i5);
        try {
            Path path = new Path();
            path.moveTo((float) i, (float) i2);
            path.lineTo((float) i3, (float) i4);
            if (Build.VERSION.SDK_INT >= 26) {
                dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0, 500, true)).build(), gestureResultCallback, (Handler) null);
                return true;
            }
            Toast.makeText(this, String.format("系统版本太低", new Object[0]), Toast.LENGTH_SHORT).show();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void start(int i) {
        getOperateBase(i).startMode(this);
    }

    public void stop() {
        this.waitHandler.removeCallbacks(this.waitRunnable);
        LOG.toast(this, "已停止");
    }

    public void wait(long j, OnWaitCallback onWaitCallback) {
        this.callback = onWaitCallback;
        this.waitHandler.postDelayed(this.waitRunnable, j);
    }
}
