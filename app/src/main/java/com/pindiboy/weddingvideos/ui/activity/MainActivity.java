package com.pindiboy.weddingvideos.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.pindi.shadiraat.R;
import com.pindiboy.weddingvideos.common.Constant;
import com.pindiboy.weddingvideos.model.bean.ChannelConfig;
import com.pindiboy.weddingvideos.model.bean.IpInfo;
import com.pindiboy.weddingvideos.presenter.MainPresenter;
import com.pindiboy.weddingvideos.presenter.contract.MainContract;
import com.pindiboy.weddingvideos.ui.BaseActivity;
import com.pindiboy.weddingvideos.ui.ViewPagerAdapter;
import com.pindiboy.weddingvideos.ui.fragment.ChannelFragment;
import com.pindiboy.weddingvideos.ui.fragment.FavoriteFragment;
import com.pindiboy.weddingvideos.util.SPUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    @BindView(R.id.tab_main)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager_main)
    ViewPager mViewPager;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.search_view)
    MaterialSearchView mSearchView;

    private ViewPagerAdapter mAdapter;
    private List<Fragment> fragments = new ArrayList<>();
    List<ChannelConfig> channels;

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
        channels = new Gson().fromJson(getIntent().getStringExtra(Constant.INTENT_EXTRA_CHANNELS), new TypeToken<List<ChannelConfig>>() {
        }.getType());
        Collections.sort(channels, (c1, c2) -> c1.getOrder() - c2.getOrder());

        mToolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(mToolbar);

        // get country code
        onIpInfoLoaded(null); // set default value
        mPresenter.getIpInfo();

        ChannelFragment fragment;
        Bundle bundle;
        for (int i = 0; i < channels.size(); i++) {
            fragment = new ChannelFragment();
            bundle = new Bundle();
            bundle.putString(Constant.BUNDLE_CHANNEL_ID, channels.get(i).getChannelId());
            fragment.setArguments(bundle);
            fragments.add(fragment);

            mTabLayout.addTab(mTabLayout.newTab());
        }

        // favorite
        fragments.add(new FavoriteFragment());

        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        // must set title after set viewpager
        for (int i = 0; i < channels.size(); i++) {
            mTabLayout.getTabAt(i).setText(channels.get(i).getTitle());
        }
        mTabLayout.getTabAt(channels.size()).setText("Favorite");

        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (TextUtils.isEmpty(query)) {
                    return true;
                }
                mSearchView.closeSearch();
                Intent intent = new Intent(mContext, SearchActivity.class);
                intent.putExtra(Constant.INTENT_EXTRA_SEARCH_Q, query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);
        return true;
    }

    @Override
    public void onBackPressedSupport() {
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            super.onBackPressedSupport();
        }
    }

    @Override
    public void onIpInfoLoaded(IpInfo ipInfo) {
        String countryCode;
        if (null != ipInfo && !TextUtils.isEmpty(ipInfo.getCountryCode())) {
            countryCode = ipInfo.getCountryCode();
        } else {
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            countryCode = tm.getNetworkCountryIso();
        }

        SPUtil.setCountryCode(countryCode.toUpperCase());
    }
}
