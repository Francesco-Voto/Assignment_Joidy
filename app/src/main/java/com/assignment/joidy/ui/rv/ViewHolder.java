package com.assignment.joidy.ui.rv;


import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.assignment.joidy.data.models.Product;
import com.assignment.joidy.databinding.ListItemProductBinding;

public class ViewHolder extends RecyclerView.ViewHolder {

    public ListItemProductBinding mViewDataBinding;
    public Product mItem;

    public ViewHolder(final View view) {
        super(view);
        mViewDataBinding = DataBindingUtil.bind(view);
    }

    public View setClickHandler(final @NonNull View.OnClickListener onClickListener){
        mViewDataBinding.listItemPriceButton.setOnClickListener(onClickListener);
        return mViewDataBinding.listItemPriceButton;
    }

}
