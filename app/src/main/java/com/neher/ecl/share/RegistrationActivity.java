package com.neher.ecl.share;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = "RegistrationActivity";
    private Button registrationButton;
    private EditText nameView, mobileView, passwordView, passwordConView;
    private RadioGroup genderView, shareWithView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptToRegister();
            }
        });

    }

    private void init(){
        registrationButton = findViewById(R.id.button_register);
        nameView = findViewById(R.id.edit_text_name);
        mobileView = findViewById(R.id.edit_text_mobile);
        passwordView = findViewById(R.id.edit_text_password);
        passwordConView = findViewById(R.id.edit_text_password_con);
        genderView = findViewById(R.id.gender);
        shareWithView = findViewById(R.id.share_with);
    }

    private void attemptToRegister(){
        final String name = nameView.getText().toString();
        final String mobile = mobileView.getText().toString();
        final String password = passwordView.getText().toString();
        final String passwordCon = passwordConView.getText().toString();
        final String gender = getGender();
        final String shareWith = getShareWith();

        StringRequest RegisterRequest = new StringRequest(Request.Method.POST, Env.remote.register_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);

                        SharedPreferences sharedPref = RegistrationActivity.this.getSharedPreferences(Env.sp.sp_name, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();

                        editor.putString(Env.sp.user_name, name);
                        editor.putString(Env.sp.user_mobile, mobile);
                        editor.putString(Env.sp.user_gender, gender);
                        editor.putString(Env.sp.user_password, password);
                        editor.putString(Env.sp.access_token, "yes");
                        editor.commit();
                        startActivity(new Intent(RegistrationActivity.this, ShareActivity.class));
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d(TAG, String.valueOf(error));
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();

                params.put("username", mobile);
                params.put("name", name);
                params.put("gender", gender);
                params.put("password", password);
                params.put("share_with", shareWith);

                return params;
            }
        };

        MyRequestQueue.getInstance(RegistrationActivity.this).addToRequestque(RegisterRequest);


    }

    private String getGender(){
        String gender = "";

        if(R.id.gender_male == genderView.getCheckedRadioButtonId())
        {
            gender = "Male";
        }
        else if(R.id.gender_female == genderView.getCheckedRadioButtonId()){
            gender = "Female";
        }
        return gender;
    }

    private String getShareWith(){
        String shareWith = "";

        if(R.id.share_male == shareWithView.getCheckedRadioButtonId())
        {
            shareWith = "Male";
        }
        else if(R.id.share_female == shareWithView.getCheckedRadioButtonId()){
            shareWith = "Female";
        }
        else if(R.id.share_both == shareWithView.getCheckedRadioButtonId()){
            shareWith = "Both";
        }
        return shareWith;
    }

}
