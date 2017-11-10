package com.khalincheverria.mydictionary.Adapter;


import android.support.annotation.CallSuper;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.khalincheverria.mydictionary.BinaryTree.BinaryTree;
import com.khalincheverria.mydictionary.R;
import com.zhukic.sectionedrecyclerview.SectionedRecyclerViewAdapter;

public abstract class BaseSectionedAdapter extends SectionedRecyclerViewAdapter<BaseSectionedAdapter.SubheaderHolder, BaseSectionedAdapter.ContactListViewHolder> {

    protected BinaryTree contactList;
    protected OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onSubheaderClicked(int position);
    }


    BaseSectionedAdapter (BinaryTree contactList){
        super();
        this.contactList = contactList;
    }

    static class SubheaderHolder extends RecyclerView.ViewHolder{
        TextView subheaderText;
        ImageView subheaderArrow;

        public SubheaderHolder(View view){
            super(view);
            this.subheaderText = view.findViewById(R.id.subheaderText);
            this.subheaderArrow = view.findViewById(R.id.arrow);
        }
    }

    static class ContactListViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ImageView imageView;
        public View view;
        public ContactListViewHolder(View view){
            super(view);
            this.view= view;
            textView= view.findViewById(R.id.contacts_list);
            imageView = view.findViewById(R.id.contact_icon);
        }
    }

    @Override
    public ContactListViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ContactListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.word_list,parent,false));
    }

    @Override
    public SubheaderHolder onCreateSubheaderViewHolder(ViewGroup parent, int viewType) {
        return new SubheaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.subheader_layout,parent,false));
    }



    @Override
    @CallSuper
    public void onBindSubheaderViewHolder(SubheaderHolder subheaderHolder,int nextItemPosition){
        boolean isSectionExpanded = isSectionExpanded(getSectionIndex(subheaderHolder.getAdapterPosition()));
        if(isSectionExpanded){
            subheaderHolder.subheaderArrow.setImageDrawable(ContextCompat.getDrawable(subheaderHolder.itemView.getContext(),R.drawable.ic_keyboard_arrow_up_black_24dp));
        }else {
            subheaderHolder.subheaderArrow.setImageDrawable(ContextCompat.getDrawable(subheaderHolder.itemView.getContext(),R.drawable.ic_keyboard_arrow_down_black_24dp));
        }
        subheaderHolder.itemView.setOnClickListener(v -> onItemClickListener.onSubheaderClicked(subheaderHolder.getAdapterPosition()));
    }

    @Override
    public int getItemSize(){
        return contactList.count();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
