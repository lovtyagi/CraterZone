package com.example.createrzone;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.createrzone.databinding.CustomAdapterBinding;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    List<String> listOfImages = new ArrayList<>();
    Context context;

    public CustomAdapter(Context _context) {
        this.context = _context;
    }

    public void setList(List<String> listURL) {
        this.listOfImages = listURL;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomAdapterBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.custom_adapter, parent, false);
        return new CustomViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        String imageURL = listOfImages.get(position);
        setImage(holder.imgView,imageURL);
    }

    @Override
    public int getItemCount() {
        return listOfImages.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        androidx.appcompat.widget.AppCompatImageView imgView;

        public CustomViewHolder(CustomAdapterBinding binding) {
            super(binding.getRoot());
            this.imgView = binding.imageView;
        }
    }

    public void setImage(final androidx.appcompat.widget.AppCompatImageView imgView, final String url){
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Glide.with(context)
                        .asBitmap()
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imgView);
            }
        }.execute();
    }
}
