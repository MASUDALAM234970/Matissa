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
import com.error.mantissa.databinding.ActivityAdminRegisterBinding;
import com.error.mantissa.student.StudentDashBordMainActivity;
import com.error.mantissa.teacher.DashbordTeacherActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeacherRegisterActivity extends AppCompatActivity {

    ActivityAdminRegisterBinding binding;


    private ProgressDialog loadingBar;
    String    selectedDepartment;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        reference = FirebaseDatabase.getInstance().getReference().child("teacher");
        loadingBar = new ProgressDialog(this);
        PasswordHidden();

        binding.adminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdminLoginActivity.class));
                finish();
            }
        });

        binding.studentlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ComonLoginActivity.class));
                finish();
            }
        });
        binding.LogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  CreateAccount();
            }
        });

        List<String> Dept = new ArrayList<>();
        Dept.add("CSE");
        Dept.add("EEE");
        Dept.add("TE");
        Dept.add("FDEA");
        Dept.add("ME");
        Dept.add("Other");
        // Add more departments as needed

        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<>(TeacherRegisterActivity.this, R.layout.custom_spinner_dropdown_item, Dept);

        binding.studentDepartment.setAdapter(departmentAdapter);

        binding.studentDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDepartment = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.LogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });
    }

    private void LoginUser() {
        String phone = binding.Logphone.getText().toString();
        String password = binding.Logpassword.getText().toString();

        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter phone number and password", Toast.LENGTH_SHORT).show();
            return;
        }

        loadingBar.setTitle("Login Account");
        loadingBar.setMessage("Please wait, while we are checking the credentials.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        reference.child(selectedDepartment).
                child(phone).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        loadingBar.dismiss();
                        if (dataSnapshot.exists()) {
                            String storedPassword = dataSnapshot.child("password").getValue(String.class);
                           // Toast.makeText(getApplicationContext(), "In"+storedPassword, Toast.LENGTH_SHORT).show();
                            if (storedPassword != null && storedPassword.equals(password)) {
                                Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), DashbordTeacherActivity.class));
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
        binding.Logpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

// Toggle password visibility
        binding.Logpassword.setOnTouchListener(new View.OnTouchListener() {
            final int DRAWABLE_RIGHT = 2;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (  binding.Logpassword.getRight() -   binding.Logpassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // Toggle password visibility
                        if (  binding.Logpassword.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                            binding.Logpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        } else {
                            binding.Logpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }

                        // Move cursor to the end
                        binding.Logpassword.setSelection(  binding.Logpassword.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });
    }
}



