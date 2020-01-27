package com.developersbreach.sandwichclub.detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.developersbreach.sandwichclub.list.Sandwich;


public class SandwichDetailFragmentViewModel extends AndroidViewModel {

    private MutableLiveData<Sandwich> mSelectedSandwichLiveData;

    public SandwichDetailFragmentViewModel(@NonNull Application application, Sandwich sandwich) {
        super(application);
        mSelectedSandwichLiveData = new MutableLiveData<>();
        mSelectedSandwichLiveData.setValue(sandwich);
    }

    MutableLiveData<Sandwich> getSelectedSandwich() {
        return mSelectedSandwichLiveData;
    }
}
