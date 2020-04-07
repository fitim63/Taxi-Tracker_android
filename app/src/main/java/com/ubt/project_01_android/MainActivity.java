package com.ubt.project_01_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import com.e.vehicle_tracker_android.R;
import com.ubt.project_01_android.utils.LocationWorker;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 1;
    private static final String UNIQUE_ID = "123";
    public static Context mContext;

    public  static Context getContext() {
        return mContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        PeriodicWorkRequest.Builder testBuilder = new PeriodicWorkRequest.Builder(
                LocationWorker.class,
                15,
                TimeUnit.MINUTES);


        PeriodicWorkRequest testWork = testBuilder.addTag(UNIQUE_ID).build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork("123",
                ExistingPeriodicWorkPolicy.REPLACE, testWork);

        ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

    }
}