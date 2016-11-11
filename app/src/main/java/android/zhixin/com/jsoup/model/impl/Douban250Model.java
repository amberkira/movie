package android.zhixin.com.jsoup.model.impl;

import android.zhixin.com.jsoup.data.Douban250Bean;
import android.zhixin.com.jsoup.model.IDouban250Model;
import android.zhixin.com.jsoup.network.HttpUtil;
import android.zhixin.com.jsoup.tools.GlobalParams;

import rx.Observable;

/**
 * Created by zhangwenxing on 2016/11/9.
 */

public class Douban250Model implements IDouban250Model {
    @Override
    public Observable<Douban250Bean> getDoubanMovie250(int start) {
        return HttpUtil.getApiService(GlobalParams.DOUBAN).getDouban250Data(20, start);
    }
}
