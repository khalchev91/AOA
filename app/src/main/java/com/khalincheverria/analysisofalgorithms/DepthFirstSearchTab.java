package com.khalincheverria.analysisofalgorithms;


import android.annotation.SuppressLint;
import android.os.Bundle;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;


import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.khalincheverria.analysisofalgorithms.Adapter.BinaryTreeAdapter;
import com.khalincheverria.analysisofalgorithms.BinaryTree.BinaryTree;


import xyz.danoz.recyclerviewfastscroller.vertical.VerticalRecyclerViewFastScroller;


@SuppressWarnings("deprecation")
public class DepthFirstSearchTab extends Fragment {

    protected static BinaryTree binaryTree = new BinaryTree();
    protected RecyclerView recyclerView;
    protected LinearLayoutManager linearLayoutManager;

    private VerticalRecyclerViewFastScroller fastScroller;


    public DepthFirstSearchTab() {

    }

    public static BinaryTree getBinaryTree() {
        return binaryTree;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_display_words, container, false);
        recyclerView = view.findViewById(R.id.word_list);
        linearLayoutManager = new LinearLayoutManager(this.getContext());
        fastScroller = view.findViewById(R.id.fast_scroller);
        CoordinatorLayout coordinatorLayout = getActivity().findViewById(R.id.main_layout);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        fastScroller.setRecyclerView(recyclerView);
        recyclerView.setOnScrollListener(fastScroller.getOnScrollListener());

        binaryTree = Contacts.getBinaryTree();

        BinaryTreeAdapter binaryTreeAdapter = new BinaryTreeAdapter();
        binaryTreeAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(binaryTreeAdapter);

        return view;
    }




}