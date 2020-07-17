package com.ubt.project_01_android.Requests;

public class VehicleDrivingDetails {

    private String firstName;
    private String lastName;
    private String latitude;
    private String longitude;
    private Double currentAverageSpeed;
    private int vehicleId;
    private boolean isVehicleActive;

    public VehicleDrivingDetails(String firstName, String lastName, String latitude, String longitude, Double currentAverageSpeed, int vehicleId, boolean isVehicleActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.currentAverageSpeed = currentAverageSpeed;
        this.vehicleId = vehicleId;
        this.isVehicleActive = isVehicleActive;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Double getCurrentAverageSpeed() {
        return currentAverageSpeed;
    }

    public void setCurrentAverageSpeed(Double currentAverageSpeed) {
        this.currentAverageSpeed = currentAverageSpeed;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public boolean isVehicleActive() {
        return isVehicleActive;
    }

    public void setVehicleActive(boolean vehicleActive) {
        isVehicleActive = vehicleActive;
    }
}
