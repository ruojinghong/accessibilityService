package com.demacia.garen;

import android.app.Application;

import com.demacia.garen.utils.CrashHandler;
import com.demacia.garen.utils.LOG;

public class App extends Application {
    public void onCreate() {
        super.onCreate();
        LOG.m10e("========================================================= start app : " + this);
        new CrashHandler().init(this);
    }
}
