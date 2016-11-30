package com.shizhefei.view.coolrefreshview.demo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.shizhefei.view.coolrefreshview.demo.DisplayUtil;
import com.shizhefei.view.coolrefreshview.demo.R;
import com.shizhefei.view.coolrefreshview.demo.activity.moreviews.ListViewFragment;
import com.shizhefei.view.coolrefreshview.demo.activity.moreviews.RecyclerViewFragment;
import com.shizhefei.view.coolrefreshview.demo.activity.moreviews.ScrollViewFragment;
import com.shizhefei.view.coolrefreshview.demo.activity.moreviews.TextViewFragment;
import com.shizhefei.view.coolrefreshview.demo.activity.moreviews.WebViewFragment;
import com.shizhefei.view.coolrefreshview.demo.adapters.PagesAdapter;
import com.shizhefei.view.coolrefreshview.demo.events.RefreshEvent;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

public class MoreViewsActivity extends AppCompatActivity {


    private IndicatorViewPager indicatorViewPager;
    private View refreshButton;
    private View completeButton;
    private PagesAdapter pagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_views);
        Indicator indicator = (Indicator) findViewById(R.id.moreviews_indicatorView);
        ViewPager viewPager = (ViewPager) findViewById(R.id.moreviews_viewPager);
        refreshButton = findViewById(R.id.moreviews_refresh_button);
        completeButton = findViewById(R.id.moreviews_complete_button);

        Class[] fragments = {
                RecyclerViewFragment.class,
                ListViewFragment.class,
                WebViewFragment.class,
                TextViewFragment.class,
                ScrollViewFragment.class
        };

        indicator.setScrollBar(new ColorBar(this, ContextCompat.getColor(this, R.color.primary), DisplayUtil.dipToPix(this, 3)));
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColor(Color.BLACK, Color.GRAY));
        viewPager.setOffscreenPageLimit(2);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(pagesAdapter = new PagesAdapter(getSupportFragmentManager(), fragments));

        refreshButton.setOnClickListener(onClickListener);
        completeButton.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Fragment fragment = pagesAdapter.getCurrentFragment();
            RefreshEvent refreshEvent = (RefreshEvent) fragment;
            if (view == refreshButton) {
                refreshEvent.setRefreshing(true);
            } else if (view == completeButton) {
                refreshEvent.setRefreshing(false);
            }
        }
    };
}
