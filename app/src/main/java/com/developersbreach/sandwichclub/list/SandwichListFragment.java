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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.developersbreach.sandwichclub.R;

import java.util.List;
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
    // Declaring a AndroidViewModel which is associated with our fragment will be attached once created.
    private SandwichListFragmentViewModel mViewModel;
    // A horizontal scrolling recycler view showing sandwich's.
    private RecyclerView mSandwichRecyclerView;
    // RecyclerView adapter with ViewHolder returns list of sandwich items.
    private SandwichAdapter mSandwichAdapter;
    // This ViewGroup declared to show a simple animation.
    private ConstraintLayout mParentConstraintLayout;
    // Using preferences with editor to change the default value in string COMPLETED_ANIMATION_PREF_NAME
    private SharedPreferences mSharedPreferences;

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
        View view = inflater.inflate(R.layout.fragment_sandwich_list, container, false);
        mSandwichRecyclerView = view.findViewById(R.id.sandwich_recycler_view);
        mParentConstraintLayout = view.findViewById(R.id.parent_constraint_layout);
        // Calling this method only once so that user stops seeing same animation every time navigating.
        animateForFirstTimeOnly();
        return view;
    }

    // Until value in sharedPreference won't effect, we continue showing animation to user.
    private void animateForFirstTimeOnly() {
        // Getting our shared preference in primate mode.
        mSharedPreferences = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        // Check for change in default value, if false returned then no effect happens.
        if (!mSharedPreferences.getBoolean(COMPLETED_ANIMATION_PREF_NAME, false)) {
            startLinearAnimation();
        }
    }

    /**
     * Called when the fragment's activity has been created and this fragment's view hierarchy instantiated.
     * <p>
     * Start observing data with associated ViewModel class {@link SandwichListFragmentViewModel}
     * using ViewModelProviders.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SandwichListFragmentViewModel.class);
        // Observe exposed data frm AndroidViewModel class
        mViewModel.mMutableSandwichList.observe(getViewLifecycleOwner(), new Observer<List<Sandwich>>() {
            /**
             * @param sandwichList contains list of data which is observable
             */
            @Override
            public void onChanged(List<Sandwich> sandwichList) {
                // SnapHelper helps placing one item from the list of items properly.
                SnapHelper helper = new LinearSnapHelper();
                // Attach snapHelper to our RecyclerView.
                helper.attachToRecyclerView(mSandwichRecyclerView);
                // Pass all params to adapter and set adapter to RecyclerView
                mSandwichAdapter = new SandwichAdapter(getContext(), sandwichList, new SandwichListener(), mViewModel);
                mSandwichRecyclerView.setAdapter(mSandwichAdapter);
            }
        });
    }

    /**
     * This class implements custom listener from adapter class to handle click events by user.
     */
    private class SandwichListener implements SandwichAdapter.SandwichAdapterListener {
        /**
         * @param sandwich of object Sandwich is Parcelable class
         * @param view     required by NavController as parameter to navigate to other destination.
         */
        @Override
        public void onSandwichSelected(Sandwich sandwich, View view) {
            // This handles operation to navigate with an argument.
            NavDirections direction = SandwichListFragmentDirections.actionSandwichListFragmentToSandwichDetailFragment(sandwich);
            Navigation.findNavController(view).navigate(direction);
        }
    }

    /**
     * Method which carries showing item animation from right to left in device with animate object.
     * <p>
     * ViewGroup mParentConstraintLayout makes the slide animation.
     * Once animation is shown now we don't want user to see it again, for this we change value
     * inside shared preference using editor to set boolean value to true.
     * This boolean value changes to false again only when we set it to true.
     */
    private void startLinearAnimation() {
        mParentConstraintLayout.setTranslationX(800);
        mParentConstraintLayout.animate().translationX(0f).setDuration(1000L).start();
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(COMPLETED_ANIMATION_PREF_NAME, true);
        mEditor.apply();
    }
}
