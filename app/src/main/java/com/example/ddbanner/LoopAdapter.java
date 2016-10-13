package com.example.ddbanner;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2016/10/12.
 */

public class LoopAdapter extends FragmentPagerAdapter
{
    private int mPosition;

    public LoopAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        mPosition = position%4;
        return ViewPagerFragment.newInstance(mPosition);
    }

    @Override
    public int getCount()
    {
        return Integer.MAX_VALUE;
    }
}
