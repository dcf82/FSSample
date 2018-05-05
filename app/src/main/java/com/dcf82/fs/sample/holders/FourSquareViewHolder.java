package com.dcf82.fs.sample.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dcf82.fs.sample.R;
import com.dcf82.fs.sample.helpers.Utilities;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FourSquareViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.icon)
    protected ImageView mIcon;

    @BindView(R.id.name)
    protected TextView mName;

    @BindView(R.id.category)
    protected TextView mCategory;

    @BindView(R.id.distance)
    protected TextView mDistance;

    @BindView(R.id.favorite)
    protected ImageView mFavorite;

    public FourSquareViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public FourSquareViewHolder setIcon(String url) {
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.ic_image_black_48dp)
                .error(R.drawable.ic_image_black_48dp)
                .into(mIcon);
        return this;
    }

    public FourSquareViewHolder setName(String name) {
        Utilities.setText(this.mName, name);
        return this;
    }

    public FourSquareViewHolder setCategory(String category) {
        Utilities.setText(this.mCategory, category);
        return this;
    }

    public FourSquareViewHolder setDistance(String distance) {
        Utilities.setText(this.mDistance, distance + " meters");
        return this;
    }

    public FourSquareViewHolder setFavorite(boolean favorite) {
        mFavorite.setImageResource(favorite ? R.drawable.ic_thumb_up_black_24dp : R.drawable.ic_thumb_up_white_24dp);
        return this;
    }
}
