package com.bangyou.android.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bangyou.android.R;
import com.bangyou.android.activity.OrderDetailActivity;
import com.bangyou.android.dao.MessageInfo;
import com.bangyou.android.datalayer.DataService;
import com.bangyou.android.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kasni.huang on 4/26/16.
 */
public class MessageFragment extends BaseFragment {
    private ListView mLvMessage;
    private MessageAdapter mMessageAdapter;
    private List<MessageInfo> mMessageInfoList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMessageInfoList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     return inflater.inflate(R.layout.message_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLvMessage = (ListView) view.findViewById(R.id.lv_message);
        mLvMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageInfo info = (MessageInfo) mMessageAdapter.getItem(position);
                if (info.getMessageType() == MessageInfo.MSG_TYPE_ROB) {
                  //  Intent intent = new Intent(mActivity, OrderDetailActivity.class);
                  //  intent.putExtra(Constants.ORDER_IS_ROB, true);
                    //startActivity(intent);
                   // OrderDetailActivity.openOrderDetailActivity(mActivity,info,true);
                }
            }
        });
        mMessageAdapter = new MessageAdapter(mActivity);
        mLvMessage.setAdapter(mMessageAdapter);
    }

    @Override
    protected void initTitleBar() {
        mTitleBar.setTitleText(R.string.tab_message);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        //TODO 如果正式数据时,且更新数据放到onResume中,如果数据是全新的数据,记得先清除当前数据,因为tab切换到第三屏再切回来时,onResume会被调用
//        mMessageInfoList.add(new MessageInfo("你已完成订单:乐视TV机架安装1", "新安装", MessageInfo.MSG_TYPE_NORMAL));
//        MessageInfo info = new MessageInfo("XX品牌代言服务", "新安装", MessageInfo.MSG_TYPE_ROB);
//        mMessageInfoList.add(0, info);
//        mMessageInfoList.add(new MessageInfo("你已完成订单:乐视xxxxxxxxxxxxxxxxxxTV机架安装2", "移机, 安装", MessageInfo.MSG_TYPE_NORMAL));
//        mMessageInfoList.add(new MessageInfo("你已完成订单:乐视TV机架安装3", "消息概要", MessageInfo.MSG_TYPE_NORMAL));
//        mMessageInfoList.add(new MessageInfo("你已完成订单:乐视TV机架安装4", "安装", MessageInfo.MSG_TYPE_NORMAL));
        mMessageInfoList = DataService.Instance().getMessages();
        mMessageAdapter.updateData(mMessageInfoList);
    }

    private class MessageAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private List<MessageInfo> messageInfos;

        public MessageAdapter(Context context) {
            inflater = LayoutInflater.from(context);
            messageInfos = new ArrayList<>();
        }

        public void updateData(List<MessageInfo> list) {
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
                convertView = inflater.inflate(R.layout.message_item_layout, null);
                viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
                viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_message_content);
                viewHolder.tvDetail = (TextView) convertView.findViewById(R.id.tv_message_detail);
                viewHolder.tvType = (TextView) convertView.findViewById(R.id.tv_message_type);
                viewHolder.btnRob = (Button) convertView.findViewById(R.id.btn_rob_order);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            MessageInfo info = messageInfos.get(position);
            viewHolder.tvContent.setText(info.getTitle());
            viewHolder.tvDetail.setText(info.getSubtitle());
            if (info.getMessageType() == MessageInfo.MSG_TYPE_ROB) {
                viewHolder.ivIcon.setImageResource(R.drawable.icon_message);
                viewHolder.tvType.setText(R.string.tab_order);
                viewHolder.btnRob.setVisibility(View.VISIBLE);
                viewHolder.btnRob.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        messageInfos.remove(position);
                        notifyDataSetChanged();
                    }
                });
            } else {
                viewHolder.tvType.setText(R.string.tab_message);
                viewHolder.ivIcon.setImageResource(R.drawable.icon_order);
                viewHolder.btnRob.setVisibility(View.GONE);
            }
            return convertView;
        }

        private class ViewHolder {
            public ImageView ivIcon;
            public TextView tvContent;
            public TextView tvDetail;
            public TextView tvType;
            public Button btnRob;
        }
    }
}
