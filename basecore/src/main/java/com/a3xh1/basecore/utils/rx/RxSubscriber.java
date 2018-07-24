package com.a3xh1.basecore.utils.rx;


import com.a3xh1.basecore.utils.gson.ResultException;

import rx.Subscriber;
import timber.log.Timber;

/**
 * Created by louiszgm-pc on 2016/10/9.
 */

public abstract class RxSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        try{
            Timber.e(e.getMessage());
            if (e instanceof ResultException) {
                _onResultError((ResultException) e);
            } else {
                _onError(e);
            }
        }catch (Exception e1){

        }

    }

    public abstract void _onResultError(ResultException e);

    @Override
    public void onNext(T t) {
        _onNext(t);
    }


    public abstract void _onNext(T t);

    public abstract void _onError(Throwable throwable);
}
