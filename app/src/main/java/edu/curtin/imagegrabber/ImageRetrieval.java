package edu.curtin.imagegrabber;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class ImageRetrieval implements Callable<ArrayList<Bitmap>> {

    private Activity uiActivity;
    private String data;
    private RemoteUtils remoteUtils;
    public ImageRetrieval(Activity uiActivity) {
        remoteUtils = RemoteUtils.getInstance(uiActivity);
        this.uiActivity=uiActivity;
        this.data = null;
    }

    @Override
    public ArrayList<Bitmap> call() throws Exception {
        ArrayList<Bitmap> image = new ArrayList<>();
        ArrayList<String> endpoint = getEndpoint(this.data);
        if(endpoint==null){
            uiActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(uiActivity,"No image found",Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            image = getImageFromUrl(endpoint);
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
            }

        }
        return image;
    }

    private ArrayList<String> getEndpoint(String data){
        ArrayList<String> imageUrl = new ArrayList<>();
        try {
            JSONObject jBase = new JSONObject(data);
            JSONArray jHits = jBase.getJSONArray("hits");
            // check if result has more than 15 image urls
            if(jHits.length() >= 15){
                for (int i = 0; i < 15; i++) {
                    JSONObject jHitsItem = jHits.getJSONObject(i);
                    imageUrl.add(jHitsItem.getString("previewURL"));
                }
            }else if(jHits.length() > 0){
                // if less than 15 add currently found image urls
                for (int i = 0; i < jHits.length(); i++) {
                    JSONObject jHitsItem = jHits.getJSONObject(i);
                    imageUrl.add(jHitsItem.getString("previewURL"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return imageUrl;
    }

    // add bitmap images from image urls
    private ArrayList<Bitmap> getImageFromUrl(ArrayList<String> imageUrl){
        ArrayList<Bitmap> image = new ArrayList<>();
        for (String s:imageUrl
             ) {
            Uri.Builder url = Uri.parse(s).buildUpon();
            String urlString = url.build().toString();
            HttpURLConnection connection = remoteUtils.openConnection(urlString);
            if(connection!=null){
                if(remoteUtils.isConnectionOkay(connection)==true){
                    image.add(getBitmapFromConnection(connection));
                    connection.disconnect();
                }
            }
        }
        return image;
    }

    public Bitmap getBitmapFromConnection(HttpURLConnection conn){
        Bitmap data = null;
        try {
            InputStream inputStream = conn.getInputStream();
            byte[] byteData = getByteArrayFromInputStream(inputStream);
            data = BitmapFactory.decodeByteArray(byteData,0,byteData.length);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return data;
    }

    private byte[] getByteArrayFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[4096];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }

    public void setData(String data) {
        this.data = data;
    }
}
