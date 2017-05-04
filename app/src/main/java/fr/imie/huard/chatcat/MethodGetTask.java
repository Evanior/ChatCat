package fr.imie.huard.chatcat;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

/**
 * Created by huard.cdi04 on 04/05/2017.
 */

public class MethodGetTask extends AsyncTask<URL, Integer, String> {

    private MainActivity activity;

    public MethodGetTask(MainActivity activity) {
        super();
        this.activity = activity;
    }

    @Override
    protected String doInBackground(URL... params) {
        for (URL u : params) {
            try {
                return MainActivity.getResponseFromHttpUrl(u);
            }catch (IOException e){
                e.printStackTrace();
                return "error";
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        try{
            JSONArray tablMessage = new JSONArray(s);
            for (int i = 0; i < tablMessage.length(); i++) {
                JSONObject msg = tablMessage.getJSONObject(i);
                Iterator<?> keys = msg.keys();
                /*while (keys.hasNext()){
                    String key = (String) keys.next();
                    Message m = new Message()
                    //Toast.makeText(activity.getApplicationContext(), msg.getString(key), Toast.LENGTH_SHORT).show();
                }*/
                DateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                Message m = new Message(msg.getString("pseudo"),dateFormater.parse(msg.getString("date")),msg.getString("message"));
                activity.getAdapter().add(m);
            }
            activity.getListView().setAdapter(activity.getAdapter());
        }catch (JSONException jsonError){
            jsonError.printStackTrace();
        }catch (ParseException p){
            p.printStackTrace();
        }
        //Toast.makeText(activity.getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        super.onPostExecute(s);
    }
}
