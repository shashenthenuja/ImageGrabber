package edu.curtin.imagegrabber;

import android.app.Activity;
import android.net.Uri;

import java.net.HttpURLConnection;
import java.util.concurrent.Callable;

public class SearchImage implements Callable<String> {
    private String searchkey;
    private String baseUrl;
    private RemoteUtils remoteUtils;
    private Activity uiActivity;

    public SearchImage(Activity uiActivity) {
        this.searchkey = null;
        baseUrl ="https://pixabay.com/api/";
        remoteUtils = RemoteUtils.getInstance(uiActivity);
        this.uiActivity = uiActivity;
    }

    @Override
    public String call() throws Exception {
        String response=null;
        String endpoint = getSearchEndpoint();
        HttpURLConnection connection = remoteUtils.openConnection(endpoint);
        if(connection!=null){
            if(remoteUtils.isConnectionOkay(connection)==true){
                response = remoteUtils.getResponseString(connection);
                connection.disconnect();
                try {
                    Thread.sleep(3000);
                }
                catch (Exception e){

                }
            }
        }
        return response;
    }

    private String getSearchEndpoint(){
        String data = null;
        Uri.Builder url = Uri.parse(this.baseUrl).buildUpon();
        url.appendQueryParameter("key","23319229-94b52a4727158e1dc3fd5f2db");
        url.appendQueryParameter("q",this.searchkey);
        String urlString = url.build().toString();
        return urlString;
    }

    public void setSearchkey(String searchkey) {
        this.searchkey = searchkey;
    }
}
