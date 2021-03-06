package com.khalincheverria.analysisofalgorithms.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.khalincheverria.analysisofalgorithms.BinaryTree.BinaryTree;
import com.khalincheverria.analysisofalgorithms.DepthFirstSearchTab;
import com.khalincheverria.analysisofalgorithms.Model.Contact;
import com.khalincheverria.analysisofalgorithms.R;
import com.khalincheverria.analysisofalgorithms.ViewContact;



public class BinaryTreeAdapter extends RecyclerView.Adapter<BinaryTreeAdapter.ViewHolder> {
private BinaryTree contactsTree;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public View view;
        public ViewHolder(View view){
            super(view);
            this.view= view;
            textView= view.findViewById(R.id.contacts_list);
        }
    }
    public BinaryTreeAdapter(){
        this.contactsTree= DepthFirstSearchTab.getBinaryTree();
    }

    @Override
    public BinaryTreeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.word_list,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(BinaryTreeAdapter.ViewHolder viewHolder,int position){
        final Contact contact =contactsTree.get(position);
        viewHolder.textView.setText(contactsTree.get(position).getName().toString());
        final Context context;

        context=viewHolder.view.getContext();
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(v.getContext(), ViewContact.class);
                intent.putExtra("Contacts", contact);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount(){
        return contactsTree.count();
    }

}
