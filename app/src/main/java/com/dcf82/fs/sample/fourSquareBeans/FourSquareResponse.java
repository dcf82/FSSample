package com.dcf82.fs.sample.fourSquareBeans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FourSquareResponse implements Serializable {

    @SerializedName("meta")
    private Meta mMeta;

    @SerializedName("response")
    private Response mResponse;

    public Meta getMeta() {
        return mMeta;
    }

    public Response getResponse() {
        return mResponse;
    }

}
