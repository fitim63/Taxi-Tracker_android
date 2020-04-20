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

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{
    EditText etFullname;
    EditText etUsername;
    EditText etEmail;
    EditText etPassword;
    EditText etConfirmPassword;
    Button btnSignUp;

    AwesomeValidation awesomeValidation;

    TextView mTextViewRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        awesomeValidation =  new AwesomeValidation(ValidationStyle.BASIC);

        etFullname = (EditText)findViewById(R.id.etName2);
        etFullname = (EditText)findViewById(R.id.etUsername2);
        etEmail = (EditText)findViewById(R.id.etEmail2);
        etPassword = (EditText)findViewById(R.id.etPassword2);
        etConfirmPassword = (EditText)findViewById(R.id.etConfirmPassword2);
        btnSignUp = (Button)findViewById(R.id.btnSignUp);

          awesomeValidation.addValidation(SignupActivity.this,R.id.etName2,"[a-zA-Z\\s]+",R.string.firstnameerr);
          awesomeValidation.addValidation(SignupActivity.this,R.id.etUsername2,"(\\+383[0-9]*)",R.string.lastnameerr); //Nese ka shkronja "(\\+383[a-zA-Z]*)"
          awesomeValidation.addValidation(SignupActivity.this,R.id.etEmail2,android.util.Patterns.EMAIL_ADDRESS,R.string.emailerr);
          awesomeValidation.addValidation(SignupActivity.this,R.id.etPassword2, "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d]).{8,}",R.string.passworderr);
          awesomeValidation.addValidation(SignupActivity.this,R.id.etConfirmPassword2,R.id.etPassword2,R.string.cpassworderr);

        btnSignUp.setOnClickListener(this);

        mTextViewRegister = (TextView)findViewById(R.id.txtLogin2);
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent( SignupActivity.this,LoginActivity.class);
                startActivity(loginIntent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(awesomeValidation.validate()){
            Toast.makeText(SignupActivity.this, "Data Received Successfully", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(SignupActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }

        /*switch (v.getId()){
            case R.id.btnSignUp:
                String name = etFullname.getText().toString();
                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                User user = new User(name, username, email, password);

                break;
        }*/
    }
}
