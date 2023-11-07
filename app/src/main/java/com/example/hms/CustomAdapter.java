package com.example.hms;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hms.model.MyDataModel;
import com.squareup.picasso.Picasso; // Picasso library for loading images

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private List<MyDataModel> dataList;

    public CustomAdapter(List<MyDataModel> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyDataModel data = dataList.get(position);

        holder.textView1.setText(data.getComplaint());
        holder.textView2.setText(data.getResName());

        // Load image using Picasso library
        Picasso.get().load(data.getEvidenceImage()).into(holder.itemImageView);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView itemImageView;
        public TextView textView1;
        public TextView textView2;

        public ViewHolder(View view) {
            super(view);
            itemImageView = view.findViewById(R.id.itemImageView);
            textView1 = view.findViewById(R.id.textView1);
            textView2 = view.findViewById(R.id.textView2);
        }
    }
}
