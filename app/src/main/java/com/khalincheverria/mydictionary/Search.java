package com.khalincheverria.mydictionary;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.khalincheverria.mydictionary.BinaryTree.BinaryTree;
import com.khalincheverria.mydictionary.LinkedList.LinkedList;
import com.khalincheverria.mydictionary.Model.Contact;

import layout.SearchFragment;



public class Search extends AppCompatActivity implements View.OnClickListener{

    private EditText searchWordField;
    private Button searchButton;
    public  static LinkedList linkedList= new LinkedList();
    private boolean isTree;
    public static BinaryTree binaryTree;

    Contact contact;

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String searchWord="";

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setTitle("Search");


        isTree=getIntent().getExtras().getBoolean("Tree");

        searchWordField=(EditText)findViewById(R.id.search_field);
        searchButton=(Button)findViewById(R.id.search_word);
        searchButton.setEnabled(false);
        searchWordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equalsIgnoreCase("")){
                    searchButton.setEnabled(false);
                }else {
                    searchButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        searchButton.setOnClickListener(this);

    }
@SuppressLint("DefaultLocale")
@Override
    public void onClick(View view){



    searchWord=searchWordField.getText().toString().trim();
    if(isTree){
        long start=System.nanoTime();
        if(binaryTree.searchWord(searchWord)!=null) {
            setContact(binaryTree.searchWord(searchWord));
        }
        long end=System.nanoTime();
        double duration = (double)(end - start)/1000000000;
        Toast.makeText(Search.this, String.format("That took: %.5f seconds",duration), Toast.LENGTH_SHORT).show();

            if(contact !=null){
                int count=binaryTree.count;
                SearchFragment searchFragment=new SearchFragment();

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                Bundle bundle= new Bundle();
                bundle.putBoolean("Tree",true);
                bundle.putSerializable("Contacts", contact);
                bundle.putDouble("time",duration);
                bundle.putInt("position",count);
                searchFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.search_frame,searchFragment,"FOUND_WORD");
                fragmentTransaction.commit();


            }else {
            AlertDialog alertDialog=new AlertDialog.Builder(this).create();
                alertDialog.setMessage("Contacts " + "\""+searchWord +"\""+ " not found in the dictionary");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        }
    }else {
        long start = System.nanoTime();
        contact = linkedList.retrieve(searchWord);
        long end=System.nanoTime();
        double duration = (double)(end - start)/1000000000;
        Toast.makeText(Search.this, String.format("That took: %.5f seconds",duration), Toast.LENGTH_SHORT).show();

        if (contact != null) {
            int count =linkedList.count;
            SearchFragment searchFragment=new SearchFragment();


            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            Bundle bundle= new Bundle();

            bundle.putBoolean("Tree",false);
            bundle.putSerializable("Contacts", contact);
            bundle.putDouble("time",duration);
            bundle.putInt("position",count);
            searchFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.search_frame,searchFragment,"FOUND_WORD");
            fragmentTransaction.commit();


        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage("Contacts " + "\""+searchWord +"\""+ " not found in the dictionary");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        }
    }
}

}
