package com.khalincheverria.mydictionary;


import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.khalincheverria.mydictionary.Model.Contact;


public class ViewWord extends AppCompatActivity {

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_word);
        ActionBar actionBar= getSupportActionBar();
        TextView definition;
        TextView wordText;
        TextView partOfSpeech;
        Contact contact;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle=getIntent().getExtras();
        contact =(Contact)bundle.getSerializable("Contacts");

        actionBar.setTitle(contact.getWord());
        wordText=(TextView)findViewById(R.id.contact);
        partOfSpeech=(TextView)findViewById(R.id.partOfSpeech);
        definition=(TextView)findViewById(R.id.definition);

        wordText.setText(contact.getWord());
        partOfSpeech.setText(contact.getPartOfSpeech());
        definition.setText(contact.getDefinition());



    }
}
