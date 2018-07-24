package com.a3xh1.basecore.di.component;

import android.content.Context;
import android.content.res.Resources;

import com.a3xh1.basecore.base.App;
import com.a3xh1.basecore.di.modules.ApplicationModule;
//import com.a3xh1.basecore.utils.location.MyLocationClient;
//import com.baidu.location.BDLocationListener;
//import com.baidu.location.LocationClient;

import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import rx.subjects.BehaviorSubject;

/**
 * Author: GIndoc on 2017/9/19 上午9:45
 * email : 735506583@qq.com
 * FOR   :
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(App application);

//    BaseActivityComponent plus(BaseActivityModule module);

//    ServiceComponent plus();

    Resources providesResources();

    Context getContext();

    BehaviorSubject provideBehaviorSubject();

//    LocationClient provideLocationClient();
//
//    MyLocationClient provideMyLocationClient();
//
//    BDLocationListener provideBDLocationListener();

    @Named("commonHeaderWithLang")
    Map<String, Object> provideCommonHeaderWithLang();

    @Named("commonHeader")
    Map<String, Object> provideCommonHeader();

    @Named("langMap")
    Map<String, String> provideLanguageMap();

}
