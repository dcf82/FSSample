package com.dcf82.fs.sample.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.dcf82.fs.sample.R;

import com.dcf82.fs.sample.fourSquareBeans.FourSquareResponse;
import com.dcf82.fs.sample.fragments.FourSquareList;
import com.dcf82.fs.sample.fragments.FourSquareMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FourSquareListActivity extends AppCompatActivity {

    enum ViewType {
        LIST, MAP
    }

    private FourSquareResponse data;
    private ViewType mCurrentViewType = ViewType.LIST;

    @BindView(R.id.toogle)
    protected FloatingActionButton mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_square_list);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setDisplayShowHomeEnabled(true);
        }

        // Getting data back
        data = (FourSquareResponse)getIntent().getSerializableExtra("data");

        // Ensure data is there, if not just kill it
        if (data == null) {
            finish();
            return;
        }

        replaceView(ViewType.LIST);
        mCurrentViewType = ViewType.LIST;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @OnClick(R.id.toogle)
    protected void toggleView() {
        replaceView(mCurrentViewType == ViewType.LIST ? ViewType.MAP : ViewType.LIST);
    }

    private void replaceView(ViewType type) {
        Fragment fragment;
        int drawable;

        switch (type) {
            case MAP:
                fragment = FourSquareMap.newInstance(data, null);
                drawable = R.drawable.ic_library_books_black_36dp;
                break;
            default: {
                fragment = FourSquareList.newInstance(data);
                drawable = R.drawable.ic_map_black_36dp;
            }
        }

        mCurrentViewType = type;
        mButton.setImageDrawable(ContextCompat.getDrawable(this, drawable));
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment).commit();
    }
}
