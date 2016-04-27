package com.bangyou.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bangyou.android.R;


/**
 * Created by kasni.huang.
 */
public class BaseTitleBar extends RelativeLayout implements View.OnClickListener {

    protected Button mBtnLeft;
    protected Button mBtnRight;
    protected ImageView mIBtnLeft;
    protected ImageView mIBtnRight;
    protected TextView mTitleView = null;
//    protected TextView mSecTitleView;
    protected HeaderClickListener mHeaderClickListener;
    protected Context mContext;
//    protected View mDividerView;

    public interface HeaderClickListener {
        void onRightButtonClicked();
        void onLeftButtonClicked();
    }

    public BaseTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.title_bar_layout, this);
        mBtnLeft = (Button) findViewById(R.id.btnTopLeft);
        mBtnLeft.setOnClickListener(this);
        mBtnRight = (Button) findViewById(R.id.btnTopRight);
        mBtnRight.setOnClickListener(this);
        mIBtnLeft = (ImageView) findViewById(R.id.btnTopLeftImage);
        mIBtnLeft.setOnClickListener(this);
        mIBtnRight = (ImageView) findViewById(R.id.btnTopRightImage);
        mIBtnRight.setOnClickListener(this);
        mTitleView = (TextView) findViewById(R.id.title);
//        mSecTitleView = (TextView) findViewById(R.id.tv_sec_title);
//        mDividerView = findViewById(R.id.divider);
    }

    public ImageView getIBtnRight() {
        return mIBtnRight;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTopRight:
            case R.id.btnTopRightImage:
                mHeaderClickListener.onRightButtonClicked();
                break;

            case R.id.btnTopLeft:
            case R.id.btnTopLeftImage:
                mHeaderClickListener.onLeftButtonClicked();
                break;
        }
    }

    public void setTitleSize(float size) {
        mTitleView.setTextSize(size);
    }

    public void setTitleText(String name) {
        mTitleView.setText(name);
    }

    public void setTitleText(int stringId) {
        mTitleView.setText(stringId);
    }

    /*public void setSecTitleText(String name) {
        mSecTitleView.setText(name);
    }

    public void setSecTitleText(int stringId) {
        mSecTitleView.setText(stringId);
    }

    public void setDividerColor(int color) {
        mDividerView.setBackgroundColor(getResources().getColor(color));
    }*/

    public void setButtonsClickCallback(HeaderClickListener _headerClickListener) {
        mHeaderClickListener = _headerClickListener;
    }

    public void setRightImageRes(int resId) {
        mIBtnRight.setImageResource(resId);
    }

    public void setRightImageVisibility(int visibility) {
        mIBtnRight.setVisibility(visibility);
    }

    public void setRightVisibility(int visibility) {
        mBtnRight.setVisibility(visibility);
    }

    public void setRightBtnEnabled(boolean enabled) {
        mBtnRight.setEnabled(enabled);
    }

    public void setRightImageBtnEnabled(boolean enabled) {
        mIBtnRight.setEnabled(enabled);
    }

    public void setRightText(int stringId) {
        mBtnRight.setText(stringId);
    }

    public void setLeftImageRes(int resId) {
        mIBtnLeft.setImageResource(resId);
    }

    public void setLeftImageVisibility(int visibility) {
        mIBtnLeft.setVisibility(visibility);
    }

    public void setLeftVisibility(int visibility) {
        mBtnLeft.setVisibility(visibility);
    }

    public void setLeftBtnEnabled(boolean enabled) {
        mBtnLeft.setEnabled(enabled);
    }

    public void setLeftImageBtnEnabled(boolean enabled) {
        mIBtnLeft.setEnabled(enabled);
    }

    public void setLeftText(int stringId) {
        mBtnLeft.setText(stringId);
    }

    public void setTitleVisibility(int visibility) {
        mTitleView.setVisibility(visibility);
    }

    /*public void setSecTitleVisibility(int visibility) {
        mSecTitleView.setVisibility(visibility);
    }*/
}
