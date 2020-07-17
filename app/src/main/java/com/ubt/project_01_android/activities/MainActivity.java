package com.ubt.project_01_android.activities;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.e.vehicle_tracker_android.R;
import com.ubt.project_01_android.Requests.ApiEndpoints;
import com.ubt.project_01_android.Requests.VehicleTrackerApi;
import com.ubt.project_01_android.models.DriverRequest;
import com.ubt.project_01_android.models.VehicleRequest;
import com.ubt.project_01_android.utils.LocationService;
import com.ubt.project_01_android.utils.PermissionRequestService;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{

    private PermissionRequestService permissionRequest = new PermissionRequestService();
    private boolean permissionGranted = false;
    SharedPreferences sharedPreferences;
    public  final String CONTEXT = "MAIN";
    List<String> vehicles = new ArrayList<>();
    NiceSpinner niceSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionGranted = permissionRequest.checkIfReadyToGo(this);
        sharedPreferences = getApplicationContext().getSharedPreferences(CONTEXT, 0);
        Intent serviceIntent = new Intent(this, LocationService.class);
        serviceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        serviceIntent.putExtra("inputExtra", "Location Request");
        startService(serviceIntent);
        List<String> dataset;
        getVehicles();

        if (!vehicles.isEmpty()) {
            dataset = new LinkedList<>(vehicles);
        } else {
            Log.d("Veturat", vehicles.toString());
            dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        }
         niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);

        niceSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                // This example uses String, but your type can be any
                String item = (String) parent.getItemAtPosition(position);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        Menu menu1 = (Menu) findViewById(R.id.my_menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        final SharedPreferences.Editor editor;
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(CONTEXT, 0);
        editor = sharedPreferences.edit();
        if (id == R.id.my_menu) {
                editor.putBoolean("logout", true);
                editor.putBoolean("login", false);
                editor.remove("token");
                editor.apply();
            Intent loginActivityIntent = new Intent( MainActivity.this,LoginActivity.class);
            startActivity(loginActivityIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void startService(View view) {
        Intent serviceIntent = new Intent(this, LocationService.class);
        serviceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        serviceIntent.putExtra("inputExtra", "Location Request");
        startService(serviceIntent);
        getVehicles();
    }
    public void stopService(View view) {
        Intent intentService = new Intent(this, LocationService.class);
        intentService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        stopService(intentService);
    }

    public void getDriverDetails() {
        sharedPreferences = getApplicationContext().getSharedPreferences(CONTEXT, 0);
        String username = sharedPreferences.getString("username", "");
        final SharedPreferences.Editor editor;
        String token = sharedPreferences.getString("token", "");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiEndpoints.DRIVERS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        VehicleTrackerApi vehicleTrackerApi = retrofit.create(VehicleTrackerApi.class);
        Call<DriverRequest> driverRequest = vehicleTrackerApi.getDriverByUsername("Bearer " + token, username);

        driverRequest.enqueue(new Callback<DriverRequest>() {
            @Override
            public void onResponse(Call<DriverRequest> call, @NotNull Response<DriverRequest> response) {
                if (response.isSuccessful()) {
                    Log.d(CONTEXT, "ALL CARS: "+response.toString());
                }
            }

            @Override
            public void onFailure(Call<DriverRequest> call, Throwable t) {
                Log.d("Driver details", Objects.requireNonNull(t.getMessage()));
            }
        });

    }

    public List<String> getVehicles(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiEndpoints.VEHICLES)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sharedPreferences = getApplicationContext().getSharedPreferences(CONTEXT, 0);
        String token = sharedPreferences.getString("token", "");
        VehicleTrackerApi vehicleTrackerApi = retrofit.create(VehicleTrackerApi.class);
        Call<List<VehicleRequest>> vehicleRequestCall = vehicleTrackerApi.getAllVehicle("Bearer " + token);
        Log.e(CONTEXT, "ALL CARS: "+ApiEndpoints.VEHICLES);

        final List<String> cars = new ArrayList<>();

        vehicleRequestCall.enqueue(new Callback<List<VehicleRequest>>() {
            @Override
            public void onResponse(Call<List<VehicleRequest>> call, Response<List<VehicleRequest>> response) {
                assert response.body() != null;
                for (VehicleRequest v : response.body()) {
                    Log.d("Car: " + v.getId(), v.toString());
                    vehicles.add(v.getName() + " " + v.getModel());
                }
                Log.d("CARS: ", response.body().toString());
                niceSpinner.attachDataSource(vehicles);
            }

            @Override
            public void onFailure(Call<List<VehicleRequest>> call, Throwable t) {

            }
        });
        return vehicles;

    }

}