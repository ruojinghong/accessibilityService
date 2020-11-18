package com.demacia.garen.tools;

import android.content.Context;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.demacia.garen.activity.ActivityDyFenGuan;
import com.demacia.garen.service.DyAccessibilityService;
import com.demacia.garen.utils.LOG;
import com.demacia.garen.utils.NodeHelper;
import com.demacia.garen.view.Ball;

import static androidx.core.view.accessibility.AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD;

public class OperateDyFenGuan extends OperateBase {
    /* access modifiers changed from: private */
    public int allCount;
    /* access modifiers changed from: private */
    public int currCount;
    private long runSpeed;
    /* access modifiers changed from: private */
    public DyAccessibilityService service;

    public OperateDyFenGuan(DyAccessibilityService dyAccessibilityService) {
        this.service = dyAccessibilityService;
    }

    static /* synthetic */ int access$208(OperateDyFenGuan operateDyFenGuan) {
        int i = operateDyFenGuan.currCount;
        operateDyFenGuan.currCount = i + 1;
        return i;
    }

    /* access modifiers changed from: private */
    public void clickGuanZhu(final AccessibilityNodeInfo accessibilityNodeInfo, final int i) {
        if (i < accessibilityNodeInfo.getChildCount()) {
            this.service.wait(this.runSpeed - 500, new DyAccessibilityService.OnWaitCallback() {
                public void process() {
                    if (OperateDyFenGuan.this.service.clickIfFindOne(accessibilityNodeInfo.getChild(i), "关注", new NodeHelper.OnFindCallback() {
                        public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                            return !TextUtils.isEmpty(str) && str.equals("android.widget.TextView") && !TextUtils.isEmpty(str2) && str2.equals("关注");
                        }
                    }, (DyAccessibilityService.OnClickFunction) null) != null) {
                        OperateDyFenGuan.access$208(OperateDyFenGuan.this);
                        if (OperateDyFenGuan.this.currCount % 10 == 0) {
                            long currentTimeMillis = System.currentTimeMillis() - OperateDyFenGuan.this.startTime;
                            Context access$400 = OperateDyFenGuan.this.context;
                            String access$500 = OperateDyFenGuan.this.formatTime(currentTimeMillis);
                            OperateDyFenGuan operateDyFenGuan = OperateDyFenGuan.this;
                            Toast.makeText(access$400, String.format("\n运行了 %s\n平均每个 %s\n当前第 %s 个\n要运行总数 %s 个", new Object[]{access$500, operateDyFenGuan.formatTime(currentTimeMillis / ((long) operateDyFenGuan.currCount)), Integer.valueOf(OperateDyFenGuan.this.currCount), OperateDyFenGuan.this.allCount > 0 ? Integer.valueOf(OperateDyFenGuan.this.allCount) : "不限"}), Toast.LENGTH_LONG).show();
                        }
                    }
                    if (OperateDyFenGuan.this.allCount <= 0 || OperateDyFenGuan.this.currCount < OperateDyFenGuan.this.allCount) {
                        OperateDyFenGuan.this.clickGuanZhu(accessibilityNodeInfo, i + 1);
                        return;
                    }
                    Ball.stopRunning();
                    Toast.makeText(OperateDyFenGuan.this.context, "运行完毕", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            swipeNext(accessibilityNodeInfo);
        }
    }

    private void findGuanZhu() {
        this.service.wait(this.runSpeed - 500, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                OperateDyFenGuan.this.service.clickIfFindOne("RecyclerView", new NodeHelper.OnFindCallback() {
                    public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                        return !TextUtils.isEmpty(str) && str.equals("androidx.recyclerview.widget.RecyclerView") && accessibilityNodeInfo.isScrollable() && i == 2;
                    }
                }, new DyAccessibilityService.OnClickFunction() {
                    public void click(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
                        OperateDyFenGuan.this.clickGuanZhu(accessibilityNodeInfo, 0);
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
                OperateDyFenGuan.this.willStartOne();
            }
        });
    }

    /* access modifiers changed from: private */
    public void willStartOne() {
        startOne();
    }

    public void start() {
        this.allCount = ActivityDyFenGuan.readFen(this.context).intValue();
        this.currCount = 0;
        this.runSpeed = (long) (ActivityDyFenGuan.readRunSpeed(this.context).intValue() * 1000);
        LOG.m11v(String.format("allCount = %s, runSpeed = %s", new Object[]{Integer.valueOf(this.allCount), Long.valueOf(this.runSpeed / 1000)}));
        startOne();
    }

    public void startOne() {
        LOG.m11v("---------startOne");
        findGuanZhu();
    }
}
