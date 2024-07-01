package com.error.mantissa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.error.mantissa.R;
import com.error.mantissa.databinding.VideoListItemBinding;
import com.error.mantissa.model.Notepdf;

import java.util.ArrayList;

public class VideoShiowAdapter extends RecyclerView.Adapter<VideoShiowAdapter.MyVideoHolder> {

    Context context;
    ArrayList<Notepdf> csList;
    OnItemClickListener onItemClickListener;
    private String category;

    public VideoShiowAdapter(Context context, ArrayList<Notepdf> arrayList, String category) {
        this.context = context;
        this.csList = arrayList;
        this.category = category;
    }

    @NonNull
    @Override
    public VideoShiowAdapter.MyVideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_list_item, parent, false);
        return new MyVideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoShiowAdapter.MyVideoHolder holder, int position)
    {
        holder.binding.listItemTitle.setText(csList.get(position).getPdf_title());
        Glide.with(context).load(csList.get(position).getPdfUri()).into(holder.binding.listItemImage);

        // Check if onItemClickListener is not null before setting the click listener
        if (onItemClickListener != null) {
            holder.binding.listItemTitle.setOnClickListener(view -> onItemClickListener.onClick(csList.get(position)));
        }

    }

    @Override
    public int getItemCount() {
        return csList.size();
    }

    public class MyVideoHolder extends RecyclerView.ViewHolder {
        VideoListItemBinding binding;
        public MyVideoHolder(@NonNull View itemView) {
            super(itemView);
            binding=VideoListItemBinding.bind(itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(Notepdf notepdf);
    }
}
