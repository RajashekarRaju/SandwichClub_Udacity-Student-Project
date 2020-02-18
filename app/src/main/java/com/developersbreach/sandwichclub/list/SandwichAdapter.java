package com.developersbreach.sandwichclub.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.developersbreach.sandwichclub.AppExecutors;
import com.developersbreach.sandwichclub.R;
import com.developersbreach.sandwichclub.databinding.ItemSandwichBinding;

public class SandwichAdapter extends ListAdapter<Sandwich, SandwichAdapter.SandwichViewHolder> {


    public SandwichAdapter() {
        super(DIFF_ITEM_CALLBACK);
    }

    class SandwichViewHolder extends RecyclerView.ViewHolder {

        private final ItemSandwichBinding mBinding;

        private SandwichViewHolder(ItemSandwichBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        void bind(final Sandwich sandwich) {
            AppExecutors.getInstance().mainThread().execute(new Runnable() {
                @Override
                public void run() {
                    mBinding.setSandwich(sandwich);
                    mBinding.executePendingBindings();
                }
            });
        }
    }

    @NonNull
    @Override
    public SandwichViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemSandwichBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_sandwich, parent, false);
        return new SandwichViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final SandwichViewHolder holder, final int position) {
        final Sandwich sandwich = getItem(position);
        holder.bind(sandwich);

        // Set listener using itemView and call onSandwichSelected from declared custom interface
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections direction = SandwichListFragmentDirections.actionSandwichListFragmentToSandwichDetailFragment(sandwich);
                Navigation.findNavController(view).navigate(direction);
            }
        });
    }

    private static final DiffUtil.ItemCallback<Sandwich> DIFF_ITEM_CALLBACK = new  DiffUtil.ItemCallback<Sandwich>() {

        @Override
        public boolean areItemsTheSame(@NonNull Sandwich oldItem, @NonNull Sandwich newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Sandwich oldItem, @NonNull Sandwich newItem) {
            return oldItem.getSandwichName().equals(newItem.getSandwichName());
        }
    };
}
