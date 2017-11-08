package com.khalincheverria.mydictionary;



import android.annotation.SuppressLint;
import android.os.Bundle;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;


import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.khalincheverria.mydictionary.Adapter.BinaryTreeAdapter;
import com.khalincheverria.mydictionary.BinaryTree.BinaryTree;


import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;


/*
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("deprecation")
public class DisplayWords extends Fragment {

    protected static BinaryTree binaryTree= new BinaryTree();
    protected RecyclerView recyclerView;
    protected LinearLayoutManager linearLayoutManager;

    private VerticalRecyclerViewFastScroller fastScroller;




    public DisplayWords() {
        // Required empty public constructor
    }




    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_display_words, container, false);
        recyclerView=view.findViewById(R.id.word_list);
        linearLayoutManager= new LinearLayoutManager(this.getContext());
        fastScroller=view.findViewById(R.id.fast_scroller);
        CoordinatorLayout coordinatorLayout=getActivity().findViewById(R.id.main_layout);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        fastScroller.setRecyclerView(recyclerView);
        recyclerView.setOnScrollListener(fastScroller.getOnScrollListener());

        binaryTree= Contacts.getBinaryTree();

            BinaryTreeAdapter binaryTreeAdapter=new BinaryTreeAdapter(binaryTree);

            binaryTreeAdapter.notifyDataSetChanged();

            recyclerView.setAdapter(binaryTreeAdapter);

Snackbar.make(coordinatorLayout,"Number of words: "+binaryTree.count(),Snackbar.LENGTH_SHORT).setAction("Contacts",null).show();

        return view;
    }

}
