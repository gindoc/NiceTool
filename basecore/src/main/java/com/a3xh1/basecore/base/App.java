package com.a3xh1.basecore.base;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.a3xh1.basecore.BuildConfig;
import com.a3xh1.basecore.di.component.ApplicationComponent;
import com.a3xh1.basecore.di.component.DaggerApplicationComponent;
import com.a3xh1.basecore.di.modules.ApplicationModule;
import com.a3xh1.basecore.utils.ComponentHolder;
import com.a3xh1.basecore.utils.Saver;
import com.a3xh1.basecore.utils.Utils;
import com.qihoo360.replugin.RePlugin;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;


/**
 * Author: GIndoc on 2017/9/14 下午12:00
 * email : 735506583@qq.com
 * FOR   :
 */
public class App extends MultiDexApplication {

    private ApplicationComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        RePlugin.App.onCreate();
        getApplicationComponent().inject(this);

        LeakCanary.install(this);
        Saver.initSaver(this);
        Utils.init(this);

        if (BuildConfig.isDebug) {
            Timber.plant(new Timber.DebugTree());
        }

    }

    public ApplicationComponent getApplicationComponent() {
        if (mComponent == null) {
            mComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        ComponentHolder.setAppComponent(mComponent);
        return mComponent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        RePlugin.App.attachBaseContext(this);
    }
}
