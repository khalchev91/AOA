package com.khalincheverria.analysisofalgorithms.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.khalincheverria.analysisofalgorithms.BinaryTree.BinaryTree;
import com.khalincheverria.analysisofalgorithms.Contacts;
import com.khalincheverria.analysisofalgorithms.DepthFirstSearchTab;
import com.khalincheverria.analysisofalgorithms.BreadthFirstSearch;
import com.khalincheverria.analysisofalgorithms.KMPSearch;

public class SearchTabPagerAdapter extends FragmentPagerAdapter {
    private int tabCount;

    public SearchTabPagerAdapter (FragmentManager manager,int tabCount){
        super(manager);
        this.tabCount = tabCount;

    }

    @Override
    public int getCount() {
        return tabCount;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new DepthFirstSearchTab();

            case 1 : return new BreadthFirstSearch();

            case 2 : return new KMPSearch();

            default:return null;
        }
    }
}
