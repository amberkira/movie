package android.zhixin.com.jsoup.network;

import android.zhixin.com.jsoup.BuildConfig;
import android.zhixin.com.jsoup.data.Bean;
import android.zhixin.com.jsoup.tools.GlobalParams;
import android.zhixin.com.jsoup.tools.ZhuoXinToast;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 网络工具封装类
 * Created by zhangwenxing on 16/9/7.
 */
public class RetrofitUtil {
    public static final int CONNECT_TIMEOUT = 15;
    public static final int WRITE_TIMEOUT = 15;
    public static final int READ_TIMEOUT = 15;


    static final Observable.Transformer mTransformer = new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return ((Observable) observable).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMap(new Func1() {
                @Override
                public Object call(Object bean) {
                    return getResponse((Bean<Object>) bean);
                }
            });
        }
    };

    //网络分割
    public static <T> Observable<T> getResponse(final Bean<T> response) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
//                if (response.getSuccess()) {
//                    if (!subscriber.isUnsubscribed()) {
//                        subscriber.onNext(response.getResult());
//                    }
//                } else {
//                    if (!subscriber.isUnsubscribed()) {
//                        subscriber.onError(new APIException(response.getError()));
//                    }
//                    return;
//                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    //Retrofit异常处理
    public static void resolveError( Throwable e) {
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            String str = null;
            if (httpException.code() == 401) {
                try {
                    str = httpException.response().errorBody().string();
                    Gson gson = new Gson();
                    Bean bean = gson.fromJson(str, Bean.class);
//                    mZhuoXinToast.show(BaseApplication.getInstance(),bean.getError().getMessage());
//                    if (bean.getError().getCode() == 10015) {
//                        mZhuoXinToast.show(BaseApplication.getInstance(),"登录过期，请重新登录");
//                        Activity activity = BaseApplication.getInstance().lastActivity();
//                        Intent intent = new Intent(activity, PhoneLoginActivity.class);
//                        activity.startActivity(intent);
//                        BaseApplication.getInstance().exit();
//                    }
                } catch (IOException e1) {
                    ZhuoXinToast.getInstance().show("网络连接错误，请稍后再试");
                }
            }
        } else if (e instanceof APIException) {
            APIException error = (APIException) e;
//            ZhuoXinToast.getInstance().show(error.getApiMessage());
        } else if (e instanceof SocketTimeoutException) {
            ZhuoXinToast.getInstance().show("网络连接超时");
        } else if (e instanceof ConnectException) {
            ZhuoXinToast.getInstance().show("网络连接超时");
        } else if (e instanceof UnknownHostException) {
            ZhuoXinToast.getInstance().show("网络异常，请检查网络是否开启！");
        }
    }

    //Rxjava操作符封装
    public static <T> Observable.Transformer<Bean<T>, T> composeResponse() {
        return (Observable.Transformer<Bean<T>, T>) mTransformer;
    }

    public static <S> S createService(Class<S> serviceClass, String baseUrl) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(createOkHttpBuilder().build())
                .build();
        return retrofit.create(serviceClass);
    }

    public static OkHttpClient.Builder createOkHttpBuilder() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS) //
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS) //
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS) //
                .addInterceptor(mTokenInterceptor);


        return okHttpClient;
    }

    static Interceptor mTokenInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request authorised = originalRequest.newBuilder()
                    .addHeader("platform", "android")
                    .addHeader("appVersion", BuildConfig.VERSION_NAME)
                    .addHeader("Content-Type", "application/json;charset=utf-8")
                    .build();
            return chain.proceed(authorised);
        }
    };


    /**
     * 固定服务器
     *
     * @return API
     */
    public static APIService getApiService() {
        return getApiService(GlobalParams.BASE_URL);
    }

    /**
     * 任意指定服务器
     *
     * @param baseUrl 服务器URL
     * @return API
     */
    public static APIService getApiService(String baseUrl) {
        return createService(APIService.class, baseUrl);
    }


}
