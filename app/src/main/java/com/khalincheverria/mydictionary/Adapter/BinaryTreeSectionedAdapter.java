package com.khalincheverria.mydictionary.Adapter;


import android.content.Context;
import android.content.Intent;

import com.khalincheverria.mydictionary.BinaryTree.BinaryTree;
import com.khalincheverria.mydictionary.Model.Contact;
import com.khalincheverria.mydictionary.ViewContact;

public class BinaryTreeSectionedAdapter extends BaseSectionedAdapter{

    public BinaryTreeSectionedAdapter(BinaryTree contactList){
        super(contactList);
    }

@Override
public boolean onPlaceSubheaderBetweenItems(int position){  //this function gets the contacts and places the different contacts in their respective headings

    Contact contact = contactList.get(position);
    Contact nextContact = contactList.get(position+1);

    return !contact.getName().getLastName().substring(0,1).equals(nextContact.getName().getLastName().substring(0,1));
}


    public void onBindItemViewHolder(ContactListViewHolder viewHolder, int position){
    Contact contact = contactList.get(position);
    viewHolder.textView.setText(contact.getName().toString());
    Context context = viewHolder.view.getContext();
    viewHolder.view.setOnClickListener(view -> {
        Intent intent= new Intent(view.getContext(), ViewContact.class);
        intent.putExtra("Contacts", contact);
        context.startActivity(intent);
    });
    }

    @Override
    public void onBindSubheaderViewHolder(SubheaderHolder subheaderHolder, int nextItemPosition) {
        super.onBindSubheaderViewHolder(subheaderHolder, nextItemPosition);
        final Contact nextContact = contactList.get(nextItemPosition);
        subheaderHolder.subheaderText.setText(nextContact.getName().getLastName().substring(0, 1));
    }


}
