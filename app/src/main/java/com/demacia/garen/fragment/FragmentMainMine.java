package com.demacia.garen.fragment;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.demacia.garen.R;
import com.demacia.garen.service.DyAccessibilityService;
import com.demacia.garen.utils.LOG;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class FragmentMainMine extends BaseFragment implements View.OnClickListener {
    private Holder holder = new Holder();
    private View rootView = null;

    private class Holder {
        /* access modifiers changed from: private */
        public TextView setAccessibilityPermission;

        private Holder() {
        }
    }

    private void initHolder(View view) {
        TextView unused = this.holder.setAccessibilityPermission = (TextView) view.findViewById(R.id.setAccessibilityPermission);
    }

    private void showAccessibilitySetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setTitle("提示信息");
        builder.setMessage("无障碍服务未开启，请单击【开启】按钮前往设置中心开启无障碍服务。");
        builder.setNeutralButton("取消", (DialogInterface.OnClickListener) null);
        builder.setPositiveButton("开启", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                FragmentMainMine.this.context.startActivity(intent);
            }
        });
        builder.show();
    }

    public boolean isServiceON(String str) {
        Context context = this.context;
        Context context2 = this.context;
        List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningServices(100);
        for (int i = 0; i < runningServices.size(); i++) {
            if (runningServices.get(i).service.getClassName().contains(str)) {
                return true;
            }
        }
        return false;
    }

    public void onClick(View view) {
        if (view.getId() == R.id.setBallPermission) {
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.parse("package:" + this.context.getPackageName()));
            startActivity(intent);
        } else if (view.getId() == R.id.setAccessibilityPermission) {
            Intent intent2 = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
            intent2.setFlags(FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent2);
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getBaseActivity().setStatusBarColor(-14408668, false);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (this.rootView == null) {
            View inflate = layoutInflater.inflate(R.layout.fragment_main_mine, viewGroup, false);
            this.rootView = inflate;
            initHolder(inflate);
            LOG.m11v("onCreateView : " + this);
            this.rootView.findViewById(R.id.setBallPermission).setOnClickListener(this);
            this.rootView.findViewById(R.id.setAccessibilityPermission).setOnClickListener(this);
            boolean isServiceON = isServiceON(DyAccessibilityService.class.getName());
            LOG.m11v("dyIsOn : " + isServiceON);
            if (!isServiceON) {
                this.holder.setAccessibilityPermission.setText("开启无障碍服务");
                this.holder.setAccessibilityPermission.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                showAccessibilitySetting();
            } else {
                this.holder.setAccessibilityPermission.setText("无障碍服务已开启");
                this.holder.setAccessibilityPermission.setTextColor(-14112955);
            }
        }
        return this.rootView;
    }

    public void onDestroyView() {
        super.onDestroyView();
        LOG.m11v("onDestroyView : " + this);
    }

    public void onResume() {
        super.onResume();
    }

    public void setConnected(boolean z) {
        if (z) {
            this.holder.setAccessibilityPermission.setText("无障碍服务已开启");
            this.holder.setAccessibilityPermission.setTextColor(-14112955);
            return;
        }
        this.holder.setAccessibilityPermission.setText("开启无障碍服务");
        this.holder.setAccessibilityPermission.setTextColor(ViewCompat.MEASURED_STATE_MASK);
    }
}
