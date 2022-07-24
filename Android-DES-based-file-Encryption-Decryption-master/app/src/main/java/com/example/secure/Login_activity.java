package com.example.secure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login_activity extends AppCompatActivity {
    EditText loginKey;
    Button registerButton;
    Intent encIntent;
    SharedPreferences sharedPreferences;
    private static final String Shared_pref_name = "secureDesApp";
    private static final String Key_key = "secureDesKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences(Shared_pref_name, MODE_PRIVATE);
        loginKey = findViewById(R.id.loginKey);
        registerButton = findViewById(R.id.signinButton);
        registerButton.setOnClickListener((View v) -> {
            String Key = loginKey.getText().toString();
            if(Key.length()==0 || Key.length()<8){
                Toast.makeText(v.getContext(), "KEY TOO SHORT", Toast.LENGTH_SHORT).show();
            }else if(Key.contains(" ")) {
                Toast.makeText(v.getContext(), "INVALID KEY!!!", Toast.LENGTH_SHORT).show();
            }else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Key_key, Key);
                editor.apply();
                encIntent = new Intent(Login_activity.this, Encrypt_activity.class);
                startActivity(encIntent);
                finish();
            }
        });


    }
}