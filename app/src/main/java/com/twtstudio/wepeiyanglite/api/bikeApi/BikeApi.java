package com.twtstudio.wepeiyanglite.api.bikeApi;

import com.twtstudio.wepeiyanglite.model.BikeAuth;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jcy on 2016/8/6.
 */

public interface BikeApi {

    @FormUrlEncoded
    @POST("user/auth")
    Observable<BikeApiResponse<BikeAuth>> getBikeToken(@Field("wpy_tk") String wpy_token);


}
