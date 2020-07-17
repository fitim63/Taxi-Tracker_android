package com.ubt.project_01_android.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ubt.project_01_android.Requests.VehicleDrivingDetails;

import java.util.List;

public class VehicleRequest {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("model")
    @Expose
    private String model;

    @SerializedName("fuelType")
    @Expose
    private String fuelType;

    @SerializedName("drivingDetails")
    @Expose
    private List<VehicleDrivingDetails> drivingDetails = null;

    @SerializedName("driver")
    @Expose
    private List<DriverRequest> driver = null;

    @SerializedName("currentStatus")
    @Expose
    private boolean currentStatus;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public List<VehicleDrivingDetails> getDrivingDetails() {
        return drivingDetails;
    }

    public void setDrivingDetails(List<VehicleDrivingDetails> drivingDetails) {
        this.drivingDetails = drivingDetails;
    }

    public List<DriverRequest> getDriver() {
        return driver;
    }

    public void setDriver(List<DriverRequest> driver) {
        this.driver = driver;
    }

    public boolean isCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(boolean currentStatus) {
        this.currentStatus = currentStatus;
    }

    @NonNull
    @Override
    public String toString() {
        return "Id: " + id + " Name: " + name + " Model: " + model + " FuelType: " + fuelType;
    }
}
