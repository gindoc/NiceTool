package com.a3xh1.basecore.customview.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: GIndoc on 2017/12/20 下午9:03
 * email : 735506583@qq.com
 * FOR   :
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<DataBindingViewHolder> {
    protected List<T> mData;

    public BaseRecyclerViewAdapter() {
        mData = new ArrayList<>();
    }

    public List<T> getData() {
        return mData;
    }

    public void setData(List<T> data) {
        if (data != null) {
            mData.clear();
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void add(T t) {
        if (t != null) {
            mData.add(t);
            notifyItemInserted(mData.size());
        }
    }

    public void add(int pos, T t) {
        if (t != null) {
            mData.add(pos, t);
            notifyItemInserted(pos);
        }
    }

    public void addAll(List<T> ts) {
        if (ts != null && ts.size()>0) {
            int startPos = mData.size();
            mData.addAll(ts);
            notifyItemRangeInserted(startPos, ts.size());
        }
    }

    @Override
    public void onBindViewHolder(final DataBindingViewHolder holder, int position) {
        View view = holder.getBinding().getRoot();
        if (clickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClickListener(view, holder.getLayoutPosition());
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    clickListener.onItemLongClickListener(view, holder.getLayoutPosition());
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    /**
     * 给recyclerview设置数据，如果有数据则隐藏empty View，如果没数据，page=1，显示emptyVIew，page！=1，隐藏empty View
     * @param data
     * @param page
     * @return          true 有数据，false 无数据
     */
    public boolean addData(RecyclerViewWithEmptyView recyclerView, List<T> data, int page) {
        if (data != null && data.size() > 0) {
            if (page == 1) {
                setData(data);
            } else {
                addAll(data);
            }
            recyclerView.hideEmptyView();
            return true;
        } else {
            if (page == 1) {
                recyclerView.showEmptyView();
            }
            return false;
        }
    }

    protected RecyclerViewClickListener clickListener;

    public void setClickListener(RecyclerViewClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
