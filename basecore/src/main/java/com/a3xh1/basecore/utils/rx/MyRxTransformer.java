package com.a3xh1.basecore.utils.rx;

import com.a3xh1.basecore.base.IBaseView;
import com.a3xh1.basecore.utils.rx.RxResultHelper;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: GIndoc on 2017/12/27 上午11:33
 * email : 735506583@qq.com
 * FOR   :
 */
public class MyRxTransformer {

    public static <T> Observable.Transformer<T, T> transfom(final IBaseView view){
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable
                        .compose(view.<T>getBindToLifecycle())
                        .compose(RxResultHelper.<T>handleResult())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
