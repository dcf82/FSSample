package com.dcf82.fs.sample.helpers;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class Utilities {

    private static final String TAG = "Utilities";

    // Custom Thread Pool
    private static final int THREAD_POOL_SIZE = 8;
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    public static final Scheduler POOLED_SCHEDULER = Schedulers.from(EXECUTOR);

    private Utilities() {}

    public static void showSoftKeyboard(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public static <T> boolean isNullOrEmpty(List<T> list) {
        return list == null || list.size() == 0;
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.length() == 0;
    }

    public static void log(String tag, String info) {
        Log.i(tag, Thread.currentThread().getName() + ", Log: " + info);
    }

    public static void log(String info) {
        Log.i(TAG, Thread.currentThread().getName() + ", Log: " + info);
    }

    public static void showMessage(Context context, int msgId) {
        showMessage(context, context.getResources().getString(msgId));
    }

    public static void showMessage(Context context, String msg) {
        if (context == null || (context instanceof Activity && ((Activity)context).isFinishing())) return;
        Toast to = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        to.setGravity(Gravity.CENTER, 0, 0);
        to.show();
    }

    public static void setText(TextView textView, String text) {
        if (Utilities.isNullOrEmpty(text)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(text);
        }
    }

    public static void openSoftKeyboard(Activity activity, EditText editText) {
        InputMethodManager im = ((InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE));
        if (im != null) {
            im.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
        }
    }

    public static void showView(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
