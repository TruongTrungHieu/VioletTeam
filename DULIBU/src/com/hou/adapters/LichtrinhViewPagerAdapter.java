package com.hou.adapters;

import com.hou.dulibu.R;
import com.hou.fragment.TripDetailInfoActivity;
import com.hou.fragment.TripDetailMemberActivity;
import com.hou.fragment.TripDetailMessageActivity;
import com.hou.fragment.TripDetailTripActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
 
/**
 * Created by hp1 on 21-01-2015.
 */
public class LichtrinhViewPagerAdapter extends FragmentStatePagerAdapter {
	Context context;
    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    private int[] imageResId = { R.drawable.selector_trip_info_for_user, R.drawable.selector_trip_member, R.drawable.selector_trip_message, R.drawable.selector_trip_trip_for_user,

	};
 
    // Build a Constructor and assign the passed Values to appropriate values in the class
    public LichtrinhViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb, Context c) {
        super(fm);
        this.context = c;
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
 
    }
 
    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
 
        if(position == 0) // if the position is 0 we are returning the First tab
        {
            TripDetailInfoActivity tripDetailInfoActivity = new TripDetailInfoActivity();
            return tripDetailInfoActivity;
        }
        if(position == 1)             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            TripDetailMemberActivity tripDetailMemberActivity = new TripDetailMemberActivity();
            return tripDetailMemberActivity;
        }
        if(position == 2)
        {
        	TripDetailMessageActivity tripDetailMessageActivity = new TripDetailMessageActivity();
        	return tripDetailMessageActivity;
        }
        else{
        	TripDetailTripActivity tripDetailTripActivity = new TripDetailTripActivity();
        	return tripDetailTripActivity;
        }
 
    }
 
    // This method return the titles for the Tabs in the Tab Strip
 
    @Override
	public CharSequence getPageTitle(int position) {
		Drawable image = context.getResources().getDrawable(imageResId[position]);
		image.setBounds(0, 0, 10, 10);
		SpannableString sb = new SpannableString(" ");
		ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
		sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return sb;
		// return Titles[position];
	}

	// This method return the Number of tabs for the tabs Strip

	@Override
	public int getCount() {
		//return NumbOfTabs;
		return imageResId.length;
	}
	public int getDrawableId(int position){
        //Here is only example for getting tab drawables
		
        return imageResId[position];
    }
}