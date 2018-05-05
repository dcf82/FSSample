package com.dcf82.fs.sample.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcf82.fs.sample.R;
import com.dcf82.fs.sample.activities.VenueItemDetailsActivity;
import com.dcf82.fs.sample.controller.FSController;
import com.dcf82.fs.sample.fourSquareBeans.Category;
import com.dcf82.fs.sample.fourSquareBeans.VenueItem;
import com.dcf82.fs.sample.helpers.Utilities;
import com.dcf82.fs.sample.holders.FourSquareViewHolder;

import java.util.ArrayList;
import java.util.List;

public class FourSquareAdapter extends RecyclerView.Adapter<FourSquareViewHolder> implements View.OnClickListener {

    private final List<VenueItem> mItems = new ArrayList<>();

    @Override
    public FourSquareViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.venue_item,
                parent, false);

        return new FourSquareViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FourSquareViewHolder holder, int position) {
        VenueItem venue = mItems.get(position);

        Category category = null;
        if (!Utilities.isNullOrEmpty(venue.getCategories())) {
            category = venue.getCategories().get(0);
        }

        holder.setIcon(category != null ? category.getIcon().getUrl() : null)
                .setName(venue.getName())
                .setCategory(category != null ? category.getName() : null)
                .setDistance(Integer.toString(venue.getLocation().getDistance() ))
                .setFavorite(FSController.getApp().getMyPreferences().getBoolean(venue.getId(),
                        false));

        holder.itemView.setTag(venue);
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setmItems(List<VenueItem> list) {
        mItems.clear();
        mItems.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (view.getTag() instanceof VenueItem) {
            VenueItem item = ((VenueItem)view.getTag());

            // Open Details screen
            Intent activity = new Intent(view.getContext(), VenueItemDetailsActivity.class);
            activity.putExtra("item", item);
            view.getContext().startActivity(activity);
        }
    }
}
