package com.error.mantissa.student.Show;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.error.mantissa.R;
import com.error.mantissa.adapter.galleryAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventStudentActivity extends AppCompatActivity {

    private RecyclerView recyclerView_con,recyclerView_Inter,recyclerViewOther;
    private galleryAdapter adapter;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event_student);

        recyclerView_con=findViewById(R.id.conRecyclerView);
        recyclerView_Inter=findViewById(R.id.interRecyclerView);
        recyclerViewOther=findViewById(R.id.otherRecyclerView);

        reference= FirebaseDatabase.getInstance().getReference("gallery");

        getGalleryImageConvocation();
        getGalleryImageOtherEvent();
        getGalleryImageIndependenceDey();

    }

    private void getGalleryImageIndependenceDey()
    {
        List<String> imagelist=new ArrayList<>();
        reference.child("Independence Dey ").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    String data= (String) snapshot1.getValue();
                    imagelist.add(data);
                }
                adapter= new galleryAdapter(EventStudentActivity.this,imagelist);
                recyclerView_Inter.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));

                recyclerView_Inter.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void getGalleryImageOtherEvent()
    {
        List<String>imagelist=new ArrayList<>();
        reference.child("Others ever").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    String data= (String) snapshot1.getValue();
                    imagelist.add(data);
                }
                adapter= new galleryAdapter(EventStudentActivity.this,imagelist);
                recyclerViewOther.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));

                recyclerViewOther.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getGalleryImageConvocation()
    {
        List<String>imagelist=new ArrayList<>();
        reference.child("Convocation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    String data= (String) snapshot1.getValue();
                    imagelist.add(data);
                }
                adapter= new galleryAdapter(EventStudentActivity.this,imagelist);
                recyclerView_con.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));

                recyclerView_con.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}