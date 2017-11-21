package com.khalincheverria.analysisofalgorithms;

import android.os.Bundle;
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

import com.khalincheverria.analysisofalgorithms.Adapter.FoundContactAdapter;
import com.khalincheverria.analysisofalgorithms.BinaryTree.BinaryTree;
import com.khalincheverria.analysisofalgorithms.Model.Contact;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    BinaryTree binaryTree = Contacts.binaryTree;
    RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    FoundContactAdapter contactAdapter;

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
                binaryTree.depthFirstSearch(name);
                ArrayList<Contact> contacts =  BinaryTree.getContacts();
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
