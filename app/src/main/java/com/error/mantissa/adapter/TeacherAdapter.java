package com.error.mantissa.adapter;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.error.mantissa.R;

import com.error.mantissa.databinding.AssignmentviewBinding;


import com.error.mantissa.model.Notepdf;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewAdapter> {

   // private List<Notepdf> list;
    List<Notepdf>list;
    private Context context;
    private String category;

    public TeacherAdapter(List<Notepdf> list, Context context, String category) {
        this.list = list;
        this.context = context;
        this.category = category;
    }


    @NonNull
    @Override
    public TeacherAdapter.TeacherViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.assignmentview,parent,false);
        return new TeacherViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherAdapter.TeacherViewAdapter holder, int position)
    {
        Notepdf item=list.get(position);

      /*  holder.name.setText(item.getName());
        holder.phone.setText(item.getPhone());
        holder.email.setText(item.getEmail());
        holder.post.setText(item.getPost());*/

        holder.binding.assignment12.setText(item.getPdf_title());
         holder.binding.teachername12.setText(item.getTname());
        holder.binding.coursetitle12.setText(item.getCtitle());
        holder.binding.dept12.setText(item.getDept());
        holder.binding.semester.setText(item.getSem());


        try {

         //   Picasso.get().load(item.getImage()).into(holder.imageView);
            Picasso.get().load(item.getPdfUri()).into(holder.binding.image12);

        } catch (Exception e) {

            e.printStackTrace();
        }

    /*   holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, .class);

                intent.putExtra("name",item.getName());
                intent.putExtra("phone",item.getPhone());
                intent.putExtra("email",item.getEmail());
                intent.putExtra("post",item.getPost());
                intent.putExtra("image",item.getImage());
                intent.putExtra("key",item.getKey());
                intent.putExtra("category",category);
                context.startActivity(intent);


            }
        });*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TeacherViewAdapter extends RecyclerView.ViewHolder {

         AssignmentviewBinding binding;
        public TeacherViewAdapter(@NonNull View itemView) {
            super(itemView);


            binding=AssignmentviewBinding.bind(itemView);
        }
    }
}
