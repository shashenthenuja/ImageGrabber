package edu.curtin.imagegrabber;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class ImageUpload {
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    Context context;
    public ImageUpload(Context context) {
        this.context = context;
    }

    // upload image to firebase
    public void uploadImage(Bitmap image) {
        StorageReference storageReference = firebaseStorage.getReferenceFromUrl("gs://imagegrabber-6977a.appspot.com");

        StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());

        byte[] file = convertBitmap(image);

        Toast.makeText(context, "Uploading Image...", Toast.LENGTH_SHORT).show();
        UploadTask uploadTask = ref.putBytes(file);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Failed to upload!", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(context, "Uploaded Successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // convert bitmap image to byte array
    public byte[] convertBitmap(Bitmap image) {
        Bitmap bitmap = image;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

}
