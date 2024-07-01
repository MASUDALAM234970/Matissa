package com.error.mantissa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.error.mantissa.auth.AdminLoginActivity;
import com.error.mantissa.auth.TeacherRegisterActivity;
import com.error.mantissa.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       // binding=ActivityMainBinding.inflate(getLayoutInflater());
       // setContentView(binding.getRoot());

        binding.res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TeacherRegisterActivity.class));
               // finish();
            }
        });

        binding.log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdminLoginActivity.class));
               // finish();
            }
        });
    }
}