package com.error.mantissa.teacher.dash;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.error.mantissa.R;
import com.error.mantissa.adapter.AccountsAdapter;
import com.error.mantissa.adapter.CategoryDepAdapter;
import com.error.mantissa.adapter.SectionAdapter;
import com.error.mantissa.databinding.ActivityClassNoteBinding;
import com.error.mantissa.databinding.ListDialogBinding;
import com.error.mantissa.model.Account;
import com.error.mantissa.model.CategoryModel;
import com.error.mantissa.model.SectionModel;
import com.error.mantissa.teacher.DashbordTeacherActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;



import java.util.ArrayList;
import java.util.HashMap;

public class ClassNoteActivity extends AppCompatActivity {


    ActivityClassNoteBinding binding;
    private String title;
    private ProgressDialog pd;
    private String pdfName;

    private final int REQ = 1;
    private Uri pdfdata;

    private DatabaseReference reference123;
    private StorageReference storageReference;
    private String downloadUrl = " ";
    String pdf_title,tname,ctitle,dept,sem,ses;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding=ActivityClassNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
/*

        Toolbar mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("College_Management");
*/


        binding.department.setOnClickListener(v -> {
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
                    binding.department.setText(category.getCategoryName());
                    dialog.dismiss(); // Dismiss the dialog upon selecting a category
                }

            });

            dialogBinding.recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
            dialogBinding.recyclerView.setAdapter(categoryDepAdapter);


            dialog.show();
        });

        binding.Session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ListDialogBinding dialogBinding = ListDialogBinding.inflate(getLayoutInflater());
                AlertDialog accountsDialog = new AlertDialog.Builder(ClassNoteActivity.this).create();
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

                SectionAdapter adapter=new SectionAdapter(getApplicationContext(), sectionModels, new SectionAdapter.SectionClickListener() {
                    @Override
                    public void onAccountSelected(SectionModel sectionModel) {
                        binding.Session.setText(sectionModel.getSectionName());

                        accountsDialog.dismiss();
                    }
                });


                dialogBinding.recyclerView.setLayoutManager(new LinearLayoutManager(ClassNoteActivity.this));
                dialogBinding.recyclerView.addItemDecoration(new DividerItemDecoration(ClassNoteActivity.this,DividerItemDecoration.VERTICAL));
                dialogBinding.recyclerView.setAdapter(adapter);

                accountsDialog.show();
            }
        });


        binding.account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ListDialogBinding dialogBinding = ListDialogBinding.inflate(getLayoutInflater());
                AlertDialog accountsDialog = new AlertDialog.Builder(ClassNoteActivity.this).create();
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

                AccountsAdapter adapter= new AccountsAdapter(getApplicationContext(), accounts, new AccountsAdapter.AccountsClickListener() {
                    @Override
                    public void onAccountSelected(Account account)
                    {
                        binding.account.setText(account.getAccountName());
                        accountsDialog.dismiss();
                    }
                });



                dialogBinding.recyclerView.setLayoutManager(new LinearLayoutManager(ClassNoteActivity.this));
                dialogBinding.recyclerView.addItemDecoration(new DividerItemDecoration(ClassNoteActivity.this,DividerItemDecoration.VERTICAL));
                dialogBinding.recyclerView.setAdapter(adapter);

                accountsDialog.show();
            }
        });

        reference123 = FirebaseDatabase.getInstance().getReference().child("document");
        storageReference = FirebaseStorage.getInstance().getReference();
        pd = new ProgressDialog(this);


       binding.addPdfCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });


        binding.addebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Checking();

              /*  title = pdfTitleedittext.getText().toString();
                if (title.isEmpty()) {
                    pdfTitleedittext.setError("Empty");
                    pdfTitleedittext.requestFocus();
                } else if (pdfdata == null) {
                    Toast.makeText(getApplicationContext(), "Please upload pdf", Toast.LENGTH_LONG).show();
                } else {
                    UploadPdf();
                }*/
            }
        });

    }

    private void Checking()
    {

        pdf_title=binding.pdfTitletextbox.getText().toString().trim();
        tname=binding.teachername.getText().toString().trim();
        ctitle=binding.coursetitle.getText().toString().trim();
        dept=binding.department.getText().toString().trim();
        sem=binding.account.getText().toString().trim();
        ses=binding.Session.getText().toString().trim();

        if (pdf_title.isEmpty() || tname.isEmpty() || ctitle.isEmpty() || dept.isEmpty() || sem.isEmpty() || ses.isEmpty()) {
            // If any field is empty, show a toast message indicating validation failed
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            // All fields are filled, proceed with further actions
            // For example, you can call a method to process the data
            UploadPdf();
        }

    }
    private void UploadPdf() {
        pd.setTitle("Add New Class Note ");
        pd.setMessage("Dear Admin, please wait while we are adding the new Class Note.");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        StorageReference reference = storageReference.child("pdf/" + pdfName);
        reference.putFile(pdfdata).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Something is wrong: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

       

        reference123.child(dept).child(ses).child(sem).child("ClassNote").child(unique_key).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "E-book added successfully", Toast.LENGTH_SHORT).show();
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

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, REQ);
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK) {
            pdfdata = data.getData();
            if (pdfdata != null) {
                Cursor cursor = null;
                try {
                    cursor = getContentResolver().query(pdfdata, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        pdfName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        binding.pdftextview.setText(pdfName);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        }
    }
}