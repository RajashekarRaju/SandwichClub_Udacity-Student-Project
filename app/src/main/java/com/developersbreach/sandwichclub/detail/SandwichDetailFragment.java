package com.developersbreach.sandwichclub.detail;

import android.app.Application;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.developersbreach.sandwichclub.AppExecutors;
import com.developersbreach.sandwichclub.R;
import com.developersbreach.sandwichclub.list.Sandwich;
import com.developersbreach.sandwichclub.list.SandwichListFragment;
import com.google.android.material.chip.Chip;

import java.util.Objects;


/**
 * This is detail fragment which shows detail view for selected sandwich from {@link SandwichListFragment}
 * list by user.
 * <p>
 * This class uses {@link SandwichDetailFragmentViewModel} to get data which is exposed from. Also helps
 * to keep this class clean and no operations are handled here. Also helps to preserve data of the fragment
 * without loosing it's data when user navigates between different destinations or changing orientation
 * of the device.
 * <p>
 * each time user opens this screen we show a basic animation of ViewGroup in list sliding from bottom
 * to top of the screen.
 */
public class SandwichDetailFragment extends Fragment {

    // Declaring a AndroidViewModel which is associated with our fragment will be attached once created.
    private SandwichDetailFragmentViewModel mViewModel;
    // Declaring all views which will be showing sandwich details in this fragment.
    private TextView mSandwichNameDetailTextView;
    private ImageView mSandwichImageDetailView;
    private Chip mSandwichOriginDetailChipView;
    private TextView mSandwichDescriptionDetailTextView;
    private TextView mAlsoKnownAsHeaderTag;
    private TextView mSandwichAlsoKnownAsDetailTextView;
    private TextView mIngredientsHeaderTag;
    private TextView mSandwichIngredientsDetailTextView;
    // This ViewGroup declared to show a simple animation and custom background color for each item.
    private ConstraintLayout mDetailBackGroundConstraintLayout;
    // Horizontal divider between sandwich title and description.
    private View mTitleDividerDetailView;

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null. This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     *
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sandwich_detail, container, false);
        // Separate method with view to get reference for all views to keep this code place clean.
        setSandwichDetailViews(view);
        // Call this animation every time when fragment opens.
        startAnimation();
        return view;
    }

    /**
     * @param view passing this view parameter to find all views in layout using id.
     */
    private void setSandwichDetailViews(View view) {
        mSandwichNameDetailTextView = view.findViewById(R.id.sandwich_name_detail_text_view);
        mSandwichImageDetailView = view.findViewById(R.id.sandwich_image_detail_view);
        mSandwichOriginDetailChipView = view.findViewById(R.id.sandwich_origin_detail_chip);
        mSandwichDescriptionDetailTextView = view.findViewById(R.id.sandwich_description_detail_text_view);
        mAlsoKnownAsHeaderTag = view.findViewById(R.id.also_known_as_header_tag);
        mSandwichAlsoKnownAsDetailTextView = view.findViewById(R.id.sandwich_alsoknownas_detail_text_view);
        mIngredientsHeaderTag = view.findViewById(R.id.ingredients_header_tag);
        mSandwichIngredientsDetailTextView = view.findViewById(R.id.sandwich_ingredients_detail_text_view);
        mDetailBackGroundConstraintLayout = view.findViewById(R.id.detail_background_view);
        mTitleDividerDetailView = view.findViewById(R.id.main_detail_title_divider);
    }

    // Method which creates animation for ViewGroup using animate object.
    private void startAnimation() {
        mDetailBackGroundConstraintLayout.setTranslationY(800);
        mDetailBackGroundConstraintLayout.animate().translationY(0f).setDuration(1000L).start();
    }

    /**
     * Called when the fragment's activity has been created and this fragment's view hierarchy instantiated.
     * <p>
     * Start observing data with associated ViewModel class {@link SandwichDetailFragmentViewModel}
     * using ViewModelProviders.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Get application of activity to pass into ViewModelFactory
        Application application = (Objects.requireNonNull(getActivity())).getApplication();
        // Get arguments from bundle into sandwich object to pass into ViewModelFactory
        // This arguments are received properly only after proper build using NavigationComponent.
        Sandwich sandwich = SandwichDetailFragmentArgs
                .fromBundle(Objects.requireNonNull(getArguments())).getSandwichDetailArgs();
        // Create factory object and pass application and sandwich
        SandwichDetailFragmentViewModelFactory factory
                = new SandwichDetailFragmentViewModelFactory(application, sandwich);
        // Get AndroidViewModel using ViewModelProvides
        mViewModel = new ViewModelProvider(this, factory).get(SandwichDetailFragmentViewModel.class);
        // Observe exposed data frm AndroidViewModel class
        mViewModel.getSelectedSandwich().observe(getViewLifecycleOwner(), new Observer<Sandwich>() {
            /**
             * @param sandwich contains value for current sandwich data.
             */
            @Override
            public void onChanged(final Sandwich sandwich) {
                // Start getting data in newly created main thread using Executor
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        loadSandwichDetails(sandwich);
                    }
                });
            }
        });
    }

    /**
     * @param sandwich data for each view in Sandwich's
     */
    private void loadSandwichDetails(Sandwich sandwich) {

        // First check whether sandwich has a value before showing it to user.
        if (sandwich.getSandwichName().equals("") || sandwich.getSandwichName() == null) {
            // If value not available we set something appropriate to it.
            mSandwichNameDetailTextView.setVisibility(View.GONE);
        } else {
            // If value is valid, show it to user
            mSandwichNameDetailTextView.setText(sandwich.getSandwichName());
        }

        // First check whether sandwich has a value before showing it to user.
        // If value not available we set something appropriate to it.
        // If value is valid, show it to user
        if (sandwich.getSandwichPlaceOfOrigin().equals("") || sandwich.getSandwichPlaceOfOrigin() == null) {
            mSandwichOriginDetailChipView.setVisibility(View.GONE);
        } else {
            mSandwichOriginDetailChipView.setText(sandwich.getSandwichPlaceOfOrigin());
        }

        // First check whether sandwich has a value before showing it to user.
        // If value not available we set something appropriate to it.
        // If value is valid, show it to user
        if (sandwich.getSandwichDescription().equals("") || sandwich.getSandwichDescription() == null) {
            mSandwichDescriptionDetailTextView.setVisibility(View.GONE);
        } else {
            mSandwichDescriptionDetailTextView.setText(sandwich.getSandwichDescription());
        }

        // Check if list size is zero and the display data to user
        if (sandwich.getSandwichAlsoKnownAs().size() == 0) {
            // Hide view since no data is available to show
            mSandwichAlsoKnownAsDetailTextView.setVisibility(View.GONE);
            mAlsoKnownAsHeaderTag.setVisibility(View.GONE);
        } else {
            // Format list without special characters and set to TextView.
            mViewModel.formatList(sandwich.getSandwichAlsoKnownAs(), mSandwichAlsoKnownAsDetailTextView);
        }

        // Check if list size is zero and the display data to user
        if (sandwich.getSandwichIngredients().size() == 0) {
            // Hide view since no data is available to show
            mSandwichIngredientsDetailTextView.setVisibility(View.GONE);
            mIngredientsHeaderTag.setVisibility(View.GONE);
        } else {
            // Format list without special characters and set to TextView.
            mViewModel.formatList(sandwich.getSandwichIngredients(), mSandwichIngredientsDetailTextView);
        }

        // With Glide we are loading image as bitmap
        Glide.with(Objects.requireNonNull(getContext()))
                .asBitmap()
                .load(sandwich.getSandwichImage())
                .placeholder(R.color.colorBackground)
                .centerCrop()
                // Start a new request for ImageView extending BitmapImageViewTarget
                .into(new targetView(mSandwichImageDetailView));
    }

    /**
     * Extending into BitmapImageViewTarget to show an ImageView.
     */
    private class targetView extends BitmapImageViewTarget {

        /**
         * @param imageView is valid constructor parameter fro class
         */
        targetView(ImageView imageView) {
            super(imageView);
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
                @Override
                public void onGenerated(@Nullable Palette palette) {
                    // Only apply if palette has resources generated successfully.
                    if (palette != null) {
                        applySwatchToViews(palette);
                    }
                }
            });
        }

        /**
         * @param palette to extract colors from ImageView.
         */
        private void applySwatchToViews(@NonNull Palette palette) {
            Palette.Swatch lightVibrantSwatch = palette.getLightVibrantSwatch();
            if (lightVibrantSwatch != null) {
                mDetailBackGroundConstraintLayout.setBackgroundColor(lightVibrantSwatch.getRgb());
            }

            Palette.Swatch darkMutedSwatch = palette.getDarkMutedSwatch();
            if (darkMutedSwatch != null) {
                mSandwichNameDetailTextView.setTextColor(darkMutedSwatch.getRgb());
                mSandwichOriginDetailChipView.setChipStrokeColor(ColorStateList.valueOf(darkMutedSwatch.getRgb()));
                mSandwichOriginDetailChipView.setTextColor(darkMutedSwatch.getRgb());
                mSandwichOriginDetailChipView.setChipIconTint(ColorStateList.valueOf(darkMutedSwatch.getRgb()));
                mSandwichDescriptionDetailTextView.setTextColor(darkMutedSwatch.getRgb());
                mSandwichAlsoKnownAsDetailTextView.setTextColor(darkMutedSwatch.getRgb());
                mSandwichIngredientsDetailTextView.setTextColor(darkMutedSwatch.getRgb());
            }

            Palette.Swatch mutedSwatch = palette.getMutedSwatch();
            if (mutedSwatch != null) {
                mTitleDividerDetailView.setBackgroundColor(mutedSwatch.getRgb());
                mAlsoKnownAsHeaderTag.setTextColor(mutedSwatch.getRgb());
                mIngredientsHeaderTag.setTextColor(mutedSwatch.getRgb());
            }
        }
    }
}
