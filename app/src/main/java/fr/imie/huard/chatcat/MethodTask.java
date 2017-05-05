package fr.imie.huard.chatcat;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * Created by huard.cdi04 on 04/05/2017.
 */

public class MethodTask extends AsyncTask<URL, Integer, String> {

    private MainActivity activity;
    private String methode;
    private HashMap<String,String> parametre;

    public MethodTask(MainActivity activity, String methode) {
        super();
        this.parametre = new HashMap<>();
        this.activity = activity;
        this.methode = methode;
    }

    public MethodTask(MainActivity activity, String methode, HashMap<String, String> parametre) {
        this.activity = activity;
        this.methode = methode;
        this.parametre = parametre;
    }

    public HashMap<String, String> getParametre() {
        return parametre;
    }

    public void setParametre(HashMap<String, String> parametre) {
        this.parametre = parametre;
    }

    @Override
    protected String doInBackground(URL... params) {
        for (URL u : params) {
            //try {
                String s = MainActivity.performPostCall(u,methode,parametre);
                return s;
            /*}catch (IOException e){
                e.printStackTrace();
                return "error";
            }*/
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        try{
            JSONObject msg = new JSONObject(s);
            DateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Message m = new Message(msg.getLong("id"), msg.getString("pseudo"), dateFormater.parse(msg.getString("date")), msg.getString("message"));
            activity.getAdapterRecyclable().addMessages(m);
            activity.getAdapterRecyclable().notifyDataSetChanged();
            activity.getListRecyclable().setAdapter(activity.getAdapterRecyclable());
        }catch (JSONException jsonError){
            jsonError.printStackTrace();
        }catch (ParseException p){
            p.printStackTrace();
        }
        super.onPostExecute(s);
    }
}
