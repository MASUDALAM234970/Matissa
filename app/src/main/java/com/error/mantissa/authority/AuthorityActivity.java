package com.error.mantissa.authority;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.error.mantissa.R;
import com.error.mantissa.authority.Delete.DeleteNoticeActivity;
import com.error.mantissa.authority.auth.RegisterActivity;
import com.error.mantissa.authority.auth.TeacherRegistarActivity;
import com.error.mantissa.authority.control.AdimnSliderActivity;
import com.error.mantissa.authority.control.EventActivity;
import com.error.mantissa.authority.notice.UploadNotice;
import com.error.mantissa.databinding.ActivityAuthorityBinding;

public class AuthorityActivity extends AppCompatActivity {


    ActivityAuthorityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAuthorityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.createStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        binding.createTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TeacherRegistarActivity.class));
            }
        });

        binding.slide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdimnSliderActivity.class));
            }
        });

        binding.event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EventActivity.class));
            }
        });

        binding.notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UploadNotice.class));
            }
        });

        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DeleteNoticeActivity.class));
            }
        });

        binding.showStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(getApplicationContext(), DeleteNoticeActivity.class));
                Toast.makeText(AuthorityActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
                Toast.makeText(AuthorityActivity.this, "Please wait ", Toast.LENGTH_SHORT).show();
                Toast.makeText(AuthorityActivity.this, "Developer are not Robot ", Toast.LENGTH_SHORT).show();
            }
        });
        binding.showTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(getApplicationContext(), DeleteNoticeActivity.class));
                Toast.makeText(AuthorityActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
                Toast.makeText(AuthorityActivity.this, "Please wait ", Toast.LENGTH_SHORT).show();
                Toast.makeText(AuthorityActivity.this, "Developer are not Robot ", Toast.LENGTH_SHORT).show();
            }
        });
        binding.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DeleteNoticeActivity.class));
                Toast.makeText(AuthorityActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
                Toast.makeText(AuthorityActivity.this, "Please wait ", Toast.LENGTH_SHORT).show();
                Toast.makeText(AuthorityActivity.this, "Developer are not Robot ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}