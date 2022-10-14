package edu.curtin.imagegrabber;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListViewHolder extends RecyclerView.ViewHolder{
    ImageView image;
    Button upload;
    public ListViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.item_list_image);
        upload = itemView.findViewById(R.id.item_list_upload);
    }
}
