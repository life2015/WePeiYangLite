package com.twtstudio.wepeiyanglite.ui.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.twtstudio.wepeiyanglite.R;
import com.twtstudio.wepeiyanglite.common.ui.PFragment;
import com.twtstudio.wepeiyanglite.model.BikeUserInfo;
import com.twtstudio.wepeiyanglite.support.BikeStationUtils;
import com.twtstudio.wepeiyanglite.support.TimeStampUtils;

import butterknife.BindView;

/**
 * Created by jcy on 2016/8/9.
 */

public class HomeFragment extends PFragment<HomePresenter> implements HomeViewController {
    @BindView(R.id.srl_home)
    SwipeRefreshLayout mSrlHome;
    //自行车卡片
    @BindView(R.id.leave_station)
    TextView mLeaveStation;
    @BindView(R.id.leave_time)
    TextView mLeaveTime;
    @BindView(R.id.arr_station)
    TextView mArrStation;
    @BindView(R.id.arr_time)
    TextView mArrTime;
    @BindView(R.id.bike_fee)
    TextView mBikeFeeText;


    @Override
    protected HomePresenter getPresenter() {
        return new HomePresenter(getContext(),this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void preInitView() {
        super.preInitView();
        mSrlHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getBikeUserInfo();
            }
        });
        mSrlHome.post(new Runnable() {
            @Override
            public void run() {
                mPresenter.getBikeUserInfo();
            }
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    public void setBikeUserInfo(BikeUserInfo bikeUserInfo) {
        mSrlHome.setRefreshing(false);
        String dep = BikeStationUtils.getInstance().queryId(bikeUserInfo.record.dep).name;
        mLeaveStation.setText(dep+"-"+bikeUserInfo.record.dep_dev+"号桩");
        mLeaveTime.setText(TimeStampUtils.getDateString(bikeUserInfo.record.dep_time));
        String arr = BikeStationUtils.getInstance().queryId(bikeUserInfo.record.arr).name;
        mArrStation.setText(arr+"-"+bikeUserInfo.record.arr_dev+"号桩");
        mArrTime.setText(TimeStampUtils.getDateString(bikeUserInfo.record.arr_time));
        mBikeFeeText.setText("本次消费:"+bikeUserInfo.record.fee+"账户余额:"+bikeUserInfo.balance);
    }
}
