package com.demacia.garen.tools;

import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.demacia.garen.activity.ActivityDyLive;
import com.demacia.garen.service.DyAccessibilityService;
import com.demacia.garen.utils.LOG;
import com.demacia.garen.utils.NodeHelper;
import java.util.List;

import static androidx.core.view.accessibility.AccessibilityNodeInfoCompat.ACTION_CLICK;

public class OperateDyLive extends OperateBase {
    /* access modifiers changed from: private */
    public DyAccessibilityService service = null;

    public OperateDyLive(DyAccessibilityService dyAccessibilityService) {
        this.service = dyAccessibilityService;
    }

    private void clickInput() {
        DyAccessibilityService dyAccessibilityService = this.service;
        dyAccessibilityService.wait(dyAccessibilityService.largeWaitTime, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                if (OperateDyLive.this.service.clickIfFindOne("说点什么...", new NodeHelper.OnFindCallback() {
                    public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                        return !TextUtils.isEmpty(str) && str.equals("android.widget.TextView") && !TextUtils.isEmpty(str2) && str2.equals("说点什么...");
                    }
                }, (DyAccessibilityService.OnClickFunction) null) != null) {
                    OperateDyLive.this.inputText();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void clickSend(final AccessibilityNodeInfo accessibilityNodeInfo) {
        DyAccessibilityService dyAccessibilityService = this.service;
        dyAccessibilityService.wait(dyAccessibilityService.largeWaitTime, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                NodeHelper.printNode(accessibilityNodeInfo.getParent());
                List<AccessibilityNodeInfo> findNodes = NodeHelper.findNodes(accessibilityNodeInfo.getParent(), new NodeHelper.OnFindCallback() {
                    public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                        return i == 1 && !TextUtils.isEmpty(str) && str.equals("android.widget.ImageView");
                    }
                });
                if (findNodes.size() == 2) {
                    LOG.m11v("click send");
                    findNodes.get(findNodes.size() - 1).performAction(ACTION_CLICK);
                    OperateDyLive.this.willStartOne();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void inputText() {
        DyAccessibilityService dyAccessibilityService = this.service;
        dyAccessibilityService.wait(dyAccessibilityService.largeWaitTime, new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                OperateDyLive.this.service.clickIfFindOne("EditText", new NodeHelper.OnFindCallback() {
                    public boolean find(AccessibilityNodeInfo accessibilityNodeInfo, int i, String str, String str2, String str3, String str4) {
                        return !TextUtils.isEmpty(str) && str.equals("android.widget.EditText");
                    }
                }, new DyAccessibilityService.OnClickFunction() {
                    public void click(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
                        String randomReadMsg = ActivityDyLive.randomReadMsg(OperateDyLive.this.context);
                        LOG.m11v("输入 " + randomReadMsg);
                        OperateDyLive.this.service.setNodeText(accessibilityNodeInfo, randomReadMsg);
                        OperateDyLive.this.clickSend(accessibilityNodeInfo);
                    }
                });
            }
        });
    }

    /* access modifiers changed from: private */
    public void willStartOne() {
        this.service.wait((long) (ActivityDyLive.readLive(this.context).intValue() * 1000), new DyAccessibilityService.OnWaitCallback() {
            public void process() {
                OperateDyLive.this.startOne();
            }
        });
    }

    public void start() {
        if (!TextUtils.isEmpty(ActivityDyLive.randomReadMsg(this.context))) {
            startOne();
        } else {
            Toast.makeText(this.context, "请先设置直播引流话术", Toast.LENGTH_SHORT).show();
        }
    }

    public void startOne() {
        LOG.m11v("---------startOne");
        clickInput();
    }
}
