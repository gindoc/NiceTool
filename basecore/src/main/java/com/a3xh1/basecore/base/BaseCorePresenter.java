package com.a3xh1.basecore.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Author: GIndoc on 2017/10/31 上午12:12
 * email : 735506583@qq.com
 * FOR   :
 */
public class BaseCorePresenter<T> {
    public Reference<T> mViewRef;

    public void attachView(T view){
        mViewRef = new WeakReference<T>(view);
    }

    protected T getView(){
        return mViewRef == null ? null : mViewRef.get();
    }

    public boolean isViewAttached(){
        return mViewRef != null && mViewRef.get()!=null;
    }

    public void detachView(){
        if(mViewRef != null){
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
