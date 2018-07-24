package com.a3xh1.basecore.base;

import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.a3xh1.basecore.utils.StatusBarUtils;
import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.android.FragmentEvent;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;

import me.yokeyword.fragmentation.SupportFragment;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Author: GIndoc on 2017/10/21 下午2:50
 * email : 735506583@qq.com
 * FOR   :
 */
public abstract class BaseCoreFragment<V , T extends BaseCorePresenter<V>> extends SupportFragment implements LifecycleProvider<FragmentEvent> {
    private T mPresent;
    protected String packageName;
    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();
    /**
     * 懒加载过
     */
    public boolean isLazyLoaded;
    public boolean isPrepared;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        packageName = getClass().getName();
        lifecycleSubject.onNext(FragmentEvent.CREATE);
        mPresent = createPresent();
        if(mPresent != null){
            mPresent.attachView((V)this);
        }
    }

    protected abstract T createPresent();

    @Override
    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject);
    }

    @Override
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
    }

    @Override
    public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(mPageName);
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    public void onPause() {
//        MobclickAgent.onPageEnd(mPageName);
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @Override
    public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
        if(mPresent != null){
            mPresent.detachView();
        }
    }

    @Override
    public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
    }

    protected void processStatusBar(View view, boolean isTransparentStatusbar, boolean isChangeTextColor){
        StatusBarUtils.from(getActivity())
                .setActionbarView(view)
                .setTransparentStatusbar(isTransparentStatusbar)
                .setLightStatusBar(isChangeTextColor)
                .process();
    }

    @Override
    public void onActivityCreated(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        //只有Fragment onCreateView好了，
        //另外这里调用一次lazyLoad(）
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoad();
    }

    private void lazyLoad(){
        if (getUserVisibleHint() && isPrepared && !isLazyLoaded) {
            lazyLoadContent();
            isLazyLoaded = true;
        }
    }

    public void lazyLoadContent(){};
}
