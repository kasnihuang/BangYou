package com.bangyou.android.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bangyou.android.R;
import com.bangyou.android.dao.OrderInfo;
import com.bangyou.android.datalayer.DataService;
import com.bangyou.android.utils.Constants;
import com.bangyou.android.utils.Utils;
import com.bangyou.android.widget.DateTimeDialogPicker;

import java.util.Date;


/**
 * Created by kasni.huang on 4/26/16.
 */
public class OrderDetailActivity extends BaseActivity implements View.OnClickListener {
    private OrderInfo mOrderInfo;
    private TextView mTvName;
    private TextView mTvPhone;
    private TextView mTvAdress;
    private TextView mTvWallType;
    private TextView mTvHangType;
    private TextView mTvTvSize;
    private TextView mTvPreInstallTime;
    private TextView mTvSuggestTime;
    private TextView mTvRemain;
    private View mViewContact;
    private View mViewInstall;
    private View mViewPhone;
    private View mViewTime;
    private View mViewProgressTip;
    private View mViewProgress;
    private View mViewHandle;
    private View mViewButton;
    private View mDialogView;
    private Button mBtnAccept;
    private Button mBtnRefuse;
    private Button mBtnRob;
    private Button mBtnDone;
    private AlertDialog mCodeDialog;

    private boolean mIsRob = false;

