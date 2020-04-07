package com.ubt.project_01_android.utils;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.ubt.project_01_android.Requests.ApiEndpoints;
import com.ubt.project_01_android.Requests.PositionEntries;
import com.ubt.project_01_android.Requests.VehicleTrackerApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.os.Looper.getMainLooper;

public class UpdatedLocationService  {

    private SharedPreferences.Editor editor;

    public void updateLocation(int id, String lat, String lng, String position, int vehicle_id) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiEndpoints.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        VehicleTrackerApi vehicleTrackerApi = retrofit.create(VehicleTrackerApi.class);

        PositionEntries positionEntries = new PositionEntries();
        positionEntries.setLat(lat);
        positionEntries.setLng(lng);
        positionEntries.setPosition(position);
        positionEntries.setVehicle_id(vehicle_id);
        Call<PositionEntries> putCall = vehicleTrackerApi.putPosition(
                id,
                positionEntries.getLat(),
                positionEntries.getLng(),
                positionEntries.getPosition()
        );

        putCall.enqueue(new Callback<PositionEntries>() {
            @Override
            public void onResponse(Call<PositionEntries> call, Response<PositionEntries> response) {
                if (!response.isSuccessful()) {
                    Log.e("Error while sending put call: ", response.message());
                    return;
                }
                PositionEntries p = response.body();
                if (p != null) {
                    String content = "";
                    content = content + "ID: " + p.getId() + "\n";
                    content = content + "Lat: " + p.getLat() + "\n";
                    content = content + "Lng: " + p.getLng() + "\n";
                    content = content + "Position:  " + p.getPosition() + "\n\n";
                    Log.e("Data sent: ", content);
                }
            }

            @Override
            public void onFailure(Call<PositionEntries> call, Throwable t) {

            }
        });
    }

    public void requestLocationUpdates(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PermissionChecker.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PermissionChecker.PERMISSION_GRANTED) {
            FusedLocationProviderClient fusedLocationProviderClient = new FusedLocationProviderClient(context);
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setFastestInterval(1000);
            locationRequest.setInterval(1000);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            editor = preferences.edit();

            fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    String lat = String.valueOf(locationResult.getLastLocation().getLatitude());
                    String lng = String.valueOf(locationResult.getLastLocation().getLongitude());
                    updateLocation(76, lat, lng, String.valueOf(Math.random()), 5);
                    editor.putString("lat", lat);
                    editor.putString("lng", lng);
                    Log.e("Service Latitude: ", lat);
                    Log.e("Service Longitude: ", lng);
                    editor.apply();
                }
            }, getMainLooper());
        } else requestForPermission(context);
    }

    public void requestForPermission(final Context context) {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};
        String rationale = "Location permissions are required to get User Locations";

        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Location permissions")
                .setSettingsDialogTitle("Warning");
        Permissions.check(context, permissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                // do your task.
                requestLocationUpdates(context);
            }
            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
            }
        });
    }

}
