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
import android.widget.Toast;

import com.error.mantissa.R;
import com.error.mantissa.authority.AuthorityActivity;
import com.error.mantissa.databinding.ActivityAdminLoginBinding;
import com.error.mantissa.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginActivity extends AppCompatActivity {


   ActivityAdminLoginBinding binding;
    private ProgressDialog loadingBar;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        setContentView(binding.getRoot());
        PasswordHidden();

     //   mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // String AdminLink= binding.adminPanelLink;
        // binding.notAdminPanelLink;

        loadingBar=new ProgressDialog(this);
        binding.LogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                LoginUser();



            }
        });


        binding.studentlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                  // startActivity(new Intent(getApplicationContext(), ComonLoginActivity.class));
                  // finish();
            }
        });


       binding.teacherlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(), TeacherRegisterActivity.class));
                finish();
            }
        });



    }


    private void LoginUser() {

        String phone = binding.LogPhone.getText().toString();
        String password = binding.LogPassword.getText().toString();



        if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();


            AllowAccessToAccount(phone, password);
        }

    }

    private void AllowAccessToAccount(final String phone, final String password) {
        mDatabase.child("Authority").child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User exists, try to authenticate
                    String storedPassword = dataSnapshot.child("password").getValue(String.class);
                    String storedPhone= dataSnapshot.child("phone").getValue(String.class);

                    Toast.makeText(getApplicationContext(),""+phone,Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),""+password,Toast.LENGTH_SHORT).show();
                    if (password.equals(storedPassword)&&phone.equals(storedPhone)) {
                        // Password matches, login successful
                        Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                        // You can start your next activity here
                        startActivity(new Intent(getApplicationContext(), AuthorityActivity.class));
                        finish(); // Finish current activity to prevent going back
                    } else {
                        loadingBar.dismiss();
                        // Password incorrect
                        Toast.makeText(getApplicationContext(), "Incorrect password!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    loadingBar.dismiss();
                    // User does not exist
                    Toast.makeText(getApplicationContext(), "User with this phone number does not exist!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loadingBar.dismiss();
                // Handle database error
                Toast.makeText(getApplicationContext(), "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void PasswordHidden()
    {
        // Set initial input type to textPassword
        binding.LogPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

// Toggle password visibility
        binding.LogPassword.setOnTouchListener(new View.OnTouchListener() {
            final int DRAWABLE_RIGHT = 2;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (  binding.LogPassword.getRight() -   binding.LogPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // Toggle password visibility
                        if (  binding.LogPassword.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                            binding.LogPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        } else {
                            binding.LogPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }

                        // Move cursor to the end
                        binding.LogPassword.setSelection(  binding.LogPassword.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });
    }
}