package com.example.zhengboyi.soke_videotest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Login 登录界面
 * Created by ZhengBoyi on 2017/2/12.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //定义文本框、按钮、复选框、超链接
    EditText et_username, et_password;
    Button btn_login;
    ProgressDialog progressDialog;

    // 返回的数据
    private String info;
    // 返回主线程更新数据
    private static Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();
    }

    private void init() {
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (validate()) {
                    progressDialog = new ProgressDialog(this);
                    progressDialog.setTitle("提示");
                    progressDialog.setMessage("正在登陆，请稍后...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    // 创建子线程，分别进行Get和Post传输
                    new Thread(new MyThread()).start();
                }
                break;
        }
    }

    /*
    * 检验用户名和密码是否符合要求
    * 1、用户名,密码不能为空
    * */
    private boolean validate() {
        String username = et_username.getText().toString().trim();
        if (username.equals("")) {
            Toast.makeText(this, "用户账户是必填项!", Toast.LENGTH_SHORT).show();
            return false;
        }
        String password = et_password.getText().toString().trim();
        if (password.equals("")) {
            Toast.makeText(this, "用户口令是必填项!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // 子线程接收数据，主线程修改数据
    public class MyThread implements Runnable {

        @Override
        public void run() {
            info = WebServiceGet.executeHttpGet(et_username.getText().toString(), et_password.getText().toString());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(LoginActivity.this,info,Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }
}
