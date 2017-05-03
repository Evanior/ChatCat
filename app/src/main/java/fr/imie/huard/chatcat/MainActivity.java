package fr.imie.huard.chatcat;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CoordinatorLayout coordinatorLayout;
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private  EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editText = (EditText) findViewById(R.id.edit);

        listView = (ListView) findViewById(R.id.list);
        if(adapter == null){
            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        }

        listView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
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

        Snackbar.make(coordinatorLayout, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_settings:

                return true;
            case R.id.action_search:

                return true;
            case R.id.action_delete:

                return true;
            case R.id.action_refresh:

                return true;
            case R.id.action_about:

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view){
        int id = view.getId();
        switch (id) {
            case R.id.fab:
                adapter.add(editText.getText().toString());
                listView.setAdapter(adapter);
                editText.setText("");
                break;
        }
    }
}
