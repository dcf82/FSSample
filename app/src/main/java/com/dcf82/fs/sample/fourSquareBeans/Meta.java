package com.dcf82.fs.sample.fourSquareBeans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Meta implements Serializable {

    @SerializedName("code")
    private int mCode;

    public int getCode() {
        return mCode;
    }

}
