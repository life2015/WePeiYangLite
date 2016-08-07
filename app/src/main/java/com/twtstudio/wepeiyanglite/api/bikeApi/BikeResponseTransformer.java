package com.twtstudio.wepeiyanglite.api.bikeApi;

import rx.functions.Func1;

/**
 * Created by jcy on 2016/8/7.
 */

public class BikeResponseTransformer<T> implements Func1<BikeApiResponse<T>, T> {
    @Override
    public T call(BikeApiResponse<T> tBikeApiResponse) {
        if (tBikeApiResponse.getErrno() != 0) {
            throw new BikeApiException(tBikeApiResponse);
        }
        return tBikeApiResponse.getData();
    }
}
