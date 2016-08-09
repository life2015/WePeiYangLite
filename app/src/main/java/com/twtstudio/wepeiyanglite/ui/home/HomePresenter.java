package com.twtstudio.wepeiyanglite.ui.home;

import android.content.Context;

import com.twtstudio.wepeiyanglite.api.bikeApi.BikeApiClient;
import com.twtstudio.wepeiyanglite.common.Presenter;

/**
 * Created by jcy on 2016/8/9.
 */

public class HomePresenter extends Presenter {
    private HomeViewController mViewController;
    public HomePresenter(Context context,HomeViewController viewController) {
        super(context);
        mViewController=viewController;
    }

    @Override
    public void onDestroy() {
        //取消两个API的订阅
        super.onDestroy();
        BikeApiClient.getInstance().unSubscribe(mContext);
    }
}
