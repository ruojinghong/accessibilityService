package com.demacia.garen.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
// import android.support.p000v4.app.ActivityCompat;
// import android.support.p000v4.content.ContextCompat;
// import android.support.p000v4.graphics.ColorUtils;
// import android.support.p003v7.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;

// import com.lisyx.tap.utils.CommandExecution;
// import com.lisyx.tap.utils.LOG;
import com.demacia.garen.utils.CommandExecution;
import com.demacia.garen.utils.LOG;

import java.util.ArrayList;
import java.util.List;

import static android.content.pm.PackageManager.GET_PERMISSIONS;
import static android.content.pm.PackageManager.MATCH_SYSTEM_ONLY;

public class BaseActivity extends AppCompatActivity {
    protected Context context;
    private int mPermissionRequestCode = 101;

    private String[] filterPermission(String... strArr) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (String str : strArr) {
            if (!str.equals("android.permission.SYSTEM_ALERT_WINDOW") && !str.equals("android.permission.SYSTEM_OVERLAY_WINDOW") && !str.equals("android.permission.REQUEST_INSTALL_PACKAGES") && !str.equals("android.permission.READ_LOGS") && !str.equals("android.permission.CHANGE_CONFIGURATION") && !str.equals("android.permission.MOUNT_UNMOUNT_FILESYSTEMS") && !str.equals("android.permission.BIND_ACCESSIBILITY_SERVICE") && !str.equals("android.permission.INTERACT_ACROSS_USERS_FULL") && !str.equals("com.mediatek.permission.CTA_ENABLE_WIFI") && !str.equals("android.permission.WRITE_SETTINGS")) {
                arrayList.add(str);
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    private List<String> getDeniedPermissions(String... strArr) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (String str : strArr) {
            if (ContextCompat.checkSelfPermission(this, str) != 0) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    private void showTipsDialog() {
        List<String> list;
        try {
            // TODO: 2020/11/17
            list = getDeniedPermissions(filterPermission(getPackageManager().getPackageInfo(getPackageName(), MATCH_SYSTEM_ONLY).requestedPermissions));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            list = null;
        }
        StringBuilder sb = new StringBuilder();
        if (list != null) {
            for (String str : list) {
                sb.append(CommandExecution.COMMAND_LINE_END).append(str);
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息");
        builder.setMessage("当前应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【设置】按钮前往设置中心进行权限授权。" + sb.toString());
        builder.setNeutralButton("取消", (DialogInterface.OnClickListener) null);
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.parse("package:" + BaseActivity.this.getPackageName()));
                BaseActivity.this.startActivity(intent);
            }
        });
        builder.show();
    }

    private boolean verifyPermissions(int[] iArr) {
        for (int i : iArr) {
            if (i != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean checkPermissions() {
        try {
            // TODO: 2020/11/17
            return checkPermissions(filterPermission(getPackageManager().getPackageInfo(getPackageName(), MATCH_SYSTEM_ONLY).requestedPermissions));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkPermissions(String... strArr) {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        for (String checkSelfPermission : strArr) {
            if (ContextCompat.checkSelfPermission(this, checkSelfPermission) != 0) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.context = this;
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i != this.mPermissionRequestCode) {
            return;
        }
        if (verifyPermissions(iArr)) {
            permissionSuccess();
        } else {
            permissionFail();
        }
    }

    public void permissionFail() {
        LOG.m11v("获取权限失败=");
        showTipsDialog();
    }

    public void permissionSuccess() {
        LOG.m11v("获取权限成功=");

    }

    public void requestPermission() {
        try {
            // TODO: 2020/11/17
            requestPermission(filterPermission(getPackageManager().getPackageInfo(getPackageName(), GET_PERMISSIONS).requestedPermissions));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void requestPermission(String... strArr) {
        if (checkPermissions(strArr)) {
            permissionSuccess();
            return;
        }
        List<String> deniedPermissions = getDeniedPermissions(strArr);
        if (deniedPermissions.size() > 0) {
            ActivityCompat.requestPermissions(this, (String[]) deniedPermissions.toArray(new String[deniedPermissions.size()]), this.mPermissionRequestCode);
        } else {
            permissionFail();
        }
    }

    public void setStatusBarColor(int i, boolean z) {
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().addFlags(Integer.MIN_VALUE);
            getWindow().clearFlags(67108864);
            getWindow().setStatusBarColor(i);
            if (ColorUtils.calculateLuminance(i) >= 0.5d) {
                if (z) {
                    getWindow().getDecorView().setSystemUiVisibility(9216);
                } else {
                    getWindow().getDecorView().setSystemUiVisibility(8192);
                }
            } else if (z) {
                getWindow().getDecorView().setSystemUiVisibility(1024);
            } else {
                getWindow().getDecorView().setSystemUiVisibility(0);
            }
        }
    }
}
