package com.ss.base.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ss.base.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public abstract class SimpleBaseAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    private View.OnClickListener mEmptyClickListener;
    private boolean useEmpty;
    private ImageView mIv_empty;

    public SimpleBaseAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
        setHeaderFooterEmpty(false, false);
    }

    public SimpleBaseAdapter(@Nullable List<T> data) {
        super(data);
    }

    public SimpleBaseAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    public void bindToRecyclerView(RecyclerView recyclerView) {
        bindToRecyclerView(recyclerView, false,false);
    }

    public void bindToRecyclerView(RecyclerView recyclerView, boolean useEmpty) {
        bindToRecyclerView(recyclerView,useEmpty,false);
    }

    public void bindToRecyclerView(RecyclerView recyclerView, boolean useEmpty,boolean isWrapImg) {
        super.bindToRecyclerView(recyclerView);
        if (useEmpty) {

            if (isWrapImg) {
                setEmptyView(R.layout.base_empty_view, recyclerView);
            }else {
                setEmptyView(R.layout.base_empty_view_full, recyclerView);
            }
        }
        View  mEmptyView = getEmptyView();
        if (mEmptyView != null) {
            mIv_empty = mEmptyView.findViewById(R.id.iv_empty);
        }
        if (mEmptyClickListener != null) setEmptyClickListener(mEmptyClickListener);
        isUseEmpty(false);
        this.useEmpty = useEmpty;
    }

    public void setEmptyImgSrc(int src) {
        if (mIv_empty != null)
            mIv_empty.setImageResource(src);
    }

    @Override
    public void isUseEmpty(boolean isUseEmpty) {
        this.useEmpty = isUseEmpty;
        super.isUseEmpty(isUseEmpty);
    }

    public void setEmptyClickListener(View.OnClickListener emptyClickListener) {
        this.mEmptyClickListener = emptyClickListener;
            if (mIv_empty != null) mIv_empty.setOnClickListener(emptyClickListener);
    }

    @Override
    public void setNewData(@Nullable List<T> data) {
        isUseEmpty(useEmpty);
        super.setNewData(data);
    }

    @Override
    public void addData(int position, @NonNull T data) {
        isUseEmpty(useEmpty);
        super.addData(position, data);
    }

    public void setEmptyText(String text) {
        if (getEmptyView() != null) {
            View view = getEmptyView().findViewById(R.id.tv_err_desc);
            if (view != null && view instanceof TextView)
                ((TextView) view).setText(text);
        }
    }
}