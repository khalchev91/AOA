package com.khalincheverria.analysisofalgorithms;

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


public class KMPSearch extends Fragment {


    protected static BinaryTree binaryTree = new BinaryTree();
    protected RecyclerView recyclerView;
    protected LinearLayoutManager linearLayoutManager;


    public static void setBinaryTree() {
        KMPSearch.binaryTree = Contacts.binaryTree;
    }

    public static BinaryTree getBinaryTree() {
        return binaryTree;
    }

    public static KMPSearch NewInstance(){
        KMPSearch kmpSearch= new KMPSearch();
        KMPSearch.setBinaryTree();
        return kmpSearch;
    }


    public KMPSearch() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_two, container, false);

        binaryTree = Contacts.binaryTree;

        recyclerView = view.findViewById(R.id.kmp_recycler_view);
        linearLayoutManager = new LinearLayoutManager(this.getContext());

        CoordinatorLayout coordinatorLayout = getActivity().findViewById(R.id.main_layout);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);

        BinaryTreeAdapter binaryTreeAdapter = new BinaryTreeAdapter();

        recyclerView.setAdapter(new BinaryTreeAdapter());

        return view;
    }
}
