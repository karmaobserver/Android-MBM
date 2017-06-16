package com.mbm.ftn.mbm.adapters;

import android.util.Log;

import com.mbm.ftn.mbm.models.WeatherData;
import com.mbm.ftn.mbm.services.WeatherService;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Milos on 5/21/2017.
 */

public class WeatherRestAdapter {
    protected final String TAG = getClass().getSimpleName();
    protected Retrofit mRestAdapter;
    protected WeatherService mApi;
    static final String WEATHER_URL="http://api.openweathermap.org/";
    static final String OPEN_WEATHER_API = "49391d964b73dccedbefb6c076fd6180";

    public WeatherRestAdapter() {
        mRestAdapter = new Retrofit.Builder()
                .baseUrl(WEATHER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi = mRestAdapter.create(WeatherService.class); // create the interface
        Log.d(TAG, "WeatherRestAdapter -- created");
    }

    public void testWeatherApi(String latitude,String longitude, Callback<WeatherData> callback){
        Log.d(TAG, "testWeatherApi: for latitude :" + latitude + "and longitude" + longitude);
        mApi.getWeatherFromApi(latitude,longitude, OPEN_WEATHER_API).enqueue(callback);
    }
}