    public static void openOrderDetailActivity(Context context, OrderInfo orderInfo, boolean isRob) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra(Constants.ORDER_INFO, orderInfo);
        intent.putExtra(Constants.ORDER_IS_ROB, isRob);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            mOrderInfo = (OrderInfo) intent.getSerializableExtra(Constants.ORDER_INFO);
            mIsRob = intent.getBooleanExtra(Constants.ORDER_IS_ROB, false);
        }
        setContentView(R.layout.order_detail_layout);
        initViews();
        initData();
    }

    @Override
    protected void initTitleBar() {
        mTitleBar.setTitleText(R.string.order_detail);
        if (mOrderInfo != null) {
            mTitleBar.setSecTitleText(mOrderInfo.getOrderTitle());
        }
    }

    //TODO 在initviews之前先把数据准备好,在initviews里面可以直接对TextView.setText(xxx)设置数据
    private void initData() {
        mTvName.setText(mOrderInfo.getContactUser());
        mTvPhone.setText(mOrderInfo.getTeleNo());
        mTvAdress.setText(mOrderInfo.getAddr());
        //wall
        //mTvWallType.setText();
        // mTvHangType.setText();
        //
        //mTvTvSize.setText();
        mTvPreInstallTime.setText(Utils.ConverToString(mOrderInfo.getInstallTime(), Constants.TIME_FORMAT));

        mTvSuggestTime.setText(Utils.subDate(mOrderInfo.getInstallTime(), 24,Constants.TIME_FORMAT));
        mTvRemain.setText(Utils.disTime(mOrderInfo.getInstallTime(), new Date()));
    }

    private void initViews() {
        mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mTvAdress = (TextView) findViewById(R.id.tv_address);
        mTvWallType = (TextView) findViewById(R.id.tv_wall);
        mTvHangType = (TextView) findViewById(R.id.tv_hang);
        mTvTvSize = (TextView) findViewById(R.id.tv_tv);
        mTvPreInstallTime = (TextView) findViewById(R.id.tv_time);
        mTvSuggestTime = (TextView) findViewById(R.id.tv_suggest_time);
        mTvRemain = (TextView) findViewById(R.id.tv_remain);
        mBtnAccept = (Button) findViewById(R.id.btn_accept);
        mBtnAccept.setOnClickListener(this);
        mBtnRefuse = (Button) findViewById(R.id.btn_refuse);
        mBtnRefuse.setOnClickListener(this);
        mBtnDone = (Button) findViewById(R.id.btn_done);
        mBtnDone.setOnClickListener(this);
        mBtnRob = (Button) findViewById(R.id.btn_rob_order);
        mBtnRob.setOnClickListener(this);
        mViewContact = findViewById(R.id.view_contact);
        mViewInstall = findViewById(R.id.view_install);
        mViewPhone = findViewById(R.id.layout_phone);
        mViewPhone.setOnClickListener(this);
        mViewTime = findViewById(R.id.layout_time);
        mViewTime.setOnClickListener(this);
        mViewProgressTip = findViewById(R.id.layout_progress_tip);
        mViewProgress = findViewById(R.id.layout_progress_diagram);
        mViewHandle = findViewById(R.id.layout_handle);
        mViewButton = findViewById(R.id.layout_button);
        if (mOrderInfo != null) {
            handleViewByType(mOrderInfo.getOrderType());
            if (mOrderInfo.isContacted()) {
                mViewContact.setBackgroundResource(R.drawable.order_detail_blue_left_progress);
            } else {
                mViewContact.setBackgroundResource(R.drawable.order_detail_gray_left_progress);
            }
        }
    }

    private void handleViewByType(int type) {
        switch (type) {
            case Constants.ORDER_TYPE_PENDING_HANDLE:
            case Constants.ORDER_TYPE_ACCEPTED:
                mViewButton.setVisibility(View.GONE);
                break;

            case Constants.ORDER_TYPE_PENDING_ACCEPT:
                mViewPhone.setVisibility(View.GONE);
                mViewProgressTip.setVisibility(View.GONE);
                mViewProgress.setVisibility(View.GONE);
                mBtnDone.setVisibility(View.GONE);
                //TODO 根据是否是抢单来显示不同的按钮
                if (mIsRob) {
                    mViewButton.setVisibility(View.GONE);
                } else {
                    mBtnRob.setVisibility(View.GONE);
                }
                break;

            case Constants.ORDER_TYPE_GRAB:
                mViewPhone.setVisibility(View.GONE);
                mViewProgressTip.setVisibility(View.GONE);
                mViewProgress.setVisibility(View.GONE);
                //TODO 根据是否是抢单来显示不同的按钮
                mViewHandle.setVisibility(View.GONE);
                mBtnDone.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_phone:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+
                        mTvPhone.getText()));
                startActivity(intent);
                mOrderInfo.setContacted(true);
                DataService.Instance().addOrder(mOrderInfo);
                DataService.Instance().saveOrder(OrderDetailActivity.this);
                break;

            case R.id.layout_time:
                String time = mTvPreInstallTime.getText().toString();//.split("-");
                DateTimeDialogPicker dateTimePicKDialog = new DateTimeDialogPicker(
                        this, time);
                dateTimePicKDialog.dateTimePicKDialog(mTvPreInstallTime);
                /*Calendar calendarFrom = Calendar.getInstance();
                DatePickerDialog datePickerDialogFrom = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override

                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        //更新EditText控件日期 小于10加0
                        mTvPreInstallTime.setText(new StringBuilder().append(year).append("年")
                                .append((month + 1) < 10 ? "0" + (month + 1) : (month + 1))
                                .append("月")
                                .append((day < 10) ? "0" + day : day) );
                    }
                }, calendarFrom.get(Calendar.YEAR), calendarFrom.get(Calendar.MONTH),
                        calendarFrom.get(Calendar.DAY_OF_MONTH));
                datePickerDialogFrom.getDatePicker().setCalendarViewShown(false);
                datePickerDialogFrom.show();*/
                break;

            case R.id.btn_accept:

                break;

            case R.id.btn_refuse:

                break;

            case R.id.btn_rob_order:

                break;

            case R.id.btn_done:
                if (mCodeDialog == null) {
                    initDialog();
                }
                mCodeDialog.show();
                final EditText editText = (EditText) mDialogView.findViewById(R.id.et_code);
                mCodeDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String code = editText.getText().toString();
                        if (TextUtils.isEmpty(code)) {
                            editText.requestFocus();
                            Toast.makeText(OrderDetailActivity.this, R.string.order_input_verify_code, Toast.LENGTH_LONG).show();
                        } else {
                            if (isCodeInvalid(editText.getText().toString())) {
                                editText.setText("");
                                mCodeDialog.dismiss();
                            } else {
                                Toast.makeText(OrderDetailActivity.this, R.string.order_error_verify_code, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
                break;
        }
    }

    private void initDialog() {
        mCodeDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.order_verify_code)
                .setView(mDialogView)
                .setPositiveButton(R.string.sure, null).create();
    }

    private boolean isCodeInvalid(String code) {
        //Todo 在此处增加判断验证码是否正确的代码,当前默认返回true
        return true;
    }
}
