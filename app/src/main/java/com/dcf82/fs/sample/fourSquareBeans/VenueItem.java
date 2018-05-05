package com.dcf82.fs.sample.fourSquareBeans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class VenueItem implements Serializable {

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("location")
    private Location mLocation;

    @SerializedName("categories")
    private List<Category> mCategories;

    @SerializedName("referralId")
    private String mReferralId;

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public Location getLocation() {
        return mLocation;
    }

    public List<Category> getCategories() {
        return mCategories;
    }

    public String getReferralId() {
        return mReferralId;
    }
}
