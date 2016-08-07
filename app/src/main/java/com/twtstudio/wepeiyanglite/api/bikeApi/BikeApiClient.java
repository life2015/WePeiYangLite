package com.twtstudio.wepeiyanglite.api.bikeApi;

import com.twtstudio.wepeiyanglite.api.WePeiYangClient;
import com.twtstudio.wepeiyanglite.model.BikeAuth;
import com.twtstudio.wepeiyanglite.support.PrefUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jcy on 2016/8/6.
 */

public class BikeApiClient {

    protected Retrofit mRetrofit;

    protected Map<Object, CompositeSubscription> mSubscriptionsMap = new HashMap<>();

    private BikeApi mService;

    private BikeApiClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(sRequsetInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://bike.twtstudio.com/api.php")
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = mRetrofit.create(BikeApi.class);
    }

    private static class SingletonHolder {
        private static final BikeApiClient INSTANCE = new BikeApiClient();
    }

    public static BikeApiClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected static Interceptor sRequsetInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originRequset = chain.request();
            //添加header
            Request.Builder builder = originRequset.newBuilder()
                    .addHeader("Accept", "application/json");
            //auth的时候是不需要token的
            List<String> pathList = originRequset.url().pathSegments();
            if (!pathList.contains("auth")) {
                //遍历增加requestbody
                if (originRequset.body() instanceof FormBody) {
                    FormBody.Builder newFormBody = new FormBody.Builder();
                    FormBody oldFormBody = (FormBody) originRequset.body();
                    for (int i = 0; i < oldFormBody.size(); i++) {
                        newFormBody.addEncoded(oldFormBody.encodedName(i), oldFormBody.encodedValue(i));
                    }
                    newFormBody.addEncoded("auth_token", PrefUtils.getBikeToken());
                    builder.method(originRequset.method(), newFormBody.build());
                }
            }

            Request request = builder.build();
            return chain.proceed(request);
        }
    };

    public void unSubscribe(Object tag) {
        if (mSubscriptionsMap.containsKey(tag)) {
            CompositeSubscription subscriptions = mSubscriptionsMap.get(tag);
            subscriptions.unsubscribe();
            mSubscriptionsMap.remove(tag);
        }
    }

    protected void addSubscription(Object tag, Subscription subscription) {
        if (tag == null) {
            return;
        }
        CompositeSubscription subscriptions;
        if (mSubscriptionsMap.containsKey(tag)) {
            subscriptions = mSubscriptionsMap.get(tag);
        } else {
            subscriptions = new CompositeSubscription();
        }
        subscriptions.add(subscription);
        mSubscriptionsMap.put(tag, subscriptions);
    }

    public void getBikeToken(Object tag, Subscriber subscriber, String wpy_token) {
        Subscription subscription = mService.getBikeToken(wpy_token)
                .map(new BikeResponseTransformer<BikeAuth>())
                .compose(BikeApiUtils.<BikeAuth>applySchedulers())
                .subscribe(subscriber);
        addSubscription(tag, subscription);
    }
}
