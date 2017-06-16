package com.mbm.ftn.mbm.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mbm.ftn.mbm.R;
import com.mbm.ftn.mbm.adapters.WeatherRestAdapter;
import com.mbm.ftn.mbm.adapters.WeatherRestAdapterCity;
import com.mbm.ftn.mbm.models.WeatherData;
import com.mbm.ftn.mbm.utils.ConnectionDetector;
import com.mbm.ftn.mbm.utils.GPSTracker;
import com.mbm.ftn.mbm.utils.Utils;

import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mbm.ftn.mbm.utils.AppPreference.saveLastUpdateTimeMillis;

/**
 * Created by Milos on 5/21/2017.
 */

public class WeatherActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener{

    protected final String TAG = getClass().getSimpleName();
    private RetainedAppData mRetainedAppData;
    private RetainedAppDataCoordinates mRetainedAppDataCoordinates;
    private ProgressBar mProgressBar;
    private TextView mTemperatureView;
    private TextView mIconWeatherView;
    private TextView mWindSpeedView;
    private TextView mIconWindView;
    private TextView mHumidityView;
    private String mSpeedScale;
    private TextView mPressureView;
    private TextView mCloudinessView;
    private TextView mLastUpdateView;
    private TextView mSunriseView;
    private TextView mSunsetView;
    private AppBarLayout mAppBarLayout;
    private TextView mIconHumidityView;
    private TextView mIconPressureView;
    private TextView mIconCloudinessView;
    private TextView mIconSunriseView;
    private TextView mIconSunsetView;



    private Menu mToolbarMenu;

    private SharedPreferences mPrefWeather;
    private SwipeRefreshLayout mSwipeRefresh;
    private Boolean isNetworkAvailable;
    private ConnectionDetector connectionDetector;

    private LocationManager locationManager;

    private double longitude1;
    private double latitude1;
    private static String currentCity;

    private String search;

    long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 500; // in Meters
    long MINIMUM_TIME_BETWEEN_UPDATES = 60000; // in Milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_id);
        if (savedInstanceState != null) {
            if (getLastCustomNonConfigurationInstance() != null) {
                mRetainedAppData = (RetainedAppData) getLastCustomNonConfigurationInstance();
                currentCity = savedInstanceState.getString("SavedCity");
                Log.d(TAG,"onCreate(): Reusing retained data set");
            }
        } else {
            mRetainedAppData = new RetainedAppData();
            Log.d(TAG, "onCreate(): Creating new  data set");
        }

        // Setup activity reference
        // mActivityRef = new WeakReference<>(this);
        mRetainedAppData.setAppContext(this);


        setView();

