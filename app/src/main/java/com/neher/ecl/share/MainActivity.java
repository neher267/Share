package com.neher.ecl.share;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button loginButton, registrationButton;
    private EditText mobileView, passwordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        loginButton.setOnClickListener(this);
        registrationButton.setOnClickListener(this);

    }

    private void init(){
        mobileView = findViewById(R.id.edit_text_mobile);
        passwordView = findViewById(R.id.edit_text_password);
        loginButton = findViewById(R.id.button_login);
        registrationButton = findViewById(R.id.button_registration);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_registration){
            startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
        }else if(v.getId() == R.id.button_login){
            startActivity(new Intent(this, ShareActivity.class));
            //Toast.makeText(this, "Login Button is clicked.", Toast.LENGTH_LONG).show();
        }
    }
}
