package com.twtstudio.wepeiyanglite.ui.home;

import com.twtstudio.wepeiyanglite.R;
import com.twtstudio.wepeiyanglite.common.ui.PFragment;

/**
 * Created by jcy on 2016/8/9.
 */

public class HomeFragment extends PFragment<HomePresenter> implements HomeViewController {
    @Override
    protected HomePresenter getPresenter() {
        return new HomePresenter(getContext(),this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {

    }
}
