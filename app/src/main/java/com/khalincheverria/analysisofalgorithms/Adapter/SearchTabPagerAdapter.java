package com.khalincheverria.analysisofalgorithms.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.khalincheverria.analysisofalgorithms.DepthFirstSearchTab;
import com.khalincheverria.analysisofalgorithms.SearchOneFragment;
import com.khalincheverria.analysisofalgorithms.SearchTwoFragment;

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
            case 0: return new DepthFirstSearchTab();

            case 1: return new SearchOneFragment();

            case 2:return new SearchTwoFragment();


            default:return null;
        }
    }
}