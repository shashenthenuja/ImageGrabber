package edu.curtin.imagegrabber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    EditText searchInput;
    Button loadImage;
    ProgressBar loadBar;
    ArrayList<Bitmap> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchInput = findViewById(R.id.inputSearch);
        loadImage = findViewById(R.id.loadImageBtn);
        loadBar = findViewById(R.id.progressBar);
        loadBar.setVisibility(View.INVISIBLE);

        loadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!searchInput.getText().toString().isEmpty()) {
                    /*Intent intent = new Intent(MainActivity.this, ImageDisplay.class);
                    intent.putExtra("search", searchInput.getText().toString());
                    startActivity(intent);*/
                    searchImage();
                }else {
                    searchInput.setError("Enter text to search!");
                }
            }
        });
    }

    public void searchImage() {
        Toast.makeText(MainActivity.this, "Searching starts", Toast.LENGTH_SHORT).show();
        loadBar.setVisibility(View.VISIBLE);
        SearchImage searchTask = new SearchImage(MainActivity.this);
        searchTask.setSearchkey(searchInput.getText().toString());
        Single<String> searchObservable = Single.fromCallable(searchTask);
        searchObservable = searchObservable.subscribeOn(Schedulers.io());
        searchObservable = searchObservable.observeOn(AndroidSchedulers.mainThread());
        searchObservable.subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onSuccess(@NonNull String s) {
                Toast.makeText(MainActivity.this, "Searching Ends", Toast.LENGTH_SHORT).show();
                loadBar.setVisibility(View.INVISIBLE);
                loadImage(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(MainActivity.this, "Searching Error", Toast.LENGTH_SHORT).show();
                loadBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void loadImage(String response) {
        ImageRetrieval imageRetrievalTask = new ImageRetrieval(MainActivity.this);
        imageRetrievalTask.setData(response);

        Toast.makeText(MainActivity.this, "Image loading starts", Toast.LENGTH_SHORT).show();
        loadBar.setVisibility(View.VISIBLE);
        Single<ArrayList<Bitmap>> searchObservable = Single.fromCallable(imageRetrievalTask);
        searchObservable = searchObservable.subscribeOn(Schedulers.io());
        searchObservable = searchObservable.observeOn(AndroidSchedulers.mainThread());
        searchObservable.subscribe(new SingleObserver<ArrayList<Bitmap>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onSuccess(@NonNull ArrayList<Bitmap> bitmap) {
                //Toast.makeText(MainActivity.this, "Image loading Ends", Toast.LENGTH_SHORT).show();
                loadBar.setVisibility(View.INVISIBLE);
                images = bitmap;
                sendData(bitmap);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(MainActivity.this, "Image loading error, search again", Toast.LENGTH_SHORT).show();
                loadBar.setVisibility(View.INVISIBLE);
            }
        });

        /*Intent intent = new Intent(this, ImageDisplay.class);
        intent.putExtra("list", searchObservable.blockingGet());
        startActivity(intent);*/

    }

    public void sendData(ArrayList<Bitmap> images) {
        ArrayList<byte[]> list = new ArrayList<>();
        for (Bitmap b:images
             ) {
            Bitmap bitmap = b;
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
            byte[] byteArray = stream.toByteArray();
            list.add(byteArray);
        }
        if (list.size() > 0 ) {
            Intent intent = new Intent(this, ImageDisplay.class);
            intent.putExtra("list", list);
            startActivity(intent);
        }
    }

}