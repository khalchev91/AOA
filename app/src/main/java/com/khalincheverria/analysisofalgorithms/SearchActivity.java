package com.khalincheverria.analysisofalgorithms;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.khalincheverria.analysisofalgorithms.Adapter.FoundContactAdapter;
import com.khalincheverria.analysisofalgorithms.BinaryTree.BinaryTree;
import com.khalincheverria.analysisofalgorithms.Model.Contact;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    BinaryTree binaryTree = Contacts.binaryTree;
    RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    FoundContactAdapter contactAdapter;
    ArrayList<Contact> contacts = new ArrayList<>();
    private long start;
    private long end;
    private double duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView= findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        String name = getIntent().getStringExtra("Name");
        String tab = getIntent().getStringExtra("tab");


        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        assert tab!=null;
        switch (tab){
            case "dst":
                start = System.nanoTime();
                binaryTree.depthFirstSearch(name);
                contacts =  BinaryTree.getContacts();
                end = System.nanoTime();
                duration = (double)(end - start)/1000000000;
                Toast.makeText(SearchActivity.this, String.format("That took: %.5f seconds", duration), Toast.LENGTH_SHORT).show();

                if(contacts.size()==0){
                    MaterialDialog dialog = new MaterialDialog.Builder(this)
                            .title("Not found")
                            .content("\""+name+"\" not found in the list")
                            .positiveText("OK")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                        SearchActivity.this.finish();
                                }
                            })
                            .canceledOnTouchOutside(false)
                            .cancelable(false)
                            .build();



                    dialog.show();

                }
                contactAdapter= new FoundContactAdapter(contacts);
                recyclerView.setAdapter(contactAdapter);
                contactAdapter.notifyDataSetChanged();

                break;
            case "bst":
                long start = System.nanoTime();
                binaryTree.depthFirstSearch(name);
                contacts =  BinaryTree.getContacts();
                long end=System.nanoTime();
                double duration = (double)(end - start)/1000000000;
                Toast.makeText(SearchActivity.this, String.format("That took: %.5f seconds",duration), Toast.LENGTH_SHORT).show();
                if(contacts.size()==0){
                    MaterialDialog dialog = new MaterialDialog.Builder(this)
                            .title("Not found")
                            .content("\""+name+"\" not found in the list")
                            .positiveText("OK")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                    SearchActivity.this.finish();
                                }
                            })
                            .canceledOnTouchOutside(false)
                            .cancelable(false)
                            .build();



                    dialog.show();

                }
                contactAdapter= new FoundContactAdapter(contacts);
                recyclerView.setAdapter(contactAdapter);
                contactAdapter.notifyDataSetChanged();

        }

    }

    private void displayAllContactsFound(String name){
        binaryTree.depthFirstSearch(name);
        ArrayList<Contact> contacts =  BinaryTree.getContacts();
        contactAdapter= new FoundContactAdapter(contacts);

    }



}
