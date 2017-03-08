package com.example.zhengboyi.soke_videotest;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Login 登录界面
 * Created by ZhengBoyi on 2017/2/12.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //定义文本框、按钮、复选框、超链接
    EditText et_username,et_password;
    Button btn_login;
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
        switch (v.getId()){
            case R.id.btn_login:
                if (validate()){
                    if (loginPro()){

                    }
                    else{
                        
                    }
                }
                break;
        }
    }

    private boolean validate() {
        String username = et_username.getText().toString().trim();
        if (username.equals("")){
            Toast.makeText(this,"用户账户是必填项!",Toast.LENGTH_SHORT).show();
            return false;
        }
        String password = et_password.getText().toString().trim();
        if (password.equals("")){
            Toast.makeText(this,"用户口令是必填项!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean loginPro() {
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();
        return false;
    }

    private JSONObject query(String username, String password){
        //使用Map封装请求参数
        Map<String,String> map = new HashMap<String, String>();
        map.put("username",username);
        map.put("password",password);
        //定义发送请求的URL
        String url = "";
        return new JSONObject();
    }
}
