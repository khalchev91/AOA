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
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.khalincheverria.mydictionary.BinaryTree.BinaryTree;
import com.khalincheverria.mydictionary.LinkedList.LinkedList;
import com.khalincheverria.mydictionary.Model.Contact;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;



    @SuppressWarnings({"deprecation", "ConstantConditions"})
public class Contacts extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static LinkedList wordList= new LinkedList();
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
            wordList.clear();

        myAsyncTask = new MyAsyncTask();
        InputStream inputStream;
        try{
            if(uri!=null) {
                inputStream = getContentResolver().openInputStream(uri);
            }else {
                inputStream=getResources().openRawResource(R.raw.wb1913_samp260);
            }
            final BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
        /*
        loadingDialog=new ProgressDialog(Contacts.this);
        loadingDialog.setMessage("Loading Contacts");
        loadingDialog.setIndeterminate(true);
        loadingDialog.setCancelable(false);
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingDialog.show();
        */
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

 /*
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int count = 0;
                        while (count < linesFromFile.size()) {
                            String[] words = linesFromFile.get(count).split("\t");
                            if (words.length == 3) {
                                if (words[0].charAt(0) == '-') {
                                    char[] letters = words[0].toCharArray();
                                    letters[0] = ' ';
                                    words[0] = String.valueOf(letters);
                                }
                                words[1] = words[1].replaceAll("[.]", "");
                                Log.d("WORD", words[0]);
                                contact.setContact(words[0].trim().toLowerCase());
                                contact.setPartOfSpeech(words[1]);
                                contact.setDefinition(words[2]);
                                if (isTree) {
                                    binaryTree.insert(new Contacts(contact));
                                } else {
                                    wordList.insert(ne!w Contacts(contact));
                                }
                            }
                            count++;
                        }
                        handler.sendEmptyMessage(0);
                }
            }).start();*/

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
                 String[] words = linesFromFile.get(count).split(",");
                if (words.length == 3) {
                    Log.d("WORD", words[0]);
                    if (isTree) {
                        binaryTree.insert(new Contact(contact));
                    } else {
                        wordList.insert(new Contact(contact));
                    }
                }
                publishProgress(count);
                Log.d("Count","Number: "+count+" out of "+linesFromFile.size());

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

        isTree=getIntent().getExtras().getBoolean("BinaryTree");

        loadFiles();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CoordinatorLayout coordinatorLayout= (CoordinatorLayout)findViewById(R.id.main_layout);



        floatingActionMenu=(FloatingActionMenu)findViewById(R.id.fab_menu);

        addWord=(FloatingActionButton)findViewById(R.id.new_word);


        if(isTree) {
            DisplayWords displayWords = new DisplayWords();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            getSupportActionBar().setTitle("All Contacts");
            Bundle bundle = new Bundle();
            bundle.putBoolean("Tree",true);
            displayWords.setArguments(bundle);
            fragmentTransaction.replace(R.id.frame_layout, displayWords, "DISPLAY_WORDS");
            fragmentTransaction.commit();
            Snackbar.make(coordinatorLayout,"Number of words: "+binaryTree.count(),Snackbar.LENGTH_SHORT).setAction("Contacts",null).show();
        }else {
            DisplayWords displayWords = new DisplayWords();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            getSupportActionBar().setTitle("All Contacts");
            Bundle bundle = new Bundle();
            bundle.putBoolean("Tree",false);
            displayWords.setArguments(bundle);
            fragmentTransaction.replace(R.id.frame_layout, displayWords, "DISPLAY_WORDS");
            fragmentTransaction.commit();
            Snackbar.make(coordinatorLayout,"Number of words: "+wordList.getSizeOfList(),Snackbar.LENGTH_SHORT).setAction("Contacts",null).show();
        }






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        addWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Contacts.this,AddWord.class);
                startActivityForResult(intent,1);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data){
        if(requestCode==1){
            if(resultCode== Activity.RESULT_OK){

                contact = (com.khalincheverria.mydictionary.Model.Contact)data.getExtras().getSerializable("NewWord");
                if(isTree){
                    long start = System.nanoTime();
                    binaryTree.insert(new com.khalincheverria.mydictionary.Model.Contact(contact));
                    long end=System.nanoTime();
                    double duration = (double)(end - start)/1000000000;
                    Toast.makeText(Contacts.this, String.format("That took: %.4f seconds",duration), Toast.LENGTH_SHORT).show();
                }else {
                    long start = System.nanoTime();
                    wordList.addWord(new com.khalincheverria.mydictionary.Model.Contact(contact));
                    long end=System.nanoTime();
                    double duration = (double)(end - start)/1000000000;
                    Toast.makeText(Contacts.this, String.format("That took: %.4f seconds",duration), Toast.LENGTH_SHORT).show();
                }
            }
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu=menu;
        getMenuInflater().inflate(R.menu.main, menu);
        if(isTree) {
            MenuItem menuItem = this.menu.findItem(R.id.sort_list);
            menuItem.setVisible(false);
        }
        return true;
    }


    @SuppressLint("DefaultLocale")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.search_icon){

            if(isTree){
                Search.binaryTree=binaryTree;
            }else {
                Search.linkedList= wordList;
            }
            Intent intent= new Intent(this,Search.class);
            intent.putExtra("Tree",isTree);
                startActivity(intent);
        }else if(id==R.id.type_sentence){
            if(isTree) {
                TypeSentence.binaryTree=binaryTree;
            }else {
                TypeSentence.wordList=wordList;
            }
            Intent intent= new Intent(this,TypeSentence.class);
            intent.putExtra("Tree",isTree);
            startActivityForResult(intent,3);
        }else if(id==R.id.sort_list){
            loadingDialog=new ProgressDialog(Contacts.this);
            loadingDialog.setMessage("Sorting words...");
            loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            loadingDialog.setIndeterminate(true);
            loadingDialog.show();
            start=System.nanoTime();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    wordList.sortList(wordList);
                    handler.sendEmptyMessage(0);
                }
            }).start();

        }



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        ActionBar actionBar= getSupportActionBar();
        if(id==R.id.displayWords) {
            if(isTree) {
                DisplayWords displayWords = new DisplayWords();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                if (actionBar != null) {
                    actionBar.setTitle("All Contacts");
                }
                Bundle bundle = new Bundle();
                bundle.putBoolean("Tree",true);
                displayWords.setArguments(bundle);
                fragmentTransaction.replace(R.id.frame_layout, displayWords, "DISPLAY_WORDS");

                fragmentTransaction.commit();
            }else {
                DisplayWords displayWords = new DisplayWords();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                if (actionBar != null) {
                    actionBar.setTitle("All Contacts");
                }
                Bundle bundle = new Bundle();
                bundle.putBoolean("Tree",false);
                displayWords.setArguments(bundle);
                fragmentTransaction.replace(R.id.frame_layout, displayWords, "DISPLAY_WORDS");
                fragmentTransaction.commit();
            }
        }
            else if(id==R.id.load_file){
            int PICKFILE_RESULT_CODE=2;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("text/*");
            startActivityForResult(intent,PICKFILE_RESULT_CODE);
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

    public static LinkedList getWordList() {
        return wordList;
    }
}
