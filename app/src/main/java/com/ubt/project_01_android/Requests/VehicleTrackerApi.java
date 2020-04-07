package com.ubt.project_01_android.Requests;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface VehicleTrackerApi {

    @GET("api/positionEntries/")
    Call<List<PositionEntries>> getPositionEntries();

    @FormUrlEncoded
    @PUT("api/positionEntries/{id}")
    Call<PositionEntries> putPosition(
            @Path("id") int id,
            @Field("lat") String lat,
            @Field("lng") String lng,
            @Field("position") String position);
}
