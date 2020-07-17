package com.ubt.project_01_android.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ubt.project_01_android.Requests.DriverWorkSchedule;

import java.util.UUID;

public class DriverRequest {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("firstName")
    @Expose
    private String firstName;

    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("age")
    @Expose
    private int age;

    @SerializedName("uuid")
    @Expose
    private UUID uuid;

    @SerializedName("driverWorkSchedule")
    @Expose
    private DriverWorkSchedule driverWorkSchedule;

    @NonNull
    @Override
    public String toString() {
        return "\nID: " + id +
                "\nFirst name: " + firstName +
                "\nLast name: " + lastName +
                "\nAge : " + age +
                "\nUUID : " + uuid;
    }

    @SerializedName("dateOfBirth")
    @Expose
    private int dateOfBirth;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public DriverWorkSchedule getDriverWorkSchedule() {
        return driverWorkSchedule;
    }

    public void setDriverWorkSchedule(DriverWorkSchedule driverWorkSchedule) {
        this.driverWorkSchedule = driverWorkSchedule;
    }

    public int getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(int dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
