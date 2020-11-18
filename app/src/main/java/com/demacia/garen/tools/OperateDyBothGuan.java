package com.demacia.garen.tools;

import android.content.Context;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.demacia.garen.activity.ActivityDyBothGuan;
import com.demacia.garen.service.DyAccessibilityService;
import com.demacia.garen.utils.LOG;
import com.demacia.garen.utils.NodeHelper;
import com.demacia.garen.view.Ball;

import static androidx.core.view.accessibility.AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD;

public class OperateDyBothGuan extends OperateBase {
    /* access modifiers changed from: private */
    public int allCount;
    /* access modifiers changed from: private */
    public int currCount;
    private long runSpeed;
    /* access modifiers changed from: private */
    public DyAccessibilityService service;

    public OperateDyBothGuan(DyAccessibilityService dyAccessibilityService) {
        this.service = dyAccessibilityService;
    }

    static /* synthetic */ int access$208(OperateDyBothGuan operateDyBothGuan) {
        int i = operateDyBothGuan.currCount;
        operateDyBothGuan.currCount = i + 1;
        return i;
    }

    /* access modifiers changed from: private */
    public void clickGuanZhu(final AccessibilityNodeInfo accessibilityNodeInfo, final int i) {
        if (i < accessibilityNodeInfo.getChildCount()) {
            this.service.wait(this.runSpeed - 500, new DyAccessibilityService.OnWaitCallback() {
                public void process() {
                    if (OperateDyBothGuan.this.service.clickIfFindOne(accessibilityNodeInfo.getChild(i), "关注", new NodeHelper.OnFindCallback() {
                        public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                            return !TextUtils.isEmpty(str) && str.equals("android.widget.TextView") && !TextUtils.isEmpty(str2) && str2.equals("关注");
                        }
                    }, (DyAccessibilityService.OnClickFunction) null) != null) {
                        OperateDyBothGuan.access$208(OperateDyBothGuan.this);
                        if (OperateDyBothGuan.this.currCount % 10 == 0) {
                            long currentTimeMillis = System.currentTimeMillis() - OperateDyBothGuan.this.startTime;
                            Context access$400 = OperateDyBothGuan.this.context;
                            String access$500 = OperateDyBothGuan.this.formatTime(currentTimeMillis);
                            OperateDyBothGuan operateDyBothGuan = OperateDyBothGuan.this;
                            Toast.makeText(access$400, String.format("\n运行了 %s\n平均每个 %s\n当前第 %s 个\n要运行总数 %s 个", new Object[]{access$500, operateDyBothGuan.formatTime(currentTimeMillis / ((long) operateDyBothGuan.currCount)), Integer.valueOf(OperateDyBothGuan.this.currCount), OperateDyBothGuan.this.allCount > 0 ? Integer.valueOf(OperateDyBothGuan.this.allCount) : "不限"}), Toast.LENGTH_LONG).show();
                        }
                    }
                    if (OperateDyBothGuan.this.allCount <= 0 || OperateDyBothGuan.this.currCount < OperateDyBothGuan.this.allCount) {
                        OperateDyBothGuan.this.clickGuanZhu(accessibilityNodeInfo, i + 1);
                        return;
                    }
                    Ball.stopRunning();
                    Toast.makeText(OperateDyBothGuan.this.context, "运行完毕", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            swipeNext(accessibilityNodeInfo);
        }
    }

    private void findGuanZhu() {
        this.service.wait(this.runSpeed - 500, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                OperateDyBothGuan.this.service.clickIfFindOne("RecyclerView", new NodeHelper.OnFindCallback() {
                    public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                        return !TextUtils.isEmpty(str) && (str.equals("android.support.v7.widget.RecyclerView") || str.equals("androidx.recyclerview.widget.RecyclerView")) && accessibilityNodeInfo.isScrollable();
                    }
                }, new DyAccessibilityService.OnClickFunction() {
                    public void click(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
                        OperateDyBothGuan.this.clickGuanZhu(accessibilityNodeInfo, 0);
                    }
                });
            }
        });
    }

    private void swipeNext(final AccessibilityNodeInfo accessibilityNodeInfo) {
        this.service.wait(this.runSpeed - 500, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                LOG.m11v("swipeNext");
                accessibilityNodeInfo.performAction(ACTION_SCROLL_FORWARD);
                OperateDyBothGuan.this.willStartOne();
            }
        });
    }

    /* access modifiers changed from: private */
    public void willStartOne() {
        startOne();
    }

    public void start() {
        this.allCount = ActivityDyBothGuan.readBoth(this.context).intValue();
        this.currCount = 0;
        this.runSpeed = (long) (ActivityDyBothGuan.readRunSpeed(this.context).intValue() * 1000);
        LOG.m11v(String.format("allCount = %s, runSpeed = %s", new Object[]{Integer.valueOf(this.allCount), Long.valueOf(this.runSpeed / 1000)}));
        startOne();
    }

    public void startOne() {
        LOG.m11v("---------startOne");
        findGuanZhu();
    }
}
