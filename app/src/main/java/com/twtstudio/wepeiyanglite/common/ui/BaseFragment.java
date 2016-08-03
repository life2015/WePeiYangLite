package com.twtstudio.wepeiyanglite.common.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twtstudio.wepeiyanglite.common.IViewController;
import com.twtstudio.wepeiyanglite.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by huangyong on 16/5/18.
 */
public abstract class BaseFragment extends Fragment implements IViewController{

    private Unbinder unbinder;
    protected abstract int getLayout();

    protected void preInitView() {

    }

    protected void afterInitView(){

    }

    protected abstract void initView();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        preInitView();
        initView();
        afterInitView();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void toastMessage(String message) {
        ToastUtils.showMessage(getActivity(),message);
    }

    @Override
    public void showLoadingDialog() {

    }
    @Override
    public void showLoadingDialog(String message) {

    }

    @Override
    public void dismissLoadingDialog() {

    }
}
