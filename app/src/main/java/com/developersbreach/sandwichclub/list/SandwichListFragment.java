package com.developersbreach.sandwichclub.list;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.developersbreach.sandwichclub.R;
import com.developersbreach.sandwichclub.databinding.FragmentSandwichListBinding;

import java.util.Objects;


/**
 * This fragment will be initially visible for user which shows a list of sandwiches using RecyclerView
 * adapter {@link SandwichAdapter}
 * <p>
 * This class uses {@link SandwichListFragmentViewModel} to get data which is exposed from. Also helps
 * to keep this class clean and no operations are handled here. Also helps to preserve data of the fragment
 * without loosing it's data when user navigates between different destinations or changing orientation
 * of the device.
 * <p>
 * We show a basic animation of items in list sliding from right to left of the screen for first time
 * for user at this point to make aware of horizontal scrolling list implementation.
 */
public class SandwichListFragment extends Fragment {

    // This string used to get boolean value from SharedPreferences by editor whether to show or
    // hide animation for user when this fragment opens.
    private final String COMPLETED_ANIMATION_PREF_NAME = "ANIMATION_LINEAR_OUT";
    // This ViewGroup declared to show a simple animation.
    private ConstraintLayout mParentConstraintLayout;
    // Using preferences with editor to change the default value in string COMPLETED_ANIMATION_PREF_NAME
    private SharedPreferences mSharedPreferences;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentSandwichListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sandwich_list, container, false);

        // Declaring a AndroidViewModel which is associated with our fragment will be attached once created.
        SandwichListFragmentViewModel viewModel = new ViewModelProvider(this).get(SandwichListFragmentViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);

        // A horizontal scrolling recycler view showing sandwich's.
        RecyclerView sandwichRecyclerView = binding.sandwichRecyclerView;
        mParentConstraintLayout = binding.parentConstraintLayout;

        SnapHelper helper = new LinearSnapHelper();
        // Attach snapHelper to our RecyclerView.
        helper.attachToRecyclerView(sandwichRecyclerView);

        // Calling this method only once so that user stops seeing same animation every time navigating.
        animateForFirstTimeOnly();
        return binding.getRoot();
    }

    private void animateForFirstTimeOnly() {
        // Getting our shared preference in primate mode.
        mSharedPreferences = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        // Check for change in default value, if false returned then no effect happens.
        if (!mSharedPreferences.getBoolean(COMPLETED_ANIMATION_PREF_NAME, false)) {
            startLinearAnimation();
        }
    }

    private void startLinearAnimation() {
        mParentConstraintLayout.setTranslationX(800);
        mParentConstraintLayout.animate().translationX(0f).setDuration(1000L).start();
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(COMPLETED_ANIMATION_PREF_NAME, true);
        mEditor.apply();
    }
}
