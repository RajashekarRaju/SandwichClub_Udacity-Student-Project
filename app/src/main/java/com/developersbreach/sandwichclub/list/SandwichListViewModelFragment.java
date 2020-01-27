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


public class SandwichListViewModelFragment extends AndroidViewModel {

    MutableLiveData<List<Sandwich>> mMutableSandwichList;

    public SandwichListViewModelFragment(@NonNull Application application) {
        super(application);
        mMutableSandwichList = new MutableLiveData<>();
        getSandwichData(application);
    }

    private void getSandwichData(Application application) {

        final List<Sandwich> sandwichList = new ArrayList<>();
        final String[] arrayList = application.getResources().getStringArray(R.array.sandwich_details);
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                for (String currentSandwich: arrayList) {
                    Sandwich sandwich = JsonUtils.fetchSandwichJsonData(currentSandwich);
                    sandwichList.add(sandwich);
                }
            }
        });

        mMutableSandwichList.setValue(sandwichList);
    }

}
