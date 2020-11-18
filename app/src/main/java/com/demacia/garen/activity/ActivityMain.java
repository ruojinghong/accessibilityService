package com.demacia.garen.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.demacia.garen.R;
import com.demacia.garen.fragment.FragmentMainHome;
import com.demacia.garen.fragment.FragmentMainHot;
import com.demacia.garen.fragment.FragmentMainMine;
import com.demacia.garen.utils.HttpUtils;
import com.demacia.garen.utils.LOG;
import com.demacia.garen.utils.SPHelper;
import com.demacia.garen.utils.SysUtils;
import com.demacia.garen.view.Ball;
import java.util.HashMap;
import java.util.Map;

public class ActivityMain extends BaseActivity implements View.OnClickListener {
    public static final String SP_Key_active = "active";
    public static final String SP_Key_code = "code";
    private FragmentMainHome fragmentMainHome = null;
    private FragmentMainHot fragmentMainHot = null;
    /* access modifiers changed from: private */
    public FragmentMainMine fragmentMainMine = null;
    private Holder holder = new Holder();
    private long lastPressBackTime = 0;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            LOG.m11v("Action:" + intent.getAction());
            if (intent.getAction().equals(ActivityMain.Action_Connected(context))) {
                if (ActivityMain.this.fragmentMainMine != null) {
                    ActivityMain.this.fragmentMainMine.setConnected(true);
                }
            } else if (intent.getAction().equals(ActivityMain.Action_Unbind(context)) && ActivityMain.this.fragmentMainMine != null) {
                ActivityMain.this.fragmentMainMine.setConnected(false);
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
        public ImageView navHome;
        /* access modifiers changed from: private */
        public ImageView navHot;
        /* access modifiers changed from: private */
        public ImageView navMine;

        private Holder() {
        }
    }

    public static String Action_Connected(Context context) {
        return context.getPackageName() + "." + ActivityMain.class.getName() + ".connected";
    }

    public static String Action_Unbind(Context context) {
        return context.getPackageName() + "." + ActivityMain.class.getName() + ".unbind";
    }

    private void gotoActive(final String str) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("mobileId", SysUtils.getOnlyId(this.context));
        hashMap.put("checkKey", str);

        SPHelper.putBoolean(ActivityMain.this.context, "active", true);
        SPHelper.putString(ActivityMain.this.context, "code", str);
        ActivityMain.this.showActivePage(false);


