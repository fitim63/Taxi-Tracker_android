package com.ubt.project_01_android.utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.ubt.project_01_android.MainActivity;

public class LocationWorker extends Worker {

    private static final String TAG = "MyPeriodicWork";

    public LocationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        UpdatedLocationService locationService = new UpdatedLocationService();
        locationService.requestForPermission(MainActivity.getContext());
        Log.e(TAG, "Background task is executing..");
        return Result.success();
    }
}
