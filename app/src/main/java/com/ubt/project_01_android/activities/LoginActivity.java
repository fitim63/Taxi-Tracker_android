package com.ubt.project_01_android.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.e.vehicle_tracker_android.R;
import com.ubt.project_01_android.model.UserLocalStore;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etUsername;
    EditText etPassword;
    Button btnLogin;
    AwesomeValidation awesomeValidation;
    TextView mTextViewRegister;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        awesomeValidation =  new AwesomeValidation(ValidationStyle.BASIC);
        etUsername = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        awesomeValidation.addValidation(LoginActivity.this,R.id.etUsername,"(?=.*[\\d]).{8}",R.string.lastnameerr);
        awesomeValidation.addValidation(LoginActivity.this,R.id.etPassword, "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d]).{8,}",R.string.passworderr);

        btnLogin.setOnClickListener(this);

        mTextViewRegister = (TextView)findViewById(R.id.txtSignUp);
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent( LoginActivity.this,SignupActivity.class);
                startActivity(signUpIntent);
            }
        });

        userLocalStore = new UserLocalStore(this);
    }

    @Override
    public void onClick(View v) {
        if(awesomeValidation.validate()){
            Toast.makeText(LoginActivity.this, "Data Received Successfully", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}
