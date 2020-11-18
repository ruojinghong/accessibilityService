package com.demacia.garen.view;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.demacia.garen.R;
import com.demacia.garen.service.DyAccessibilityService;
import com.demacia.garen.utils.SysUtils;

public class MenuPanel extends RelativeLayout implements View.OnClickListener {
    /* access modifiers changed from: private */
    public static Context context;
    private static WindowManager.LayoutParams layoutParams;
    private static MenuPanel menuPanelView;
    private static WindowManager windowManager;
    private Holder holder = new Holder();
    private OnListener listener = null;

    private class Holder {
        private Holder() {
        }
    }

    public interface OnListener {
        void onClick(int i);
    }

    public MenuPanel(Context context2) {
        super(context2);
        LayoutInflater.from(context2).inflate(R.layout.view_menu_panel, this, true);
        initHolder();
        // findViewById(R.id.f57bg).setOnClickListener(this);
        findViewById(R.id.optDyYingLiu).setOnClickListener(this);
        findViewById(R.id.optDyYangHao).setOnClickListener(this);
        findViewById(R.id.optDyQuGuan).setOnClickListener(this);
        findViewById(R.id.optDyBothGuan).setOnClickListener(this);
        findViewById(R.id.optDyFenGuan).setOnClickListener(this);
        findViewById(R.id.optDyShiXing).setOnClickListener(this);
        findViewById(R.id.optDyPingXing).setOnClickListener(this);
        findViewById(R.id.optDyLive).setOnClickListener(this);
        findViewById(R.id.test1).setOnClickListener(this);
        findViewById(R.id.test2).setOnClickListener(this);
        if (SysUtils.isDebug()) {
            findViewById(R.id.test1).setVisibility(VISIBLE);
            findViewById(R.id.test2).setVisibility(VISIBLE);
            return;
        }
        findViewById(R.id.test1).setVisibility(GONE);
        findViewById(R.id.test2).setVisibility(GONE);
    }

    public static void hidePanel() {
        MenuPanel menuPanel = menuPanelView;
        if (menuPanel != null) {
            windowManager.removeView(menuPanel);
            menuPanelView = null;
        }
    }

    private void initHolder() {
    }

    public static boolean isShow() {
        return menuPanelView != null;
    }

    private void setListener(OnListener onListener) {
        this.listener = onListener;
    }

    public static void showPanel(Context context2, OnListener onListener) {
        if (menuPanelView == null) {
            Context applicationContext = context2.getApplicationContext();
            context = applicationContext;
            windowManager = (WindowManager) applicationContext.getSystemService(Context.WINDOW_SERVICE);
            layoutParams = new WindowManager.LayoutParams();
            if (Build.VERSION.SDK_INT > 23) {
                layoutParams.type = 2038;
            } else {
                layoutParams.type = 2003;
            }
            layoutParams.flags = 808;
            layoutParams.width = -1;
            layoutParams.height = -1;
            layoutParams.x = 0;
            layoutParams.y = 0;
            layoutParams.format = 1;
            MenuPanel menuPanel = new MenuPanel(context);
            menuPanelView = menuPanel;
            menuPanel.setListener(onListener);
            try {
                windowManager.addView(menuPanelView, layoutParams);
            } catch (Exception e) {
                try {
                    if (Build.VERSION.SDK_INT > 23) {
                        layoutParams.type = 2003;
                    } else {
                        layoutParams.type = 2038;
                    }
                    windowManager.addView(menuPanelView, layoutParams);
                } catch (Exception e2) {
                }
            }
        }
    }

    public void onClick(View view) {
      /*  if (view.getId() == R.id.f57bg) {
            hidePanel();
        } else */if (view.getId() == R.id.optDyYingLiu) {
            hidePanel();
            OnListener onListener = this.listener;
            if (onListener != null) {
                onListener.onClick(1);
            }
        } else if (view.getId() == R.id.optDyYangHao) {
            hidePanel();
            OnListener onListener2 = this.listener;
            if (onListener2 != null) {
                onListener2.onClick(2);
            }
        } else if (view.getId() == R.id.optDyQuGuan) {
            hidePanel();
            OnListener onListener3 = this.listener;
            if (onListener3 != null) {
                onListener3.onClick(3);
            }
        } else if (view.getId() == R.id.optDyBothGuan) {
            hidePanel();
            OnListener onListener4 = this.listener;
            if (onListener4 != null) {
                onListener4.onClick(4);
            }
        } else if (view.getId() == R.id.optDyFenGuan) {
            hidePanel();
            OnListener onListener5 = this.listener;
            if (onListener5 != null) {
                onListener5.onClick(5);
            }
        } else if (view.getId() == R.id.optDyShiXing) {
            hidePanel();
            OnListener onListener6 = this.listener;
            if (onListener6 != null) {
                onListener6.onClick(6);
            }
        } else if (view.getId() == R.id.optDyPingXing) {
            hidePanel();
            OnListener onListener7 = this.listener;
            if (onListener7 != null) {
                onListener7.onClick(7);
            }
        } else if (view.getId() == R.id.optDyLive) {
            hidePanel();
            OnListener onListener8 = this.listener;
            if (onListener8 != null) {
                onListener8.onClick(8);
            }
        } else if (view.getId() == R.id.test1) {
            hidePanel();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent();
                    intent.setAction(DyAccessibilityService.Action_test(MenuPanel.context));
                    MenuPanel.context.sendBroadcast(intent);
                }
            }, 100);
        } else if (view.getId() == R.id.test2) {
            hidePanel();
        }
    }
}
