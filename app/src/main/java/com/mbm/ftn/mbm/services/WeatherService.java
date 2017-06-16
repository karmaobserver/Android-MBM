package com.mbm.ftn.mbm.services;

import com.mbm.ftn.mbm.models.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Milos on 5/24/2017.
 */

public interface WeatherService {
    @GET("/data/2.5/weather")
    Call<WeatherData> getWeatherFromApi (
            @Query("lat") String latitude,
            @Query("lon") String longitude,
            @Query("APPID") String appid);
}
