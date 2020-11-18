package com.demacia.garen.activity;

import android.app.ActivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import com.demacia.garen.R;
import com.demacia.garen.utils.CommandExecution;
import com.demacia.garen.utils.CommonUtils;
import com.demacia.garen.utils.LOG;
import com.demacia.garen.utils.SysUtils;

import java.lang.reflect.Field;

public class ActivityInfos extends BaseActivity {
    private TextView info;

    private void collectBuildInfo(StringBuilder sb) {
        Field[] fields = Build.class.getFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    sb.append(field.getName());
                    sb.append(" = ");
                    sb.append(field.get((Object) null).toString());
                    sb.append(CommandExecution.COMMAND_LINE_END);
                } catch (IllegalAccessException e) {
                    sb.append(field.getName());
                    sb.append(" = ");
                    sb.append("???");
                    sb.append(CommandExecution.COMMAND_LINE_END);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_infos);
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("屏幕大小：(%d, %d)\n", new Object[]{Integer.valueOf(SysUtils.screenWidth(this.context)), Integer.valueOf(SysUtils.screenHeight(this.context))}));
        sb.append("screenWidth(dp)：" + SysUtils.px2dp((float) SysUtils.screenWidth(this.context)) + CommandExecution.COMMAND_LINE_END);
        sb.append("screenWidth(sp)：" + SysUtils.px2sp((float) SysUtils.screenWidth(this.context)) + CommandExecution.COMMAND_LINE_END);
        sb.append(CommandExecution.COMMAND_LINE_END);
        sb.append(String.format("存储空间：总共：%s，剩余：%s\n", new Object[]{CommonUtils.formatSize((double) Environment.getExternalStorageDirectory().getTotalSpace()), CommonUtils.formatSize((double) Environment.getExternalStorageDirectory().getFreeSpace())}));
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ((ActivityManager) getSystemService(ACTIVITY_SERVICE)).getMemoryInfo(memoryInfo);
        sb.append(String.format("内存：总共：%s，剩余：%s\n", new Object[]{CommonUtils.formatSize((double) memoryInfo.totalMem, 1000), CommonUtils.formatSize((double) memoryInfo.availMem, 1000)}));
        sb.append(CommandExecution.COMMAND_LINE_END);
        sb.append("厂商：" + Build.BRAND + CommandExecution.COMMAND_LINE_END);
        sb.append("型号：" + Build.MODEL + CommandExecution.COMMAND_LINE_END);
        sb.append("版本号：" + Build.DISPLAY + CommandExecution.COMMAND_LINE_END);
        sb.append("Android 版本：" + Build.VERSION.RELEASE + CommandExecution.COMMAND_LINE_END);
        sb.append("SDK 版本：" + Build.VERSION.SDK_INT + CommandExecution.COMMAND_LINE_END);
        sb.append("Serial(设备号)：" + Build.SERIAL + CommandExecution.COMMAND_LINE_END);
        sb.append("IMEI：" + SysUtils.getIMEI(this.context) + CommandExecution.COMMAND_LINE_END);
        sb.append("ANDROID_ID：" + SysUtils.getAndroidId(this.context) + CommandExecution.COMMAND_LINE_END);
        sb.append(CommandExecution.COMMAND_LINE_END);
        collectBuildInfo(sb);
        ((TextView) findViewById(R.id.info)).setText(sb.toString());
        LOG.m11v(sb.toString());
    }
}
