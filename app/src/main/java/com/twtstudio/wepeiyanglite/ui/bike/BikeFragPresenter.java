package com.twtstudio.wepeiyanglite.ui.bike;

import android.content.Context;

import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.twtstudio.wepeiyanglite.R;
import com.twtstudio.wepeiyanglite.api.OnNextListener;
import com.twtstudio.wepeiyanglite.api.bikeApi.BikeApiClient;
import com.twtstudio.wepeiyanglite.api.bikeApi.BikeApiSubscriber;
import com.twtstudio.wepeiyanglite.common.BikePresenter;
import com.twtstudio.wepeiyanglite.model.BikeStation;
import com.twtstudio.wepeiyanglite.model.StationsBrief;
import com.twtstudio.wepeiyanglite.model.StationsDetail;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcy on 2016/8/11.
 */

public class BikeFragPresenter extends BikePresenter {
    private BikeViewController mViewController;
    private List<BikeStation> mCachedStatusList;

    public BikeFragPresenter(Context context, BikeViewController viewController) {
        super(context);
        mViewController = viewController;
    }

    public void getStationStatus(String id) {
        BikeApiClient.getInstance().getStationStatus(mContext, new BikeApiSubscriber(mContext, mStationListener), id);
    }

    protected OnNextListener<List<BikeStation>> mStationListener = new OnNextListener<List<BikeStation>>() {
        @Override
        public void onNext(List<BikeStation> bikeStations) {
            // TODO: 2016/8/12 更新底部图片的逻辑
            BikeStation detail = bikeStations.get(0);
            mViewController.setStationDetail(detail);
        }
    };

    public void cacheStationStatus() {
        BikeApiClient.getInstance().cacheStationStatus(mContext, new BikeApiSubscriber<>(mContext, mCacheListener));
    }

    protected OnNextListener<List<BikeStation>> mCacheListener = new OnNextListener<List<BikeStation>>() {
        @Override
        public void onNext(List<BikeStation> bikeStations) {
            mCachedStatusList = bikeStations;
        }
    };

    public void queryCachedStatus(String id){
        if (mCachedStatusList != null) {
            List<BikeStation> list = Stream.of(mCachedStatusList)
                    .filter(value -> value.id.equals(id))
                    .collect(Collectors.toList());
            mViewController.setStationDetail(list.get(0));
        }
    }
}
