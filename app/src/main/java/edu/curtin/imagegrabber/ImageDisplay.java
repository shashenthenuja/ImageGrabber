package edu.curtin.imagegrabber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import edu.curtin.imagegrabber.fragments.GridView;
import edu.curtin.imagegrabber.fragments.ListView;

public class ImageDisplay extends AppCompatActivity {

    Button listView;
    Button gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        listView = findViewById(R.id.listBtn);
        gridView = findViewById(R.id.gridBtn);

        // receive byte array and convert to bitmap
        ArrayList<byte[]> images = (ArrayList<byte[]>) getIntent().getSerializableExtra("list");
        ArrayList<Bitmap> conImages = convertImage(images);

        // set initial layout
        FragmentManager headerFrag = getSupportFragmentManager();
        ListView listImages = (ListView) headerFrag.findFragmentById(R.id.fragLayout);
        if (listImages == null) {
            listImages = new ListView(conImages);
            headerFrag.beginTransaction().add(R.id.fragLayout, listImages).commit();
        }

        // change to list layout
        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!conImages.isEmpty()) {
                    FragmentManager fragMan = getSupportFragmentManager();
                    FragmentTransaction s = fragMan.beginTransaction();
                    s.replace(R.id.fragLayout, new ListView(conImages), null);
                    s.commitAllowingStateLoss();
                }else {
                    Snackbar notify = Snackbar.make(view, "No Images!", Snackbar.LENGTH_SHORT);
                    notify.show();
                }
            }
        });

        // change to grid layout
        gridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!conImages.isEmpty()) {
                    FragmentManager fragMan = getSupportFragmentManager();
                    FragmentTransaction s = fragMan.beginTransaction();
                    s.replace(R.id.fragLayout, new GridView(conImages), null);
                    s.commitAllowingStateLoss();
                }else {
                    Snackbar notify = Snackbar.make(view, "No Images!", Snackbar.LENGTH_SHORT);
                    notify.show();
                }
            }
        });
    }

    // convert byte arrays to bitmap
    public ArrayList<Bitmap> convertImage(ArrayList<byte[]> images) {
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        for (byte[] b:images
             ) {
            Bitmap compressedBitmap = BitmapFactory.decodeByteArray(b,0,b.length);
            bitmaps.add(compressedBitmap);
        }
        return bitmaps;
    }
}