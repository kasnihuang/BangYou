package com.bangyou.android.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bangyou.android.R;
import com.bangyou.android.activity.OrderHandleActivity;
import com.bangyou.android.dao.OrderTypeInfo;
import com.bangyou.android.utils.Constants;
import com.bangyou.android.widget.TabView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kasni.huang on 4/26/16.
 */
public class OrderFragment extends BaseFragment {
    private static final String TAG = "OrderFragment";
    private GridView mGvOrder;
    private OrderAdapter mOrderAdapter;
    private List<OrderTypeInfo> mOrderTypeList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGvOrder = (GridView) view.findViewById(R.id.gv_order);
        mGvOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrderHandleActivity.openOrderHandleActivity(mActivity, position);
            }
        });
        generateOrderTypeList();
        mOrderAdapter = new OrderAdapter(mActivity, mOrderTypeList);
        mGvOrder.setAdapter(mOrderAdapter);
    }

    @Override
    protected void initTitleBar() {
        mTitleBar.setTitleText(R.string.tab_order);
    }

    private void generateOrderTypeList() {
        mOrderTypeList = new ArrayList<>();
        mOrderTypeList.add(new OrderTypeInfo(R.drawable.launch_bar_btn_message_normal, R.string.order_type_pending_handle, 3));
        mOrderTypeList.add(new OrderTypeInfo(R.drawable.launch_bar_btn_message_normal, R.string.order_type_pending_accept, 13));
        mOrderTypeList.add(new OrderTypeInfo(R.drawable.launch_bar_btn_message_normal, R.string.order_type_accepted, 3));
        mOrderTypeList.add(new OrderTypeInfo(R.drawable.launch_bar_btn_message_normal, R.string.order_type_done, 3));
    }

    /**
     * 更新单条未处理值
     * Position using Constants:
     * public static final int ORDER_TYPE_PENDING_HANDLE = 0;
     * public static final int ORDER_TYPE_PENDING_ACCEPT = 1;
     * public static final int ORDER_TYPE_ACCEPTED = 2;
     * public static final int ORDER_TYPE_DONE = 3;
     * @param position
     * @param count
     */
    private void updateOrderIndicator(int position, int count) {
        ((TextView)mGvOrder.getChildAt(position).findViewById(R.id.tab_indicator_counter)).setText(count);
    }

    /**
     * 批量更新未处理值
     * @param pendingHandle
     * @param pendingAccept
     * @param accepted
     * @param done
     */
    private void updateOrderIndicators(int pendingHandle, int pendingAccept, int accepted, int done) {
        if (mOrderTypeList != null && mOrderTypeList.size() > 0) {
            mOrderTypeList.get(Constants.ORDER_TYPE_PENDING_HANDLE).setCount(pendingHandle);
            mOrderTypeList.get(Constants.ORDER_TYPE_PENDING_ACCEPT).setCount(pendingAccept);
            mOrderTypeList.get(Constants.ORDER_TYPE_ACCEPTED).setCount(accepted);
            mOrderTypeList.get(Constants.ORDER_TYPE_DONE).setCount(done);
            mOrderAdapter.updateData(mOrderTypeList);
        }
    }

    private class OrderAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private List<OrderTypeInfo> orderTypeInfos;

        public OrderAdapter(Context context, List<OrderTypeInfo> list) {
            inflater = LayoutInflater.from(context);
            if (list != null && list.size() > 0) {
                orderTypeInfos = list;
            } else {
                orderTypeInfos = new ArrayList<>();
            }
        }

        public void updateData(List<OrderTypeInfo> orderTypeInfos) {
            this.orderTypeInfos = orderTypeInfos;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return orderTypeInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return orderTypeInfos.get(position);
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
                convertView = inflater.inflate(R.layout.order_item_layout, null);
                viewHolder.tvOrder = (TextView) convertView.findViewById(R.id.tab_main_text);
                viewHolder.tvIndicator = (TextView) convertView.findViewById(R.id.tab_indicator_counter);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            OrderTypeInfo info = orderTypeInfos.get(position);
            viewHolder.tvOrder.setText(info.getTextId());
            Drawable drawable = getResources().getDrawable(info.getDrawableId());
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            }
            viewHolder.tvOrder.setCompoundDrawables(null, drawable, null, null);
            if (info.getCount() > 0) {
                viewHolder.tvIndicator.setText(info.getCount() <= TabView.MAX_MESSAGES_COUNT_TO_BE_DISPLAYED ?
                        String.valueOf(info.getCount()) : getContext().getString(R.string.menu_more_than_99));
                viewHolder.tvIndicator.setVisibility(View.VISIBLE);
                try {
                    RelativeLayout.LayoutParams indicatorParams = (RelativeLayout.LayoutParams) viewHolder.tvIndicator.getLayoutParams();
                    if (info.getCount() < TabView.CHANGE_MARGIN_LIMIT) {
                        int marginRight = getResources().getDimensionPixelSize(R.dimen.dimen_5);
                        indicatorParams.setMargins(0, getResources().getDimensionPixelSize(R.dimen.dimen_3), marginRight, 0);
                    } else {
                        indicatorParams.setMargins(0, getResources().getDimensionPixelSize(R.dimen.dimen_3), 0, 0);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "OrderAdapter getView(): exception :" + e.toString(), e);
                }
            } else {
                viewHolder.tvIndicator.setVisibility(View.GONE);
            }
            return convertView;
        }

        private class ViewHolder {
            public TextView tvOrder;
            public TextView tvIndicator;
        }
    }

}
