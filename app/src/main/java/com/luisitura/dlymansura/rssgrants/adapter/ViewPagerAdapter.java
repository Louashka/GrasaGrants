package com.luisitura.dlymansura.rssgrants.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.luisitura.dlymansura.rssgrants.fragment.FCPFragment;
import com.luisitura.dlymansura.rssgrants.fragment.GosZakupkiFragment;
import com.luisitura.dlymansura.rssgrants.fragment.GrantFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 01.04.2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            GrantFragment tab1 = new GrantFragment();
            return tab1;
        }
        else if(position == 1)
        {
            FCPFragment tab2 = new FCPFragment();
            return tab2;
        } else {
            GosZakupkiFragment tab3 = new GosZakupkiFragment();
            return tab3;
        }


    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
