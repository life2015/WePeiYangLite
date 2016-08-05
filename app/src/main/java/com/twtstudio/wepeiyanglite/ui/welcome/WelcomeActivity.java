package com.twtstudio.wepeiyanglite.ui.welcome;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.twtstudio.wepeiyanglite.R;
import com.twtstudio.wepeiyanglite.common.ui.PActivity;
import com.twtstudio.wepeiyanglite.router.RouterSchema;
import com.twtstudio.wepeiyanglite.support.ApplicationUtils;
import com.twtstudio.wepeiyanglite.support.PrefUtils;
import com.twtstudio.wepeiyanglite.support.UserAgent;
import com.twtstudio.wepeiyanglite.ui.auth.AuthActivity;
import com.twtstudio.wepeiyanglite.ui.main.CrossfadeDrawerLayoutActvitiy;
import com.twtstudio.wepeiyanglite.ui.main.MainActivity;

import butterknife.BindView;
import cn.campusapp.router.Router;

/**
 * Created by jcy on 2016/8/1.
 */

public class WelcomeActivity extends PActivity<WelcomePresenter> implements WelcomeViewController {

    @BindView(R.id.tv_version_name)
    TextView mTvVersionName;
    private static boolean hasCachedLogo = false;


    @Override
    protected WelcomePresenter getPresenter() {
        return new WelcomePresenter(this, this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void actionStart(Context context) {

    }

    @Override
    protected int getStatusbarColor() {
        return android.R.color.white;
    }

    @Override
    protected void initView() {
        mTvVersionName.setText(UserAgent.getAppVersion());
        if (!hasCachedLogo) {
            mPresenter.cacheLogo();
        }
        // TODO: 2016/8/2 测试代码需要删除
        PrefUtils.setPrefVersion(ApplicationUtils.getVersionName());
    }

    @Override
    protected void afterInitView() {
        super.afterInitView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!PrefUtils.getPreFversion().equals(ApplicationUtils.getVersionName())) {
                    // TODO: 2016/8/1 jump to guide activity
                    Router.open(RouterSchema.AUTH_PREFIX+"/"+"twt");
                    //Router.open(RouterSchema.urlBuilder(RouterSchema.AUTH_PREFIX, "twt"));
                } else if (PrefUtils.isLogin()) {
                    // TODO: 2016/8/1 jump to mainactivity
                    Router.open(RouterSchema.urlBuilder(RouterSchema.MAIN_PREFIX));
                } else {
                    // TODO: 2016/8/1 params to go....
                    Router.open(RouterSchema.AUTH_PREFIX+"/"+"twt");
                    //Router.open(RouterSchema.urlBuilder(RouterSchema.AUTH_PREFIX, "twt"));
                }
                finish();
            }
        }, 1000);
    }

    @Override
    protected Toolbar getToolbar() {
        return null;
    }

    @Override
    public void setCacheLogoStatue(boolean statue) {
        hasCachedLogo = statue;
    }


}
