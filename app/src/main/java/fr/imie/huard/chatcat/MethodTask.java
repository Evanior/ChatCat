package fr.imie.huard.chatcat;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;

/**
 * Created by huard.cdi04 on 04/05/2017.
 */

public class MethodTask extends AsyncTask<URL, Integer, String> {

    private MainActivity activity;
    private String methode;

    public MethodTask(MainActivity activity, String methode) {
        super();
        this.activity = activity;
        this.methode = methode;
    }

    @Override
    protected String doInBackground(URL... params) {
        for (URL u : params) {
            try {
                String s = MainActivity.methodResponseFromHttpUrl(u,methode);
                return s;
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
