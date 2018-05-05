package com.dcf82.fs.sample.activities;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.dcf82.fs.sample.R;
import com.dcf82.fs.sample.controller.FSController;
import com.dcf82.fs.sample.fourSquareBeans.Category;
import com.dcf82.fs.sample.fourSquareBeans.VenueItem;
import com.dcf82.fs.sample.fragments.FourSquareMap;
import com.dcf82.fs.sample.helpers.Utilities;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VenueItemDetailsActivity extends AppCompatActivity {

    VenueItem mItem;

    @BindView(R.id.app_bar)
    protected AppBarLayout mAppBarLayout;

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

    private boolean mFavoriteStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vinue_details);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setDisplayShowHomeEnabled(true);
        }

        mItem = (VenueItem)getIntent().getSerializableExtra("item");
        if (mItem == null) {
            finish();
            return;
        }

        toolbar.setTitle("");

        Fragment fragment = FourSquareMap.newInstance(null, mItem);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment).commit();

        mFavoriteStatus = FSController.getApp().getMyPreferences().getBoolean(mItem.getId(), false);

        Category category = null;
        if (!Utilities.isNullOrEmpty(mItem.getCategories())) {
            category = mItem.getCategories().get(0);
        }

        // Icon
        Picasso.get()
                .load(category != null ? category.getIcon().getUrl() : null)
                .placeholder(R.drawable.ic_image_black_48dp)
                .error(R.drawable.ic_image_black_48dp)
                .into(mIcon);

        // Name
        Utilities.setText(mName, mItem.getName());

        // Category
        Utilities.setText(mCategory, category != null ? category.getName() : null);

        // Distance
        Utilities.setText(mDistance, mItem.getLocation().getDistance() + " meters");

        // Favorite
        setIconFavorite();

        // This is to ensure setting 50% of the available drawing height
        ViewTreeObserver vto = mAppBarLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mAppBarLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                ViewGroup.LayoutParams params = mAppBarLayout.getLayoutParams();

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int height = displayMetrics.heightPixels;

                params.height = height / 2;
                mAppBarLayout.setLayoutParams(params);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @OnClick(R.id.favorite)
    protected void toggle() {
        mFavoriteStatus = !mFavoriteStatus;
        FSController.getApp().getMyPreferences().edit().putBoolean(mItem.getId(),
                mFavoriteStatus).apply();
        setIconFavorite();
    }

    private void setIconFavorite() {
        mFavorite.setImageResource(mFavoriteStatus ? R.drawable.ic_thumb_up_black_24dp :
                R.drawable.ic_thumb_up_white_24dp);
    }
}
