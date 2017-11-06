package com.khalincheverria.mydictionary;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.khalincheverria.mydictionary.BinaryTree.BinaryTree;
import com.khalincheverria.mydictionary.LinkedList.LinkedList;


import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TypeSentence extends AppCompatActivity implements View.OnClickListener,ValidateSentence.OnDataPass {

    private EditText userSentence;
    private Button validateSentenceButton;
    String newSentence;
    private boolean isTree;
    public static LinkedList wordList;
    public static BinaryTree binaryTree;
    public int current;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_sentence);

        isTree = getIntent().getExtras().getBoolean("Tree");
        userSentence = (EditText) findViewById(R.id.sentence);
        validateSentenceButton = (Button)findViewById(R.id.enter_sentence);
        validateSentenceButton.setEnabled(false);
        userSentence.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equalsIgnoreCase("")){
                    validateSentenceButton.setEnabled(false);
                }else {
                    validateSentenceButton.setEnabled(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        validateSentenceButton.setOnClickListener(this);
    }


    @SuppressLint("DefaultLocale")
    @Override
    public void onClick(View view) {
        newSentence = userSentence.getText().toString();
        newSentence=newSentence.replaceAll("[^A-Za-z]+"," ");
        final String[] wordsInSentence = newSentence.split("\\s+");
        if (isTree) {
            ArrayList<String> list=new ArrayList<>();//something to do with ui
            long start = System.nanoTime();
            for(current=0;current<wordsInSentence.length;current++) {
                if (!(binaryTree.contains(wordsInSentence[current]))) {
                    if(!list.contains(wordsInSentence[current])) {
                        list.add(wordsInSentence[current].trim().toLowerCase());
                    }
                }
            }
                if(list.size()>0){
                 ValidateSentence validateSentence = new ValidateSentence();
                    FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("InvalidWords",list);
                    bundle.putBoolean("Tree",isTree);
                    bundle.putSerializable("Contacts",binaryTree);
                    validateSentence.setArguments(bundle);
                    fragmentTransaction.replace(R.id.validate_sentence,validateSentence);
                    fragmentTransaction.commit();
                }

            long end=System.nanoTime();
            double duration = (double)(end - start)/1000000000;
            Toast.makeText(TypeSentence.this, String.format("That took: %.4f seconds",duration), Toast.LENGTH_SHORT).show();

            if(list.size()==0){
                AlertDialog alertDialog= new AlertDialog.Builder(this).create();
                alertDialog.setMessage("All words are in the dictionary");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Contacts.binaryTree=binaryTree;
                        Intent returnStructure= new Intent();
                        returnStructure.putExtra("Tree",true);

                        setResult(Activity.RESULT_OK,returnStructure);
                        dialog.dismiss();
                        finish();
                    }
                });
                alertDialog.show();
            }

            }
         else {

            ArrayList<String> list= new ArrayList<>();
            long start = System.nanoTime();
            for(current=0;current<wordsInSentence.length;current++) {
                if (!(wordList.contains(wordsInSentence[current]))) {
                    if(!list.contains(wordsInSentence[current]))
                        list.add(wordsInSentence[current].trim().toLowerCase());
                }
            }
            if(list.size()>0){
                ValidateSentence validateSentence = new ValidateSentence();
                FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("InvalidWords",list);
                bundle.putBoolean("Tree",isTree);
                bundle.putSerializable("Contacts",wordList);
                validateSentence.setArguments(bundle);
                fragmentTransaction.replace(R.id.validate_sentence,validateSentence);

                fragmentTransaction.commit();
            }
            long end=System.nanoTime();
            double duration = (double)(end - start)/1000000000;
            Toast.makeText(TypeSentence.this, String.format("That took: %.4f seconds",duration), Toast.LENGTH_SHORT).show();

            if(list.size()==0){
                AlertDialog alertDialog=new AlertDialog.Builder(this).create();
                alertDialog.setMessage("All words in sentence are in dictionary");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Contacts.wordList=wordList;
                        Intent returnStructure= new Intent();
                            returnStructure.putExtra("Tree",false);

                        setResult(Activity.RESULT_OK,returnStructure);
                        finish();
                    }
                });
                alertDialog.show();
            }
        }
    }
    @Override
    public void onLinkedListPass(LinkedList linkedList){
        TypeSentence.wordList=linkedList;
        Contacts.wordList=wordList;
        Intent returnWord= new Intent();
        returnWord.putExtra("Tree",false);

        setResult(Activity.RESULT_OK,returnWord);

    }
    @Override
    public void onBinaryTreePass(BinaryTree binaryTree){
        TypeSentence.binaryTree= binaryTree;
        Contacts.binaryTree=TypeSentence.binaryTree;
        Intent returnWord= new Intent();
        returnWord.putExtra("Tree",true);


        setResult(Activity.RESULT_OK,returnWord);
    }



}
