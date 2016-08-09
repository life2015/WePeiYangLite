package com.twtstudio.wepeiyanglite.api.bikeApi;

import com.twtstudio.wepeiyanglite.model.BikeAuth;
import com.twtstudio.wepeiyanglite.model.BikeCard;

import java.util.List;

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

    @FormUrlEncoded
    @POST("user/card")
    Observable<BikeApiResponse<List<BikeCard>>> getBikeCard(@Field("idnum") String idnum);

    @FormUrlEncoded
    @POST("user/bind")
    Observable<BikeApiResponse<Void>> bindBikeCard(@Field("id") String cardId, @Field("sign") String cardSign);

    @FormUrlEncoded
    @POST("user/unbind")
    Observable<BikeApiResponse<Void>> unbindBikeCard();

//    @FormUrlEncoded
//    @POST("user/info")

}
