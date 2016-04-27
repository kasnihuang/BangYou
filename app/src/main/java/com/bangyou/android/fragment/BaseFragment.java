package com.bangyou.android.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.bangyou.android.R;
import com.bangyou.android.activity.MainActivity;
import com.bangyou.android.widget.BaseTitleBar;

/**
 * Created by kasni.huang on 4/26/16.
 */
public class BaseFragment extends Fragment {
    protected MainActivity mActivity;
    protected BaseTitleBar mTitleBar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTitleBar = (BaseTitleBar) view.findViewById(R.id.title_bar);
        initTitleBar();
    }

    protected void initTitleBar() {}
}
