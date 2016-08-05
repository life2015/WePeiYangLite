package com.twtstudio.wepeiyanglite.ui.studyRoom;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.twtstudio.wepeiyanglite.R;
import com.twtstudio.wepeiyanglite.common.ui.PFragment;
import com.twtstudio.wepeiyanglite.model.SchoolBuildings;

import java.util.List;

import butterknife.BindView;

/**
 * Created by jcy on 2016/8/4.
 */

public class StudyRoomFragment extends PFragment<StudyRoomPresenter> implements StudyRoomViewController {
    @BindView(R.id.school_building_rcv)
    RecyclerView mRecyclerView;
    @BindView(R.id.school_week)
    TextView mWeekText;
    @BindView(R.id.study_room_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private StudyBuildingsAdapter mAdapter;


    @Override
    protected StudyRoomPresenter getPresenter() {
        return new StudyRoomPresenter(getContext(), this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_study_rooms;
    }

    @Override
    protected void initView() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getBuildings();
            }
        });
        mAdapter = new StudyBuildingsAdapter(getContext());
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.getBuildings();

    }


    @Override
    public void refreshItems(List<SchoolBuildings.BuildingsBean> beanList) {
        mAdapter.refreshItems(beanList);
    }

    @Override
    public void setWeekNumber(String s) {
        mWeekText.setText(s+" 周");
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
