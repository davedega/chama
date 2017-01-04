package com.dega.chama.presenter.io;

import android.support.v4.util.LruCache;

import com.dega.chama.core.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by davedega on 03/01/17.
 */

public class ChamaNetService {

    private static String baseUrl = Constants.BASE_URL;
    private OkHttpClient okHttpClient;
    private LruCache<Class<?>, Observable<?>> apiObservables;
    private Retrofit instanceRetrofit;
}
