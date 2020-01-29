package com.developersbreach.sandwichclub.detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.developersbreach.sandwichclub.list.Sandwich;

/**
 * A ViewModelFactory which is used to create a instance of {@link SandwichDetailFragmentViewModel}
 * AndroidViewModel for fragment class {@link SandwichDetailFragment} with a constructor.
 */
class SandwichDetailFragmentViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    // Needs to to be passed as parameter for AndroidViewModel class.
    private final Application mApplication;
    private final Sandwich mSandwich;

    /**
     * Creates a {@code AndroidViewModelFactory}
     *
     * @param application an application to pass in {@link AndroidViewModel}
     * @param sandwich    a user selected Sandwich object to pass in {@link AndroidViewModel}
     */
    SandwichDetailFragmentViewModelFactory(@NonNull Application application, Sandwich sandwich) {
        super(application);
        this.mApplication = application;
        this.mSandwich = sandwich;
    }

    /**
     * @param modelClass to check if our {@link SandwichDetailFragmentViewModel} model class is assignable.
     * @param <T>        type of generic class
     * @return returns the ViewModel class with passing parameters if instance is created.
     */
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SandwichDetailFragmentViewModel.class)) {
            //noinspection unchecked
            return (T) new SandwichDetailFragmentViewModel(mApplication, mSandwich);
        }
        throw new IllegalArgumentException("Cannot create Instance for this class");
    }
}
