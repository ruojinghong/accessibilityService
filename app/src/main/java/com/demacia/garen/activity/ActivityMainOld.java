package com.demacia.garen.activity;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
// import android.support.p000v4.app.NotificationCompat;
// import android.support.p000v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.view.ViewCompat;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.demacia.garen.R;
import com.demacia.garen.service.DyAccessibilityService;
import com.demacia.garen.utils.HttpUtils;
import com.demacia.garen.utils.LOG;
import com.demacia.garen.utils.SPHelper;
import com.demacia.garen.utils.SysUtils;
import com.demacia.garen.view.Ball;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class ActivityMainOld extends BaseActivity implements View.OnClickListener {
    public static final String SP_Key_active = "active";
    public static final String SP_Key_code = "code";
    /* access modifiers changed from: private */
    public Holder holder = new Holder();
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            LOG.m11v("Action:" + intent.getAction());
            // TODO: 2020/11/17
            if (intent.getAction().equals(ActivityMain.Action_Connected(context))) {
                ActivityMainOld.this.holder.setAccessibilityPermission.setText("无障碍服务已开启");
                ActivityMainOld.this.holder.setAccessibilityPermission.setTextColor(-16776961);
                // TODO: 2020/11/17
            } else if (intent.getAction().equals(ActivityMain.Action_Unbind(context))) {
                ActivityMainOld.this.holder.setAccessibilityPermission.setText("开启无障碍服务");
                ActivityMainOld.this.holder.setAccessibilityPermission.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            }
        }
    };

    private class Holder {
        /* access modifiers changed from: private */
        public EditText activeCode;
        /* access modifiers changed from: private */
        public ViewGroup activePage;
        /* access modifiers changed from: private */
        public TextView info;
        /* access modifiers changed from: private */
        public Button setAccessibilityPermission;

        private Holder() {
        }
    }

    public static String Action_Connected(Context context) {
        return context.getPackageName() + "." + ActivityMain.class.getName() + ".connected";
    }

    public static String Action_Unbind(Context context) {
        return context.getPackageName() + "." + ActivityMain.class.getName() + ".unbind";
    }

    private void gogogo() {
        initView();
        if (SPHelper.getBoolean(this.context, "active", false)) {
            showActivePage(false);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ActivityMain.Action_Connected(this));
        intentFilter.addAction(ActivityMain.Action_Unbind(this));
        registerReceiver(this.mReceiver, intentFilter);
    }

    private void gotoActive(final String str) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("mobileId", SysUtils.getOnlyId(this.context));
        hashMap.put("checkKey", str);
        HttpUtils.doHttpPost(this.context, "http://loan.eco-ozg.cn/jy/jyMobileKey/check", (Map<String, String>) hashMap, (HttpUtils.OnCallback) new HttpUtils.OnCallback() {
            public void onResponse(String str) {
                if (!TextUtils.isEmpty(str)) {
                    JSONObject parseObject = JSONObject.parseObject(str, Feature.OrderedField);
                    if (parseObject == null) {
                        Toast.makeText(ActivityMainOld.this.context, "返回数据解析失败", Toast.LENGTH_SHORT).show();
                    } else if (parseObject.getInteger("code").intValue() == 200) {
                        SPHelper.putBoolean(ActivityMainOld.this.context, "active", true);
                        SPHelper.putString(ActivityMainOld.this.context, "code", str);
                        ActivityMainOld.this.showActivePage(false);
                    } else {
                        Toast.makeText(ActivityMainOld.this.context, parseObject.getString(NotificationCompat.CATEGORY_MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ActivityMainOld.this.context, "返回为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initHolder() {
        Button unused = this.holder.setAccessibilityPermission = (Button) findViewById(R.id.setAccessibilityPermission);
        ViewGroup unused2 = this.holder.activePage = (ViewGroup) findViewById(R.id.activePage);
        TextView unused3 = this.holder.info = (TextView) findViewById(R.id.info);
        EditText unused4 = this.holder.activeCode = (EditText) findViewById(R.id.activeCode);
    }

    private void initView() {
        this.holder.info.setText(String.format("设备号：%s", new Object[]{SysUtils.getOnlyId(this.context)}));
        findViewById(R.id.activeBtn).setOnClickListener(this);
        findViewById(R.id.setBallPermission).setOnClickListener(this);
        findViewById(R.id.setAccessibilityPermission).setOnClickListener(this);
        findViewById(R.id.info).setOnClickListener(this);
        findViewById(R.id.funcDyYingLiu).setOnClickListener(this);
        findViewById(R.id.funcDyYangHao).setOnClickListener(this);
        findViewById(R.id.funcDyQuGuan).setOnClickListener(this);
        findViewById(R.id.funcDyBothGuan).setOnClickListener(this);
        findViewById(R.id.funcDyFenGuan).setOnClickListener(this);
        findViewById(R.id.funcDyShiXing).setOnClickListener(this);
        findViewById(R.id.funcDyLive).setOnClickListener(this);
    }

    private void showAccessibilitySetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setTitle("提示信息");
        builder.setMessage("无障碍服务未开启，请单击【开启】按钮前往设置中心开启无障碍服务。");
        builder.setNeutralButton("取消", (DialogInterface.OnClickListener) null);
        builder.setPositiveButton("开启", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
                // TODO: 2020/11/17   268435456
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                ActivityMainOld.this.context.startActivity(intent);
            }
        });
        builder.show();
    }

    /* access modifiers changed from: private */
    public void showActivePage(boolean z) {
        if (z) {
            this.holder.activePage.setVisibility(View.VISIBLE);
            Ball.hideBall();
            return;
        }
        this.holder.activePage.setVisibility(View.INVISIBLE);
        Ball.showBall(this.context);
        boolean isServiceON = isServiceON(DyAccessibilityService.class.getName());
        LOG.m11v("dyIsOn : " + isServiceON);
        if (!isServiceON) {
            this.holder.setAccessibilityPermission.setText("开启无障碍服务");
            this.holder.setAccessibilityPermission.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            showAccessibilitySetting();
            return;
        }
        this.holder.setAccessibilityPermission.setText("无障碍服务已开启");
        this.holder.setAccessibilityPermission.setTextColor(-16776961);
    }

    public boolean isServiceON(String str) {
        Context context = this.context;
        Context context2 = this.context;
        List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) context.getSystemService(ACTIVITY_SERVICE)).getRunningServices(100);
        for (int i = 0; i < runningServices.size(); i++) {
            if (runningServices.get(i).service.getClassName().contains(str)) {
                return true;
            }
        }
        return false;
    }

    public void onClick(View view) {
        // TODO: 2020/11/17
        if (view.getId() == R.id.activeBtn) {
            String trim = this.holder.activeCode.getText().toString().trim();
            if (TextUtils.isEmpty(trim)) {
                Toast.makeText(this.context, "请输入激活码", Toast.LENGTH_SHORT).show();
            } else {
                gotoActive(trim);
            }
        } else if (view.getId() == R.id.setBallPermission) {
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.parse("package:" + this.context.getPackageName()));
            startActivity(intent);
        } else if (view.getId() == R.id.setBallPermission) {
            Intent intent2 = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
            intent2.setFlags(FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent2);
        } else if (view.getId() == R.id.funcDyYingLiu) {
            startActivity(new Intent(this, ActivityDyYingLiu.class));
        } else if (view.getId() == R.id.funcDyYangHao) {
            startActivity(new Intent(this, ActivityDyYangHao.class));
        } else if (view.getId() == R.id.funcDyQuGuan) {
            startActivity(new Intent(this, ActivityDyQuGuan.class));
        } else if (view.getId() == R.id.funcDyBothGuan) {
            startActivity(new Intent(this, ActivityDyBothGuan.class));
        } else if (view.getId() == R.id.funcDyFenGuan) {
            startActivity(new Intent(this, ActivityDyFenGuan.class));
        } else if (view.getId() == R.id.funcDyShiXing) {
            startActivity(new Intent(this, ActivityDyShiXing.class));
        } else if (view.getId() == R.id.funcDyLive) {
            startActivity(new Intent(this, ActivityDyLive.class));
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LOG.m11v("--------------------------- onCreate : " + getClass().getSimpleName());
        setContentView((int) R.layout.activity_main_old);
        initHolder();
        this.holder.activePage.setVisibility(View.VISIBLE); // TODO: 2020/11/17
        requestPermission();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        LOG.m11v("--------------------------- onDestroy : " + getClass().getSimpleName());
        try {
            unregisterReceiver(this.mReceiver);
        } catch (Exception e) {
        }
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LOG.m11v("--------------------------- onNewIntent : " + getClass().getSimpleName());
        if (intent.getBooleanExtra("past", false)) {
            showActivePage(true);
        }
    }

    public void permissionFail() {
        super.permissionFail();
    }

    public void permissionSuccess() {
        super.permissionSuccess();
        gogogo();
    }
}
