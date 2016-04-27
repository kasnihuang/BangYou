package com.bangyou.android.activity;

import android.app.Activity;

import com.bangyou.android.R;
import com.bangyou.android.widget.BaseTitleBar;


/**
 * Created by kasni.huang on 7/29/15.
 */
public class BaseActivity extends Activity {
    protected BaseTitleBar mTitleBar;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mTitleBar = (BaseTitleBar) findViewById(R.id.title_bar);
        initTitleBar();
    }

    protected void initTitleBar() {
    }

}
