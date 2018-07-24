package com.a3xh1.basecore.utils.rx;

import com.a3xh1.basecore.pojo.response.Response;
import com.a3xh1.basecore.utils.gson.ResultException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;


public class RxResultHelper {

    public static <T> ObservableTransformer<T, T> handleResult() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(io.reactivex.Observable<T> upstream) {
                return upstream.flatMap(new Function<T, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(T result) throws Exception {
                        if (result instanceof Response) {
                            return doResponse((Response) result);
                        } else {
                            return null;
                        }
                    }
                });
            }
        };
    }

    private static <T> ObservableSource<T> doResponse(Response response) {
        //如果errcode不等于200,则返回的是服务器错误信息
        if (!response.isStatus()) {
            return Observable.error(new ResultException(response.isStatus(), response.getDesc()));
        } else {
            return (ObservableSource<T>) createData(response);
        }
    }


    private static <T> Observable<T> createData(final T t) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }
}
