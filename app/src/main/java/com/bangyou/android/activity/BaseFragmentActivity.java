package com.bangyou.android.activity;

import android.support.v4.app.FragmentActivity;

import com.bangyou.android.R;
import com.bangyou.android.widget.BaseTitleBar;


/**
 * Created by kasni.huang on 7/29/15.
 */
public class BaseFragmentActivity extends FragmentActivity {
    protected BaseTitleBar mTitleBar;
    private boolean showTitleBar = true;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        if (showTitleBar) {
            mTitleBar = (BaseTitleBar) findViewById(R.id.title_bar);
            initTitleBar();
        }
    }

    protected void turnOffTitleBar() {
        showTitleBar = false;
    }

    protected void initTitleBar() {
    }

}
