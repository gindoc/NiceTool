package com.a3xh1.basecore.customview.recyclerview;

import android.view.View;

/**
 * Author: GIndoc on 2017/7/24 下午2:09
 * email : 735506583@qq.com
 * FOR   :
 */
public interface RecyclerViewClickListener {
    void onItemClickListener(View view, int position);

    void onItemLongClickListener(View view, int position);
}
