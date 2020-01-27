package com.developersbreach.sandwichclub.detail;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developersbreach.sandwichclub.R;
import com.developersbreach.sandwichclub.list.Sandwich;

import java.util.Objects;

public class SandwichDetailFragment extends Fragment {

    private SandwichDetailFragmentViewModel mViewModel;
    private SandwichDetailFragmentViewModelFactory mViewModelFactory;
    private TextView textView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sandwich_detail, container, false);
        textView = view.findViewById(R.id.detail);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Application application = (Objects.requireNonNull(getActivity())).getApplication();
        Sandwich sandwich = SandwichDetailFragmentArgs.fromBundle(Objects.requireNonNull(getArguments())).getSandwichDetailArgs();
        mViewModelFactory = new SandwichDetailFragmentViewModelFactory(application, sandwich);
        mViewModel = new ViewModelProvider(this, mViewModelFactory).get(SandwichDetailFragmentViewModel.class);
        mViewModel.getSelectedSandwich().observe(getViewLifecycleOwner(), new Observer<Sandwich>() {
            @Override
            public void onChanged(Sandwich sandwich) {
                textView.setText(sandwich.getSandwichName());
            }
        });
    }

}
