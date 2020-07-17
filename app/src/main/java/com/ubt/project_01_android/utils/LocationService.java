package com.ubt.project_01_android.utils;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.e.vehicle_tracker_android.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.ubt.project_01_android.activities.MainActivity;
import com.ubt.project_01_android.Requests.ApiEndpoints;
import com.ubt.project_01_android.Requests.PositionEntries;
import com.ubt.project_01_android.Requests.VehicleTrackerApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ubt.project_01_android.App.CHANNEL_ID;


public class LocationService extends Service {

    private static final String TAG = "UpdatedLocationService";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Location Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .build();

        requestLocationUpdates(this);
        startForeground(1, notification);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void requestLocationUpdates(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PermissionChecker.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PermissionChecker.PERMISSION_GRANTED) {

            FusedLocationProviderClient fusedLocationProviderClient = new FusedLocationProviderClient(context);
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            locationRequest.setInterval(10000);

            fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    String lat = String.valueOf(locationResult.getLastLocation().getLatitude());
                    String lng = String.valueOf(locationResult.getLastLocation().getLongitude());
                    updateLocation(2, lat, lng, String.valueOf(Math.random()), 2);
                    Log.e(TAG, "Updating data Latitude: " + lat + " Longitude: " + lng);
                }
            }, getMainLooper());
        }
    }

    public void updateLocation(int id, String lat, String lng, String position, int vehicle_id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiEndpoints.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        VehicleTrackerApi vehicleTrackerApi = retrofit.create(VehicleTrackerApi.class);
        PositionEntries positionEntries = new PositionEntries();
        positionEntries.setLat(lat);
        positionEntries.setLng(lng);
        positionEntries.setPosition(position);
        positionEntries.setVehicle_id(vehicle_id);
       /* Call<PositionEntries> putCall = vehicleTrackerApi.putPosition(
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
                    Log.e(TAG, "Service sending data successfully: ");
                    Log.e("Data sent: ", content);
                }
            }

            @Override
            public void onFailure(Call<PositionEntries> call, Throwable t) {
            }
        });*/
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
