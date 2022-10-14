package edu.curtin.imagegrabber;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridViewHolder extends RecyclerView.ViewHolder{
    ImageView image;
    Button upload;
    public GridViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.item_grid_image);
        upload = itemView.findViewById(R.id.item_grid_upload);
    }
}
