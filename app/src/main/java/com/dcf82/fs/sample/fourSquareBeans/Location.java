package com.dcf82.fs.sample.fourSquareBeans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Location implements Serializable {

    @SerializedName("address")
    private String mAddress;

    @SerializedName("lat")
    private double mLatitude;

    @SerializedName("lng")
    private double mLongitude;

    @SerializedName("distance")
    private int mDistance;

    public String getAddress() {
        return mAddress;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public int getDistance() {
        return mDistance;
    }
}
