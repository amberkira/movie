package com.zhixin.com.jsoup.ui.fragment;

import android.view.View;
import com.zhixin.com.jsoup.base.fragment.BaseMvpFrgament;
import com.zhixin.com.jsoup.presenter.MainPresenter;
import com.zhixin.com.jsoup.ui.view.MainView;

/**
 * Created by zhangwenxing on 2016/10/28.
 */

public class MainFragment extends BaseMvpFrgament<MainView, MainPresenter> {
    @Override
    public int initFragmantLayout() {
        return 0;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter();
    }
}