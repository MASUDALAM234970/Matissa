package com.error.mantissa.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.error.mantissa.R;
import com.error.mantissa.databinding.ActivityDashbordTeacherBinding;
import com.error.mantissa.teacher.dash.AssignmentActivity;
import com.error.mantissa.teacher.dash.AttendanceActivity;
import com.error.mantissa.teacher.dash.ClassNoteActivity;
import com.error.mantissa.teacher.dash.ClassVedioActivity;
import com.error.mantissa.teacher.dash.EbookActivity;
import com.error.mantissa.teacher.dash.ShowClassNoteActivity;
import com.error.mantissa.teacher.dash.ShowEbookActivity;

public class DashbordTeacherActivity extends AppCompatActivity {
ActivityDashbordTeacherBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashbordTeacherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ClassNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(), ClassNoteActivity.class));
            }
        });
        binding.ebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(), EbookActivity.class));
            }
        });
        binding.assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(), AssignmentActivity.class));
            }
        });
        binding.classVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ClassVedioActivity.class));
            }
        });
        binding.attendant1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Coming soon",Toast.LENGTH_SHORT).show();
               // startActivity(new Intent(getApplicationContext(), AttendanceActivity.class));
            }
        });

        binding.showebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Coming soon",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ShowEbookActivity.class));
            }
        });

        binding.showClassNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Coming soon",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ShowClassNoteActivity.class));
            }
        });

        binding.attendance25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Coming soon",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ShowClassNoteActivity.class));
            }
        });
    }
}