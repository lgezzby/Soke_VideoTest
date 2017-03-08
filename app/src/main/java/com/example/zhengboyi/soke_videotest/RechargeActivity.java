package com.example.zhengboyi.soke_videotest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by ZhengBoyi on 2017/2/17.
 */

public class RechargeActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_alipay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recharge);
        setTitle("账户充值");

        initView();
    }

    private void initView() {
        /*去支付宝充值*/
        btn_alipay = (Button) findViewById(R.id.btn_alipay);
        btn_alipay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*调用支付宝接口*/
            case R.id.btn_alipay:
                break;
        }
    }
}
