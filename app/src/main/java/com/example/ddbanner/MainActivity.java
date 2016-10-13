package com.example.ddbanner;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    //选中和未选中的图片
    private static final int[] mIndicator = {R.drawable.selected,R.drawable.unselected};
    //循环时未拉动的标志
    private static final int NO_DRAGGING = 0;
    //循环时拉动的标志
    private static final int DRAGGING = 1;
    //每张图片停留时间
    private static final long DURATION_TIME = 4000;
    //实现滑动图片和轮播的容器
    private ViewPager mViewPager;
    //管理每个图片fragment
    private FragmentManager manager;
    //轮播适配器
    private LoopAdapter mLoopAdapter;
    //指示点所在布局的背景
    private LinearLayout mIndicatorBackground;
    //存放指示点的图片
    private List<ImageView> mIndicators;
    //做更新轮播和延时传递轮播的工具
    private Handler loopHandler;
    //判断是否处于拖动状态
    private boolean isDragging;
    //上次拖动的时间

    private long lastTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();
        mViewPager = (ViewPager) findViewById(R.id.R_id_view_pager_container);
        mLoopAdapter = new LoopAdapter(manager);
        mViewPager.setAdapter(mLoopAdapter);
        mViewPager.setCurrentItem(500000);

        mIndicatorBackground = (LinearLayout) findViewById(R.id.indicator_id);
        mIndicators = new ArrayList<>();

        for(int i=0;i<4;i++)
        {
            ImageView indicator = new ImageView(MainActivity.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10,10,10,10);
            indicator.setLayoutParams(lp);

            if(i==0)
            {
                indicator.setImageResource(mIndicator[0]);
            }
            else
            {
                indicator.setImageResource(mIndicator[1]);

            }
            mIndicators.add(indicator);
            mIndicatorBackground.addView(indicator);
        }

        final Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                long nowTime = System.currentTimeMillis();

                if(nowTime-lastTime<DURATION_TIME-500||!isDragging)
                {
                    loopHandler.sendEmptyMessage(NO_DRAGGING);
                }
                else
                {
                    loopHandler.sendEmptyMessage(DRAGGING);
                }
            }
        };

        loopHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                int what = msg.what;
                switch (what)
                {
                    case NO_DRAGGING:
                        loopHandler.removeCallbacks(runnable);
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
                        break;
                    case DRAGGING:
                        loopHandler.removeCallbacks(runnable);
                        loopHandler.postDelayed(runnable,DURATION_TIME);
                        break;
                }
            }
        };


        isDragging = false;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                int mPosition = position%4;
                for(int i=0;i<4;i++)
                {
                    mIndicators.get(i).setImageResource(mIndicator[1]);
                }
                mIndicators.get(mPosition).setImageResource(mIndicator[0]);
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
                if(state == ViewPager.SCROLL_STATE_DRAGGING)
                {
                    lastTime = System.currentTimeMillis();
                    isDragging = true;
                    loopHandler.removeCallbacks(runnable);
                    return;
                }
                if(state == ViewPager.SCROLL_STATE_IDLE)
                {
                    if(!isDragging)
                    {
                        loopHandler.postDelayed(runnable,DURATION_TIME);
                    }
                }
                isDragging = false;
            }
        });

        loopHandler.postDelayed(runnable,DURATION_TIME);
    }
}
