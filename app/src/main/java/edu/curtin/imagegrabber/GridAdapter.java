package edu.curtin.imagegrabber;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class GridAdapter extends RecyclerView.Adapter<GridViewHolder>{
    ArrayList<Bitmap> image = new ArrayList<>();

    public GridAdapter(ArrayList<Bitmap> image) {
        this.image = image;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.grid_layout,parent,false);
        GridViewHolder myViewHolder = new GridViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        holder.image.setImageBitmap(image.get(position));
    }

    @Override
    public int getItemCount() {
        return image.size();
    }
}
