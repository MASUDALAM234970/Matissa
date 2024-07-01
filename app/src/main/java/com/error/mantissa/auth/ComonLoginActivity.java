package com.error.mantissa.auth;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.error.mantissa.R;
import com.error.mantissa.databinding.ActivityComonLoginBinding;
import com.error.mantissa.student.StudentDashBordMainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ComonLoginActivity extends AppCompatActivity {

    private ActivityComonLoginBinding binding;
    private ProgressDialog loadingBar;
    private DatabaseReference reference;
    private String selectedDepartment, selectedYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityComonLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
         PasswordHidden();
        loadingBar = new ProgressDialog(this);
        reference = FirebaseDatabase.getInstance().getReference().child("student");

        binding.LogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });

        // Populate department spinner
        List<String> Dept = new ArrayList<>();
        Dept.add("CSE");
        Dept.add("EEE");
        Dept.add("TE");
        Dept.add("FDEA");
        Dept.add("ME");
        Dept.add("Other");
        // Add more departments as needed

        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<>(ComonLoginActivity.this, R.layout.custom_spinner_dropdown_item, Dept);
        binding.studentDepartment.setAdapter(departmentAdapter);

        // Populate year spinner
        List<String> ses = new ArrayList<>();
        ses.add("2017-2018");
        ses.add("2018-2019");
        ses.add("2019-2020");
        ses.add("2020-2021");
        ses.add("2021-2022");
        ses.add("2022-2023");
        ses.add("2023-2024");
        ses.add("2024-2025");
        ses.add("2025-2026");
        ses.add("2026-2027");
        // Add more years as needed

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(ComonLoginActivity.this, R.layout.custom_spinner_dropdown_item, ses);
        binding.sessions.setAdapter(yearAdapter);

        // Set up spinner listeners
        binding.studentDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDepartment = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.sessions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedYear = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.adminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdminLoginActivity.class));
                finish();
            }
        });

        binding.teacherlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TeacherRegisterActivity.class));
                finish();
            }
        });
    }

    private void LoginUser() {
        String phone = binding.studentphone12.getText().toString();
        String password = binding.studentpassword11.getText().toString();

        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter phone number and password", Toast.LENGTH_SHORT).show();
            return;
        }

        loadingBar.setTitle("Login Account");
        loadingBar.setMessage("Please wait, while we are checking the credentials.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        reference.child(selectedDepartment).child(selectedYear).child(
                        "USER").child(phone).
                addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loadingBar.dismiss();
                if (dataSnapshot.exists()) {
                    String storedPassword = dataSnapshot.child("password").getValue(String.class);
                    Toast.makeText(getApplicationContext(), "In"+storedPassword, Toast.LENGTH_SHORT).show();
                    if (storedPassword != null && storedPassword.equals(password)) {
                        Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), StudentDashBordMainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect password!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "User with this phone number does not exist!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loadingBar.dismiss();
                Toast.makeText(getApplicationContext(), "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void PasswordHidden()
    {
        // Set initial input type to textPassword
        binding.studentpassword11.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

// Toggle password visibility
        binding.studentpassword11.setOnTouchListener(new View.OnTouchListener() {
            final int DRAWABLE_RIGHT = 2;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (  binding.studentpassword11.getRight() -   binding.studentpassword11.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // Toggle password visibility
                        if (  binding.studentpassword11.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                            binding.studentpassword11.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        } else {
                            binding.studentpassword11.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }

                        // Move cursor to the end
                        binding.studentpassword11.setSelection(  binding.studentpassword11.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
