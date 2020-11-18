package com.demacia.garen.tools;

import android.content.Context;

public abstract class OperateBase {
    /* access modifiers changed from: protected */
    public Context context = null;
    /* access modifiers changed from: protected */
    public long startTime = 0;

    /* access modifiers changed from: protected */
    public String formatTime(long j) {
        long j2 = j / 1000;
        long j3 = j2 / 60;
        long j4 = j2 % 60;
        long j5 = j3 / 60;
        long j6 = j3 % 60;
        if (j5 > 0) {
            return String.format("%d小时%d分钟", new Object[]{Long.valueOf(j5), Long.valueOf(j6)});
        } else if (j6 > 0) {
            return String.format("%d分钟%d秒", new Object[]{Long.valueOf(j6), Long.valueOf(j4)});
        } else {
            return String.format("%d秒", new Object[]{Long.valueOf(j4)});
        }
    }

    public abstract void start();

    public void startMode(Context context2) {
        this.context = context2;
        this.startTime = System.currentTimeMillis();
        start();
    }

    public abstract void startOne();
}
