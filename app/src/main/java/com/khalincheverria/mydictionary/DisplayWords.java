package com.khalincheverria.mydictionary;



import android.os.Bundle;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;


import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import com.khalincheverria.mydictionary.Adapter.BinaryTreeAdapter;
import com.khalincheverria.mydictionary.Adapter.WordAdapter;
import com.khalincheverria.mydictionary.BinaryTree.BinaryTree;
import com.khalincheverria.mydictionary.LinkedList.LinkedList;
import com.github.clans.fab.FloatingActionMenu;

import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;


/*
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("deprecation")
public class DisplayWords extends Fragment {

protected static LinkedList wordList= new LinkedList();
    protected static BinaryTree binaryTree= new BinaryTree();
    protected boolean isTree;
    protected RecyclerView recyclerView;
    protected LinearLayoutManager linearLayoutManager;

    private VerticalRecyclerViewFastScroller fastScroller;




    public DisplayWords() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_display_words, container, false);
        Bundle bundle= getArguments();
        isTree=bundle.getBoolean("Tree");
        recyclerView=(RecyclerView)view.findViewById(R.id.word_list);
        linearLayoutManager= new LinearLayoutManager(this.getContext());
        fastScroller=(VerticalRecyclerViewFastScroller)view.findViewById(R.id.fast_scroller);
        final FloatingActionMenu fabMenu=(FloatingActionMenu)getActivity().findViewById(R.id.fab_menu);
        CoordinatorLayout coordinatorLayout=(CoordinatorLayout)getActivity().findViewById(R.id.main_layout);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        fastScroller.setRecyclerView(recyclerView);
        recyclerView.setOnScrollListener(fastScroller.getOnScrollListener());
        if(isTree){

                binaryTree= Contacts.getBinaryTree();
            BinaryTreeAdapter binaryTreeAdapter=new BinaryTreeAdapter(binaryTree);
            binaryTreeAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(binaryTreeAdapter);


                Snackbar.make(coordinatorLayout,"Number of words: "+binaryTree.count(),Snackbar.LENGTH_SHORT).setAction("Contacts",null).show();
            }else {

            wordList= Contacts.getWordList();
            WordAdapter wordAdapter = new WordAdapter(wordList);
            wordAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(wordAdapter);

                Snackbar.make(coordinatorLayout,"Number of words: "+wordList.getSizeOfList(),Snackbar.LENGTH_SHORT).setAction("Contacts",null).show();
            }


            recyclerView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(fabMenu.isOpened()){
                        fabMenu.close(true);
                    }
                    return false;
                }
            });
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if(newState==RecyclerView.SCROLL_STATE_IDLE){
                        fabMenu.showMenuButton(true);
                    }
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if(dy>0 && fabMenu.isShown()){
                        fabMenu.hideMenuButton(true);
                    }else if(dy<0){
                        fabMenu.showMenuButton(true);
                    }

                    super.onScrolled(recyclerView, dx, dy);
                }
            });

        return view;
    }

}
