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
                return MainActivity.methodResponseFromHttpUrl(u,"GET");
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
                DateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Message m = new Message(msg.getLong("id"), msg.getString("pseudo"), dateFormater.parse(msg.getString("date")), msg.getString("message"));
                activity.getAdapterRecyclable().addMessages(m);
                activity.getAdapterRecyclable().notifyDataSetChanged();
            }
        }catch (JSONException jsonError){
            jsonError.printStackTrace();
        }catch (ParseException p){
            p.printStackTrace();
        }
        activity.getListRecyclable().scrollToPosition(activity.getAdapterRecyclable().getItemCount());
        super.onPostExecute(s);
    }
}
