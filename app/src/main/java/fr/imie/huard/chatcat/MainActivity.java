package fr.imie.huard.chatcat;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import static fr.imie.huard.chatcat.R.id.fab;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //private CoordinatorLayout coordinatorLayout;
    //private RequestQueue queue = Volley.newRequestQueue(this);
    private MessageAdapter<Message> adapter;
    private ListView listView;

    private RecyclerView listRecyclable;
    private MessageRecycleAdapter adapterRecyclable;

    private EditText editText;
    private AsyncTask downloadNewPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editText = (EditText) findViewById(R.id.edit);

        /*listView = (ListView) findViewById(R.id.list);
        adapter = new MessageAdapter<Message>(this);

        listView.setAdapter(adapter);*/

        listRecyclable = (RecyclerView) findViewById(R.id.list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        listRecyclable.setLayoutManager(linearLayoutManager);

        listRecyclable.setHasFixedSize(false);
        adapterRecyclable = new MessageRecycleAdapter();
        listRecyclable.setAdapter(adapterRecyclable);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                adapterRecyclable.removeMessage(((MessageRecycleAdapter.MessageHolder) viewHolder).getIndex());
                adapterRecyclable.notifyItemRemoved(((MessageRecycleAdapter.MessageHolder) viewHolder).getIndex());
                deleteOneMessage(((MessageRecycleAdapter.MessageHolder) viewHolder).getId());
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(listRecyclable);

        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*Snackbar.make(coordinatorLayout, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();*/

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_settings:

                return true;
            case R.id.action_search:

                return true;
            case R.id.action_delete:

                return true;
            case R.id.action_refresh:
                refresh();
                return true;
            case R.id.action_about:

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case fab:
                DateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                /*Uri contructionURI = Uri.parse("http://10.2.6.30:8080").buildUpon()
                        .appendPath("ChatCat").appendPath("message")
                        .appendQueryParameter("pseudo","Tim")
                        .appendQueryParameter("date",dateFormater.format(new Date()))
                        .appendQueryParameter("message",editText.getText().toString()).build();*/
                Uri contructionURI = Uri.parse("http://10.2.6.30:8080").buildUpon()
                        .appendPath("ChatCat").appendPath("message").build();

                HashMap<String,String> parametre = new HashMap<>();
                parametre.put("pseudo", "Tim");
                parametre.put("message",editText.getText().toString());
                parametre.put("date", dateFormater.format(new Date()));

                downloadNewPosts = new MethodTask(this, "POST", parametre);

                //((MethodTask)downloadNewPosts).setParametre(parametre);

                editText.setText("");

                try {
                    URL urlFinal = new URL(contructionURI.toString());
                    URL[] urls = {urlFinal};
                    Log.i("url", urlFinal.toString());
                    downloadNewPosts.execute(urls);
                }catch (MalformedURLException error){
                    error.printStackTrace();
                }
                break;
        }
    }

    @Deprecated
    public ListView getListView() {
        return listView;
    }

    @Deprecated
    public MessageAdapter<Message> getAdapter() {
        return adapter;
    }

    public RecyclerView getListRecyclable() {
        return listRecyclable;
    }

    public MessageRecycleAdapter getAdapterRecyclable() {
        return adapterRecyclable;
    }

    public EditText getEditText() {
        return editText;
    }

    /**
     * permet de rechager la list
     */
    public void refresh(){
        //adapter.clear();
        //listView.setAdapter(adapter);
        adapterRecyclable.getMesMessages().clear();
        adapterRecyclable.notifyDataSetChanged();
        Uri contructionURI = Uri.parse("http://10.2.6.30:8080").buildUpon()
                .appendPath("ChatCat").appendPath("message").build();

        downloadNewPosts = new MethodGetTask(this);

        try {
            URL urlFinal = new URL(contructionURI.toString());
            URL[] urls = {urlFinal};
            downloadNewPosts.execute(urls);
        }catch (MalformedURLException error){
            error.printStackTrace();
        }
    }

    public void deleteOneMessage(long id){
        Uri contructionURI = Uri.parse("http://10.2.6.30:8080").buildUpon()
                .appendPath("ChatCat").appendPath("message")
                .appendPath(""+id).build();

        downloadNewPosts = new MethodTask(this,"DELETE");

        try {
            URL urlFinal = new URL(contructionURI.toString());
            URL[] urls = {urlFinal};
            downloadNewPosts.execute(urls);
        }catch (MalformedURLException error){
            error.printStackTrace();
        }
    }

    /**
     * Permet d'envoyer des requetes a un serveur
     * @param url du serveur
     * @param methode chaine de charactère pour selectionner la méthode
     * <UL>
     *  <LI>GET
     *  <LI>POST
     *  <LI>HEAD
     *  <LI>OPTIONS
     *  <LI>PUT
     *  <LI>DELETE
     *  <LI>TRACE
     * </UL> sont légal
     * @return la reponse du serveur
     * @throws IOException
     */
    public static String methodResponseFromHttpUrl(URL url, String methode) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod(methode);
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static String  performPostCall(URL url, String method,
                                          HashMap<String, String> postDataParams) {

        //URL url;
        String response = "";
        try {
            //url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod(method);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}

