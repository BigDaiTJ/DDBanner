package com.example.ddbanner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/10/11.
 */

public class ViewPagerFragment extends Fragment
{
    private static final String LOOP_POSITION = "loop_position";
    private int[] mLoops = {R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d};
    private int mPosition;
    public ImageView mLoopView;
    private View view;

    public static ViewPagerFragment newInstance(int position)
    {
        Bundle arg = new Bundle();
        arg.putInt(LOOP_POSITION,position);

        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mPosition = getArguments().getInt(LOOP_POSITION);

        view = inflater.inflate(R.layout.loop_layout,container,false);
        mLoopView = (ImageView) view.findViewById(R.id.loop_picture);
        mLoopView.setImageResource(mLoops[mPosition]);
        return view;
    }
}
