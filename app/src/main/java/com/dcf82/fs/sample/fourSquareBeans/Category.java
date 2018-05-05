package com.dcf82.fs.sample.fourSquareBeans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Category implements Serializable {

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("icon")
    private Icon mIcon;

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public Icon getIcon() {
        return mIcon;
    }
}
