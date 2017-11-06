package com.khalincheverria.mydictionary.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.khalincheverria.mydictionary.BinaryTree.BinaryTree;
import com.khalincheverria.mydictionary.Model.Contact;
import com.khalincheverria.mydictionary.R;
import com.khalincheverria.mydictionary.ViewWord;



public class BinaryTreeAdapter extends RecyclerView.Adapter<BinaryTreeAdapter.ViewHolder> {
private BinaryTree wordTree;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ViewHolder(TextView textView){
            super(textView);
            this.textView=textView;
        }
    }
    public BinaryTreeAdapter(BinaryTree wordTree){
        this.wordTree=wordTree;
    }
    @Override
    public BinaryTreeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        TextView textView=(TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.word_list,parent,false);
        BinaryTreeAdapter.ViewHolder viewHolder=new BinaryTreeAdapter.ViewHolder(textView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(BinaryTreeAdapter.ViewHolder viewHolder,int position){
        final Contact contact =wordTree.get(position);
        viewHolder.textView.setText(wordTree.get(position).getWord());
        final Context context;
        context=viewHolder.textView.getContext();
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(v.getContext(), ViewWord.class);
                intent.putExtra("Contacts", contact);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount(){
        return wordTree.count();
    }

}
