package edu.curtin.imagegrabber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ImageDisplay extends AppCompatActivity {

    Button listView;
    Button gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        listView = findViewById(R.id.listBtn);
        gridView = findViewById(R.id.gridBtn);

        ArrayList<byte[]> images = (ArrayList<byte[]>) getIntent().getSerializableExtra("list");
        ArrayList<Bitmap> conImages = convertImage(images);

        FragmentManager headerFrag = getSupportFragmentManager();
        ListView listImages = (ListView) headerFrag.findFragmentById(R.id.fragLayout);
        if (listImages == null) {
            listImages = new ListView(conImages);
            headerFrag.beginTransaction().add(R.id.fragLayout, listImages).commit();
        }

        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager headerFrag = getSupportFragmentManager();
                ListView listImages = (ListView) headerFrag.findFragmentById(R.id.fragLayout);
                if (listImages == null) {
                    listImages = new ListView(conImages);
                    headerFrag.beginTransaction().add(R.id.fragLayout, listImages).commit();
                }
            }
        });


    }

    /*FragmentManager headerFrag = getSupportFragmentManager();
                ListView view = (ListView) headerFrag.findFragmentById(R.id.header);
                if (mainHeader == null) {
                    mainHeader = new MainHeader(resList);
                    headerFrag.beginTransaction().add(R.id.header, mainHeader).commit();
                }*/

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