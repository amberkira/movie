package com.zhixin.com.jsoup.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.zhixin.com.jsoup.base.presenter.BasePresenterImpl;

/**
 * Created by zhangwenxing on 2016/11/14.
 */

public abstract class BaseMvpActivity<V, P extends BasePresenterImpl> extends BaseActivity {
    protected P presenter;

    protected abstract P initPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=initPresenter();
        presenter.attach((V)this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
