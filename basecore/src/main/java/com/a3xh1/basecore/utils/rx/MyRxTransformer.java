package com.a3xh1.basecore.utils.rx;

import com.a3xh1.basecore.base.IBaseView;

import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.Observable;

/**
 * Author: GIndoc on 2017/12/27 上午11:33
 * email : 735506583@qq.com
 * FOR   :
 */
public class MyRxTransformer {

    public static <T> ObservableTransformer<T, T> transfom(final IBaseView view) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .compose(view.<T>getBindToLifecycle())
                        .compose(RxResultHelper.<T>handleResult())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
