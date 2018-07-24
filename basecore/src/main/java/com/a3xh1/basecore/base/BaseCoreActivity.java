package com.a3xh1.basecore.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.a3xh1.basecore.utils.StatusBarUtils;
import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;

import javax.annotation.Nonnull;

import me.yokeyword.fragmentation.SupportActivity;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Author: GIndoc on 2017/10/31 上午12:04
 * email : 735506583@qq.com
 * FOR   :
 */
public abstract class BaseCoreActivity<V , T extends BaseCorePresenter<V>> extends SupportActivity implements LifecycleProvider<ActivityEvent> {
    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();
    protected T mPresenter;
    protected String packageName;

    protected abstract T createPresent();

    @Override
    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    protected void processStatusBar(View view, boolean isTransparentStatusbar, boolean isChangeTextColor){
        StatusBarUtils.from(this)
                .setActionbarView(view)
                .setTransparentStatusbar(isTransparentStatusbar)
                .setLightStatusBar(isChangeTextColor)
                .process();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        packageName = getClass().getName();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        lifecycleSubject.onNext(ActivityEvent.CREATE);
        mPresenter = createPresent();
        if(mPresenter != null){
            V view = (V) this;
            mPresenter.attachView(view);
        }
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(mPageName);
//        MobclickAgent.onResume(this);
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    @CallSuper
    protected void onPause() {
//        MobclickAgent.onPageEnd(mPageName);
//        MobclickAgent.onPause(this);
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @Override
    @CallSuper
    protected void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
        if(mPresenter != null){
            mPresenter.detachView();
        }
    }
}
