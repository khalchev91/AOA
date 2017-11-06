package com.khalincheverria.mydictionary.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.khalincheverria.mydictionary.LinkedList.LinkedList;
import com.khalincheverria.mydictionary.Model.Contact;

import com.khalincheverria.mydictionary.ViewWord;
import com.khalincheverria.mydictionary.R;



public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {
private LinkedList wordList;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ViewHolder(TextView textView){
            super(textView);
            this.textView=textView;
        }
    }

    public WordAdapter(LinkedList wordList){
        this.wordList=wordList;
    }
    @Override
    public WordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){

        TextView textView=(TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.word_list,parent,false);
        WordAdapter.ViewHolder viewHolder=new WordAdapter.ViewHolder(textView);

        return viewHolder;
    }
@Override
    public void onBindViewHolder(WordAdapter.ViewHolder viewHolder,int position){
    final Contact contact =wordList.get(position);
    viewHolder.textView.setText(wordList.get(position).getWord());
    final Context context;
    context=viewHolder.textView.getContext();
    viewHolder.textView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent= new Intent(v.getContext(),ViewWord.class);
            intent.putExtra("Contacts", contact);
            context.startActivity(intent);

        }
    });


}
@Override
    public int getItemCount(){
return this.wordList.getSizeOfList();
}
}
