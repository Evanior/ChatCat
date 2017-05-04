package fr.imie.huard.chatcat;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;

/**
 * Created by huard.cdi04 on 04/05/2017.
 */

public class MethodPostTask extends AsyncTask<URL, Integer, String> {

    private MainActivity activity;

    public MethodPostTask(MainActivity activity) {
        super();
        this.activity = activity;
    }

    @Override
    protected String doInBackground(URL... params) {
        for (URL u : params) {
            try {
                return MainActivity.postResponseFromHttpUrl(u);
            }catch (IOException e){
                e.printStackTrace();
                return "error";
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        activity.refresh();// FIXME: 04/05/2017 
        super.onPostExecute(s);
    }
}
