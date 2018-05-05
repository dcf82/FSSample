package com.dcf82.fs.sample.helpers;

import android.util.Log;

import io.reactivex.functions.Consumer;

public class ErrorHandler implements Consumer<Throwable> {

    private static final String TAG = "ErrorHandler";

    private static ErrorHandler mSingleton;

    private ErrorHandler() {}

    public static ErrorHandler getSingleton() {
        if (mSingleton == null) {
            synchronized (ErrorHandler.class) {
                mSingleton = new ErrorHandler();
            }
        }
        return mSingleton;
    }

    @Override
    public void accept(Throwable error) throws Error {
        Log.e(TAG, "Thread: " + Thread.currentThread().getName() + ", Error: ", error);
    }

}
