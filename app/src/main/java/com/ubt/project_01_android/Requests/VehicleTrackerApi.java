package com.ubt.project_01_android.Requests;

import com.ubt.project_01_android.models.DriverRequest;
import com.ubt.project_01_android.models.LoginRequest;
import com.ubt.project_01_android.models.LoginResponse;
import com.ubt.project_01_android.models.RegisterRequest;
import com.ubt.project_01_android.models.VehicleRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface VehicleTrackerApi {



    @GET("/vehicles/getAll")
    Call<List<VehicleRequest>> getAllVehicle(@Header("Authorization") String authorization);

    @GET("/drivers")
    Call<DriverRequest> getDriverByUsername(@Header("Authorization") String authorization, @Query("username") String username);

    @POST("/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("/drivers/createDriver")
    Call<RegisterRequest> register(@Body RegisterRequest registerRequest);
}
