package android.zhixin.com.jsoup.presenter;

import android.zhixin.com.jsoup.base.presenter.BasePresenterImpl;
import android.zhixin.com.jsoup.model.MainModel;
import android.zhixin.com.jsoup.model.impl.MainModelImpl;
import android.zhixin.com.jsoup.ui.view.MainView;

import rx.Subscriber;

/**
 * model请求数据返回给P
 * Created by zhangwenxing on 2016/10/28.
 */

public class MainPresenter extends BasePresenterImpl<MainView> {
    private MainModel model;

    public MainPresenter() {
        model = new MainModelImpl();
    }

    public void getFQPhotoDataList(String photoUrl) {
        mSubscription = model.requestPhoto(photoUrl).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                view.onSuccess();
            }
        });
    }


}
