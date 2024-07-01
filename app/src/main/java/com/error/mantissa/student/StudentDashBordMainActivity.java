package com.error.mantissa.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.error.mantissa.R;
import com.error.mantissa.authority.control.EventActivity;
import com.error.mantissa.authority.control.model.DataClass;
import com.error.mantissa.authority.control.model.MyAdapter;
import com.error.mantissa.databinding.ActivityStudentDashBordMainBinding;
import com.error.mantissa.student.Show.EventStudentActivity;
import com.error.mantissa.student.Show.StudentNoticeActivity;
import com.error.mantissa.student.Show.StudentShowAssignmentMainActivity;
import com.error.mantissa.student.Show.StudentShowClassNoteMainActivity;
import com.error.mantissa.student.Show.StudentShowEbookMainActivity;
import com.error.mantissa.student.Show.StudentShowVedioLectureMainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentDashBordMainActivity extends AppCompatActivity {

    ActivityStudentDashBordMainBinding binding;


 //   private ArrayList<DataClass> dataList;

    private ArrayList<SlideModel> slideModels = new ArrayList<>();
    private ArrayList<DataClass> dataList = new ArrayList<>();
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("SLIDERS");
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentDashBordMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.showClassNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(), StudentShowClassNoteMainActivity.class));
            }
        });


        binding.notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StudentNoticeActivity.class));
            }
        });



        binding.event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EventStudentActivity.class));
            }
        });


        binding.showebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StudentShowEbookMainActivity.class));
            }
        });
        binding.showLecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StudentShowVedioLectureMainActivity.class));
            }
        });

       binding.attendance25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StudentShowAssignmentMainActivity.class));
            }
        });
        binding.attendant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  startActivity(new Intent(getApplicationContext(),StdudentProfileActivity.class));
            }
        });

        //ArrayList<SlideModel>slideModels=new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                slideModels.clear(); // Clear existing images
                dataList.clear();   // Clear existing data

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DataClass dataClass = dataSnapshot.getValue(DataClass.class);

                    if (dataClass != null) {
                        // Add images to the image slider
                        slideModels.add(new SlideModel(dataClass.getImageURL(), ScaleTypes.FIT));
                        // Add data to the data list
                        dataList.add(dataClass);
                    }
                }

                // Set the image list for the image slider
                binding.imageSli50.setImageList(slideModels, ScaleTypes.FIT);

                // Notify your adapter to update the UI with the data
             //   adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error, if needed
            }
        });



    }
}


