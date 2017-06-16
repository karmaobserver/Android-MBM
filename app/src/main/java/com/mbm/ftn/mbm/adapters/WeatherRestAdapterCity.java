package com.mbm.ftn.mbm.adapters;

import android.util.Log;

import com.mbm.ftn.mbm.models.WeatherData;
import com.mbm.ftn.mbm.services.WeatherServiceCity;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Milos on 5/23/2017.
 */

public class WeatherRestAdapterCity {
    protected final String TAG = getClass().getSimpleName();
    protected Retrofit mRestAdapter;
    protected WeatherServiceCity mApi;
    static final String WEATHER_URL="http://api.openweathermap.org/";
    static final String OPEN_WEATHER_API = "426de2bbac53f36e54ccc702cc07a73c";

    public WeatherRestAdapterCity() {
        mRestAdapter = new Retrofit.Builder()
                .baseUrl(WEATHER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi = mRestAdapter.create(WeatherServiceCity.class); // create the interface
        Log.d(TAG, "WeatherRestAdapter -- created");
    }


    public void testWeatherApiCity(String cityName, Callback<WeatherData> callback){
        Log.d(TAG, "testWeatherApi: for city name :" + cityName );
        mApi.getWeatherFromApi(cityName, OPEN_WEATHER_API).enqueue(callback);
    }
}
