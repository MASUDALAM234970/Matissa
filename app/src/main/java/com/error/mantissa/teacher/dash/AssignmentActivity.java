package com.error.mantissa.teacher.dash;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.error.mantissa.R;
import com.error.mantissa.adapter.AccountsAdapter;
import com.error.mantissa.adapter.CategoryDepAdapter;
import com.error.mantissa.adapter.SectionAdapter;
import com.error.mantissa.databinding.ActivityAssignmentBinding;
import com.error.mantissa.databinding.ListDialogBinding;
import com.error.mantissa.model.Account;
import com.error.mantissa.model.CategoryModel;
import com.error.mantissa.model.SectionModel;
import com.error.mantissa.teacher.DashbordTeacherActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AssignmentActivity extends AppCompatActivity {


    ActivityAssignmentBinding binding;


    private Spinner imageCategory;
    private CardView selectedImage;

    private Button UploadImage;

    private ImageView galleryImageView;

    private  String category;
    private  final int REQ = 1;

    private Bitmap bitmap;

    private ProgressDialog pd;
    private DatabaseReference reference;

    private StorageReference storageReference;
    // private FirebaseAuth mAuth;

    String downloadUrl;

    String pdf_title,tname,ctitle,dept,sem,ses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAssignmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        storageReference= FirebaseStorage.getInstance().getReference().child("Assignment");

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
                AlertDialog accountsDialog = new AlertDialog.Builder(AssignmentActivity.this).create();
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

                SectionAdapter adapter=new SectionAdapter(AssignmentActivity.this, sectionModels, new SectionAdapter.SectionClickListener() {
                    @Override
                    public void onAccountSelected(SectionModel sectionModel) {
                        binding.Session1.setText(sectionModel.getSectionName());

                        accountsDialog.dismiss();
                    }
                });


                dialogBinding.recyclerView.setLayoutManager(new LinearLayoutManager(AssignmentActivity.this));
                dialogBinding.recyclerView.addItemDecoration(new DividerItemDecoration(AssignmentActivity.this,DividerItemDecoration.VERTICAL));
                dialogBinding.recyclerView.setAdapter(adapter);

                accountsDialog.show();
            }
        });


        binding.account1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ListDialogBinding dialogBinding = ListDialogBinding.inflate(getLayoutInflater());
                AlertDialog accountsDialog = new AlertDialog.Builder(AssignmentActivity.this).create();
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

                AccountsAdapter adapter= new AccountsAdapter(AssignmentActivity.this, accounts, new AccountsAdapter.AccountsClickListener() {
                    @Override
                    public void onAccountSelected(Account account)
                    {
                        binding.account1.setText(account.getAccountName());
                        accountsDialog.dismiss();
                    }
                });



                dialogBinding.recyclerView.setLayoutManager(new LinearLayoutManager(AssignmentActivity.this));
                dialogBinding.recyclerView.addItemDecoration(new DividerItemDecoration(AssignmentActivity.this,DividerItemDecoration.VERTICAL));
                dialogBinding.recyclerView.setAdapter(adapter);

                accountsDialog.show();
            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("document");



        pd = new ProgressDialog(this);


        binding.addPdfCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });


        binding.addebook1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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
        } else if (bitmap==null)

    {
        Toast.makeText(getApplicationContext(),"Please upload_Image",Toast.LENGTH_SHORT).show();



    }



    else {

        pd.setTitle("Add New Assignment");
        pd.setMessage("Dear Admin, please wait while we are adding the new Assignment.");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

          uploadImage();

    }

    }

    private void uploadImage()
    {



        ByteArrayOutputStream baos= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte [] finalimge= baos.toByteArray();

        final StorageReference filepath;
        filepath=storageReference.child(finalimge+"jpg");

        final UploadTask uploadTask= filepath.putBytes(finalimge);

        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
            {

                if (task.isSuccessful())
                {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                    {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                            {
                                @Override
                                public void onSuccess(Uri uri)
                                {
                                    downloadUrl= String.valueOf(uri);
                                    UploadData();
                                    pd.dismiss();
                                }
                            });
                        }
                    });
                } else
                {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(),"Something is wrong",Toast.LENGTH_LONG).show();
                }

            }
        });

    }   // uploadImage finished

    private void UploadData() {
        String unique_key = reference.child("pdf").push().getKey();
        HashMap<String, Object> data = new HashMap<>();
        data.put("pdf_title", pdf_title);
        data.put("pdfUri", downloadUrl);
        data.put("tname",tname);
        data.put("ctitle",ctitle);
        data.put("dept",dept);
        data.put("sem",sem);
        data.put("ses",ses);
        data.put("unique_key",unique_key);



        reference.child(dept).child(ses).child(sem).child("Assignment").child(unique_key).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Assignment added successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), DashbordTeacherActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Something is wrong: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery()
    {
        Intent pickImage=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage,REQ);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==REQ&& resultCode ==RESULT_OK)
        {
            Uri uri= data.getData();
            try {
                bitmap =MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            binding.image.setImageBitmap(bitmap);
         //   galleryImageView.setImageBitmap(bitmap);
        }

      //  galleryImageView.setImageBitmap(bitmap);
        binding.image.setImageBitmap(bitmap);
    }
}