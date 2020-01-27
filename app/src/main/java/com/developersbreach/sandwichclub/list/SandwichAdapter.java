package com.developersbreach.sandwichclub.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.developersbreach.sandwichclub.AppExecutors;
import com.developersbreach.sandwichclub.R;

import java.util.List;

public class SandwichAdapter extends RecyclerView.Adapter<SandwichAdapter.SandwichViewHolder> {

    private Context mContext;
    private List<Sandwich> mSandwichList;
    private SandwichAdapterListener mListener;

    /**
     * Constructor for our adapter class
     */
    SandwichAdapter(Context context, List<Sandwich> sandwichList, SandwichAdapterListener listener) {
        this.mContext = context;
        this.mSandwichList = sandwichList;
        this.mListener = listener;
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

        private ImageView mSandwichImageView;
        private TextView mSandwichNameTextView;

        SandwichViewHolder(@NonNull final View itemView) {
            super(itemView);
            mSandwichImageView = itemView.findViewById(R.id.sandwich_image_item_view);
            mSandwichNameTextView = itemView.findViewById(R.id.sandwich_name_item_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onSandwichSelected(mSandwichList.get(getAdapterPosition()), itemView);
                }
            });
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
    public void onBindViewHolder(@NonNull final SandwichViewHolder holder, int position) {
        final Sandwich sandwich = mSandwichList.get(position);

        AppExecutors.getInstance().mainThread().execute(new Runnable() {
            @Override
            public void run() {
                holder.mSandwichNameTextView.setText(sandwich.getSandwichName());
                Glide.with(mContext).load(sandwich.getSandwichImage()).centerCrop().into(holder.mSandwichImageView);
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
