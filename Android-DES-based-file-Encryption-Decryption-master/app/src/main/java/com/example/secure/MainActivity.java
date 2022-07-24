package com.example.secure;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    Button encBtn;
    Button decBtn;
    Intent encIntent;
    Intent signinIntent;
    Intent loginIntent;
    SharedPreferences sharedPreferences;
    private static final String Shared_pref_name = "secureDesApp";
    private static final String Key_key = "secureDesKey";


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= 28) {
                    finishAffinity();
                } else {
                    finish();
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }


        setContentView(R.layout.activity_main);
        encBtn = findViewById(R.id.encBtn);
        decBtn = findViewById(R.id.decBtn);


        encBtn.setOnClickListener((View v)-> {
            sharedPreferences = getSharedPreferences(Shared_pref_name, MODE_PRIVATE);
            String key = sharedPreferences.getString(Key_key, null);
            if(key!=null){
                encIntent = new Intent(MainActivity.this, Encrypt_activity.class);
                startActivity(encIntent);
            }else {
                loginIntent = new Intent(MainActivity.this, Login_activity.class);
                startActivity(loginIntent);
            }

        });


        decBtn.setOnClickListener((View v)->{
            sharedPreferences = getSharedPreferences(Shared_pref_name, MODE_PRIVATE);
            String key = sharedPreferences.getString(Key_key, null);
            if(key!=null){
                signinIntent = new Intent(MainActivity.this, SignIn_Activity.class);
                startActivity(signinIntent);
            }else {
                loginIntent = new Intent(MainActivity.this, Login_activity.class);
                startActivity(loginIntent);
            }
        });

    }
}