package com.khalincheverria.analysisofalgorithms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.khalincheverria.analysisofalgorithms.Adapter.FoundContactAdapter;
import com.khalincheverria.analysisofalgorithms.BinaryTree.BinaryTree;
import com.khalincheverria.analysisofalgorithms.Model.Contact;

import java.util.ArrayList;

public class KMPSearchActivity extends AppCompatActivity {

    BinaryTree binaryTree = Contacts.binaryTree;
    ArrayList<Contact> contacts = new ArrayList<>();
    FoundContactAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kmpsearch);
        binaryTree = Contacts.binaryTree;
        EditText lastName = findViewById(R.id.search_last_name);
        final TextView notFound = findViewById(R.id.not_found);
        recyclerView = findViewById(R.id.kmp_found_contacts);
        adapter = new FoundContactAdapter(contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter = new FoundContactAdapter(contacts);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                contacts.clear();
                if(charSequence.length()>=3){
                   contacts =  binaryTree.knuthMorrisPratt(charSequence.toString());
                   if(contacts.size()==0){
                    recyclerView.setVisibility(View.INVISIBLE);
                    notFound.setVisibility(View.VISIBLE);
                   }else {
                       recyclerView.setVisibility(View.VISIBLE);
                       notFound.setVisibility(View.INVISIBLE);
                       recyclerView.setAdapter(new FoundContactAdapter(contacts));
                   }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                contacts.clear();
                if(editable.toString().length()>=3){
                    contacts =  binaryTree.knuthMorrisPratt(editable.toString());
                    if(contacts.size()==0){
                        recyclerView.setVisibility(View.INVISIBLE);
                        notFound.setVisibility(View.VISIBLE);
                    }else {
                        recyclerView.setVisibility(View.VISIBLE);
                        notFound.setVisibility(View.INVISIBLE);
                        recyclerView.setAdapter(new FoundContactAdapter(contacts));

                    }
                }
            }
        });


    }
}
