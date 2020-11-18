package com.demacia.garen.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.demacia.garen.R;
import com.demacia.garen.service.DyAccessibilityService;
import com.demacia.garen.utils.LOG;
import com.demacia.garen.utils.SysUtils;

import static android.content.Context.WINDOW_SERVICE;
import static android.view.WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
import static android.view.WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

public class Ball extends RelativeLayout implements View.OnClickListener {
    private static final int OFFSET_X = SysUtils.dp2px(25.0f);
    /* access modifiers changed from: private */
    public static Ball ballView = null;
    /* access modifiers changed from: private */
    public static Context context = null;
    /* access modifiers changed from: private */
    public static WindowManager.LayoutParams layoutParams = null;
    /* access modifiers changed from: private */
    public static WindowManager windowManager = null;
    private Holder holder = new Holder();
    private boolean isRunning = false;

    private class Holder {
        /* access modifiers changed from: private */
        public View core;

        private Holder() {
        }
    }

    public Ball(Context context2) {
        super(context2);
        LayoutInflater.from(context2).inflate(R.layout.view_ball, this, true);
        initHolder();
    }

    /* access modifiers changed from: private */
    public void checkPos() {
        LOG.m11v("checkPos");
        layoutParams.x = ((-SysUtils.screenWidth(getContext())) / 2) + OFFSET_X;
        WindowManager.LayoutParams layoutParams2 = layoutParams;
        layoutParams2.y = Math.max(layoutParams2.y, (-SysUtils.screenHeight(getContext())) / 4);
        WindowManager.LayoutParams layoutParams3 = layoutParams;
        layoutParams3.y = Math.min(layoutParams3.y, SysUtils.screenHeight(getContext()) / 4);
        windowManager.updateViewLayout(ballView, layoutParams);
    }

    /* access modifiers changed from: private */
    public static void clickBall() {
        if (!isShow()) {
            return;
        }
        if (isRunning()) {
            stopRunningImpl();
        } else if (MenuPanel.isShow()) {
            MenuPanel.hidePanel();
        } else {
            MenuPanel.showPanel(context, new MenuPanel.OnListener() {
                public void onClick(int i) {
                    Intent intent = new Intent();
                    intent.setAction(DyAccessibilityService.Action_Start(Ball.context));
                    intent.putExtra("operateType", i);
                    Ball.context.sendBroadcast(intent);
                    Ball.ballView.setRunning(true);
                }
            });
        }
    }

    public static void hideBall() {
        Ball ball = ballView;
        if (ball != null) {
            windowManager.removeView(ball);
            ballView = null;
        }
    }

    private void initHolder() {
        View unused = this.holder.core = findViewById(R.id.core);
    }

    public static boolean isRunning() {
        Ball ball = ballView;
        if (ball != null) {
            return ball.isRunning;
        }
        return false;
    }

    public static boolean isShow() {
        return ballView != null;
    }

    /* access modifiers changed from: private */
    public void setRunning(boolean z) {
        this.isRunning = z;
        if (z) {
            this.holder.core.setBackgroundResource(R.drawable.ball_core_run);
            setKeepScreenOn(true);
            return;
        }
        this.holder.core.setBackgroundResource(R.drawable.ball_core_stop);
        setKeepScreenOn(false);
    }

    public static void showBall(final Context context2) {
        if (ballView == null) {
            Context applicationContext = context2.getApplicationContext();
            context = applicationContext;
            windowManager = (WindowManager) applicationContext.getSystemService(WINDOW_SERVICE);
            layoutParams = new WindowManager.LayoutParams();
            int a =  Build.VERSION.SDK_INT;
            if (Build.VERSION.SDK_INT > 23) {
                layoutParams.type = TYPE_APPLICATION_OVERLAY;
            } else {
                layoutParams.type = TYPE_SYSTEM_ALERT;
            }
            layoutParams.flags = 808;
            layoutParams.width = -2;
            layoutParams.height = -2;
            layoutParams.x = ((-SysUtils.screenWidth(context)) / 2) + OFFSET_X;
            layoutParams.y = 0;
            layoutParams.format = 1;
            Ball ball = new Ball(context);
            ballView = ball;
            ball.setOnTouchListener(new View.OnTouchListener() {
                private boolean hasMove = false;
                private int initX = 0;
                private int initY = 0;
                private PointF touchBegin = null;


                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == 0) {
                        this.touchBegin = new PointF(motionEvent.getRawX(), motionEvent.getRawY());
                        this.initX = Ball.layoutParams.x;
                        this.initY = Ball.layoutParams.y;
                        this.hasMove = false;
                    } else if (motionEvent.getAction() == 2) {
                        PointF pointF = new PointF(motionEvent.getRawX(), motionEvent.getRawY());
                        int i = (int) (pointF.x - this.touchBegin.x);
                        int i2 = (int) (pointF.y - this.touchBegin.y);
                        if (!this.hasMove && !Ball.isRunning() && (Math.abs(i) > 4 || Math.abs(i2) > 4)) {
                            this.hasMove = true;
                        }
                        if (this.hasMove) {
                            Ball.layoutParams.x = i + this.initX;
                            Ball.layoutParams.y = i2 + this.initY;
                            Ball.windowManager.updateViewLayout(Ball.ballView, Ball.layoutParams);
                        }
                    } else if (motionEvent.getAction() == 1) {
                        if (this.hasMove) {
                            Ball.ballView.checkPos();
                        } else {
                            LOG.m11v("click ball");
                            Ball.clickBall();
                        }
                    }
                    return true;
                }
            });
            try {
                windowManager.addView(ballView, layoutParams);
                LOG.toast(context2, "打开成功");
            } catch (Exception e) {
                e.printStackTrace();
                LOG.toast(context2, "打开失败");
                try {
                    if (Build.VERSION.SDK_INT > 23) {
                        layoutParams.type = 2003;
                    } else {
                        layoutParams.type = 2038;
                    }
                    windowManager.addView(ballView, layoutParams);
                    LOG.toast(context2, "打开成功2");
                } catch (Exception e2) {
                    e2.printStackTrace();
                    LOG.toast(context2, "打开失败2");
                    AlertDialog.Builder builder = new AlertDialog.Builder(context2);
                    builder.setTitle("提示信息");
                    builder.setMessage("悬浮窗权限未打开，请单击【设置】按钮前往设置中心进行权限授权。");
                    builder.setNeutralButton("取消", (DialogInterface.OnClickListener) null);
                    builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                            intent.setData(Uri.parse("package:" + context2.getPackageName()));
                            context2.startActivity(intent);
                        }
                    });
                    builder.show();
                }
            }
        }
    }

    public static void stopRunning() {
        if (isShow() && isRunning()) {
            stopRunningImpl();
        }
    }

    private static void stopRunningImpl() {
        Intent intent = new Intent();
        intent.setAction(DyAccessibilityService.Action_Stop(context));
        context.sendBroadcast(intent);
        ballView.setRunning(false);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        LOG.m11v("--------------------------- onAttachedToWindow : " + getClass().getSimpleName());
    }

    public void onClick(View view) {
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        checkPos();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LOG.m11v("--------------------------- onDetachedFromWindow : " + getClass().getSimpleName());
    }
}