         HttpUtils.doHttpPost(this.context, "http://loan.eco-ozg.cn/jy/jyMobileKey/check", (Map<String, String>) hashMap, (HttpUtils.OnCallback) new HttpUtils.OnCallback() {
             public void onResponse(String str) {
                 if (!TextUtils.isEmpty(str)) {
                     JSONObject parseObject = JSONObject.parseObject(str, Feature.OrderedField);
                     if (parseObject == null) {
                         Toast.makeText(ActivityMain.this.context, "返回数据解析失败", Toast.LENGTH_SHORT).show();
                     } else if (parseObject.getInteger("code") == 200) {
                         SPHelper.putBoolean(ActivityMain.this.context, "active", true);
                         SPHelper.putString(ActivityMain.this.context, "code", str);
                         ActivityMain.this.showActivePage(false);
                     } else {
                         Toast.makeText(ActivityMain.this.context, parseObject.getString(NotificationCompat.CATEGORY_MESSAGE), Toast.LENGTH_SHORT).show();
                     }
                 } else {
                     Toast.makeText(ActivityMain.this.context, "返回为空", Toast.LENGTH_SHORT).show();
                 }
             }
         });
    }

    private void initHolder() {
        this.holder.navHome = (ImageView) findViewById(R.id.navHome);
        ImageView unused2 = this.holder.navHot = (ImageView) findViewById(R.id.navHot);
        ImageView unused3 = this.holder.navMine = (ImageView) findViewById(R.id.navMine);
        ViewGroup unused4 = this.holder.activePage = (ViewGroup) findViewById(R.id.activePage);
        TextView unused5 = this.holder.info = (TextView) findViewById(R.id.info);
        EditText unused6 = this.holder.activeCode = (EditText) findViewById(R.id.activeCode);
    }

    private void setup(Fragment fragment) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.mainContiner, fragment);
        beginTransaction.commitAllowingStateLoss();
        this.holder.navHome.setImageResource(fragment instanceof FragmentMainHome ? R.drawable.img_nav_home_light : R.drawable.img_nav_home_gray);
        this.holder.navHot.setImageResource(fragment instanceof FragmentMainHot ? R.drawable.img_nav_hot_light : R.drawable.img_nav_hot_gray);
        this.holder.navMine.setImageResource(fragment instanceof FragmentMainMine ? R.drawable.img_nav_mine_light : R.drawable.img_nav_mine_gray);
    }

    private void setupMainHome() {
        Fragment findFragmentById = getSupportFragmentManager().findFragmentById(R.id.mainContiner);
        if (findFragmentById instanceof FragmentMainHome) {
            LOG.m11v("fragmentExist : " + findFragmentById);
            return;
        }
        if (this.fragmentMainHome == null) {
            this.fragmentMainHome = new FragmentMainHome();
        }
        setup(this.fragmentMainHome);
    }

    private void setupMainHot() {
        Fragment findFragmentById = getSupportFragmentManager().findFragmentById(R.id.mainContiner);
        if (findFragmentById instanceof FragmentMainHot) {
            LOG.m11v("fragmentExist : " + findFragmentById);
            return;
        }
        if (this.fragmentMainHot == null) {
            this.fragmentMainHot = new FragmentMainHot();
        }
        setup(this.fragmentMainHot);
    }

    private void setupMainMine() {
        Fragment findFragmentById = getSupportFragmentManager().findFragmentById(R.id.mainContiner);
        if (findFragmentById instanceof FragmentMainMine) {
            LOG.m11v("fragmentExist : " + findFragmentById);
            return;
        }
        if (this.fragmentMainMine == null) {
            this.fragmentMainMine = new FragmentMainMine();
        }
        setup(this.fragmentMainMine);
    }

    /* access modifiers changed from: private */
    public void showActivePage(boolean z) {
        // TODO: 2020/11/17
        if (z) {
            this.holder.activePage.setVisibility(View.VISIBLE);
            Ball.hideBall();
            return;
        }
        this.holder.activePage.setVisibility(View.GONE);
        Ball.showBall(this.context);
    }

    /* access modifiers changed from: private */
    public void start() {
        findViewById(R.id.navHome).setOnClickListener(this);
        findViewById(R.id.navHot).setOnClickListener(this);
        findViewById(R.id.navMine).setOnClickListener(this);
        setupMainHome();
        findViewById(R.id.info).setOnClickListener(this);
        this.holder.info.setText(String.format("设备号：%s", new Object[]{SysUtils.getOnlyId(this.context)}));
        findViewById(R.id.activeBtn).setOnClickListener(this);
        if (SPHelper.getBoolean(this.context, "active", false)) {
            showActivePage(false);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Action_Connected(this));
        intentFilter.addAction(Action_Unbind(this));
        registerReceiver(this.mReceiver, intentFilter);
        LOG.m11v("注册=");
    }

    public void onClick(View view) {
        Log.v(this.getClass().getSimpleName(), "dsfsdfdsfss");
        if (view.getId() == R.id.navHome) {
            setupMainHome();
        } else if (view.getId() == R.id.navHot) {
            setupMainHot();
        } else if (view.getId() == R.id.navMine) {
            setupMainMine();
        } else {
            view.getId();
        }
        if (view.getId() == R.id.activeBtn) {
            String trim = this.holder.activeCode.getText().toString().trim();
            if (TextUtils.isEmpty(trim)) {
                Toast.makeText(this.context, "请输入激活码", Toast.LENGTH_SHORT).show();
            } else {
                gotoActive(trim);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LOG.m11v("--------------------------- onCreate : " + getClass().getSimpleName());
        setStatusBarColor(-14477547, false);
        setContentView((int) R.layout.activity_main);
        initHolder();
        // TODO: 2020/11/17
        this.holder.activePage.setVisibility(View.VISIBLE);
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

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4 || System.currentTimeMillis() - this.lastPressBackTime <= 800) {
            return super.onKeyDown(i, keyEvent);
        }
        this.lastPressBackTime = System.currentTimeMillis();
        LOG.toast(this.context, "再按一次退出");
        return true;
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


//    public void permissionSuccess() {
//        super.permissionSuccess();
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                // TODO: 2020/11/17
//                ActivityMain.this.findViewById(R.id.logoPage).setVisibility(View.INVISIBLE);
//                ActivityMain.this.start();
//            }
//        }, 2000);
//    }


    @Override
    public void permissionSuccess() {

        findViewById(R.id.hehe).postDelayed(new Runnable() {
            public void run() {
                // TODO: 2020/11/17
                ActivityMain.this.findViewById(R.id.logoPage).setVisibility(View.INVISIBLE);
                ActivityMain.this.start();

            }
        }, 1000);
    }
}
