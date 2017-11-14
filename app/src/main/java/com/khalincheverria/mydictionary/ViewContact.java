package com.khalincheverria.mydictionary;


import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.khalincheverria.mydictionary.Model.Contact;


public class ViewContact extends AppCompatActivity {

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);
        ActionBar actionBar= getSupportActionBar();
        TextView contactName;
        TextView address;
        Contact contact;
        Bundle bundle=getIntent().getExtras();
        contact =(Contact)bundle.getSerializable("Contacts");

        actionBar.setTitle(contact.getName().toString());

        NestedScrollView scrollView=findViewById(R.id.contact_scrollview);
        scrollView.setFillViewport(true);

        contactName=findViewById(R.id.contact);
        address=findViewById(R.id.address);

        contactName.setText(contact.getName().toString());
        address.setText(contact.getAddress());



    }
}
