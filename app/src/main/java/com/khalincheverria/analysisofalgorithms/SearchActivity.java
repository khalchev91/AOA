package com.khalincheverria.analysisofalgorithms;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.khalincheverria.analysisofalgorithms.BinaryTree.BinaryTree;
import com.khalincheverria.analysisofalgorithms.Model.Contact;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    BinaryTree binaryTree = Contacts.binaryTree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        String name = getIntent().getStringExtra("Name");
        String tab = getIntent().getStringExtra("tab");


        assert tab!=null;
        switch (tab){
            case "dst":
            displayAllContactsFound(name);
        }

    }

    private void displayAllContactsFound(String name){
        binaryTree.depthFirstSearch(name);
        ArrayList<Contact> contacts =  BinaryTree.getContacts();
        for (Contact contact : contacts){
            Log.d("name",contact.getName().toString());
        }


    }

}
