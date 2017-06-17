package com.mbm.ftn.mbm.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mbm.ftn.mbm.R;
import com.mbm.ftn.mbm.dao.ProfileDao;
import com.mbm.ftn.mbm.dialogs.ChooseCityDialog;
import com.mbm.ftn.mbm.dialogs.SosDialog;
import com.mbm.ftn.mbm.models.Profile;
import com.mbm.ftn.mbm.utils.PermissionUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Makso on 6/17/2017.
 */

    public class SosActivity extends BaseActivity implements GoogleMap.OnMyLocationButtonClickListener,
            OnMapReadyCallback,
            ActivityCompat.OnRequestPermissionsResultCallback,
            LocationListener
    {

        private static final LatLng DARWIN = new LatLng(-12.4258647, 130.7932231);
        private static final LatLng MELBOURNE = new LatLng(-37.81319, 144.96298);
        private static final int INITIAL_STROKE_WIDTH_PX = 5;
        private GoogleMap mMap;
        private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
        private boolean mPermissionDenied = false;
        private LocationManager locationManager;
        private ArrayList<LatLng> points; //added
        Polyline line;

        private List<Profile> profileList = new ArrayList<>();
        ProfileDao profileDao = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setLogo(R.mipmap.ic_sos);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        points = new ArrayList<LatLng>();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean permissionGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if(permissionGranted) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    2000,5, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    2000, 10, this);
        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }

        //SOS button
        FloatingActionButton sosButton = (FloatingActionButton) findViewById(R.id.button_sos);
        sosButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("titleName", getResources().getString(R.string.dialog_title_sos));
                SosDialog sosDialog = new SosDialog();
                sosDialog.setArguments(bundle);
                sosDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
                android.support.v4.app.FragmentManager fragmentManager = (SosActivity.this).getSupportFragmentManager();
                sosDialog.show(fragmentManager, "showSosDialogTag");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_sos, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.profiles:
                Intent intent = new Intent(this, ProfilesActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_gps:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

    }

    /*@Override
    public void onMapReady(GoogleMap map) {
        GPSTracker gps = new GPSTracker(SosActivity.this);

        if(gps.canGetLocation()) {
            double lat = gps.getLatitude();
            double lon = gps.getLongitude();
            LatLng currentLocation = new LatLng(lat,lon);
            map.addMarker(new MarkerOptions().position(currentLocation).title("Marker"));
            map.moveCamera( CameraUpdateFactory.newLatLngZoom(currentLocation, 14.0f) );

        }
        else {
            map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        }
    }*/

    @Override
    public void onMapReady(GoogleMap map) {

        mMap = map;

        mMap.setOnMyLocationButtonClickListener(this);

        enableMyLocation();
        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setMapToolbarEnabled(true);

        /*map.addPolyline(new PolylineOptions()
                .add(DARWIN, MELBOURNE)
                .width(INITIAL_STROKE_WIDTH_PX)
                .color(Color.BLUE)
                .geodesic(true));*/

    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        //Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }


    @Override
    public void onLocationChanged(Location location) {
        /*String msg = "New Latitude: " + location.getLatitude()
                + "New Longitude: " + location.getLongitude();

        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();*/

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude); //you already have this

        points.add(latLng); //added
        redrawLine();

    }

    private void redrawLine() {
        mMap.clear();  //clears all Markers and Polylines

        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        for (int i = 0; i < points.size(); i++) {
            LatLng point = points.get(i);
            options.add(point);
        }
        //add Marker in current position
        line = mMap.addPolyline(options); //add Polyline
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {


    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getBaseContext(), "GPS je upaljen ",
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


}
