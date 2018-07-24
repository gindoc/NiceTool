package com.a3xh1.basecore.base;


import com.trello.rxlifecycle2.LifecycleTransformer;

public interface IBaseView {
    <T>LifecycleTransformer<T> getBindToLifecycle();

    void showError(String error);
}