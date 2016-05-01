package com.bangyou.android.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.bangyou.android.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kasni.huang on 4/26/16.
 */
public class MyProfileFragment extends BaseFragment {
    private int[] mIcons;
    private String[] mNames;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources resources = getResources();
        mIcons = new int[]{R.drawable.icon_basic_info, R.drawable.icon_service_info,
                R.drawable.icon_service_area, R.drawable.icon_my_bank};
        mNames = new String[]{resources.getString(R.string.my_profile_info), resources.getString(R.string.my_profile_server),
                resources.getString(R.string.my_profile_area), resources.getString(R.string.my_profile_bank_card)};
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_profile_fragment_layout, container, false);
    }

    @Override
    protected void initTitleBar() {
        mTitleBar.setTitleText(R.string.title_my_profile);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView lvProfile = (ListView) view.findViewById(R.id.lv_profile);
        List<Map<String, Object>> contents = new ArrayList<>();
        for (int i = 0; i < mIcons.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("icon", mIcons[i]);
            map.put("name", mNames[i]);
            contents.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(mActivity,
                contents, R.layout.my_profile_item,
                new String[] {"icon", "name"}, new int[] {
                R.id.iv_icon, R.id.tv_name});

        lvProfile.setAdapter(adapter);
    }
}
