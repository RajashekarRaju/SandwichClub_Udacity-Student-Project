package com.developersbreach.sandwichclub.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.developersbreach.sandwichclub.AppExecutors;
import com.developersbreach.sandwichclub.R;

import java.util.List;

public class SandwichAdapter extends RecyclerView.Adapter<SandwichAdapter.SandwichViewHolder> {

    // Context to access our resources
    private final Context mContext;
    // List of sandwich objects, create and return the elements
    private final List<Sandwich> mSandwichList;
    // Declaring custom listener for all click events
    private final SandwichAdapterListener mListener;
    // ViewModel to get all data from class
    private final SandwichListFragmentViewModel mViewModel;

    /**
     * Constructor for adapter class
     */
    SandwichAdapter(Context context, List<Sandwich> sandwichList, SandwichAdapterListener listener,
                    SandwichListFragmentViewModel viewModel) {
        this.mContext = context;
        this.mSandwichList = sandwichList;
        this.mListener = listener;
        this.mViewModel = viewModel;
    }

    /**
     * The interface that receives onClick listener.
     */
    public interface SandwichAdapterListener {
        void onSandwichSelected(Sandwich sandwich, View view);
    }

    /**
     * Children views for sandwich data
     */
    class SandwichViewHolder extends RecyclerView.ViewHolder {

        // Views which are visible as single item in recycler view
        final ImageView mSandwichImageView;
        final TextView mSandwichNameTextView;
        final View mSandwichBackGroundView;
        final TextView mSandwichOriginNameTextView;
        final ImageView mSandwichOriginImageView;

        private SandwichViewHolder(@NonNull final View itemView) {
            super(itemView);
            mSandwichImageView = itemView.findViewById(R.id.sandwich_image_item_view);
            mSandwichNameTextView = itemView.findViewById(R.id.sandwich_name_item_text_view);
            mSandwichBackGroundView = itemView.findViewById(R.id.sandwich_back_ground_view);
            mSandwichOriginNameTextView = itemView.findViewById(R.id.sandwich_origin_item_text_view);
            mSandwichOriginImageView = itemView.findViewById(R.id.sandwich_origin_item_image_view);
        }
    }

    /**
     * Called when RecyclerView needs a new {@link SandwichViewHolder} of the given type to represent
     * an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public SandwichViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sandwich, parent, false);
        return new SandwichViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link SandwichViewHolder#itemView} to reflect the item at the given
     * position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull final SandwichViewHolder holder, final int position) {
        final Sandwich sandwich = mSandwichList.get(position);

        // Running a executor on main thread and load data from ViewModel of sandwich properties.
        AppExecutors.getInstance().mainThread().execute(new Runnable() {
            @Override
            public void run() {
                mViewModel.loadSandwichListProperties(mContext, sandwich, holder);
            }
        });

        // Set listener using itemView and call onSandwichSelected from declared custom interface
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onSandwichSelected(sandwich, view);
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mSandwichList.size();
    }
}
