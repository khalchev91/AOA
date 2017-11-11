package com.khalincheverria.mydictionary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.khalincheverria.mydictionary.BinaryTree.BinaryTree;
import com.khalincheverria.mydictionary.Model.Contact;
import com.khalincheverria.mydictionary.Model.Name;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


    @SuppressWarnings({"deprecation", "ConstantConditions"})
public class Contacts extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static BinaryTree binaryTree= new BinaryTree();
    private com.khalincheverria.mydictionary.Model.Contact contact = new com.khalincheverria.mydictionary.Model.Contact();
    private  Uri uri=null;
    private String line;
    private boolean isTree;

    private ArrayList<String>linesFromFile= new ArrayList<>();
    MyAsyncTask myAsyncTask;


    public Menu menu;

    public FloatingActionButton addWord;

    public FloatingActionMenu floatingActionMenu;

public ProgressDialog loadingDialog;

    public long end;
public long start;
    double duration;
    @SuppressLint("HandlerLeak")
    private Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg){
            loadingDialog.dismiss();
            end=System.nanoTime();
            duration = (double)(end - start)/1000000000;
            Toast.makeText(Contacts.this, String.format("That took: %.4f seconds",duration), Toast.LENGTH_SHORT).show();
        }
    };

    @SuppressLint("DefaultLocale")
    public void loadFiles() {
            binaryTree.clear();

        myAsyncTask = new MyAsyncTask();
        InputStream inputStream;
        try{
            if(uri!=null) {
                inputStream = getContentResolver().openInputStream(uri);
            }else {
                inputStream=getResources().openRawResource(R.raw.samplecontacts);
            }
            final BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));

        start = System.nanoTime();


            Thread readThread= new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while ((line = reader.readLine()) != null) {
                        linesFromFile.add(line);
                    }
                }catch (IOException io){
                    io.printStackTrace();
                }
            }
        });

            readThread.run();

myAsyncTask.execute();

    } catch (IOException exc){
            exc.printStackTrace();
        }

    }

    class MyAsyncTask extends AsyncTask<Void,Integer,Void>{
      boolean running=true;
        ProgressDialog progressDialog;

        @Override
        protected Void doInBackground(Void... params){
            int count=0;
            while (count<linesFromFile.size()){
                 String[] contacts = linesFromFile.get(count).split(",");
                if (contacts.length == 3) {
                    contact.setName(new Name(contacts[0],contacts[1]));
                    contact.setAddress(contacts[2]);
                    binaryTree.insert(new Contact(contact));
                }
                publishProgress(count);
                count++;
            }
            return  null;
        }
        @Override
        protected void onProgressUpdate(Integer... values){
            super.onProgressUpdate(values);
            progressDialog.setMessage("Loading contact: "+String.valueOf(values[0])+"/"+linesFromFile.size());
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            running = true;
            progressDialog = ProgressDialog.show(Contacts.this, "", "Loading Contacts");
            progressDialog.setCanceledOnTouchOutside(false);

        }
        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_words);

        loadFiles();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CoordinatorLayout coordinatorLayout= findViewById(R.id.main_layout);


            DisplayWords displayWords = new DisplayWords();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            getSupportActionBar().setTitle("All Contacts");
            fragmentTransaction.replace(R.id.frame_layout, displayWords, "DISPLAY_WORDS");
            fragmentTransaction.commit();
            Snackbar.make(coordinatorLayout,"Number of contacts: "+binaryTree.count(),Snackbar.LENGTH_SHORT).setAction("Contacts",null).show();



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data){
        if(requestCode==1){
            if(resultCode== Activity.RESULT_OK){
                contact = (Contact) data.getExtras().getSerializable("NewWord");
                long start = System.nanoTime();
                binaryTree.insert(new Contact(contact));
                long end=System.nanoTime();
                double duration = (double)(end - start)/1000000000;
                Toast.makeText(Contacts.this, String.format("That took: %.4f seconds",duration), Toast.LENGTH_SHORT).show();                }
            }
        if(requestCode==2) {
            if (resultCode == Activity.RESULT_OK) {
                uri=data.getData();
                linesFromFile.clear();
                loadFiles();
            }
        }
        if(requestCode==3){
            if(resultCode==Activity.RESULT_OK){
                isTree=data.getExtras().getBoolean("Tree");
            }
        }
    }
    @Override
    public void onPause(){
        super.onPause();
    }
    @Override
    public void onResume(){
        super.onResume();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        ActionBar actionBar= getSupportActionBar();
        if(id==R.id.displayWords) {
                DisplayWords displayWords = new DisplayWords();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                if (actionBar != null) {
                    actionBar.setTitle("All Contacts");
                }
                Bundle bundle = new Bundle();
                displayWords.setArguments(bundle);
                fragmentTransaction.replace(R.id.frame_layout, displayWords, "DISPLAY_WORDS");
                fragmentTransaction.commit();
        }
            else if(id==R.id.load_file){
            int PICKFILE_RESULT_CODE=2;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("text/*");
            startActivityForResult(intent,PICKFILE_RESULT_CODE);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    public static BinaryTree getBinaryTree() {
        return binaryTree;
    }

}
