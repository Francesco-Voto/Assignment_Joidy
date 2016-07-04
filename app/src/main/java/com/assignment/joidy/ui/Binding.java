package com.assignment.joidy.ui;


import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Class container for the custom BindingAdapter to use with Android DataBinding
 * @see <a href="https://developer.android.com/topic/libraries/data-binding/index.html">DataBinding</a>
 */
public class Binding {

    @BindingAdapter("recyclerViewViewModel")
    public static void setRecyclerViewViewModel(RecyclerView recyclerView,
                                                ViewModel viewModel) {
        if(viewModel != null) viewModel.setRecyclerView(recyclerView);
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .crossFade()
                .into(view);
    }
}
