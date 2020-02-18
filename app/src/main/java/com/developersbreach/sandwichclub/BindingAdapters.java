package com.developersbreach.sandwichclub;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.developersbreach.sandwichclub.list.Sandwich;
import com.developersbreach.sandwichclub.list.SandwichAdapter;

import java.util.List;

public class BindingAdapters {

    @BindingAdapter("listData")
    public static void bindSandwichList(RecyclerView recyclerView, List<Sandwich> sandwichList) {
        SandwichAdapter adapter = new SandwichAdapter();
        adapter.submitList(sandwichList);
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("sandwichImage")
    public static void bindSandwichImage(ImageView imageView, String sandwich) {
        Glide.with(imageView.getContext())
                .asBitmap()
                .load(sandwich)
                .placeholder(R.color.colorBackground)
                .centerCrop()
                // Start a new request for ImageView extending BitmapImageViewTarget
                .into(imageView);
    }

    @BindingAdapter("sandwichName")
    public static void bindSandwichName(TextView textView, String name) {
        if ("".equals(name)) {
            // If value not available we set something appropriate to it.
            textView.setText(textView.getContext().getResources().getString(R.string.name_not_available));
        } else {
            // If value is valid, show it to user
            textView.setText(name);
        }
    }

    @BindingAdapter("sandwichOriginName")
    public static void bindSandwichOriginName(TextView textView, String name) {
        if ("".equals(name)) {
            // If value is invalid, we just hide the view from screen
            textView.setVisibility(View.INVISIBLE);
        } else {
            // Value is valid, show it to user.
            textView.setText(name);
        }
    }
}
