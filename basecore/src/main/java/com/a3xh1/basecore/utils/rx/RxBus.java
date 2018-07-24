package com.a3xh1.basecore.utils.rx;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * PublishSubject: 只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
 * <p/>
 * Created by YoKeyword on 2015/6/17.
 */
public class RxBus {
    private static volatile RxBus mDefaultInstance;
    private final Subject<Object, Object> mBus;

    private final Map<Class<?>, Object> mStickyEventMap;

    public RxBus() {
        mBus = new SerializedSubject<>(PublishSubject.create());
        mStickyEventMap = new ConcurrentHashMap<>();
    }

    public static RxBus getDefault() {
        if (mDefaultInstance == null) {
            synchronized (RxBus.class) {
                if (mDefaultInstance == null) {
                    mDefaultInstance = new RxBus();
                }
            }
        }
        return mDefaultInstance;
    }

    /**
     * 发送事件
     */
    public void post(Object event) {
        mBus.onNext(event);
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return mBus.ofType(eventType);
    }

    /**
     * 判断是否有订阅者
     */
    public boolean hasObservers() {
        return mBus.hasObservers();
    }

    public void reset() {
        mDefaultInstance = null;
    }

    /**
     * Stciky 相关
     */

    /**
     * 发送一个新Sticky事件
     */
    public void postSticky(Object event) {
        synchronized (mStickyEventMap) {
            mStickyEventMap.put(event.getClass(), event);
        }
        post(event);
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    public <T> Observable<T> toObservableSticky(final Class<T> eventType) {
        synchronized (mStickyEventMap) {
            Observable<T> observable = mBus.ofType(eventType);
            final Object event = mStickyEventMap.get(eventType);

            if (event != null) {
                return observable.mergeWith(Observable.just(eventType.cast(event)));
            } else {
                return observable;
            }
        }
    }

    /**
     * 根据eventType获取Sticky事件
     */
    public <T> T getStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.get(eventType));
        }
    }

    /**
     * 移除指定eventType的Sticky事件
     */
    public <T> T removeStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.remove(eventType));
        }
    }

    /**
     * 移除所有的Sticky事件
     */
    public void removeAllStickyEvents() {
        synchronized (mStickyEventMap) {
            mStickyEventMap.clear();
        }
    }


    /* 使用onErrorResumeNext()来catch链中发生的异常                           // 建议在Sticky时,在操作符内主动try,catch
     RxBus.getDefault().toObservableSticky(EventSticky.class)
     .flatMap(event -> {
        return Observable.just(event)
     .map(...) // 在flatMap里变换Observable
        // 由于下面onErrorResumeNext, 因此 error 事件无法传递到observer, 故需要在这里做处理
     .doOnError(e -> // todo)
     .onErrorResumeNext(Observable.never())
     })
     .subscribe(new Action1<EventSticky>() {
        @Override public void call(EventSticky eventSticky) {
            try {
            // 处理接收的事件
            } catch (Exception e) {
            e.printStackTrace();
            }
        }
    });

    RxBus.getDefault().toObservableSticky(UserBean.class)                       // 建议在Sticky时,在操作符内主动try,catch
                .flatMap(new Func1<UserBean, Observable<UserBean>>() {
                    @Override
                    public Observable<UserBean> call(UserBean userBean) {
                        if (true) {
                            throw new RuntimeException();
                        }
                        return Observable.just(userBean);
//                        return Observable.just(userBean)
//                                .map(new Func1<UserBean, UserBean>() {
//                                    @Override
//                                    public UserBean call(UserBean userBean) {
//                                        if (true) {
//                                            throw new RuntimeException();
//                                        }
//                                        return userBean;
//                                    }
//                                });
                    }
                }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        }).onErrorResumeNext(Observable.<UserBean>never())
                .subscribe();

     */
}

