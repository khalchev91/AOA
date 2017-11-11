package com.khalincheverria.mydictionary.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.khalincheverria.mydictionary.BinaryTree.BinaryTree;
import com.khalincheverria.mydictionary.Contacts;
import com.khalincheverria.mydictionary.DisplayWords;
import com.khalincheverria.mydictionary.Model.Contact;
import com.khalincheverria.mydictionary.Model.Name;
import com.khalincheverria.mydictionary.R;
import com.khalincheverria.mydictionary.ViewContact;

import org.zakariya.stickyheaders.SectioningAdapter;

import java.util.ArrayList;


public class SectionedAdapter extends SectioningAdapter {

    BinaryTree contacts;
    ArrayList<Section> sections= new ArrayList<>();
    SectionAsyncTask asyncTask;
    public class Section{
        String letter;
        BinaryTree contacts= new BinaryTree();
    }

    public class ItemViewHolder extends SectioningAdapter.ItemViewHolder{
        public TextView textView;
        public ImageView imageView;
        public View view;
        public ItemViewHolder(View view){
            super(view);
            this.view= view;
            textView= view.findViewById(R.id.contacts_list);
            imageView = view.findViewById(R.id.contact_icon);
        }
    }
        public class HeaderViewHolder extends SectioningAdapter.HeaderViewHolder{
            TextView headerText;
            public HeaderViewHolder(View item){
                super(item);
                headerText = item.findViewById(R.id.subheaderText);
            }

        }

    public void setContacts(){
    asyncTask = new SectionAsyncTask();
    asyncTask.execute();
    notifyAllSectionsDataSetChanged();

    }


    class SectionAsyncTask extends AsyncTask<Void,Void,Void> {

        char letter = 0;
        Section currentSection = null;
        int count = 1;
        @Override
        protected Void doInBackground(Void... params){
            SectionedAdapter.this.contacts= DisplayWords.getBinaryTree();
            sections.clear();

            while (contacts.atEnd()){
                Contact contact = contacts.get(count);
                if(contact.getName().getLastName().charAt(0) != letter){
                    if(currentSection !=null){
                        sections.add(currentSection);
                    }
                    currentSection = new Section();
                    letter = contact.getName().getLastName().charAt(0);
                    currentSection.letter = String.valueOf(letter);
                }
                if(currentSection !=null){
                    currentSection.contacts.insert(contact);
                }
                Log.d("Name","Name: "+contact.getName().getLastName()+" count "+count);

            }
           return  null;
        }
        @Override
        protected void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
            count++;
            sections.add(currentSection);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }
        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);

        }
    }


    @Override
    public int getNumberOfSections(){
        return sections.size();
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex){
        return sections.get(sectionIndex).contacts.count();
    }

    @Override
    public boolean doesSectionHaveHeader(int sectionIndex){
        return true;
    }

    @Override
    public boolean doesSectionHaveFooter(int sectionIndex){
        return false;
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent,int itemType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_list,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int itemType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subheader_layout,parent,false);
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, int sectionIndex, int itemIndex, int itemType){
        Section section = sections.get(sectionIndex);
        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        final Contact contact = section.contacts.get(itemIndex);
        itemViewHolder.textView.setText(contact.getName().toString());
        final Context context= itemViewHolder.view.getContext();

        itemViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(view.getContext(), ViewContact.class);
                intent.putExtra("Contacts", contact);
                context.startActivity(intent);
            }
        });
    }

@Override
public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder,int sectionIndex, int headerType){
    Section section = sections.get(sectionIndex);

    HeaderViewHolder headerViewHolder = (HeaderViewHolder)viewHolder;

    headerViewHolder.headerText.setText(section.letter);
}



}