        //
        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.main_swipe_refresh);
        int top_to_padding = 150;
        mSwipeRefresh.setProgressViewOffset(false, 0, top_to_padding);
        mSwipeRefresh.setColorSchemeResources(R.color.swipe_red, R.color.swipe_green,
                R.color.swipe_blue);
        mSwipeRefresh.setOnRefreshListener(swipeRefreshListener);

        //search = getIntent().getStringExtra("search");
        //search = getIntent().getStringExtra("search");
        connectionDetector = new ConnectionDetector(WeatherActivity.this);
        isNetworkAvailable = connectionDetector.isNetworkAvailableAndConnected();
        if (isNetworkAvailable) {
            weatherAsync();
        }
        else{
            Toast.makeText(WeatherActivity.this,
                    R.string.connection_not_found,
                    Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        mSwipeRefresh.setEnabled(verticalOffset == 0);
    }


    @Override
    public void onResume() {
        super.onResume();
        mAppBarLayout = (AppBarLayout) findViewById(R.id.main_app_bar);
        mAppBarLayout.addOnOffsetChangedListener(this);
        search = getIntent().getStringExtra("search");
        mRetainedAppData = new RetainedAppData();
        setView();
        if(search != null) {
            mRetainedAppData.runRetrofitTestAsync(search);
        }
        mRetainedAppData.runRetrofitTestAsync(currentCity);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mAppBarLayout.removeOnOffsetChangedListener(this);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mActivityRef = null;
        mRetainedAppData.setAppContext(null);
        Log.d(TAG,"onDestroy()");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putString("SavedCity",currentCity);
        super.onSaveInstanceState(savedInstanceState);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mToolbarMenu = menu;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_weather_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_refresh:
                refreshData();
                return true;
            case R.id.main_menu_detect_location:
                requestLocation2();
                return true;
            case R.id.main_menu_search_city:

                Intent intent = new Intent(WeatherActivity.this, SearchCitiesActivity.class);
                //startActivityForResult(intent,1);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void refreshData(){
        connectionDetector = new ConnectionDetector(WeatherActivity.this);
        isNetworkAvailable = connectionDetector.isNetworkAvailableAndConnected();
        if (isNetworkAvailable) {
            if(currentCity != null){
                mRetainedAppData.runRetrofitTestAsync(currentCity);
                mSwipeRefresh.setRefreshing(false);


            }
            else{
                String cityName = "Beograd";
                mRetainedAppData.runRetrofitTestAsync(cityName);
                mSwipeRefresh.setRefreshing(false);

            }


        } else {
            Toast.makeText(WeatherActivity.this,
                    R.string.connection_not_found,
                    Toast.LENGTH_SHORT).show();
            mSwipeRefresh.setRefreshing(false);
        }
    }

    public void weatherAsync(){

        if(search == null ){
            if(currentCity != null){
                mRetainedAppData.runRetrofitTestAsync(currentCity);
            }
            else{
                String cityName = "Beograd";
                mRetainedAppData.runRetrofitTestAsync(cityName);
            }
        }
        else{

            mRetainedAppData.runRetrofitTestAsync(search);
        }

        //String latitude = "20.367980";

    }
    public void setView(){

        mRetainedAppData.setAppContext(this);
        if (mRetainedAppData.mData != null) {
            update();

        }
        // Setup the progress bar
        if (mRetainedAppData.isFetchInProgress()) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
        }


    }

    public void update(){
        updateUXWithWeather(mRetainedAppData.mData);
    }
    private void initializeTextView() {
        /**
         * Create typefaces from Asset
         */
        Typeface weatherFontIcon = Typeface.createFromAsset(this.getAssets(),
                "fonts/weathericons-regular-webfont.ttf");
        Typeface robotoThin = Typeface.createFromAsset(this.getAssets(),
                "fonts/Roboto-Thin.ttf");
        Typeface robotoLight = Typeface.createFromAsset(this.getAssets(),
                "fonts/Roboto-Light.ttf");

        mIconWeatherView = (TextView) findViewById(R.id.main_weather_icon);
        mTemperatureView = (TextView) findViewById(R.id.main_temperature);
        mPressureView = (TextView) findViewById(R.id.main_pressure);
        mHumidityView = (TextView) findViewById(R.id.main_humidity);
        mWindSpeedView = (TextView) findViewById(R.id.main_wind_speed);
        mCloudinessView = (TextView) findViewById(R.id.main_cloudiness);
        mLastUpdateView = (TextView) findViewById(R.id.main_last_update);
        mSunriseView = (TextView) findViewById(R.id.main_sunrise);
        mSunsetView = (TextView) findViewById(R.id.main_sunset);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.main_app_bar);


        mIconWeatherView.setTypeface(weatherFontIcon);
        mTemperatureView.setTypeface(robotoThin);
        mWindSpeedView.setTypeface(robotoLight);
        mHumidityView.setTypeface(robotoLight);
        mPressureView.setTypeface(robotoLight);
        mCloudinessView.setTypeface(robotoLight);
        mSunriseView.setTypeface(robotoLight);
        mSunsetView.setTypeface(robotoLight);


        /**
         * Initialize and configure weather icons
         */
        mIconWindView = (TextView) findViewById(R.id.main_wind_icon);
        mIconWindView.setTypeface(weatherFontIcon);
        mIconWindView.setText(R.string.icon_wind);
        mIconHumidityView = (TextView) findViewById(R.id.main_humidity_icon);
        mIconHumidityView.setTypeface(weatherFontIcon);
        mIconHumidityView.setText(getString(R.string.icon_humidity));
        mIconPressureView = (TextView) findViewById(R.id.main_pressure_icon);
        mIconPressureView.setTypeface(weatherFontIcon);
        mIconPressureView.setText(R.string.icon_barometer);
        mIconCloudinessView = (TextView) findViewById(R.id.main_cloudiness_icon);
        mIconCloudinessView.setTypeface(weatherFontIcon);
        mIconCloudinessView.setText(R.string.icon_cloudiness);
        mIconSunriseView = (TextView) findViewById(R.id.main_sunrise_icon);
        mIconSunriseView.setTypeface(weatherFontIcon);
        mIconSunriseView.setText(R.string.icon_sunrise);
        mIconSunsetView = (TextView) findViewById(R.id.main_sunset_icon);
        mIconSunsetView.setTypeface(weatherFontIcon);
        mIconSunsetView.setText(R.string.icon_sunset);

    }

    void updateUXWithWeather (final WeatherData data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Setup UX handlers
                // Get the UX handlers every time. This is to avoid a condition
                // when runOnUiThread may not have updated UX handles when screen is rotated.
                // 'mActivityRef.get()'

                initializeTextView();

                // Refresh UX data
                if (mRetainedAppData.isFetchInProgress()) {
                    mProgressBar.setVisibility(View.VISIBLE);
                } else {
                    mProgressBar.setVisibility(View.INVISIBLE);
                }


                // Print data to Android display

                /*Resources res = getResources();
                Double temp = data.getMain().getTemp() - 273.15;
                Integer i = temp.intValue();
                String textToPrint = String.valueOf(i);
                mTemperatureView.setText(textToPrint);
                */
                String temperature = String.format(Locale.getDefault(), "%.0f",
                        data.getMain().getTemp() - 273.15);

                mTemperatureView.setText(getString(R.string.temperature_with_degree, temperature));


                mIconWeatherView.setText(
                        Utils.getStrIcon(WeatherActivity.this,data.getWeather().get(0).getIcon() ));

                mSpeedScale = "m/s";
                String wind = String.format(Locale.getDefault(), "%.1f", data.getWind().getSpeed());
                mWindSpeedView.setText(getString(R.string.wind_label, wind, mSpeedScale));

                mHumidityView.setText(getString(R.string.humidity_label,
                        String.valueOf(data.getMain().getHumidity()),
                        getString(R.string.percent_sign)));
                String pressure = String.format(Locale.getDefault(), "%.1f",
                        data.getMain().getPressure());
                mPressureView.setText(getString(R.string.pressure_label, pressure,
                        "hPa"));
                mWindSpeedView.setText(getString(R.string.wind_label, wind, mSpeedScale));
                mCloudinessView.setText(getString(R.string.cloudiness_label,
                        String.valueOf(data.getClouds().getAll()),
                        getString(R.string.percent_sign)));
                String lastUpdate = Utils.setLastUpdateTime(WeatherActivity.this,
                        saveLastUpdateTimeMillis(WeatherActivity.this));
                mLastUpdateView.setText(getString(R.string.last_update_label, lastUpdate));
                String sunrise = Utils.unixTimeToFormatTime(WeatherActivity.this, data.getSys().getSunrise());
                mSunriseView.setText(getString(R.string.sunrise_label, sunrise));
                String sunset = Utils.unixTimeToFormatTime(WeatherActivity.this, data.getSys().getSunset());
                mSunsetView.setText(getString(R.string.sunset_label, sunset));

                currentCity = data.getName();

                setTitle(data.getName());



            }
        });
        }



    private SwipeRefreshLayout.OnRefreshListener swipeRefreshListener =
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refreshData();

                }
            };


    private static class RetainedAppData {
        private WeakReference<WeatherActivity> mActivityRef;
        protected final String TAG = "RTD";
        private WeatherData mData; // Weather data received
        private AtomicBoolean mInProgress = new AtomicBoolean(false); // Is a download in progress
        private WeatherRestAdapterCity mWeatherRestAdapter; // REST Adapter
        private Callback<WeatherData> mWeatherCallback = new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                // response.isSuccessful() is true if the response code is 2xx
                if (response.isSuccessful()) {
                    WeatherData data = response.body();
                    Log.d(TAG, "Async success: Weather: Name:" + data.getName() + ", cod:" + data.getCod()
                            + ",Coord: (" + data.getCoord().getLat() + "," + data.getCoord().getLon()
                    );
                    mData = data;
                    if (mActivityRef.get() != null) {
                        mActivityRef.get().updateUXWithWeather(mData);
                        mActivityRef.get().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mActivityRef.get().mProgressBar = (ProgressBar) mActivityRef.get().
                                        findViewById(R.id.progress_bar_id);
                                mActivityRef.get().mProgressBar.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                    mInProgress.set(false);
                } else {
                    int statusCode = response.code();

                    // handle request errors yourself
                    ResponseBody errorBody = response.errorBody();
                    Log.d(TAG,"Error code:" + statusCode + ", Error:" + errorBody);
                }
            }



            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                mInProgress.set(false);
                if (mActivityRef.get() != null) {
                    mActivityRef.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mActivityRef.get().mProgressBar = (ProgressBar) mActivityRef.get().
                                    findViewById(R.id.progress_bar_id);
                            mActivityRef.get().mProgressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }
        };




        // Method to test Async. call
        public void runRetrofitTestAsync (final String cityName) {
            if ( (mActivityRef.get() != null) && (mInProgress.get())) {
                Toast.makeText(mActivityRef.get(),"Vremenska prognoza se ažurira.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            // Get the Adapter
            if (mWeatherRestAdapter == null)
                mWeatherRestAdapter = new WeatherRestAdapterCity();

            if (mActivityRef.get() != null) {
                mActivityRef.get().mProgressBar.setVisibility(View.VISIBLE);
            }

            // Test delay
            try {
                mInProgress.set(true);
                mWeatherRestAdapter.testWeatherApiCity(cityName, mWeatherCallback); // Call Async API
            } catch (Exception e) {
                Log.d(TAG, "Thread sleep error" + e);
            }
        }

        void setAppContext (WeatherActivity ref) {
            mActivityRef = new WeakReference<>(ref);
        }

        boolean isFetchInProgress() {
            return mInProgress.get();
        }


    }

    private static class RetainedAppDataCoordinates {
        private WeakReference<WeatherActivity> mActivityRef;
        protected final String TAG = "RTD";
        private WeatherData mData; // Weather data received
        private AtomicBoolean mInProgress = new AtomicBoolean(false); // Is a download in progress
        private WeatherRestAdapter mWeatherRestAdapter; // REST Adapter
        private Callback<WeatherData> mWeatherCallback = new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                // response.isSuccessful() is true if the response code is 2xx
                if (response.isSuccessful()) {
                    WeatherData data = response.body();
                    Log.d(TAG, "Async success: Weather: Name:" + data.getName() + ", cod:" + data.getCod()
                            + ",Coord: (" + data.getCoord().getLat() + "," + data.getCoord().getLon()
                    );
                    mData = data;
                    if (mActivityRef.get() != null) {
                        mActivityRef.get().updateUXWithWeather(mData);
                        mActivityRef.get().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mActivityRef.get().mProgressBar = (ProgressBar) mActivityRef.get().
                                        findViewById(R.id.progress_bar_id);
                                mActivityRef.get().mProgressBar.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                    mInProgress.set(false);
                } else {
                    int statusCode = response.code();

                    // handle request errors yourself
                    ResponseBody errorBody = response.errorBody();
                    Log.d(TAG,"Error code:" + statusCode + ", Error:" + errorBody);
                }
            }



            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                mInProgress.set(false);
                if (mActivityRef.get() != null) {
                    mActivityRef.get().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mActivityRef.get().mProgressBar = (ProgressBar) mActivityRef.get().
                                    findViewById(R.id.progress_bar_id);
                            mActivityRef.get().mProgressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }
        };




        // Method to test Async. call
        public void runRetrofitTestAsync (final String latitude, final String longitude) {
            if ( (mActivityRef.get() != null) && (mInProgress.get())) {
                Toast.makeText(mActivityRef.get(),"Weather fetch in progress.",
                        Toast.LENGTH_LONG).show();
                return;
            }
            // Get the Adapter
            if (mWeatherRestAdapter == null)
                mWeatherRestAdapter = new WeatherRestAdapter();

            if (mActivityRef.get() != null) {
                mActivityRef.get().mProgressBar.setVisibility(View.VISIBLE);
            }

            // Test delay
            try {
                mInProgress.set(true);
                mWeatherRestAdapter.testWeatherApi(latitude,longitude, mWeatherCallback); // Call Async API
            } catch (Exception e) {
                Log.d(TAG, "Thread sleep error" + e);
            }
        }

        void setAppContext (WeatherActivity ref) {
            mActivityRef = new WeakReference<>(ref);
        }

        boolean isFetchInProgress() {
            return mInProgress.get();
        }


    }





    private void requestLocation2(){
        boolean permissionGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if(permissionGranted) {
            GPSTracker gps;
            gps = new GPSTracker(WeatherActivity.this);
            if(gps.canGetLocation()){

                double latitude1 = gps.getLatitude();
                double longitude1 = gps.getLongitude();

                mRetainedAppDataCoordinates = new RetainedAppDataCoordinates();
                mRetainedAppDataCoordinates.setAppContext(this);
                if (mRetainedAppDataCoordinates.mData != null) {
                    update();

                }
                // Setup the progress bar
                if (mRetainedAppDataCoordinates.isFetchInProgress()) {
                    mProgressBar.setVisibility(View.VISIBLE);
                } else {
                    mProgressBar.setVisibility(View.INVISIBLE);
                }

                connectionDetector = new ConnectionDetector(WeatherActivity.this);
                isNetworkAvailable = connectionDetector.isNetworkAvailableAndConnected();
                if (isNetworkAvailable) {
                    mRetainedAppDataCoordinates.runRetrofitTestAsync( String.valueOf(latitude1), String.valueOf(longitude1));
                }
                else{
                    Toast.makeText(WeatherActivity.this,
                            R.string.connection_not_found,
                            Toast.LENGTH_SHORT).show();

                }




            }else{
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gps.showSettingsAlert();
            }



        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }

    }






    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case 200: {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // {Some Code}
                    try {

                        GPSTracker gps;
                        gps = new GPSTracker(WeatherActivity.this);


                            double latitude1 = gps.getLatitude();
                            double longitude1 = gps.getLongitude();

                        mRetainedAppDataCoordinates = new RetainedAppDataCoordinates();
                        mRetainedAppDataCoordinates.setAppContext(this);
                        if (mRetainedAppDataCoordinates.mData != null) {
                            update();

                        }
                        // Setup the progress bar
                        if (mRetainedAppDataCoordinates.isFetchInProgress()) {
                            mProgressBar.setVisibility(View.VISIBLE);
                        } else {
                            mProgressBar.setVisibility(View.INVISIBLE);
                        }

                        if (isNetworkAvailable) {
                            mRetainedAppDataCoordinates.runRetrofitTestAsync( String.valueOf(latitude1), String.valueOf(longitude1));
                        }
                        else{
                            Toast.makeText(WeatherActivity.this,
                                    R.string.connection_not_found,
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                    catch
                            (SecurityException e) {
                        Toast.makeText(WeatherActivity.this,
                                "Doslo je do greske",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}


