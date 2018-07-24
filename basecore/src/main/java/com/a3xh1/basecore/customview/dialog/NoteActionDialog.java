package com.a3xh1.basecore.customview.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a3xh1.basecore.R;
import com.a3xh1.basecore.databinding.DialogNoteActionBinding;

import javax.inject.Inject;

/**
 * Author: GIndoc on 2018/3/2 下午3:29
 * email : 735506583@qq.com
 * FOR   :
 */
public class NoteActionDialog extends BaseDialogFragment implements View.OnClickListener {
    private DialogNoteActionBinding mBinding;

    @Inject
    public NoteActionDialog() {
    }

    @Override
    public View initView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        mBinding = DialogNoteActionBinding.inflate(layoutInflater, viewGroup, false);
        mBinding.cancel.setOnClickListener(this);
        mBinding.tvFollow.setOnClickListener(this);
        mBinding.tvSendMsg.setOnClickListener(this);
        return mBinding.getRoot();
    }

    private OnClickListener mOnClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancel) {
            dismiss();
            return;
        }
        if (mOnClickListener==null) return;
        if (v.getId() == R.id.tv_follow) {
            mOnClickListener.onFollow(v);
        } else if (v.getId() == R.id.tv_send_msg) {
            mOnClickListener.onDirectMsg(v);
        }
    }

    public interface OnClickListener {
        void onFollow(View view);

        void onDirectMsg(View view);
    }
}
