package com.error.mantissa.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.error.mantissa.R;
import com.error.mantissa.databinding.BookItemBinding;
import com.error.mantissa.model.Notepdf;
import com.error.mantissa.teacher.dash.PdfViewActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class ShowPdfAdapter extends RecyclerView.Adapter<ShowPdfAdapter.MyPdf> {


    private Context context;

    private final List<Notepdf> list;
    private String category;

    public ShowPdfAdapter(Context context, List<Notepdf> list, String category) {
        this.context = context;
        this.list = list;
        this.category = category;
    }

    @NonNull
    @Override
    public ShowPdfAdapter.MyPdf onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
        return new MyPdf(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowPdfAdapter.MyPdf holder, int position)
    {
        final Notepdf item=list.get(position);

        holder.binding.assignment12.setText(item.getPdf_title());
        holder.binding.teachername12.setText(item.getTname());
        holder.binding.coursetitle12.setText(item.getCtitle());
        holder.binding.dept12.setText(item.getDept());
        holder.binding.semester.setText(item.getSem());
        holder.binding.ebookName5050.setText(item.getPdf_title());


     /*   try {

            //   Picasso.get().load(item.getImage()).into(holder.imageView);
         //   Picasso.get().load(item.get).into(holder.binding.ebookDownload5050);

        } catch (Exception e) {

            e.printStackTrace();
        }*/


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PdfViewActivity.class);
                intent.putExtra("pdfUrl",list.get(position).getPdfUri());
                context.startActivity(intent);
                Toast.makeText(context,"Pdf name..."+item.getPdf_title(),Toast.LENGTH_SHORT).show();
            }
        });

        holder.binding.ebookDownload5050.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri pdfUri = Uri.parse(list.get(position).getPdfUri());

                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(pdfUri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setTitle("Downloading PDF");

                // Choose a destination folder for the downloaded file
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "downloaded_file.pdf");
                request.setDestinationUri(Uri.fromFile(file));

                // Enqueue the download
                downloadManager.enqueue(request);
            }
        });




    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyPdf extends RecyclerView.ViewHolder {

        BookItemBinding binding;
        public MyPdf(@NonNull View itemView) {
            super(itemView);

            binding=BookItemBinding.bind(itemView);
        }
    }
}
