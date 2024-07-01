package com.error.mantissa.student.Show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.error.mantissa.R;
import com.error.mantissa.adapter.ShowPdfAdapter;
import com.error.mantissa.model.Notepdf;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentShowEbookMainActivity extends AppCompatActivity {


    private RecyclerView EbookrecyclerView;
    private DatabaseReference reference;
    private List<Notepdf> list;
    private ShowPdfAdapter adapter;
    private Spinner dept;
    private Spinner Ses;
    private Spinner Sem;
    String selectedDepartment , selectedYear,selectedSemester;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_show_ebook_main);


        EbookrecyclerView=findViewById(R.id.stEbook);

        list = new ArrayList<>();
        dept=findViewById(R.id.show111);

        Ses = findViewById(R.id.show222);
        Sem = findViewById(R.id.show333);



// Sample data - replace with your actual data
        List<String> Dept = new ArrayList<>();
        Dept.add("CSE");
        Dept.add("EEE");
        Dept.add("TE");
        Dept.add("FDEA");
        Dept.add("ME");
        Dept.add("Other");
// Add more semesters as needed

// Create an ArrayAdapter using the list of semesters and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Dept);

// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        dept.setAdapter(adapter);




// Sample data - replace with your actual data
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
// Add more semesters as needed

// Create an ArrayAdapter using the list of semesters and a default spinner layout
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ses);

// Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        Ses.setAdapter(adapter1);




// Sample data - replace with your actual data
        List<String> semesters = new ArrayList<>();
        semesters.add("1st");
        semesters.add("2nd");
        semesters.add("3rd");
        semesters.add("4th");
        semesters.add("5th");
        semesters.add("6th");
        semesters.add("7th");
        semesters.add("8th");
// Add more semesters as needed

// Create an ArrayAdapter using the list of semesters and a default spinner layout
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, semesters);

// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        Sem.setAdapter(adapter2);







        //  reference = FirebaseDatabase.getInstance().getReference().child("document");




        dept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDepartment = parent.getItemAtPosition(position).toString();
                updateReference();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        Ses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedYear = parent.getItemAtPosition(position).toString();
                updateReference();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSemester = parent.getItemAtPosition(position).toString();
                updateReference();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void updateReference() {
        if (selectedDepartment != null && selectedYear != null && selectedSemester  != null) {
            reference = FirebaseDatabase.getInstance().getReference().child("document")
                    .child(selectedDepartment).child(selectedYear).child(selectedSemester);

            csDepartment();
        }
    }
    private void csDepartment() {
        reference = reference.child("ClassNote");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                if (!dataSnapshot.exists()) {

                } else {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Notepdf data = snapshot.getValue(Notepdf.class);
                        list.add(data);
                    }
                    EbookrecyclerView.setHasFixedSize(true);
                    EbookrecyclerView.setLayoutManager(new LinearLayoutManager(StudentShowEbookMainActivity.this));
                    adapter=new ShowPdfAdapter(StudentShowEbookMainActivity.this,list,"ClassNote");
                    /// adapter = new ShowPdfAdapter(list, ShowEbookActivity.this, "Assignment");

                    EbookrecyclerView.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(StudentShowEbookMainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}