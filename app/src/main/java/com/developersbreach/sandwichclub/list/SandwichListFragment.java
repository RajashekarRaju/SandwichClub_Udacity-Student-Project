package com.developersbreach.sandwichclub.list;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developersbreach.sandwichclub.R;

import java.util.List;

public class SandwichListFragment extends Fragment {

    private SandwichListViewModelFragment mViewModel;
    private RecyclerView recyclerView;
    private SandwichAdapter sandwichAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sandwich_list, container, false);
        recyclerView = view.findViewById(R.id.sandwich_recycler_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SandwichListViewModelFragment.class);
        mViewModel.mMutableSandwichList.observe(getViewLifecycleOwner(), new Observer<List<Sandwich>>() {
            @Override
            public void onChanged(List<Sandwich> sandwichList) {
                sandwichAdapter = new SandwichAdapter(getContext(), sandwichList, new SandwichListener());
                recyclerView.setAdapter(sandwichAdapter);
            }
        });
    }

    private class SandwichListener implements SandwichAdapter.SandwichAdapterListener {
        @Override
        public void onSandwichSelected(Sandwich sandwich, View view) {
            NavDirections direction = SandwichListFragmentDirections.actionSandwichListFragmentToSandwichDetailFragment(sandwich);
            Navigation.findNavController(view).navigate(direction);
        }
    }
}
