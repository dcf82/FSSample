package com.dcf82.fs.sample.fourSquareBeans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Icon implements Serializable {

    @SerializedName("prefix")
    private String mPrefix;

    @SerializedName("suffix")
    private String mSuffix;

    public String getPrefix() {
        return mPrefix;
    }

    public String getSuffix() {
        return mSuffix;
    }

    public String getUrl() {
        return mPrefix + "64" + mSuffix;
    }
}
