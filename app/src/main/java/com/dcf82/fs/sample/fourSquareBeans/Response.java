package com.dcf82.fs.sample.fourSquareBeans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {

    @SerializedName("venues")
    private List<VenueItem> mVenues;

    public List<VenueItem> getVenues() {
        return mVenues;
    }
}
