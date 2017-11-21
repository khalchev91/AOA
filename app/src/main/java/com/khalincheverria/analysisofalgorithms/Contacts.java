package com.khalincheverria.analysisofalgorithms;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.khalincheverria.analysisofalgorithms.Adapter.SearchTabPagerAdapter;
import com.khalincheverria.analysisofalgorithms.BinaryTree.BinaryTree;
import com.khalincheverria.analysisofalgorithms.Model.Contact;
import com.khalincheverria.analysisofalgorithms.Model.Name;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Contacts extends AppCompatActivity{

    public static BinaryTree binaryTree= new BinaryTree();
    private Contact contact = new Contact();
    private String line = "";
    private ArrayList<String> linesFromFile = new ArrayList<>();
    TabLayout tabLayout;
    public void readLines(){

        binaryTree.clear();



        MyAsyncTask myAsyncTask = new MyAsyncTask();



        Thread readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream inputStream;
                inputStream = getResources().openRawResource(R.raw.samplecontacts);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                try {
                    while ((line = reader.readLine())!=null){
                        linesFromFile.add(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        readThread.run();

        myAsyncTask.execute();
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    readLines();
                }
            });
        thread.run();
        setContentView(R.layout.activity_words);





        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


           tabLayout  = findViewById(R.id.search_tabs);
            tabLayout.addTab(tabLayout.newTab().setText("Depth First Search"));
            tabLayout.addTab(tabLayout.newTab().setText("Breadth First Search"));
            tabLayout.addTab(tabLayout.newTab().setText("Search 3"));

            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            final ViewPager pager = findViewById(R.id.viewPager);
                    SearchTabPagerAdapter adapter = new SearchTabPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
            pager.setAdapter(adapter);
            pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    pager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

    }

@Override
public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
}

@Override
public boolean onOptionsItemSelected(MenuItem item){

    if(item.getItemId()==R.id.search_icon){

        final MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("Search by name")
                .customView(R.layout.search_view,false)
                .positiveText("Search")
                .negativeText("Cancel")
                .build();

        View view = dialog.getCustomView();
        View positiveAction = dialog.getActionButton(DialogAction.POSITIVE);


        final EditText firstName = view.findViewById(R.id.first_name);
        final EditText lastName = view.findViewById(R.id.last_name);


        if(firstName.getText() == null || lastName.getText()==null){
            positiveAction.setEnabled(false);
        }

        positiveAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name =firstName.getText().toString().trim()+" "+ lastName.getText().toString().trim();
                Intent intent= new Intent(Contacts.this, SearchActivity.class);
                intent.putExtra("Name",name);
                switch (tabLayout.getSelectedTabPosition()){
                    case 0:
                        intent.putExtra("tab","dst");
                        dialog.dismiss();
                        break;
                    case 1:
                        intent.putExtra("tab","bst");
                        dialog.dismiss();
                        break;
                    case 2:
                        //TODO add next search algorithm
                        dialog.dismiss();
                        break;
                    default:
                        intent.putExtra("tab","none");
                        break;
                }
                startActivity(intent);

            }
        });

        dialog.show();


    }

    return super.onOptionsItemSelected(item);
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
    public void onStop(){
        super.onStop();
    }

    public static BinaryTree getBinaryTree() {
        return binaryTree;
    }


    @SuppressLint("StaticFieldLeak")
    class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
        boolean running = true;
        ProgressDialog progressDialog;
        @Override
        protected Void doInBackground(Void... params) {
            int count = 0;
            while (count < linesFromFile.size()) {
                String[] contacts = linesFromFile.get(count).split(",");
                if (contacts.length == 3) {
                    contact.setName(new Name(contacts[0], contacts[1]));
                    contact.setAddress(contacts[2]);
                    binaryTree.insert(new Contact(contact));
                    //TODO add other data structures here
                }
                publishProgress(count);
                count++;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setMessage("Loading contact: " + String.valueOf(values[0]) + "/" + linesFromFile.size());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            running = true;
            progressDialog = ProgressDialog.show(Contacts.this, "", "Loading Contacts");
            progressDialog.setCanceledOnTouchOutside(false);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }
    }


}
