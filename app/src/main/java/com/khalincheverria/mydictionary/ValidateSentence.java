package com.khalincheverria.mydictionary;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import com.khalincheverria.mydictionary.Adapter.InvalidWordsAdapater;
import com.khalincheverria.mydictionary.Adapter.RecyclerViewClickListener;
import com.khalincheverria.mydictionary.BinaryTree.BinaryTree;
import com.khalincheverria.mydictionary.LinkedList.LinkedList;
import com.khalincheverria.mydictionary.Model.Contact;

import java.util.ArrayList;


/*
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("deprecation")
public class ValidateSentence extends Fragment implements RecyclerViewClickListener,View.OnClickListener{
    ArrayList<String> list= new ArrayList<>();

    LinearLayoutManager layoutManager;
    Contact newContact = new Contact();
    TextView numberOfWords;
    InvalidWordsAdapater invalidWordsAdapater;
    private LinkedList wordList;
    private boolean isTree;
    private BinaryTree binaryTree;


    private Spinner partOfSpeech;
    private EditText defintion;


    OnDataPass dataPass;
    public ValidateSentence() {

        // Required empty public constructor
    }


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_validate_sentence, container, false);
        Bundle bundle= getArguments();
        list=bundle.getStringArrayList("InvalidWords");
        isTree=bundle.getBoolean("Tree");
        RecyclerView recyclerView;
        if(isTree){
            binaryTree=(BinaryTree)bundle.getSerializable("Contacts");
        }else {
            wordList=(LinkedList)bundle.getSerializable("Contacts");
        }
        Button button;
        numberOfWords=(TextView)view.findViewById(R.id.number_of_words);
        numberOfWords.setText(Integer.toString(list.size()));
        recyclerView= (RecyclerView)view.findViewById(R.id.invalid_words);
        layoutManager= new LinearLayoutManager(this.getContext());
        button= (Button)view.findViewById(R.id.done_validating);

        DividerItemDecoration itemDecoration= new DividerItemDecoration(recyclerView.getContext(),layoutManager.getOrientation());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(itemDecoration);

        invalidWordsAdapater= new InvalidWordsAdapater(list,this);
        recyclerView.setAdapter(invalidWordsAdapater);


        button.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity typeSentence){
        super.onAttach(typeSentence);
        dataPass=(OnDataPass)typeSentence;
    }


    @SuppressLint("InflateParams")
    @Override
    public void recyclerViewListClicked(View view, final int position){
        final String word= list.get(position);
        LayoutInflater inflater =this.getActivity().getLayoutInflater();
        View addWord=inflater.inflate(R.layout.new_word,null);

         defintion =(EditText)addWord.findViewById(R.id.defi);
         partOfSpeech=(Spinner)addWord.findViewById(R.id.part_speech);

        AlertDialog dialog= new AlertDialog.Builder(this.getActivity())
                .setTitle("Add contact "+"\"" +word+"\"")
                .setView(addWord)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean emptyDefinition=(defintion.getText().toString().trim().isEmpty());
                        boolean selectPos=(partOfSpeech.getSelectedItemPosition()==0);

                            if(!(emptyDefinition) ||!(selectPos)) {
                                newContact.setWord(word);
                                newContact.setDefinition(defintion.getText().toString());
                                newContact.setPartOfSpeech(partOfSpeech.getSelectedItem().toString());
                                if (isTree) {
                                    binaryTree.insert(new Contact(newContact));
                                } else {
                                    wordList.addWord(new Contact(newContact));
                                }
                                dialog.dismiss();
                                list.remove(position);
                                numberOfWords.setText(Integer.toString(list.size()));
                                invalidWordsAdapater.notifyDataSetChanged();
                            }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();

            dialog.show();
    }
@Override
public void onClick(View v){
if(isTree){
    dataPass.onBinaryTreePass(binaryTree);
    getActivity().finish();
}else {
    dataPass.onLinkedListPass(wordList);
    getActivity().finish();
}
}

    public interface OnDataPass{
        void onLinkedListPass(LinkedList linkedList);
        void onBinaryTreePass(BinaryTree binaryTree);
    }

}

