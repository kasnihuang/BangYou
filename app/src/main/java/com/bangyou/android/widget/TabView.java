package com.bangyou.android.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bangyou.android.R;


/**
 * Created by kasni.huang on 3/24/15.
 */
public class TabView extends RelativeLayout {
    private static final String TAG = "RCTabView";
    public static final int MAX_MESSAGES_COUNT_TO_BE_DISPLAYED = 99;
    public static final int CHANGE_MARGIN_LIMIT = 10;
    private TextView mTvTab;

    public TabView(Context context) {
        super(context);
        init(context);
    }

    public TabView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }


    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.tab_view_item, this);
        this.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        this.setDuplicateParentStateEnabled(true);
        mTvTab = (TextView) findViewById(R.id.tab_main_text);
    }

    public void changeSelectedState(boolean state) {
        this.findViewById(R.id.tab_main_text).setSelected(state);
    }

    public void setPosition(int position) {
        this.setTag(position);
    }

    public void updateTableName(int textId) {
        mTvTab.setText(textId);
    }

    public void updateTopIcon(Context context, int iconId) {
        Drawable drawable = context.getResources().getDrawable(iconId);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        }
        mTvTab.setCompoundDrawables(null, drawable, null, null);
    }

    public void updateIndicator(int count) {
        TextView textView = (TextView)this.findViewById(R.id.tab_indicator_counter);
        if (count > 0) {
            textView.setText(count <= MAX_MESSAGES_COUNT_TO_BE_DISPLAYED ?
                    String.valueOf(count) : getContext().getString(R.string.menu_more_than_99));
            textView.setVisibility(View.VISIBLE);
            try {
                LayoutParams indicatorParams = (LayoutParams) textView.getLayoutParams();
                if (count < CHANGE_MARGIN_LIMIT) {
                    int marginRight = getResources().getDimensionPixelSize(R.dimen.dimen_5);
                    indicatorParams.setMargins(0, getResources().getDimensionPixelSize(R.dimen.dimen_3), marginRight, 0);
                } else {
                    indicatorParams.setMargins(0, getResources().getDimensionPixelSize(R.dimen.dimen_3), 0, 0);
                }
                /*if (CHANGE_MARGIN_LIMIT <= count && count < MAX_MESSAGES_COUNT_TO_BE_DISPLAYED) {
                    marginRight = getResources().getDimensionPixelSize(R.dimen.dimen_20);
                } else if (count < CHANGE_MARGIN_LIMIT) {
                    marginRight = getResources().getDimensionPixelOffset(R.dimen.dimen_25);
                }*/
            } catch (Exception e) {
                Log.e(TAG, "updateIndicator(): exception :" + e.toString(), e);
            }
        } else {
            textView.setVisibility(View.GONE);
        }
    }
}
