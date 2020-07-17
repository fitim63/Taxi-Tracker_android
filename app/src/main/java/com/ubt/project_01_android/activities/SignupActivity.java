package com.ubt.project_01_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.e.vehicle_tracker_android.R;
import com.ubt.project_01_android.Requests.ApiEndpoints;
import com.ubt.project_01_android.Requests.VehicleTrackerApi;
import com.ubt.project_01_android.models.RegisterRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SignupActivity extends AppCompatActivity  {
    EditText etFirstName;
    EditText etLastName;
    EditText etAge;
    EditText etPhoneNumber;
    EditText etPassword;
    EditText etConfirmPassword;
    Button btnSignUp;
    Button btnBackToLogin;
    AwesomeValidation awesomeValidation;
    final int[] responseCode = new int[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etAge = (EditText) findViewById(R.id.etAge);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etPassword = (EditText) findViewById(R.id.etPassword2);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword2);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnBackToLogin = (Button) findViewById(R.id.backButton);

        awesomeValidation.addValidation(SignupActivity.this, R.id.etFirstName, R.string.fullNameValidation, R.string.firstName);
        awesomeValidation.addValidation(SignupActivity.this, R.id.etLastName, R.string.fullNameValidation, R.string.lastName);
        awesomeValidation.addValidation(SignupActivity.this, R.id.etPhoneNumber, R.string.phoneNumberValidation, R.string.phoneNumber); //Nese ka shkronja "(\\+383[a-zA-Z]*)"
        awesomeValidation.addValidation(SignupActivity.this, R.id.etAge, R.string.ageValidation, R.string.age);
        awesomeValidation.addValidation(SignupActivity.this, R.id.etPassword2, R.string.passwordValidation, R.string.passworderr);
        awesomeValidation.addValidation(SignupActivity.this, R.id.etConfirmPassword2, R.id.etPassword2, R.string.cpassworderr);


        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(signUpIntent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()) {
                    register(etFirstName.getText().toString(),
                            etLastName.getText().toString(),
                            etAge.getText().toString(),
                            etPhoneNumber.getText().toString(),
                            etPassword.getText().toString());
                    Toast.makeText(SignupActivity.this, "Thank you for filling out your information!", Toast.LENGTH_LONG).show();
                    Intent signUpIntent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(signUpIntent);
                } else {
                    Toast.makeText(SignupActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    public void register(String firstName, String lastName, String age, String phoneNumber, String password) {
        if (firstName.equals("First name") || firstName.equals("") || lastName.equals("Last name") || phoneNumber.equals("Phone Number") ||
                phoneNumber.equals("") || age.equals("Age") || age.equals("") || password.equals("Password")
                || password.equals("")) {
            Toast.makeText(SignupActivity.this, "Please don't leave any empty field!", Toast.LENGTH_LONG).show();
        } else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiEndpoints.BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            VehicleTrackerApi vehicleTrackerApi = retrofit.create(VehicleTrackerApi.class);
            Call<RegisterRequest> registerCall = vehicleTrackerApi.register(new RegisterRequest(firstName, lastName, phoneNumber, password, age));
            registerCall.enqueue(new Callback<RegisterRequest>() {
                @Override
                public void onResponse(Call<RegisterRequest> call, Response<RegisterRequest> response) {
                    responseCode[0] = response.code();

                }
                @Override
                public void onFailure(Call<RegisterRequest> call, Throwable t) {

                }
            });


        }

    }
}
