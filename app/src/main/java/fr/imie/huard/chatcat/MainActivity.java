package fr.imie.huard.chatcat;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

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
        listRecyclable.setLayoutManager(linearLayoutManager);

        listRecyclable.setHasFixedSize(false);
        adapterRecyclable = new MessageRecycleAdapter();
        listRecyclable.setAdapter(adapterRecyclable);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

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
                /*adapter.add(editText.getText().toString());
                listView.setAdapter(adapter);
                editText.setText("");*/
                DateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                Uri contructionURI = Uri.parse("http://10.2.6.30:8080").buildUpon()
                        .appendPath("ChatCat").appendPath("message")
                        .appendQueryParameter("pseudo","Tim")
                        .appendQueryParameter("date",dateFormater.format(new Date()))
                        .appendQueryParameter("message",editText.getText().toString()).build();
                editText.setText("");

                downloadNewPosts = new MethodPostTask(this);

                try {
                    URL urlFinal = new URL(contructionURI.toString());
                    URL[] urls = {urlFinal};
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

    public void refresh(){
        adapter.clear();
        listView.setAdapter(adapter);
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

    public static String MethodResponseFromHttpUrl(URL url, String methode) throws IOException {
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


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
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

    public static String postResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
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
}

