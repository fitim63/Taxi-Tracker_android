package com.ubt.project_01_android.utils;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.util.Log;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;

public class PermissionRequestService {

    private boolean permissionResult = false;
    private final String TAG = "Permission Request Service:";

    public boolean checkIfReadyToGo(Context context) {
        return checkForLocationPermission(context) && checkIfGpsProviderAndNetworkProviderIsEnabled(context);
    }

    private boolean checkForLocationPermission(final Context context) {
        final String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};
        String rationale = "Location permissions are required to get User Locations";
        Log.e(TAG, "Request for permission method called.");

        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Location permissions")
                .setSettingsDialogTitle("Warning");

        Permissions.check(context, permissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                permissionResult = true;
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                permissionResult = false;
                super.onDenied(context, deniedPermissions);
            }
        });
        return permissionResult;
    }

    private boolean checkIfGpsProviderAndNetworkProviderIsEnabled(final Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = false;
        boolean networkEnabled = false;

        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            Log.e(TAG, "Gps failed with error: " + ex.getMessage());
        }

        try {
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            Log.e(TAG, "Network failed with error: " + ex.getMessage());
        }
        if (!gpsEnabled && !networkEnabled) {
            new AlertDialog.Builder(context)
                    .setMessage("Please enable your location in settings.")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Open location settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    }).show();
        }

        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            Log.e(TAG, "Gps failed with error: " + ex.getMessage());
        }

        try {
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            Log.e(TAG, "Network failed with error: " + ex.getMessage());
        }

        return gpsEnabled && networkEnabled;
    }

}
