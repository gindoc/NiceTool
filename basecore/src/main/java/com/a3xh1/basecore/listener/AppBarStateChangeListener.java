package com.a3xh1.basecore.listener;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.OnOffsetChangedListener;

/**
 * Author: GIndoc on 2018/3/1 上午10:28
 * email : 735506583@qq.com
 * FOR   :
 */
public abstract class AppBarStateChangeListener implements OnOffsetChangedListener {
    private int mState = State.IDLE;

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {
            if (mState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED, verticalOffset);
            }
            mState = State.EXPANDED;
        } else if (-verticalOffset >= appBarLayout.getTotalScrollRange()) {
            if (mState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED, verticalOffset);
            }
            mState = State.COLLAPSED;
        } else {
            if (mState != State.IDLE) {
                onStateChanged(appBarLayout, State.IDLE, verticalOffset);
            }
            mState = State.IDLE;
        }
    }

    public abstract void onStateChanged(AppBarLayout appBarLayout, int state, int verticalOffset);

    public @interface State {
        int EXPANDED = 0;
        int COLLAPSED = 1;
        int IDLE = 2;
    }
}
