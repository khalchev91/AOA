package com.khalincheverria.analysisofalgorithms.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.khalincheverria.analysisofalgorithms.Model.Contact;
import com.khalincheverria.analysisofalgorithms.R;
import com.khalincheverria.analysisofalgorithms.ViewContact;

import java.util.ArrayList;

public class FoundContactAdapter extends RecyclerView.Adapter<FoundContactAdapter.ViewHolder> {


    private ArrayList<Contact> contactArrayList = new ArrayList<>();


    public FoundContactAdapter(ArrayList<Contact> contactArrayList){
        this.contactArrayList= contactArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.found_contact,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Contact contact = contactArrayList.get(position);
        holder.name.setText(contact.getName().toString());
        holder.address.setText(contact.getAddress());

        final Context context = holder.view.getContext();
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewContact.class);
                intent.putExtra("Contacts",contact);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactArrayList == null ? 0 : contactArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView name, address;

        public ViewHolder(View view){
            super(view);
            this.view = view;
            name = view.findViewById(R.id.name);
            address = view.findViewById(R.id.contact_address);
        }

    }
}
