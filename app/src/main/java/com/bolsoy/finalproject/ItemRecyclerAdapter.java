package com.bolsoy.finalproject;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ItemRecyclerAdapter extends FirestoreRecyclerAdapter<Item, ItemRecyclerAdapter.ItemViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private final SimpleDateFormat format = new SimpleDateFormat("MM-dd-yy", Locale.US);
    private final OnItemClickListener listener;

    ItemRecyclerAdapter(FirestoreRecyclerOptions<Item> options, OnItemClickListener listener) {
        super(options);
        this.listener = listener;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        final CardView view;
        final TextView itemTitle;
        final TextView itemPrice;
        final TextView createdOn;

        ItemViewHolder(CardView v) {
            super(v);
            view = v;
            itemTitle = v.findViewById(R.id.item_title);
            itemPrice = v.findViewById(R.id.item_price);
            createdOn = v.findViewById(R.id.item_created_on);
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ItemViewHolder holder, @NonNull int position, @NonNull final Item item) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.itemTitle.setText(item.getTitle());
        Log.w("ItemRecyclerAdapterAlex", item.getTitle());
        holder.itemPrice.setText(item.getPrice());
        Log.w("ItemRecyclerAdapterAlex", item.getPrice());
        holder.createdOn.setText(holder.view.getContext()
                .getString(R.string.created_on, format.format(item.getCreatedTime())));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_item, parent, false);

        return new ItemViewHolder(v);
    }
}