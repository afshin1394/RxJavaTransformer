package com.samiei.rxtransformer;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientOwghat {
    private static ApiClientOwghat instance;

    private static final int TIME_OUT = 60;
    private static ApiServiceOwghat apiServiceOwghatInstance = null;

    public static ApiClientOwghat getInstance(){
        if (instance==null)
            instance=new ApiClientOwghat();
        return instance;
    }

    private static <S> S createService(Class<S> serviceClass, String baseURL) {


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();




        Retrofit.Builder builder = new Retrofit
                .Builder()
                .client(client)
                .baseUrl(baseURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());


        Retrofit retrofit = builder.build();

        return retrofit.create(serviceClass);
    }







    public ApiServiceOwghat getClientServiceOwghat() {
        String baseUrl = "https://api.keybit.ir/";
        if (apiServiceOwghatInstance == null)
            apiServiceOwghatInstance = createService(ApiServiceOwghat.class, baseUrl);

        return apiServiceOwghatInstance;
    }
}
