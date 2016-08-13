package com.twtstudio.wepeiyanglite.ui.bike;

import com.twtstudio.wepeiyanglite.common.IViewController;
import com.twtstudio.wepeiyanglite.model.BikeStation;

/**
 * Created by jcy on 2016/8/11.
 */

public interface BikeViewController extends IViewController {
    void setStationDetail(BikeStation stationDetail);
}
