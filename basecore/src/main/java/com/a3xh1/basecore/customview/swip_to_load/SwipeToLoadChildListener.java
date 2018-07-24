package com.a3xh1.basecore.customview.swip_to_load;

/**
 * Author: GIndoc on 2018/1/12 下午5:02
 * email : 735506583@qq.com
 * FOR   :
 */
public interface SwipeToLoadChildListener {
    /**
     * 启用下拉刷新功能
     *
     * @see //SwipeLayout#enableRefresh()
     */
    void enableRefresh();

    /**
     * 禁用下拉刷新功能
     *
     * @see //SwipeLayout#disableRefresh()
     */
    void disableRefresh();


    boolean isRefreshing();


    boolean isLoadMore();

    void enableLoadMore();
    void disableLoadMore();
}
