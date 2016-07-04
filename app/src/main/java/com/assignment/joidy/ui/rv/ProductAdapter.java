package com.assignment.joidy.ui.rv;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assignment.joidy.BR;
import com.assignment.joidy.data.models.Product;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ViewHolder> implements View.OnClickListener
{
    private static final int ITEM_MODEL = -124;

    @LayoutRes
    private final int layoutId;

    private final ClickHandler<Product> clickHandler;
    protected ArrayList<Product> items;

    private int mSize;

    /**
     *
     * @param layoutId: the res of layout for showing single element
     * @param clickHandler: the handler put in place for click event
     */
    public ProductAdapter(int layoutId, @Nullable ClickHandler<Product> clickHandler) {
        this.layoutId = layoutId;
        this.clickHandler = clickHandler;
    }

    public void setDataSet(ArrayList<Product> items){
        this.items = items;
        mSize = items.size();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder
                (LayoutInflater.from(parent.getContext()).inflate(layoutId,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mViewDataBinding.setVariable(BR.item,items.get(position));
        if (clickHandler != null) {
            holder.setClickHandler(this).setTag(ITEM_MODEL, items.get(position));
        }
        holder.mViewDataBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mSize;
    }

    @Override
    public void onClick(View v) {
        if (clickHandler != null)
            clickHandler.onClick((Product) v.getTag(ITEM_MODEL),v);
    }

}
