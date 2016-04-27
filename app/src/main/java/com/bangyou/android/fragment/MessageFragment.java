package com.bangyou.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bangyou.android.R;

/**
 * Created by kasni.huang on 4/26/16.
 */
public class MessageFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     return inflater.inflate(R.layout.message_fragment_layout, container, false);
    }

    @Override
    protected void initTitleBar() {
        mTitleBar.setTitleText(R.string.tab_message);
    }
}
