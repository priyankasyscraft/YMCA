package com.ymca.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ymca.Fragments.ScreenSlidePageFragment;
import com.ymca.ModelClass.SliderModelClass;

import java.util.ArrayList;

/**
 * Created by Soni on 09-Aug-16.
 */
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<SliderModelClass> bannerImage = new ArrayList<>();
    private FragmentActivity context;

    public ScreenSlidePagerAdapter(FragmentManager fm, FragmentActivity context, ArrayList<SliderModelClass> arrrayListImage) {
        super(fm);
        this.context = context;
        bannerImage = arrrayListImage;
    }

    @Override
    public Fragment getItem(int position) {
        return new ScreenSlidePageFragment(bannerImage, context,
                position);
    }

    @Override
    public int getCount() {
        return bannerImage.size();
    }
}