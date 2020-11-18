package com.demacia.garen.tools;

import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;

import com.demacia.garen.activity.ActivityDyYangHao;
import com.demacia.garen.service.DyAccessibilityService;
import com.demacia.garen.utils.LOG;
import com.demacia.garen.utils.NodeHelper;

import java.util.List;

import static androidx.core.view.accessibility.AccessibilityNodeInfoCompat.ACTION_CLICK;
import static androidx.core.view.accessibility.AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD;

public class OperateDyYangHao extends OperateBase {
    /* access modifiers changed from: private */
    public long runSpeed;
    /* access modifiers changed from: private */
    public DyAccessibilityService service = null;

    public OperateDyYangHao(DyAccessibilityService dyAccessibilityService) {
        this.service = dyAccessibilityService;
    }

    /* access modifiers changed from: private */
    public void clickClose() {
        this.service.wait(this.runSpeed + 1000, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                LOG.m11v("back");
                OperateDyYangHao.this.service.performGlobalAction(1);
                OperateDyYangHao.this.swipeNext();
            }
        });
    }

    private void clickGuanZhu() {
        this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                OperateDyYangHao.this.service.clickIfFindOne("关注", new NodeHelper.OnFindCallback() {
                    public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                        return !TextUtils.isEmpty(str) && str.equals("android.widget.Button") && !TextUtils.isEmpty(str3) && str3.equals("关注");
                    }
                }, (DyAccessibilityService.OnClickFunction) null);
                OperateDyYangHao.this.clickHeart();
            }
        });
    }

    /* access modifiers changed from: private */
    public void clickHeart() {
        this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                OperateDyYangHao.this.service.clickIfFindOne("喜欢", new NodeHelper.OnFindCallback() {
                    public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                        return !TextUtils.isEmpty(str) && str.equals("android.widget.ImageView") && !TextUtils.isEmpty(str3) && str3.startsWith("未选中，喜欢");
                    }
                }, (DyAccessibilityService.OnClickFunction) null);
                if (Math.random() >= 0.5d || TextUtils.isEmpty(ActivityDyYangHao.randomReadMsg(OperateDyYangHao.this.context))) {
                    OperateDyYangHao.this.swipeNext();
                } else {
                    OperateDyYangHao.this.clickPingLun();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void clickInput() {
        this.service.wait(this.runSpeed + 3000, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                OperateDyYangHao.this.service.clickIfFindOne("输入框", new NodeHelper.OnFindCallback() {
                    public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                        return !TextUtils.isEmpty(str) && str.equals("android.widget.EditText") && !TextUtils.isEmpty(str2) && str2.equals("留下你的精彩评论吧");
                    }
                }, (DyAccessibilityService.OnClickFunction) null);
                OperateDyYangHao.this.inputText();
            }
        });
    }

    /* access modifiers changed from: private */
    public void clickPingLun() {
        this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                OperateDyYangHao.this.service.clickIfFindOne("评论", new NodeHelper.OnFindCallback() {
                    public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                        return !TextUtils.isEmpty(str) && str.equals("android.widget.ImageView") && !TextUtils.isEmpty(str3) && str3.startsWith("评论");
                    }
                }, (DyAccessibilityService.OnClickFunction) null);
                OperateDyYangHao.this.clickInput();
            }
        });
    }

    /* access modifiers changed from: private */
    public void clickSend(final AccessibilityNodeInfo accessibilityNodeInfo) {
        this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                List<AccessibilityNodeInfo> findNodes = NodeHelper.findNodes(accessibilityNodeInfo.getParent(), new NodeHelper.OnFindCallback() {
                    public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                        return i == 1 && !TextUtils.isEmpty(str) && str.equals("android.widget.ImageView");
                    }
                });
                if (findNodes.size() == 3) {
                    LOG.m11v("click send");
                    findNodes.get(findNodes.size() - 1).performAction(ACTION_CLICK);
                    OperateDyYangHao.this.clickClose();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void clickTarget(final AccessibilityNodeInfo accessibilityNodeInfo) {
        this.service.wait(this.runSpeed + 3000, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                if (OperateDyYangHao.this.service.clickIfFindOne("TargetList", new NodeHelper.OnFindCallback() {
                    public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                        return !TextUtils.isEmpty(str) && (str.equals("android.support.v7.widget.RecyclerView") || str.equals("androidx.recyclerview.widget.RecyclerView")) && accessibilityNodeInfo.isScrollable();
                    }
                }, new DyAccessibilityService.OnClickFunction() {
                    public void click(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
                        if (accessibilityNodeInfo.getChildCount() > 0) {
                            LOG.m11v("click first target");
                            accessibilityNodeInfo.getChild(0).performAction(ACTION_CLICK);
                            OperateDyYangHao.this.service.wait(OperateDyYangHao.this.runSpeed + 1000, new DyAccessibilityService.OnWaitCallback() {
                                public void process() {
                                    String randomReadMsg = ActivityDyYangHao.randomReadMsg(OperateDyYangHao.this.context);
                                    LOG.m11v("输入 " + randomReadMsg);
                                    OperateDyYangHao.this.service.inputNode(accessibilityNodeInfo, randomReadMsg);
                                    OperateDyYangHao.this.clickSend(accessibilityNodeInfo);
                                }
                            });
                        }
                    }
                }) == null) {
                    OperateDyYangHao.this.service.wait(OperateDyYangHao.this.runSpeed + 1000, new DyAccessibilityService.OnWaitCallback() {
                        public void process() {
                            String randomReadMsg = ActivityDyYangHao.randomReadMsg(OperateDyYangHao.this.context);
                            LOG.m11v("输入 " + randomReadMsg);
                            OperateDyYangHao.this.service.setNodeText(accessibilityNodeInfo, randomReadMsg);
                            OperateDyYangHao.this.clickSend(accessibilityNodeInfo);
                        }
                    });
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void inputText() {
        this.service.wait(this.runSpeed + 1000, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                final String readAt = ActivityDyYangHao.readAt(OperateDyYangHao.this.context);
                if (TextUtils.isEmpty(readAt)) {
                    OperateDyYangHao.this.service.clickIfFindOne("输入框", new NodeHelper.OnFindCallback() {
                        public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                            return !TextUtils.isEmpty(str) && str.equals("android.widget.EditText") && !TextUtils.isEmpty(str2) && str2.equals("留下你的精彩评论吧");
                        }
                    }, new DyAccessibilityService.OnClickFunction() {
                        public void click(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
                            String randomReadMsg = ActivityDyYangHao.randomReadMsg(OperateDyYangHao.this.context);
                            LOG.m11v("输入 " + randomReadMsg);
                            OperateDyYangHao.this.service.setNodeText(accessibilityNodeInfo, randomReadMsg);
                            OperateDyYangHao.this.clickSend(accessibilityNodeInfo);
                        }
                    });
                } else {
                    OperateDyYangHao.this.service.clickIfFindOne("输入框", new NodeHelper.OnFindCallback() {
                        public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                            return !TextUtils.isEmpty(str) && str.equals("android.widget.EditText") && !TextUtils.isEmpty(str2) && str2.equals("留下你的精彩评论吧");
                        }
                    }, new DyAccessibilityService.OnClickFunction() {
                        public void click(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
                            String str2 = readAt;
                            if (!str2.startsWith("@")) {
                                str2 = "@" + str2;
                            }
                            LOG.m11v("输入 " + str2);
                            OperateDyYangHao.this.service.inputNode(accessibilityNodeInfo, str2);
                            OperateDyYangHao.this.clickTarget(accessibilityNodeInfo);
                        }
                    });
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void swipeNext() {
        this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                OperateDyYangHao.this.service.clickIfFindOne("NEXT", new NodeHelper.OnFindCallback() {
                    public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                        return !TextUtils.isEmpty(str) && (str.equals("android.support.v4.view.ViewPager") || str.equals("androidx.viewpager.widget.ViewPager")) && !TextUtils.isEmpty(str3) && str3.equals("视频");
                    }
                }, new DyAccessibilityService.OnClickFunction() {
                    public void click(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
                        LOG.m11v("swipeNext");
                        accessibilityNodeInfo.performAction(ACTION_SCROLL_FORWARD);
                    }
                });
                OperateDyYangHao.this.willStartOne();
            }
        });
    }

    /* access modifiers changed from: private */
    public void willStartOne() {
        this.service.wait(this.runSpeed, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                OperateDyYangHao.this.startOne();
            }
        });
    }

    public void start() {
        long intValue = (long) (ActivityDyYangHao.readRunSpeed(this.context).intValue() * 1000);
        this.runSpeed = intValue;
        LOG.m11v(String.format("runSpeed = %s", new Object[]{Long.valueOf(intValue / 1000)}));
        startOne();
    }

    public void startOne() {
        LOG.m11v("---------startOne");
        clickHeart();
    }
}
