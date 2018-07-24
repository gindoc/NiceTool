package com.a3xh1.basecore.di.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import com.a3xh1.basecore.utils.EnCodeUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.subjects.BehaviorSubject;


@Module
public class ApplicationModule {
    private Application mApp;
    public ApplicationModule(Application app){
        mApp = app;
    }

    @Provides
    public SharedPreferences providesPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(mApp);
    }
    @Provides
    public Resources providesResources(){
        return mApp.getResources();
    }

    @Provides
    public Context provideContext(){
        return mApp;
    }

//    @Provides
//    @Singleton
//    public LoginContext provideLoginContext(){
//        return new LoginContext();
//    }

    @Provides
    public BehaviorSubject provideBehaviorSubject(){
        return BehaviorSubject.create();
    }

//    @Provides
//    @Singleton
//    public LocationClient provideLocationClient(LocationClientOption option){
//        LocationClient locationClient = new LocationClient(mApp,option);
//        return locationClient;
//    }
//    @Provides
//    public BDLocationListener provideBDLocationListener(){
//        return new MyLocationListener();
//    }
//
//    @Provides
//    @Singleton
//    public LocationClientOption initLocation(){
//        LocationClientOption option = new LocationClientOption();
//        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
//        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
//        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
//        int span=1000;
//        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
//        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
//        option.setOpenGps(true);//可选，默认false,设置是否使用gps
//        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
//        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
//        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
//        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
//        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
//        return option;
//    }

//    @Provides
//    @Singleton
//    public MyLocationClient provideMyLocationClient(LocationClientOption option, BDLocationListener bdLocationListener) {
//        return new MyLocationClient(mApp, option, (MyLocationListener) bdLocationListener, BehaviorSubject.<BDLocation>create());
//    }

    @Provides
    @Named("langMap")
    @Singleton
    public Map<String, String> provideLanguageMap() {
        Map<String, String> languages = new HashMap<>();
        languages.put("zh_CN", "CN");
        languages.put("zh_TW", "TRAD");
        languages.put("en", "EN");
        return languages;
    }

//    @Singleton
    @Provides
    @Named("commonHeader")
    public Map<String, Object> provideCommonHeader() {
        Map<String, Object> headers = new HashMap<>();
        long time = System.currentTimeMillis();
        String key = time + "1#j0ZAqg";
        headers.put("times", time);
        headers.put("key", EnCodeUtil.MD5(key));
        return headers;
    }

//    @Singleton
    @Provides
    @Named("commonHeaderWithLang")
    public Map<String, Object> provideCommonHeaderWithLang(@Named("commonHeader") Map<String, Object> commonHeader,
                                                           @Named("langMap") Map<String, String> langMap) {
        String locale = Locale.getDefault().toString();
        String lang = locale.contains("en") ? locale.split("_")[0] : locale;
        commonHeader.put("language", langMap.get(lang));
        return commonHeader;
    }

}
