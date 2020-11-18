package com.demacia.garen.tools;

import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;

import com.demacia.garen.activity.ActivityDyQuGuan;
import com.demacia.garen.service.DyAccessibilityService;
import com.demacia.garen.utils.LOG;
import com.demacia.garen.utils.NodeHelper;
import com.demacia.garen.view.Ball;

import static androidx.core.view.accessibility.AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD;

public class OperateDyQuGuan extends OperateBase {
    /* access modifiers changed from: private */
    public int runCount;
    private long runSpeed;
    /* access modifiers changed from: private */
    public DyAccessibilityService service = null;

    public OperateDyQuGuan(DyAccessibilityService dyAccessibilityService) {
        this.service = dyAccessibilityService;
    }

    static /* synthetic */ int access$210(OperateDyQuGuan operateDyQuGuan) {
        int i = operateDyQuGuan.runCount;
        operateDyQuGuan.runCount = i - 1;
        return i;
    }

    /* access modifiers changed from: private */
    public void clickGuanZhu(final AccessibilityNodeInfo accessibilityNodeInfo, final int i) {
        if (i < accessibilityNodeInfo.getChildCount()) {
            this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
                public void process() {
                    AccessibilityNodeInfo clickIfFindOne = OperateDyQuGuan.this.service.clickIfFindOne(accessibilityNodeInfo.getChild(i), "已关注", new NodeHelper.OnFindCallback() {
                        public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                            return !TextUtils.isEmpty(str) && str.equals("android.widget.TextView") && !TextUtils.isEmpty(str2) && str2.equals("已关注");
                        }
                    }, (DyAccessibilityService.OnClickFunction) null);
                    if (clickIfFindOne == null) {
                        clickIfFindOne = OperateDyQuGuan.this.service.clickIfFindOne(accessibilityNodeInfo.getChild(i), "互相关注", new NodeHelper.OnFindCallback() {
                            public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                                return !TextUtils.isEmpty(str) && str.equals("android.widget.TextView") && !TextUtils.isEmpty(str2) && str2.equals("互相关注");
                            }
                        }, (DyAccessibilityService.OnClickFunction) null);
                    }
                    if (clickIfFindOne != null) {
                        OperateDyQuGuan.access$210(OperateDyQuGuan.this);
                        LOG.m11v("runCount : " + OperateDyQuGuan.this.runCount);
                    }
                    if (OperateDyQuGuan.this.runCount <= 0) {
                        Ball.stopRunning();
                    } else {
                        OperateDyQuGuan.this.clickGuanZhu(accessibilityNodeInfo, i + 1);
                    }
                }
            });
        } else {
            swipeNext(accessibilityNodeInfo);
        }
    }

    private void findGuanZhu() {
        this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                OperateDyQuGuan.this.service.clickIfFindOne("RecyclerView", new NodeHelper.OnFindCallback() {
                    public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                        return !TextUtils.isEmpty(str) && (str.equals("android.support.v7.widget.RecyclerView") || str.equals("androidx.recyclerview.widget.RecyclerView")) && accessibilityNodeInfo.isScrollable();
                    }
                }, new DyAccessibilityService.OnClickFunction() {
                    public void click(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
                        OperateDyQuGuan.this.clickGuanZhu(accessibilityNodeInfo, 0);
                    }
                });
            }
        });
    }

    private void swipeNext(final AccessibilityNodeInfo accessibilityNodeInfo) {
        this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                LOG.m11v("swipeNext");
                accessibilityNodeInfo.performAction(ACTION_SCROLL_FORWARD);
                OperateDyQuGuan.this.willStartOne();
            }
        });
    }

    /* access modifiers changed from: private */
    public void willStartOne() {
        this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                OperateDyQuGuan.this.startOne();
            }
        });
    }

    public void start() {
        int intValue = ActivityDyQuGuan.readGuan(this.context).intValue();
        this.runCount = intValue;
        if (intValue <= 0) {
            this.runCount = 9999;
        }
        this.runSpeed = (long) (ActivityDyQuGuan.readRunSpeed(this.context).intValue() * 1000);
        LOG.m11v(String.format("runCount = %s, runSpeed = %s", new Object[]{Integer.valueOf(this.runCount), Long.valueOf(this.runSpeed / 1000)}));
        startOne();
    }

    public void startOne() {
        LOG.m11v("---------startOne");
        findGuanZhu();
    }
}
