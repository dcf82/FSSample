package com.dcf82.fs.sample.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.dcf82.fs.sample.R;
import com.dcf82.fs.sample.controller.FSController;
import com.dcf82.fs.sample.helpers.Utilities;
import com.dcf82.fs.sample.network.RetrofitFSInterface;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

import static android.widget.TextView.OnEditorActionListener;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    @BindView(R.id.searchInfo)
    protected EditText mEditText;

    @BindView(R.id.loadScreen)
    protected View mLoadScreen;

    @Inject protected CompositeDisposable mDisposables;
    @Inject protected RetrofitFSInterface mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Inject some few dependencies
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Inject dependencies
        FSController.getApp().getNetComponent().inject(this);

        mEditText.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    search();
                }
                return false;
            }
        });
    }

    public void onResume() {
        super.onResume();

        // Small delay to give the UI some time to prepare itself
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFinishing()) return;
                Utilities.openSoftKeyboard(HomeActivity.this, mEditText);
            }
        }, 100L);
    }

    public void onDestroy() {
        super.onDestroy();

        // Clean up
        if (mDisposables != null) {
            mDisposables.dispose();
        }
        mDisposables = null;
    }

    @OnClick(R.id.search)
    protected void search() {

        String text = mEditText.getText().toString().trim();

        if (Utilities.isNullOrEmpty(text)) {
            Utilities.showMessage(this, R.string.type_search_text);
            return;
        }

        // Make visible the loock screen
        Utilities.showView(mLoadScreen, true);

        // Make the required query
        mDisposables.add(mService.getFourSquareResponse(getString(R.string.ll), text,
                getString(R.string.versioning))
                .subscribeOn(Utilities.POOLED_SCHEDULER)
                .toObservable()
                // Show toast over the UI Thread
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> {
                    Utilities.showMessage(this, R.string.no_internet_available);
                    Utilities.showView(mLoadScreen, false);
                }).subscribe(fourSquareResponse -> {
                    Utilities.log(TAG, "Code: " + fourSquareResponse.getMeta().getCode() + ", " +
                            "Size of response: " + fourSquareResponse.getResponse().getVenues().size());
                    Utilities.showView(mLoadScreen, false);
                    if (fourSquareResponse.getResponse().getVenues().size() > 0) {
                        mEditText.setText("");
                        Intent activity = new Intent(this, FourSquareListActivity.class);
                        activity.putExtra("data", fourSquareResponse);
                        startActivity(activity);
                    } else {
                        Utilities.showMessage(this, R.string.nothing_found);
                    }
                }));
    }
}
