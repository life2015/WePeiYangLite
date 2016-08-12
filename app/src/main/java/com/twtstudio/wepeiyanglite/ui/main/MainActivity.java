package com.twtstudio.wepeiyanglite.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.BaseDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialize.util.UIUtils;
import com.twtstudio.wepeiyanglite.R;
import com.twtstudio.wepeiyanglite.common.ui.PActivity;
import com.twtstudio.wepeiyanglite.ui.bike.BikeFragment;
import com.twtstudio.wepeiyanglite.ui.bike.bikeAuth.BikeAuthActivity;
import com.twtstudio.wepeiyanglite.ui.gallery.GalleryFragment;
import com.twtstudio.wepeiyanglite.ui.home.HomeFragment;
import com.twtstudio.wepeiyanglite.ui.studyRoom.StudyRoomFragment;

import butterknife.BindView;

public class MainActivity extends PActivity<MainPresenter> implements MainViewController {

    @BindView(R.id.main_toolbar)
    Toolbar mMainToolbar;
    @BindView(R.id.frame_container)
    FrameLayout mFrameContainer;

    private AccountHeader mAccountHeader = null;
    private Drawer mDrawer = null;
    private CrossfadeDrawerLayout mCrossfadeDrawerLayout = null;

    private GalleryFragment mGalleryFragment;
    private StudyRoomFragment mStudyRoomFragment;
    private HomeFragment mHomeFragment;
    private BikeFragment mBikeFragment;

    @Override
    protected MainPresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void actionStart(Context context) {

    }

    @Override
    protected int getStatusbarColor() {
        return android.R.color.holo_blue_dark;
    }

    @Override
    protected void initView() {
        mGalleryFragment = new GalleryFragment();
        //mStudyRoomFragment=new StudyRoomFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, mGalleryFragment)
                .commit();
    }

    @Override
    protected Toolbar getToolbar() {
        return mMainToolbar;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDrawer(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState = mDrawer.saveInstanceState(outState);
        outState = mAccountHeader.saveInstanceState(outState);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer != null && mDrawer.isDrawerOpen()) {
            mDrawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    private void initDrawer(Bundle savedInstanceState) {
        final IProfile profile = new ProfileDrawerItem().withName("JCY")
                .withEmail("qq976885345@hotmail.com")
                .withIcon("https://avatars3.githubusercontent.com/u/1476232?v=3&s=460");
        mAccountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(profile)
                .withSavedInstance(savedInstanceState)
                .build();
        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mMainToolbar)
                .withHasStableIds(true)
                .withDrawerLayout(R.layout.crossfade_drawer)
                .withDrawerWidthDp(72)
                .withGenerateMiniDrawer(true)
                .withAccountHeader(mAccountHeader)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_gallery).withIcon(GoogleMaterial.Icon.gmd_sun).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_study_room).withIcon(FontAwesome.Icon.faw_home).withIdentifier(2),
                        //new PrimaryDrawerItem().withName(R.string.drawer_item_action_bar_drawer).withIcon(FontAwesome.Icon.faw_home).withBadge("22").withBadgeStyle(new BadgeStyle(Color.RED, Color.RED)).withIdentifier(2).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_multi_drawer).withIcon(FontAwesome.Icon.faw_gamepad).withIdentifier(3),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_non_translucent_status_drawer).withIcon(FontAwesome.Icon.faw_eye).withIdentifier(4),
                        new PrimaryDrawerItem().withDescription("A more complex sample").withName(R.string.drawer_item_advanced_drawer).withIcon(GoogleMaterial.Icon.gmd_adb).withIdentifier(5),
                        new SectionDrawerItem().withName(R.string.drawer_item_section_header),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_github),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(GoogleMaterial.Icon.gmd_format_color_fill).withTag("Bullhorn")
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        //点击事件
//                        if (drawerItem instanceof Nameable) {
//                            Toast.makeText(MainActivity.this, ((Nameable) drawerItem).getName().getText(MainActivity.this), Toast.LENGTH_SHORT).show();
//                        }
                        if (drawerItem.getIdentifier() == 3) {
                            Intent intent = new Intent(MainActivity.this, BikeAuthActivity.class);
                            startActivity(intent);
                            return false;
                        }
                        if (drawerItem != null) {
                            replaceFragment((int) drawerItem.getIdentifier());
                            BaseDrawerItem item = (BaseDrawerItem) drawerItem;
                            //setTitle(item.getName().getText());
                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();

        mCrossfadeDrawerLayout = (CrossfadeDrawerLayout) mDrawer.getDrawerLayout();

        mCrossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(this));

        final MiniDrawer miniDrawer = mDrawer.getMiniDrawer();

        View view = miniDrawer.build(this);

        view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(this,
                com.mikepenz.materialdrawer.R.attr.material_drawer_background,
                com.mikepenz.materialdrawer.R.color.material_drawer_background));

        mCrossfadeDrawerLayout.getSmallView().addView(view,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        miniDrawer.withCrossFader(new ICrossfader() {
            @Override
            public void crossfade() {
                boolean isFaded = isCrossfaded();
                mCrossfadeDrawerLayout.crossfade(400);
                if (isFaded) {
                    mDrawer.getDrawerLayout().closeDrawer(GravityCompat.START);
                }
            }

            @Override
            public boolean isCrossfaded() {
                return mCrossfadeDrawerLayout.isCrossfaded();
            }
        });
    }

    private void replaceFragment(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        switch (position) {
            case 1:
                if (mGalleryFragment == null) {
                    mGalleryFragment = new GalleryFragment();
                }
                fragment = mGalleryFragment;
                break;
            case 2:
                if (mStudyRoomFragment == null) {
                    mStudyRoomFragment = new StudyRoomFragment();
                }
                fragment = mStudyRoomFragment;
                break;
            case 4:
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                }
                fragment = mHomeFragment;
                break;
            case 5:
                if (mBikeFragment == null){
                    mBikeFragment = new BikeFragment();
                }
                fragment = mBikeFragment;
                break;

        }
        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.frame_container, fragment)
                .commit();
    }

    private void setMainTitle(String s) {
        getSupportActionBar().setTitle(s);
    }
}
