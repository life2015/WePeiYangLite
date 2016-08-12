package com.twtstudio.wepeiyanglite.ui.bike;

import android.content.Context;

import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
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

    public BikeFragPresenter(Context context, BikeViewController viewController) {
        super(context);
        mViewController = viewController;
    }

    public void getStationStatus(String id){
        BikeApiClient.getInstance().getStationStatus(mContext,new BikeApiSubscriber(mContext,mStationListener),id);
    }

    protected OnNextListener<List<BikeStation>> mStationListener = new OnNextListener<List<BikeStation>>() {
        @Override
        public void onNext(List<BikeStation> bikeStations) {
            // TODO: 2016/8/12 更新底部图片的逻辑
        }
    };

    public List<MarkerOptions> getStationsDetail() {
        InputStream is = mContext.getResources().openRawResource(R.raw.detail);
        String detailJsonString = null;
        try {
            detailJsonString = IOUtils.toString(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        List<StationsDetail> detailList = new ArrayList<>();
        Type type = new TypeToken<ArrayList<StationsDetail>>() {
        }.getType();
        detailList = gson.fromJson(detailJsonString, type);
        List<MarkerOptions> markerOptionsList = new ArrayList<>();
        for (StationsDetail detail : detailList) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(detail.lat_c, detail.lng_c))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_unselected))
                    .snippet(String.valueOf(detail.id))
                    .title(detail.name);
            markerOptionsList.add(markerOptions);
        }
        return markerOptionsList;
    }

    public List<MarkerOptions> getStationsBrief() {
        InputStream is = mContext.getResources().openRawResource(R.raw.brief);
        String briefJsonString = null;
        try {
            briefJsonString = IOUtils.toString(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        List<StationsBrief> briefList = new ArrayList<>();
        Type type = new TypeToken<List<StationsBrief>>() {
        }.getType();
        briefList = gson.fromJson(briefJsonString,type);
        List<MarkerOptions> markerOptionsList = new ArrayList<>();
        for (StationsBrief brief : briefList) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(brief.lat_c,brief.lng_c))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_selected))
                    .title(brief.name);
            markerOptionsList.add(markerOptions);
        }
        return markerOptionsList;
    }
}
