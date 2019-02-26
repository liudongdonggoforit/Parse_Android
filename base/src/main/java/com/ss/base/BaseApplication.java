package com.ss.base;

import android.app.Application;

import androidx.multidex.MultiDexApplication;

public abstract class BaseApplication extends MultiDexApplication {
    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
    }


    public abstract void init();

    public static Application getApplication() {
        return instance;
    }
}
