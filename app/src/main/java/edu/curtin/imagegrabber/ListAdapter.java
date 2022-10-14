package edu.curtin.imagegrabber;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder>{

    ArrayList<Bitmap> image = new ArrayList<>();

    public ListAdapter(ArrayList<Bitmap> images) {
        this.image = images;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_layout,parent,false);
        ListViewHolder myViewHolder = new ListViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.image.setImageBitmap(image.get(position));
    }

    @Override
    public int getItemCount() {
        return image.size();
    }
}
