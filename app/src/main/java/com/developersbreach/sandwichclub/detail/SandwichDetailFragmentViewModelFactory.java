package com.developersbreach.sandwichclub.detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.developersbreach.sandwichclub.list.Sandwich;

public class SandwichDetailFragmentViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    private Application mApplication;
    private Sandwich mSandwich;

    /**
     * Creates a {@code AndroidViewModelFactory}
     *
     * @param application an application to pass in {@link AndroidViewModel}
     */
    SandwichDetailFragmentViewModelFactory(@NonNull Application application, Sandwich sandwich) {
        super(application);
        this.mApplication = application;
        this.mSandwich = sandwich;
    }

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
