package com.pindiboy.weddingvideos.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.pindiboy.weddingvideos.R;
import com.pindiboy.weddingvideos.presenter.MainPresenter;
import com.pindiboy.weddingvideos.presenter.contract.MainContract;
import com.pindiboy.weddingvideos.ui.BaseActivity;
import com.pindiboy.weddingvideos.ui.ViewPagerAdapter;
import com.pindiboy.weddingvideos.ui.fragment.WeddingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    private final String[] CHANNELS = new String[]{"Latest", "All Videos", "My"};

    @BindView(R.id.tab_main)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager_main)
    ViewPager mViewPager;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private ViewPagerAdapter mAdapter;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void init() {
        mToolbar.setTitle("APP NAME");
        setSupportActionBar(mToolbar);

        fragments.add(new WeddingFragment());
        fragments.add(new WeddingFragment());
        fragments.add(new WeddingFragment());

        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);

        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText(CHANNELS[0]);
        mTabLayout.getTabAt(1).setText(CHANNELS[1]);
        mTabLayout.getTabAt(2).setText(CHANNELS[2]);
    }
}
