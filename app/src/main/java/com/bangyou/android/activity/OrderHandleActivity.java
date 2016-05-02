package com.bangyou.android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bangyou.android.R;
import com.bangyou.android.dao.OrderInfo;
import com.bangyou.android.utils.Constants;
import com.bangyou.android.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kasni.huang on 4/26/16.
 */
public class OrderHandleActivity extends BaseActivity implements View.OnClickListener {
    private int mOrderType = Constants.ORDER_TYPE_PENDING_HANDLE;
    private ListView mLvOrder;
    private MessageAdapter mOrderHandleAdapter;
    private List<OrderInfo> mOrderInfoList;
//    private View mCallView;
//    private View mAcceptView;

    public static void openOrderHandleActivity(Context context, int orderType) {
        Intent intent = new Intent(context, OrderHandleActivity.class);
        intent.putExtra(Constants.ORDER_TYPE, orderType);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            mOrderType = intent.getIntExtra(Constants.ORDER_TYPE, Constants.ORDER_TYPE_PENDING_HANDLE);
        }
        setContentView(R.layout.order_handle_layout);
        initViews();
        mOrderInfoList = new ArrayList<>();
        mOrderHandleAdapter = new MessageAdapter(this);
        mLvOrder.setAdapter(mOrderHandleAdapter);
    }

    @Override
    protected void initTitleBar() {
        int resId = R.string.order_pending_handle;
        switch (mOrderType) {
            case Constants.ORDER_TYPE_PENDING_ACCEPT:
                resId = R.string.order_pending_accept;
                break;

            case Constants.ORDER_TYPE_ACCEPTED:
                resId = R.string.order_accepted;
                break;

            case Constants.ORDER_TYPE_DONE:
                resId = R.string.order_done;
                break;
        }
        mTitleBar.setTitleText(resId);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initViews() {
        mLvOrder = (ListView) findViewById(R.id.lv_order);
//        mCallView = findViewById(R.id.btn_call);
//        mCallView.setOnClickListener(this);
//        mAcceptView = findViewById(R.id.layout_pending_accept);
//        findViewById(R.id.btn_accept).setOnClickListener(this);
//        findViewById(R.id.btn_refuse).setOnClickListener(this);
//        updateTypeView();
    }

    private void initData() {
        mOrderInfoList.add(new OrderInfo("乐视TV机架安装1", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), false, false, mOrderType));
        mOrderInfoList.add(new OrderInfo("乐视xxxxxxxxxxxxxxxxxxTV机架安装2", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), true, false, mOrderType));
        mOrderInfoList.add(new OrderInfo("乐视TV机架安装3", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), true, false, mOrderType));
        mOrderInfoList.add(new OrderInfo("乐视TV机架安装4", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), false, false, mOrderType));
        mOrderHandleAdapter.updateData(mOrderInfoList);
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_call:
//
//                break;
//
//            case R.id.btn_accept:
//
//                break;
//
//            case R.id.btn_refuse:
//
//                break;
//        }
    }

    private class MessageAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private List<OrderInfo> messageInfos;

        public MessageAdapter(Context context) {
            inflater = LayoutInflater.from(context);
            messageInfos = new ArrayList<>();
        }

        public void updateData(List<OrderInfo> list) {
            messageInfos = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return messageInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return messageInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.order_handle_item_layout, null);
                viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
                viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_order_title);
                viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_order_timer_show);
                viewHolder.tvAddress = (TextView) convertView.findViewById(R.id.tv_order_addr_show);
                viewHolder.tvContact = (TextView) convertView.findViewById(R.id.tv_order_contact_progress);
                viewHolder.tvInstall = (TextView) convertView.findViewById(R.id.tv_order_install_progress);
                viewHolder.mProgressView = convertView.findViewById(R.id.layout_progress);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            OrderInfo info = messageInfos.get(position);
            viewHolder.tvTitle.setText(info.getOrderTitle());
            updateInstallTime(mOrderType, viewHolder.tvTime, info.getInstallTime());
            updateTypeView(mOrderType, viewHolder.ivIcon, viewHolder.mProgressView);
            viewHolder.tvAddress.setText(getString(R.string.order_install_address, info.getAddr()));
            viewHolder.tvContact.setText(info.isContacted() ? getString(R.string.order_has_contact) : getString(R.string.order_not_contact));
            viewHolder.tvInstall.setText(getString(R.string.order_not_install));
            return convertView;
        }

        private class ViewHolder {
            public ImageView ivIcon;
            public TextView tvTitle;
            public TextView tvTime;
            public TextView tvAddress;
            public TextView tvContact;
            public TextView tvInstall;
            public View mProgressView;
        }

        private void updateInstallTime(int orderType, TextView textView, Date time) {
            switch (orderType) {
                case Constants.ORDER_TYPE_PENDING_HANDLE:
                    textView.setText(getString(R.string.order_install_time, Utils.ConverToString(time)));
                    break;

                case Constants.ORDER_TYPE_PENDING_ACCEPT:
                    textView.setText(getString(R.string.order_install_time, Utils.ConverToString(time)));
                    break;

                case Constants.ORDER_TYPE_ACCEPTED:
                    textView.setText(getString(R.string.order_preinstall_time, Utils.ConverToString(time)));
                    break;
//                case Constants.ORDER_TYPE_DONE:
            }
        }

        private void updateTypeView(int orderType, ImageView imageView, View view) {
            switch (orderType) {
                case Constants.ORDER_TYPE_PENDING_HANDLE:
                    imageView.setImageResource(R.drawable.icon_order_list_pending_handle);
                    view.setVisibility(View.VISIBLE);
                    break;

                case Constants.ORDER_TYPE_PENDING_ACCEPT:
                    imageView.setImageResource(R.drawable.icon_order_list_pending_accept);
                    break;

                case Constants.ORDER_TYPE_ACCEPTED:
                    imageView.setImageResource(R.drawable.icon_order_list_accepted);
                    break;
//                case Constants.ORDER_TYPE_DONE:
            }
        }
    }
}
