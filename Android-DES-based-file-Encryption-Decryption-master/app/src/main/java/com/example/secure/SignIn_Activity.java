package com.example.secure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignIn_Activity extends AppCompatActivity {
    EditText signinKey;
    Button signinButton;
    Intent decIntent;
    SharedPreferences sharedPreferences;
    private static final String Shared_pref_name = "secureDesApp";
    private static final String Key_key = "secureDesKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        sharedPreferences = getSharedPreferences(Shared_pref_name, MODE_PRIVATE);
        String registeredKey = sharedPreferences.getString(Key_key, null);
        signinKey = findViewById(R.id.signinKey);
        signinButton = findViewById(R.id.signinButton);
        signinButton.setOnClickListener((View v) -> {
            String enteredKey = signinKey.getText().toString();
            if(registeredKey.equals(enteredKey)){
                decIntent= new Intent(SignIn_Activity.this, Decrypt_activity.class);
                startActivity(decIntent);
                finish();
            }else {
                Toast.makeText(v.getContext(), "WRONG KEY", Toast.LENGTH_SHORT).show();
            }
        });



    }
}