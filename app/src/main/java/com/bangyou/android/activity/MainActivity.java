package com.bangyou.android.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bangyou.android.R;
import com.bangyou.android.fragment.MessageFragment;
import com.bangyou.android.fragment.MyProfileFragment;
import com.bangyou.android.fragment.OrderFragment;
import com.bangyou.android.widget.TabView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class MainActivity extends BaseFragmentActivity implements SmartTabLayout.TabProvider {
    public static final int TAB_MESSAGE = 0;
    public static final int TAB_ORDER = 1;
    public static final int TAB_MY_PROFILE = 2;
    private SmartTabLayout mViewPagerTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        turnOffTitleBar();
        setContentView(R.layout.activity_main);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        mViewPagerTab.setCustomTabView(this);

//		FragmentPagerItems pages = new FragmentPagerItems(this);
//		for (int titleResId : demo.tabs()) {
//			pages.add(FragmentPagerItem.of(getString(titleResId), DemoFragment.class));
//		}

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.tab_message, MessageFragment.class)
                .add(R.string.tab_order, OrderFragment.class)
                .add(R.string.tab_my_profile, MyProfileFragment.class)
                .create());


//		FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
//				getSupportFragmentManager(), pages);

        viewPager.setAdapter(adapter);
        mViewPagerTab.setViewPager(viewPager);

        mViewPagerTab.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

            }
        });
    }

    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View tab = inflater.inflate(R.layout.tab_item_layout, container, false);
        TabView tabView = (TabView) tab.findViewById(R.id.tab_view);
        tab.setTag(position);
        switch (position) {
            case TAB_MESSAGE:
                tabView.updateTableName(R.string.tab_message);
                tabView.updateTopIcon(this, R.drawable.tab_message_foreground);
                tabView.updateIndicator(10);
                break;
            case TAB_ORDER:
                tabView.updateTableName(R.string.tab_order);
                tabView.updateTopIcon(this, R.drawable.tab_message_foreground);
                tabView.updateIndicator(3);
                break;
            case TAB_MY_PROFILE:
                tabView.updateTableName(R.string.tab_my_profile);
                tabView.updateTopIcon(this, R.drawable.tab_message_foreground);
                break;
//			default:
//				throw new IllegalStateException("Invalid position: " + position);
        }
        return tab;
    }

    //TODO 更新tab上显示条数的接口
    /**
     * @param position The position of tab(TAB_MESSAGE, TAB_ORDER, TAB_MY_PROFILE)
     * @param count The number which should to show in red indicator
     */
    private void updateTabIndicator(int position, int count) {
        ((TabView)mViewPagerTab.getTabAt(position).findViewById(R.id.tab_view)).updateIndicator(count);
    }
}
