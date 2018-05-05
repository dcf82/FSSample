package com.dcf82.fs.sample.network;

import com.dcf82.fs.sample.fourSquareBeans.FourSquareResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitFSInterface {

    // Client ID & Client Secret keys never change, so no need to ask for them in the query
    // parameters
    @GET ("search?client_id=GPMP0DG0XBBQVCAF3URH4LOJTET0QEHB0M4NY1LO5WKLKRG3&client_secret=IE4OD14CKZCTGXHHN4HN4FNKMA0XTVA2EWUQD0IYGCM5ZTLP")
    Single<FourSquareResponse> getFourSquareResponse(@Query("ll") String latLon, @Query("query")
            String query, @Query("v") String v);

}
