package com.error.mantissa.authority.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.se.omapi.Session;
import android.system.StructUtsname;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.error.mantissa.R;
import com.error.mantissa.adapter.CategoryDepAdapter;
import com.error.mantissa.adapter.SectionAdapter;
import com.error.mantissa.authority.AuthorityActivity;
import com.error.mantissa.databinding.ActivityRegisterBinding;
import com.error.mantissa.databinding.ListDialogBinding;
import com.error.mantissa.model.CategoryModel;
import com.error.mantissa.model.SectionModel;
import com.error.mantissa.model.Student_reg;
import com.error.mantissa.teacher.dash.AssignmentActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.PasswordAuthentication;
import java.util.ArrayList;


import java.util.Properties;

public class RegisterActivity extends AppCompatActivity {

  ActivityRegisterBinding binding;
    private ProgressDialog pd;
    private DatabaseReference reference;

    private String name,id, dep, email, phone ,password,ses ;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        reference = FirebaseDatabase.getInstance().getReference().child("student");

        pd = new ProgressDialog(this);

        binding.department11.setOnClickListener(v -> {
            ListDialogBinding dialogBinding = ListDialogBinding.inflate(LayoutInflater.from(getApplicationContext()));
            AlertDialog.Builder builder = new AlertDialog.Builder(this); // Use your activity name here instead of YourActivityName
            builder.setView(dialogBinding.getRoot());

            ArrayList<CategoryModel> categories = new ArrayList<>();
            categories.add(new CategoryModel("CSE", R.drawable.programming, R.color.category1));
            categories.add(new CategoryModel("EEE", R.drawable.cpu, R.color.category2));
            categories.add(new CategoryModel("TE", R.drawable.thread, R.color.category3));
            categories.add(new CategoryModel("FDEA", R.drawable.dress, R.color.category4));
            categories.add(new CategoryModel("ME", R.drawable.mechanic, R.color.category5));
            categories.add(new CategoryModel("Other", R.drawable.menu, R.color.category6));
            AlertDialog dialog = builder.create();

            CategoryDepAdapter categoryDepAdapter = new CategoryDepAdapter(getApplicationContext(), categories, new CategoryDepAdapter.CategoryClickListener() {
                @Override
                public void onCategoryClicked(CategoryModel category) {
                    binding.department11.setText(category.getCategoryName());
                    dialog.dismiss(); // Dismiss the dialog upon selecting a category
                }

            });

            dialogBinding.recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
            dialogBinding.recyclerView.setAdapter(categoryDepAdapter);


            dialog.show();
        });

        binding.session11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ListDialogBinding dialogBinding = ListDialogBinding.inflate(getLayoutInflater());
                AlertDialog accountsDialog = new AlertDialog.Builder(RegisterActivity.this).create();
                accountsDialog.setView(dialogBinding.getRoot());

                ArrayList<SectionModel> sectionModels = new ArrayList<>();
                sectionModels.add(new SectionModel(0,"2017-2018"));
                sectionModels.add(new SectionModel(0,"2018-2019"));
                sectionModels.add(new SectionModel(0,"2019-2020"));
                sectionModels.add(new SectionModel(0,"2020-2021"));
                sectionModels.add(new SectionModel(0,"2021-2022"));
                sectionModels.add(new SectionModel(0,"2022-2023"));
                sectionModels.add(new SectionModel(0,"2023-2024"));
                sectionModels.add(new SectionModel(0,"2024-2025"));


                // accounts.add(new Account(0, "Other"));

                SectionAdapter adapter=new SectionAdapter(RegisterActivity.this, sectionModels, new SectionAdapter.SectionClickListener() {
                    @Override
                    public void onAccountSelected(SectionModel sectionModel) {
                        binding.session11.setText(sectionModel.getSectionName());

                        accountsDialog.dismiss();
                    }
                });


                dialogBinding.recyclerView.setLayoutManager(new LinearLayoutManager(RegisterActivity.this));
                dialogBinding.recyclerView.addItemDecoration(new DividerItemDecoration(RegisterActivity.this,DividerItemDecoration.VERTICAL));
                dialogBinding.recyclerView.setAdapter(adapter);

                accountsDialog.show();
            }
        });


        binding.Reg159.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                checkValidation();
               sendMail();
            }
        });



    }




    private void checkValidation() {
        // Get the text from EditText fields
        name = binding.name11.getText().toString().trim();
        id = binding.id11.getText().toString().trim();
        dep = binding.department11.getText().toString().trim();
        email = binding.email11.getText().toString().trim();
        phone = binding.phone11.getText().toString().trim();
        password = binding.password11.getText().toString().trim();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        // Check if any EditText field is empty
        if (name.isEmpty() || id.isEmpty() || dep.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            // If any field is empty, show a toast message indicating validation failed
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else if (phone.length() != 11) {
            binding.phone11.setError("Enter valid Phone Number");
            binding.phone11.requestFocus();
        } else if (!email.matches(emailPattern)) {
            binding.email11.setError("Invalid Email");
            binding.email11.requestFocus();
        } else {
            // All fields are filled and validated, proceed with data insertion
            insertData();

            // Show ProgressDialog
            pd.setTitle("Add New Student ");
            pd.setMessage("Dear Admin, please wait while we are adding the new Student.");
            pd.setCanceledOnTouchOutside(false);
            pd.show();
        }
    }


    private void insertData() {
        String ses=binding.session11.getText().toString().trim();
        String dep=binding.department11.getText().toString().trim();
        String phone=binding.phone11.getText().toString().trim();
        dbRef = reference.child(dep);

        final String unique_key = dbRef.push().getKey();

       Student_reg studentReg=new Student_reg(name,id,dep,email,phone,password,unique_key);

        dbRef.child(ses).child("USER").child(phone).setValue(studentReg).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), " Successfully added Student ", Toast.LENGTH_SHORT).show();
              //  Intent intent = new Intent(getApplicationContext(), AuthorityActivity.class);
              //  startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Something Wrong: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }

    private void sendMail()
    {
        String recipientList =binding.email11.getText().toString();
        String[] recipients = recipientList.split(",") ;

        String subject = binding.phone11.getText().toString();
        String message = binding.password11.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,message);

        intent.setType("massage/rfc822");
        startActivity(Intent.createChooser(intent,"Choose an email clint"));
    }


}