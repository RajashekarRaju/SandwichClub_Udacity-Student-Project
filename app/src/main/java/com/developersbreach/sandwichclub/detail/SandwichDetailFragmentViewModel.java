package com.developersbreach.sandwichclub.detail;

import android.app.Application;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.developersbreach.sandwichclub.list.Sandwich;

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
class SandwichDetailFragmentViewModel extends AndroidViewModel {

    /*
     * Expose value publicly for class using this ViewModel
     *
     * MutableLiveData with value containing single Sandwich object which user selected.
     * */
    private final MutableLiveData<Sandwich> mSelectedSandwichLiveData;

    /**
     * @param application is only parameter accepted for this constructor.
     * @param sandwich    data for current sandwich item selected by user.
     */
    SandwichDetailFragmentViewModel(@NonNull Application application, Sandwich sandwich) {
        super(application);
        // Initializing empty MutableLiveData
        mSelectedSandwichLiveData = new MutableLiveData<>();
        // Set current value for exposed mutableLiveData object.
        mSelectedSandwichLiveData.setValue(sandwich);
    }

    // Returns the exposed data as MutableLiveData of value sandwich object
    MutableLiveData<Sandwich> getSelectedSandwich() {
        return mSelectedSandwichLiveData;
    }

    /**
     * @param sandwichList list of strings received by method to format without characters.
     * @param textView     set to final formatted value of each list of strings.
     */
    void formatList(List<String> sandwichList, TextView textView) {
        // New empty ArrayList which will be added with strings from sandwichList
        List<String> stringList = new ArrayList<>();
        for (String string : sandwichList) {
            stringList.add(string);
            // Remove and replace characters for proper string formation.
            String formatListToString = stringList.toString()
                    .replace(",", ", ")
                    .replace("[", "")
                    .replace("]", "").trim();
            textView.setText(formatListToString);
        }
    }
}
