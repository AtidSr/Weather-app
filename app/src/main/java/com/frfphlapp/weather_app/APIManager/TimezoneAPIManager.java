package com.frfphlapp.weather_app.APIManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TimezoneAPIManager {

    private static Retrofit retrofit = null;

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);
    private static OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();

    public static <S> S createService(Class<S> serviceClass) {

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://maps.googleapis.com/maps/api/timezone/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

        }

        return retrofit.create(serviceClass);
    }
}