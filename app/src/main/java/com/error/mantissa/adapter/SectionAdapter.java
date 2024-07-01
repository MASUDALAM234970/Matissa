package com.error.mantissa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.error.mantissa.R;
import com.error.mantissa.databinding.SectiontemBinding;
import com.error.mantissa.model.SectionModel;


import java.util.ArrayList;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.SectionViewHolderView> {
    Context context;
    ArrayList<SectionModel> accountArrayList;

    public interface SectionClickListener {
        void onAccountSelected(SectionModel sectionModel);
    }

    SectionClickListener selectionClickListener;

    public SectionAdapter(Context context, ArrayList<SectionModel> accountArrayList, SectionClickListener selectionClickListener) {
        this.context = context;
        this.accountArrayList = accountArrayList;
        this.selectionClickListener = selectionClickListener;
    }

    @NonNull
    @Override
    public SectionViewHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new SectionViewHolderView(LayoutInflater.from(context).inflate(R.layout.sectiontem, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SectionViewHolderView holder, int position)
    {  SectionModel account = accountArrayList.get(position);
        holder.binding.section12.setText(account.getSectionName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               selectionClickListener.onAccountSelected(account);

            }
        });


    }

    @Override
    public int getItemCount() {
        return accountArrayList.size()
                ;
    }

    public class SectionViewHolderView extends RecyclerView.ViewHolder {
        SectiontemBinding binding;
        public SectionViewHolderView(@NonNull View itemView) {
            super(itemView);
            binding=SectiontemBinding.bind(itemView);

        }
    }
}
