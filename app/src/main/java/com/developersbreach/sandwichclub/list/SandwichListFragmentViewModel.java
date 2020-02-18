package com.developersbreach.sandwichclub.list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.developersbreach.sandwichclub.AppExecutors;
import com.developersbreach.sandwichclub.R;
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
    private final MutableLiveData<List<Sandwich>> _mMutableSandwichList;


    // Encapsulated filed
    public MutableLiveData<List<Sandwich>> getSandwiches() {
        return _mMutableSandwichList;
    }

    /**
     * @param application is only parameter accepted for this constructor
     */
    public SandwichListFragmentViewModel(@NonNull Application application) {
        super(application);
        // Initializing empty MutableLiveData
        _mMutableSandwichList = new MutableLiveData<>();
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
        _mMutableSandwichList.setValue(sandwichList);
    }
}
