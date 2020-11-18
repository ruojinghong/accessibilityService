package com.demacia.garen.tools;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.demacia.garen.activity.ActivityDyPingXing;
import com.demacia.garen.service.DyAccessibilityService;
import com.demacia.garen.utils.LOG;
import com.demacia.garen.utils.NodeHelper;
import com.demacia.garen.view.Ball;

import static androidx.core.view.accessibility.AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD;

public class OperateDyPingXing extends OperateBase {
    /* access modifiers changed from: private */
    public int allCount;
    /* access modifiers changed from: private */
    public int currCount;
    private long runSpeed;
    /* access modifiers changed from: private */
    public DyAccessibilityService service = null;

    public OperateDyPingXing(DyAccessibilityService dyAccessibilityService) {
        this.service = dyAccessibilityService;
    }

    static /* synthetic */ int access$1208(OperateDyPingXing operateDyPingXing) {
        int i = operateDyPingXing.currCount;
        operateDyPingXing.currCount = i + 1;
        return i;
    }

    /* access modifiers changed from: private */
    public void back1(final AccessibilityNodeInfo accessibilityNodeInfo, final int i) {
        this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                LOG.m11v("back 1");
                OperateDyPingXing.this.service.performGlobalAction(1);
                OperateDyPingXing.this.back2(accessibilityNodeInfo, i);
            }
        });
    }

    /* access modifiers changed from: private */
    public void back2(final AccessibilityNodeInfo accessibilityNodeInfo, final int i) {
        this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                LOG.m11v("back 2");
                OperateDyPingXing.this.service.performGlobalAction(1);
                OperateDyPingXing.this.back3(accessibilityNodeInfo, i);
            }
        });
    }

    /* access modifiers changed from: private */
    public void back3(final AccessibilityNodeInfo accessibilityNodeInfo, final int i) {
        this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                LOG.m11v("back 3");
                OperateDyPingXing.this.service.performGlobalAction(1);
                if (OperateDyPingXing.this.allCount <= 0 || OperateDyPingXing.this.currCount < OperateDyPingXing.this.allCount) {
                    OperateDyPingXing.this.clickGuanZhu(accessibilityNodeInfo, i + 1);
                    return;
                }
                Ball.stopRunning();
                Toast.makeText(OperateDyPingXing.this.context, "运行完毕", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /* access modifiers changed from: private */
    public void checkMsg(final AccessibilityNodeInfo accessibilityNodeInfo, final int i) {
        this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                final String randomReadMsg = ActivityDyPingXing.randomReadMsg(OperateDyPingXing.this.context);
                if (OperateDyPingXing.this.service.clickIfFindOne("检查消息", new NodeHelper.OnFindCallback() {
                    public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                        return !TextUtils.isEmpty(str) && str.equals("android.widget.TextView") && !TextUtils.isEmpty(str2) && str2.equals(randomReadMsg);
                    }
                }, new DyAccessibilityService.OnClickFunction() {
                    public void click(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
                    }
                }) == null) {
                    OperateDyPingXing.this.clickInput(accessibilityNodeInfo, i, randomReadMsg);
                } else {
                    OperateDyPingXing.this.back2(accessibilityNodeInfo, i);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void clickGuanZhu(final AccessibilityNodeInfo accessibilityNodeInfo, final int i) {
        if (i < accessibilityNodeInfo.getChildCount()) {
            this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
                public void process() {
                    if (OperateDyPingXing.this.service.clickIfFindOne(accessibilityNodeInfo.getChild(i), "HEAD", new NodeHelper.OnFindCallback() {
                        public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                            return !TextUtils.isEmpty(str) && str.equals("android.widget.ImageView") && !TextUtils.isEmpty(str4) && str4.equals("com.ss.android.ugc.aweme:id/jt");
                        }
                    }, new DyAccessibilityService.OnClickFunction() {
                        public void click(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
                            Rect rect = new Rect();
                            accessibilityNodeInfo.getBoundsInScreen(rect);
                            if (OperateDyPingXing.this.service.click(rect.centerX(), rect.centerY(), (AccessibilityService.GestureResultCallback) null)) {
                                Toast.makeText(OperateDyPingXing.this.context, String.format("click %s / %s", new Object[]{Integer.valueOf(i), Integer.valueOf(accessibilityNodeInfo.getChildCount())}), Toast.LENGTH_LONG).show();
                                OperateDyPingXing.this.clickMore(accessibilityNodeInfo, i);
                            }
                        }
                    }) == null) {
                        OperateDyPingXing.this.clickGuanZhu(accessibilityNodeInfo, i + 1);
                    }
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
                if (OperateDyPingXing.this.service.clickIfFindOne("发送消息…", new NodeHelper.OnFindCallback() {
                    public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                        return !TextUtils.isEmpty(str) && str.equals("android.widget.EditText") && !TextUtils.isEmpty(str2) && str2.equals("发送消息…");
                    }
                }, (DyAccessibilityService.OnClickFunction) null) != null) {
                    OperateDyPingXing.this.inputText(accessibilityNodeInfo, i, str);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void clickMore(final AccessibilityNodeInfo accessibilityNodeInfo, final int i) {
        this.service.wait(this.runSpeed + 2000, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                if (OperateDyPingXing.this.service.clickIfFindOne("更多", new NodeHelper.OnFindCallback() {
                    public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                        return !TextUtils.isEmpty(str) && str.equals("android.widget.ImageView") && !TextUtils.isEmpty(str3) && str3.equals("更多");
                    }
                }, (DyAccessibilityService.OnClickFunction) null) != null) {
                    OperateDyPingXing.this.clickShiXing(accessibilityNodeInfo, i);
                } else {
                    OperateDyPingXing.this.back3(accessibilityNodeInfo, i);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void clickSend(final AccessibilityNodeInfo accessibilityNodeInfo, final int i) {
        this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                if (OperateDyPingXing.this.service.clickIfFindOne("发送", new NodeHelper.OnFindCallback() {
                    public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                        return !TextUtils.isEmpty(str) && str.equals("android.widget.ImageView") && !TextUtils.isEmpty(str3) && str3.equals("发送");
                    }
                }, (DyAccessibilityService.OnClickFunction) null) != null) {
                    OperateDyPingXing.access$1208(OperateDyPingXing.this);
                    long currentTimeMillis = System.currentTimeMillis() - OperateDyPingXing.this.startTime;
                    Context access$1400 = OperateDyPingXing.this.context;
                    String access$1500 = OperateDyPingXing.this.formatTime(currentTimeMillis);
                    OperateDyPingXing operateDyPingXing = OperateDyPingXing.this;
                    Toast.makeText(access$1400, String.format("\n运行了 %s\n平均每个 %s\n当前第 %s 个\n要运行总数 %s 个", new Object[]{access$1500, operateDyPingXing.formatTime(currentTimeMillis / ((long) operateDyPingXing.currCount)), Integer.valueOf(OperateDyPingXing.this.currCount), OperateDyPingXing.this.allCount > 0 ? Integer.valueOf(OperateDyPingXing.this.allCount) : "不限"}), Toast.LENGTH_LONG).show();
                }
                OperateDyPingXing.this.back1(accessibilityNodeInfo, i);
            }
        });
    }

    /* access modifiers changed from: private */
    public void clickShiXing(final AccessibilityNodeInfo accessibilityNodeInfo, final int i) {
        this.service.wait(this.runSpeed + 2000, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                if (OperateDyPingXing.this.service.clickIfFindOne("发私信", new NodeHelper.OnFindCallback() {
                    public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                        return !TextUtils.isEmpty(str) && str.equals("android.widget.TextView") && !TextUtils.isEmpty(str2) && str2.equals("发私信");
                    }
                }, (DyAccessibilityService.OnClickFunction) null) != null) {
                    OperateDyPingXing.this.checkMsg(accessibilityNodeInfo, i);
                }
            }
        });
    }

    private void findGuanZhu() {
        this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                OperateDyPingXing.this.service.clickIfFindOne("RecyclerView", new NodeHelper.OnFindCallback() {
                    public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                        return !TextUtils.isEmpty(str) && (str.equals("android.support.v7.widget.RecyclerView") || str.equals("androidx.recyclerview.widget.RecyclerView")) && accessibilityNodeInfo.isScrollable();
                    }
                }, new DyAccessibilityService.OnClickFunction() {
                    public void click(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
                        OperateDyPingXing.this.clickGuanZhu(accessibilityNodeInfo, 0);
                    }
                });
            }
        });
    }

    /* access modifiers changed from: private */
    public void inputText(final AccessibilityNodeInfo accessibilityNodeInfo, final int i, final String str) {
        this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                OperateDyPingXing.this.service.clickIfFindOne("发送消息…", new NodeHelper.OnFindCallback() {
                    public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                        return !TextUtils.isEmpty(str) && str.equals("android.widget.EditText") && !TextUtils.isEmpty(str2) && str2.equals("发送消息…");
                    }
                }, new DyAccessibilityService.OnClickFunction() {
                    public void click(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
                        LOG.m11v("输入 " + str);
                        OperateDyPingXing.this.service.setNodeText(accessibilityNodeInfo, str);
                        OperateDyPingXing.this.clickSend(accessibilityNodeInfo, i);
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
                OperateDyPingXing.this.willStartOne();
            }
        });
    }

    /* access modifiers changed from: private */
    public void willStartOne() {
        this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                OperateDyPingXing.this.startOne();
            }
        });
    }

    public void start() {
        this.allCount = ActivityDyPingXing.readShiXing(this.context).intValue();
        this.currCount = 0;
        this.runSpeed = (long) (ActivityDyPingXing.readRunSpeed(this.context).intValue() * 1000);
        LOG.m11v(String.format("allCount = %s, runSpeed = %s", new Object[]{Integer.valueOf(this.allCount), Long.valueOf(this.runSpeed / 1000)}));
        if (!TextUtils.isEmpty(ActivityDyPingXing.randomReadMsg(this.context))) {
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
