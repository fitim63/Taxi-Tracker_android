package com.ubt.project_01_android.activities;/*
package com.e.vehicle_tracker_android.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.e.vehicle_tracker_android.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int REQUEST_LOCATION = 1;

    Button getLocationBtn;
    TextView showLocationTxt;

    LocationManager locationManager;
    String latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        showLocationTxt = findViewById(R.id.show_location);
        getLocationBtn = findViewById(R.id.getLocation);

    }

    private void getLocation() {
        // Check permissions again
        if (ActivityCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MapsActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location locationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (locationGps != null) {
                double lat = locationGps.getLatitude();
                double lng = locationGps.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(lng);
                showLocationTxt.setText("Your location: \n" + "Latitude: " + latitude + " Longitude: " + longitude);
            } else if (locationNetwork != null) {
                double lat = locationNetwork.getLatitude();
                double lng = locationNetwork.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(lng);
                showLocationTxt.setText("Your location: \n" + "Latitude: " + latitude + " Longitude: " + longitude);
            } else if (locationPassive != null) {
                double lat = locationPassive.getLatitude();
                double lng = locationPassive.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(lng);
                showLocationTxt.setText("Your location: \n" + "Latitude: " + latitude + " Longitude: " + longitude);
            } else {
                Toast.makeText(this, "Can't get your location", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
*/
