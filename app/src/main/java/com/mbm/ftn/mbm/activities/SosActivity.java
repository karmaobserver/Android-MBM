package com.mbm.ftn.mbm.activities;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mbm.ftn.mbm.R;
import com.mbm.ftn.mbm.dialogs.SosDialog;
import com.mbm.ftn.mbm.utils.PermissionUtils;

import java.util.ArrayList;


/**
 * Created by Makso on 6/17/2017.
 */

    public class SosActivity extends BaseActivity implements GoogleMap.OnMyLocationButtonClickListener,
            OnMapReadyCallback,
            ActivityCompat.OnRequestPermissionsResultCallback,
            LocationListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener
    {


        private GoogleMap mMap;
        private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
        private boolean mPermissionDenied = false;
        private LocationManager locationManager;
        private ArrayList<LatLng> points; //added
        Marker marker;
        Polyline line;
        SharedPreferences sharedPreferences;
        int locationCount = 0;
        boolean myLocation = false;
        PendingIntent pendingIntent;

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

        // Opening the sharedPreferences object


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
        public void onResume() {
            super.onResume();


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
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
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

        // Getting LocationManager object from System Service LOCATION_SERVICE
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Opening the sharedPreferences object
        sharedPreferences = getSharedPreferences("location", 0);

        // Getting number of locations already stored
        locationCount = sharedPreferences.getInt("locationCount", 0);

        // Getting stored zoom level if exists else return 0
        String zoom = sharedPreferences.getString("zoom", "0");
        String zoom1 = sharedPreferences.getString("zoom1", "0");

        // If locations are already saved
        if(locationCount!=0){

            String lat = "";
            String lng = "";

            // Iterating through all the locations stored
            for(int i=0;i<locationCount;i++){

                // Getting the latitude of the i-th location
                lat = sharedPreferences.getString("lat"+i,"0");

                // Getting the longitude of the i-th location
                lng = sharedPreferences.getString("lng"+i,"0");

                // Drawing marker on the map
                drawMarker(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));

                // Drawing circle on the map
                drawCircle(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
            }

            // Moving CameraPosition to last clicked position
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng))));

            // Setting the zoom level in the map on last position  is clicked
            mMap.animateCamera(CameraUpdateFactory.zoomTo(Float.parseFloat(zoom)));
        }
        else{

            //todo maybe


        }


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
        //mMap.clear();  //clears all Markers and Polylines
        //polylineFinal.remove();

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


        @Override
        public void onMapLongClick(LatLng latLng) {

            Intent proximityIntent = new Intent("com.mbm.ftn.mbm.activity.proximity");
            proximityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, proximityIntent,0);

            // Removing the proximity alert
            locationManager.removeProximityAlert(pendingIntent);

            // Removing the marker and circle from the Google Map
            mMap.clear();

            // Opening the editor object to delete data from sharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();

            // Clearing the editor
            editor.clear();

            // Committing the changes
            editor.commit();


        }

        @Override
        public void onMapClick(LatLng point) {
            // Incrementing location count
            locationCount++;

            // Drawing marker on the map
            drawMarker(point);

            // Drawing circle on the map
            drawCircle(point);

            // This intent will call the activity ProximityActivity
            Intent proximityIntent = new Intent("com.mbm.ftn.mbm.activity.proximity");
            proximityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Passing latitude to the PendingActivity
            proximityIntent.putExtra("lat",point.latitude);

            // Passing longitude to the PendingActivity
            proximityIntent.putExtra("lng", point.longitude);

            // Creating a pending intent which will be invoked by LocationManager when the specified region is
            // entered or exited
            pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, proximityIntent,0);

            // Setting proximity alert
            // The pending intent will be invoked when the device enters or exits the region 20 meters
            // away from the marked point
            // The -1 indicates that, the monitor will not be expired
            try {
                locationManager.addProximityAlert(point.latitude, point.longitude, 20, -1, pendingIntent);
            }
            catch(SecurityException e) {
                Toast.makeText(SosActivity.this,
                        "Doslo je do greske",
                        Toast.LENGTH_SHORT).show();
            }

            /** Opening the editor object to write data to sharedPreferences */
            SharedPreferences.Editor editor = sharedPreferences.edit();

            // Storing the latitude for the i-th location
            editor.putString("lat"+ Integer.toString((locationCount-1)), Double.toString(point.latitude));

            // Storing the longitude for the i-th location
            editor.putString("lng"+ Integer.toString((locationCount-1)), Double.toString(point.longitude));

            // Storing the count of locations or marker count
            editor.putInt("locationCount", locationCount);

            /** Storing the zoom level to the shared preferences */
            editor.putString("zoom", Float.toString(mMap.getCameraPosition().zoom));

            /** Saving the values stored in the shared preferences */
            editor.commit();

            Toast.makeText(getBaseContext(), "Destinacija dodata na mapi", Toast.LENGTH_SHORT).show();

        }

        private void drawMarker(LatLng latLng) {
            MarkerOptions markerOptions = new MarkerOptions();

            // Setting latitude and longitude for the marker
            markerOptions.position(latLng);

            // Adding marker on the Google Map
            mMap.addMarker(markerOptions);
        }

        private void drawCircle(LatLng point){

            // Instantiating CircleOptions to draw a circle around the marker
            CircleOptions circleOptions = new CircleOptions();

            // Specifying the center of the circle
            circleOptions.center(point);

            // Radius of the circle
            circleOptions.radius(20);

            // Border color of the circle
            circleOptions.strokeColor(Color.BLACK);

            // Fill color of the circle
            circleOptions.fillColor(0x30ff0000);

            // Border width of the circle
            circleOptions.strokeWidth(2);

            // Adding the circle to the GoogleMap
            mMap.addCircle(circleOptions);

        }
    }
