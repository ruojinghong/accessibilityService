package com.demacia.garen.tools;

import android.content.Context;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.demacia.garen.activity.ActivityDyShiXing;
import com.demacia.garen.service.DyAccessibilityService;
import com.demacia.garen.utils.LOG;
import com.demacia.garen.utils.NodeHelper;
import com.demacia.garen.view.Ball;

import static androidx.core.view.accessibility.AccessibilityNodeInfoCompat.ACTION_CLICK;
import static androidx.core.view.accessibility.AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD;

public class OperateDyShiXing extends OperateBase {
    /* access modifiers changed from: private */
    public int allCount;
    /* access modifiers changed from: private */
    public int currCount;
    private long runSpeed;
    /* access modifiers changed from: private */
    public DyAccessibilityService service = null;

    public OperateDyShiXing(DyAccessibilityService dyAccessibilityService) {
        this.service = dyAccessibilityService;
    }

    static /* synthetic */ int access$1208(OperateDyShiXing operateDyShiXing) {
        int i = operateDyShiXing.currCount;
        operateDyShiXing.currCount = i + 1;
        return i;
    }

    /* access modifiers changed from: private */
    public void back1(final AccessibilityNodeInfo accessibilityNodeInfo, final int i) {
        this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                LOG.m11v("back 1");
                OperateDyShiXing.this.service.performGlobalAction(1);
                OperateDyShiXing.this.back2(accessibilityNodeInfo, i);
            }
        });
    }

    /* access modifiers changed from: private */
    public void back2(final AccessibilityNodeInfo accessibilityNodeInfo, final int i) {
        this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                LOG.m11v("back 2");
                OperateDyShiXing.this.service.performGlobalAction(1);
                OperateDyShiXing.this.back3(accessibilityNodeInfo, i);
            }
        });
    }

    /* access modifiers changed from: private */
    public void back3(final AccessibilityNodeInfo accessibilityNodeInfo, final int i) {
        this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                LOG.m11v("back 3");
                OperateDyShiXing.this.service.performGlobalAction(1);
                if (OperateDyShiXing.this.allCount <= 0 || OperateDyShiXing.this.currCount < OperateDyShiXing.this.allCount) {
                    OperateDyShiXing.this.clickGuanZhu(accessibilityNodeInfo, i + 1);
                    return;
                }
                Ball.stopRunning();
                Toast.makeText(OperateDyShiXing.this.context, "运行完毕", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /* access modifiers changed from: private */
    public void checkMsg(final AccessibilityNodeInfo accessibilityNodeInfo, final int i) {
        this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                final String randomReadMsg = ActivityDyShiXing.randomReadMsg(OperateDyShiXing.this.context);
                if (OperateDyShiXing.this.service.clickIfFindOne("检查消息", new NodeHelper.OnFindCallback() {
                    public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                        return !TextUtils.isEmpty(str) && str.equals("android.widget.TextView") && !TextUtils.isEmpty(str2) && str2.equals(randomReadMsg);
                    }
                }, new DyAccessibilityService.OnClickFunction() {
                    public void click(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
                    }
                }) == null) {
                    OperateDyShiXing.this.clickInput(accessibilityNodeInfo, i, randomReadMsg);
                } else {
                    OperateDyShiXing.this.back2(accessibilityNodeInfo, i);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void clickGuanZhu(final AccessibilityNodeInfo accessibilityNodeInfo, final int i) {
        if (i < accessibilityNodeInfo.getChildCount()) {
            this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
                public void process() {
                    Toast.makeText(OperateDyShiXing.this.context, String.format("click %s / %s", new Object[]{Integer.valueOf(i), accessibilityNodeInfo.getChildCount()}), Toast.LENGTH_SHORT).show();
                    accessibilityNodeInfo.getChild(i).performAction(ACTION_CLICK);
                    OperateDyShiXing.this.clickMore(accessibilityNodeInfo, i);
                }
            });
        } else {
            swipeNext(accessibilityNodeInfo);
        }
    }

    /* access modifiers changed from: private */
    public void clickInput(final AccessibilityNodeInfo accessibilityNodeInfo, final int i, final String str) {
        this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                if (OperateDyShiXing.this.service.clickIfFindOne("发送消息…", new NodeHelper.OnFindCallback() {
                    public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                        return !TextUtils.isEmpty(str) && str.equals("android.widget.EditText") && !TextUtils.isEmpty(str2) && str2.equals("发送消息…");
                    }
                }, (DyAccessibilityService.OnClickFunction) null) != null) {
                    OperateDyShiXing.this.inputText(accessibilityNodeInfo, i, str);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void clickMore(final AccessibilityNodeInfo accessibilityNodeInfo, final int i) {
        this.service.wait(this.runSpeed + 2000, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                if (OperateDyShiXing.this.service.clickIfFindOne("更多", new NodeHelper.OnFindCallback() {
                    public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                        return !TextUtils.isEmpty(str) && str.equals("android.widget.ImageView") && !TextUtils.isEmpty(str3) && str3.equals("更多");
                    }
                }, (DyAccessibilityService.OnClickFunction) null) != null) {
                    OperateDyShiXing.this.clickShiXing(accessibilityNodeInfo, i);
                } else {
                    OperateDyShiXing.this.back3(accessibilityNodeInfo, i);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void clickSend(final AccessibilityNodeInfo accessibilityNodeInfo, final int i) {
        this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                if (OperateDyShiXing.this.service.clickIfFindOne("发送", new NodeHelper.OnFindCallback() {
                    public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                        return !TextUtils.isEmpty(str) && str.equals("android.widget.ImageView") && !TextUtils.isEmpty(str3) && str3.equals("发送");
                    }
                }, (DyAccessibilityService.OnClickFunction) null) != null) {
                    OperateDyShiXing.access$1208(OperateDyShiXing.this);
                    long currentTimeMillis = System.currentTimeMillis() - OperateDyShiXing.this.startTime;
                    Context access$1400 = OperateDyShiXing.this.context;
                    String access$1500 = OperateDyShiXing.this.formatTime(currentTimeMillis);
                    OperateDyShiXing operateDyShiXing = OperateDyShiXing.this;
                    Toast.makeText(access$1400,
                            String.format("\n运行了 %s\n平均每个 %s\n当前第 %s 个\n要运行总数 %s 个",
                                    access$1500,
                                    operateDyShiXing.formatTime(currentTimeMillis / ((long) operateDyShiXing.currCount)),
                                    OperateDyShiXing.this.currCount,
                                    OperateDyShiXing.this.allCount > 0 ? Integer.valueOf(OperateDyShiXing.this.allCount) : "不限"),
                            Toast.LENGTH_LONG).show();
                }
                OperateDyShiXing.this.back1(accessibilityNodeInfo, i);
            }
        });
    }

    /* access modifiers changed from: private */
    public void clickShiXing(final AccessibilityNodeInfo accessibilityNodeInfo, final int i) {
        this.service.wait(this.runSpeed + 2000, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                if (OperateDyShiXing.this.service.clickIfFindOne("发私信", new NodeHelper.OnFindCallback() {
                    public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                        return !TextUtils.isEmpty(str) && str.equals("android.widget.TextView") && !TextUtils.isEmpty(str2) && str2.equals("发私信");
                    }
                }, (DyAccessibilityService.OnClickFunction) null) != null) {
                    OperateDyShiXing.this.checkMsg(accessibilityNodeInfo, i);
                }
            }
        });
    }

    private void findGuanZhu() {
        this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                OperateDyShiXing.this.service.clickIfFindOne("RecyclerView", new NodeHelper.OnFindCallback() {
                    public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                        return !TextUtils.isEmpty(str) && (str.equals("android.support.v7.widget.RecyclerView") || str.equals("androidx.recyclerview.widget.RecyclerView")) && accessibilityNodeInfo.isScrollable();
                    }
                }, new DyAccessibilityService.OnClickFunction() {
                    public void click(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
                        OperateDyShiXing.this.clickGuanZhu(accessibilityNodeInfo, 0);
                    }
                });
            }
        });
    }

    /* access modifiers changed from: private */
    public void inputText(final AccessibilityNodeInfo accessibilityNodeInfo, final int i, final String str) {
        this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                OperateDyShiXing.this.service.clickIfFindOne("发送消息…", new NodeHelper.OnFindCallback() {
                    public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                        return !TextUtils.isEmpty(str) && str.equals("android.widget.EditText") && !TextUtils.isEmpty(str2) && str2.equals("发送消息…");
                    }
                }, new DyAccessibilityService.OnClickFunction() {
                    public void click(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
                        LOG.m11v("输入 " + str);
                        OperateDyShiXing.this.service.setNodeText(accessibilityNodeInfo, str);
                        OperateDyShiXing.this.clickSend(accessibilityNodeInfo, i);
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
                OperateDyShiXing.this.willStartOne();
            }
        });
    }

    /* access modifiers changed from: private */
    public void willStartOne() {
        this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                OperateDyShiXing.this.startOne();
            }
        });
    }

    public void start() {
        this.allCount = ActivityDyShiXing.readShiXing(this.context).intValue();
        this.currCount = 0;
        this.runSpeed = (long) (ActivityDyShiXing.readRunSpeed(this.context).intValue() * 1000);
        LOG.m11v(String.format("allCount = %s, runSpeed = %s", new Object[]{Integer.valueOf(this.allCount), Long.valueOf(this.runSpeed / 1000)}));
        if (!TextUtils.isEmpty(ActivityDyShiXing.randomReadMsg(this.context))) {
            startOne();
        } else {
            Toast.makeText(this.context, "请先设置私信话术", Toast.LENGTH_SHORT).show();
        }
    }

    public void startOne() {
        LOG.m11v("---------startOne");
        findGuanZhu();
    }
}
