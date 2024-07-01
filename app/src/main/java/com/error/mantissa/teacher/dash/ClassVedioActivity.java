package com.error.mantissa.teacher.dash;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.error.mantissa.R;
import com.error.mantissa.adapter.AccountsAdapter;
import com.error.mantissa.adapter.CategoryDepAdapter;
import com.error.mantissa.adapter.SectionAdapter;
import com.error.mantissa.databinding.ActivityClassVedioBinding;
import com.error.mantissa.databinding.ListDialogBinding;
import com.error.mantissa.model.Account;
import com.error.mantissa.model.CategoryModel;
import com.error.mantissa.model.SectionModel;
import com.error.mantissa.teacher.DashbordTeacherActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ClassVedioActivity extends AppCompatActivity {

    StorageReference storageReference;

    //  LinearProgressIndicator progressIndicator;
    Uri video;

    ActivityClassVedioBinding binding;
    String pdf_title,tname,ctitle,dept,sem,ses;
    private DatabaseReference reference123;
    private String downloadUrl = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityClassVedioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        FirebaseApp.initializeApp(this);
        storageReference = FirebaseStorage.getInstance().getReference();
        reference123 = FirebaseDatabase.getInstance().getReference().child("document");


        binding.department1.setOnClickListener(v -> {
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
                    binding.department1.setText(category.getCategoryName());
                    dialog.dismiss(); // Dismiss the dialog upon selecting a category
                }

            });

            dialogBinding.recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
            dialogBinding.recyclerView.setAdapter(categoryDepAdapter);


            dialog.show();
        });

        binding.Session1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ListDialogBinding dialogBinding = ListDialogBinding.inflate(getLayoutInflater());
                AlertDialog accountsDialog = new AlertDialog.Builder(ClassVedioActivity.this).create();
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

                SectionAdapter adapter=new SectionAdapter(ClassVedioActivity.this, sectionModels, new SectionAdapter.SectionClickListener() {
                    @Override
                    public void onAccountSelected(SectionModel sectionModel) {
                        binding.Session1.setText(sectionModel.getSectionName());

                        accountsDialog.dismiss();
                    }
                });


                dialogBinding.recyclerView.setLayoutManager(new LinearLayoutManager(ClassVedioActivity.this));
                dialogBinding.recyclerView.addItemDecoration(new DividerItemDecoration(ClassVedioActivity.this,DividerItemDecoration.VERTICAL));
                dialogBinding.recyclerView.setAdapter(adapter);

                accountsDialog.show();
            }
        });


        binding.account1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ListDialogBinding dialogBinding = ListDialogBinding.inflate(getLayoutInflater());
                AlertDialog accountsDialog = new AlertDialog.Builder(ClassVedioActivity.this).create();
                accountsDialog.setView(dialogBinding.getRoot());

                ArrayList<Account> accounts = new ArrayList<>();
                accounts.add(new Account(0, "1st"));
                accounts.add(new Account(0, "2nd"));
                accounts.add(new Account(0, "3rd"));
                accounts.add(new Account(0, "4th"));
                accounts.add(new Account(0, "5th"));
                accounts.add(new Account(0, "6th"));
                accounts.add(new Account(0, "7th"));
                accounts.add(new Account(0, "8th"));

                AccountsAdapter adapter= new AccountsAdapter(ClassVedioActivity.this, accounts, new AccountsAdapter.AccountsClickListener() {
                    @Override
                    public void onAccountSelected(Account account)
                    {
                        binding.account1.setText(account.getAccountName());
                        accountsDialog.dismiss();
                    }
                });



                dialogBinding.recyclerView.setLayoutManager(new LinearLayoutManager(ClassVedioActivity.this));
                dialogBinding.recyclerView.addItemDecoration(new DividerItemDecoration(ClassVedioActivity.this,DividerItemDecoration.VERTICAL));
                dialogBinding.recyclerView.setAdapter(adapter);

                accountsDialog.show();
            }
        });

        binding.addPdfCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("video/*");
                activityResultLauncher.launch(intent);
            }
        });

        binding.addebook1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checking();
            }
        });
    }
    private void Checking()
    {

        pdf_title=binding.pdfTitletextbox1.getText().toString().trim();
        tname=binding.teachername1.getText().toString().trim();
        ctitle=binding.coursetitle1.getText().toString().trim();
        dept=binding.department1.getText().toString().trim();
        sem=binding.account1.getText().toString().trim();
        ses=binding.Session1.getText().toString().trim();

        if (pdf_title.isEmpty() || tname.isEmpty() || ctitle.isEmpty() || dept.isEmpty() || sem.isEmpty() || ses.isEmpty()) {
            // If any field is empty, show a toast message indicating validation failed
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            // All fields are filled, proceed with further actions
            // For example, you can call a method to process the data
            uploadVideo(video);
        }

    }


    private void uploadVideo(Uri uri) {
        StorageReference reference = storageReference.child("videos/" + UUID.randomUUID().toString());
        reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        downloadUrl = uri.toString();
                        UploadData();

                    }
                });



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ClassVedioActivity.this, "Failed to upload video"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
            }
        });
    }

    private void UploadData() {
        String unique_key = reference123.child("pdf").push().getKey();
        HashMap<String, Object> data = new HashMap<>();
        data.put("pdf_title", pdf_title);
        data.put("pdfUri", downloadUrl);
        data.put("tname",tname);
        data.put("ctitle",ctitle);
        data.put("dept",dept);
        data.put("sem",sem);
        data.put("ses",ses);
        data.put("unique_key",unique_key);



        reference123.child(dept).child(ses).child(sem).child("VideoLecture").child(unique_key).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Toast.makeText(ClassVedioActivity.this, "Video uploaded successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), DashbordTeacherActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getApplicationContext(), "Something is wrong: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new
                    ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        if (result.getData() != null) {
                            binding.addebook1.setEnabled(true);
                            video = result.getData().getData();
                            Glide.with(ClassVedioActivity.this).load(video).into(binding.image);
                        }
                    } else {
                        Toast.makeText(ClassVedioActivity.this, "Please select a video", Toast.LENGTH_SHORT).show();
                    }
                }
            });
}