package com.ubt.project_01_android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.e.vehicle_tracker_android.R;
import com.ubt.project_01_android.Requests.ApiEndpoints;
import com.ubt.project_01_android.models.LoginRequest;
import com.ubt.project_01_android.models.LoginResponse;
import com.ubt.project_01_android.Requests.VehicleTrackerApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity  {
    EditText etUsername;
    EditText etPassword;
    Button btnLogin;

    public static final String SP_NAME = "MAIN";
    public static final String USERNAME_VALIDATION = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$";

    AwesomeValidation awesomeValidation;

    TextView mTextViewRegister;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        awesomeValidation =  new AwesomeValidation(ValidationStyle.BASIC);

        etUsername = (EditText)findViewById(R.id.usernameInput);
        etPassword = (EditText)findViewById(R.id.passwordInput);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        sharedPreferences = getApplicationContext().getSharedPreferences(SP_NAME, 0);
        boolean login = sharedPreferences.getBoolean("login", false);
        boolean logout = sharedPreferences.getBoolean("logout", false);
        if (login && !logout) {
            Intent mainActivityIntent = new Intent( LoginActivity.this,MainActivity.class);
            startActivity(mainActivityIntent);
        }
        awesomeValidation.addValidation(LoginActivity.this,R.id.usernameInput,USERNAME_VALIDATION,R.string.phoneNumber);

        mTextViewRegister = (TextView)findViewById(R.id.txtSignUp);
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent( LoginActivity.this,SignupActivity.class);
                startActivity(signUpIntent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()) {
                    login(etUsername.getText().toString(), etPassword.getText().toString());
                }
            }
        });
    }

    public void login(String phoneNumber, String password) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiEndpoints.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        VehicleTrackerApi vehicleTrackerApi = retrofit.create(VehicleTrackerApi.class);
        Call<LoginResponse> login = vehicleTrackerApi.login(new LoginRequest(phoneNumber, password));
        sharedPreferences = getApplicationContext().getSharedPreferences(SP_NAME, 0);
        final SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();
        editor.putString("username", etUsername.getText().toString());

        login.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.code() == 401) {
                    Toast.makeText(LoginActivity.this, "Phone Number and Password are not correct!", Toast.LENGTH_LONG).show();
                    Log.e("Login success: ", "401");
                }
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String token = response.body().getToken();
                    editor.putString("token", token);
                    editor.putBoolean("login", true);
                    editor.putBoolean("logout", false);
                    editor.apply();
                    Log.e("Login success: ", token);
                    Intent mainActivityIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainActivityIntent);
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("On Failure ", t.getMessage());

            }
        });

    }

}

