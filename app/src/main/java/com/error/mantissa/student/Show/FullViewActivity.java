package com.error.mantissa.student.Show;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.error.mantissa.R;
import com.github.chrisbanes.photoview.PhotoView;

public class FullViewActivity extends AppCompatActivity {

    private PhotoView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_full_view);
        imageView=findViewById(R.id.fullImage);
        String image=getIntent().getStringExtra("image");

        Glide.with(this).load(image).into(imageView);
        // Picasso.get().load(image).into(imageView);

    }
}