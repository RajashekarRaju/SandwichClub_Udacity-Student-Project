package com.developersbreach.sandwichclub.list;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.developersbreach.sandwichclub.AppExecutors;
import com.developersbreach.sandwichclub.R;
import com.developersbreach.sandwichclub.list.SandwichAdapter.SandwichViewHolder;
import com.developersbreach.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

/*
 * We are extending with AndroidViewModel instead of ViewModel, because AndroidViewModel is aware of
 * using application context helps to get or access resources we need.
 *
 * AndroidViewModel helps us save fragment data and keeps UI simple
 * This classes are intended to contain code which does operations like loading data from internet
 * or from repository and database.
 * */
public class SandwichListFragmentViewModel extends AndroidViewModel {

    /*
     * Expose value publicly for class using this ViewModel
     *
     * MutableLiveData with value containing list of Sandwich objects.
     * */
    final MutableLiveData<List<Sandwich>> mMutableSandwichList;

    /**
     * @param application is only parameter accepted for this constructor
     */
    public SandwichListFragmentViewModel(@NonNull Application application) {
        super(application);
        // Initializing empty MutableLiveData
        mMutableSandwichList = new MutableLiveData<>();
        getSandwichData(application);
    }

    /**
     * @param application to get resources of string array elements.
     */
    private void getSandwichData(final Application application) {
        // New empty array list to add data fetched from json.
        final List<Sandwich> sandwichList = new ArrayList<>();
        // Gets data in newly created executor thread.
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                // Get all array objects from resources into newly created string array.
                final String[] arrayList = application.getResources().getStringArray(R.array.sandwich_details);
                for (String currentSandwich : arrayList) {
                    // This thread runs in background to fetch all JSON data into our object.
                    Sandwich sandwich = JsonUtils.fetchSandwichJsonData(currentSandwich);
                    // Loop through each array in list and add each element to newly created list.
                    sandwichList.add(sandwich);
                }
            }
        });

        // Set value for our exposed MutableLiveData with list containing sandwich data.
        mMutableSandwichList.setValue(sandwichList);
    }

    /**
     * This method is used in SandwichAdapter class which contains parameters context, sandwich
     * objects and viewHolder.
     *
     * @param context  aware of activity, in this case we need for Glide to load object.
     * @param sandwich object of all elements with Sandwich data.
     * @param holder   to get all views from ViewHolder class from adapter.
     */
    void loadSandwichListProperties(Context context, Sandwich sandwich, SandwichViewHolder holder) {

        // First check whether sandwich has a value before showing it to user.
        if (sandwich.getSandwichName().equals("") || sandwich.getSandwichName() == null) {
            // If value not available we set something appropriate to it.
            holder.mSandwichNameTextView.setText(context.getString(R.string.name_not_available));
        } else {
            // If value is valid, show it to user
            holder.mSandwichNameTextView.setText(sandwich.getSandwichName());
        }

        // First check whether sandwich has a value before showing it to user.
        if (sandwich.getSandwichPlaceOfOrigin().equals("") || sandwich.getSandwichPlaceOfOrigin() == null) {
            // If value is invalid, we just hide the view from screen
            holder.mSandwichOriginNameTextView.setVisibility(View.INVISIBLE);
            holder.mSandwichOriginImageView.setVisibility(View.INVISIBLE);
        } else {
            // Value is valid, show it to user.
            holder.mSandwichOriginNameTextView.setText(sandwich.getSandwichPlaceOfOrigin());
        }

        // With Glide we are loading image as bitmap
        Glide.with(context)
                .asBitmap()
                .load(sandwich.getSandwichImage())
                .placeholder(R.color.colorBackground)
                .centerCrop()
                // Start a new request for ImageView extending BitmapImageViewTarget
                .into(new targetImageView(holder));
    }

    /**
     * Extending into BitmapImageViewTarget to show an ImageView.
     * <p>
     * This class could have been separated to other package
     * {@link com.developersbreach.sandwichclub.utils} but since this class is only for such operations
     * keeping it here.
     */
    private class targetImageView extends BitmapImageViewTarget {

        private final SandwichViewHolder mHolder;

        /**
         * @param holder is only parameter for our class constructor to have access over ImageView
         *               from {@link SandwichAdapter} holder.
         */
        targetImageView(SandwichViewHolder holder) {
            super(holder.mSandwichImageView);
            this.mHolder = holder;
        }

        /**
         * Sets the {@link Bitmap} on the view using {@link
         * ImageView#setImageBitmap(Bitmap)}.
         *
         * @param resource The bitmap to display.
         */
        @Override
        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
            super.onResourceReady(resource, transition);
            // Request to generate palette as async
            Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                /**
                 * @param palette to extract colors from ImageView.
                 */
                @Override
                public void onGenerated(@Nullable Palette palette) {
                    // Only apply if palette has resources generated successfully.
                    if (palette != null) {
                        // Set color from palette to background of main list we show by calling holder.
                        Palette.Swatch swatch = palette.getDarkVibrantSwatch();
                        if (swatch != null)
                            mHolder.mSandwichBackGroundView.setBackgroundColor(swatch.getRgb());
                    }
                }
            });
        }
    }
}
