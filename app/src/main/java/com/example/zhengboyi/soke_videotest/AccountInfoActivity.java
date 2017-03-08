package com.example.zhengboyi.soke_videotest;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by ZhengBoyi on 2017/2/17.
 */

public class AccountInfoActivity extends AppCompatActivity implements View.OnClickListener {
    /*修改头像*/
    TextView tv_portrait,tv_purchasehistory,tv_recharge;
    Uri tempUri;
    Bitmap mBitmap;
    ImageView mImage;
    /*修改邮箱*/
    TextView tv_email;
    View view;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    protected static final int CROP_SMALL_PICTURE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountinfo);
        setTitle("账户信息");

        initView();
    }

    private void initView() {
        /*头像*/
        tv_portrait = (TextView) findViewById(R.id.tv_portrait);
        mImage = (ImageView) findViewById(R.id.iv_image);
        tv_portrait.setOnClickListener(this);
        /*邮箱*/
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_email.setOnClickListener(this);
        view = View.inflate(AccountInfoActivity.this, R.layout.accountinfo, null);
        /*充值*/
        tv_recharge = (TextView) findViewById(R.id.tv_recharge);
        tv_recharge.setOnClickListener(this);
        /*查看消费记录*/
        tv_purchasehistory = (TextView) findViewById(R.id.tv_purchasehistory);
        tv_purchasehistory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_portrait:
                /*显示修改图片的对话框*/
                showChoosePicDialog();
                /*裁剪图片*/
                break;
            case R.id.tv_email:
                /*显示修改邮箱对话框*/
                showModifyEmailDialog();
                break;
            case R.id.tv_recharge:
                /*显示充值界面
                * 需要支付宝接口*/
                showRechargeActivity();
                break;
            case R.id.tv_purchasehistory:
                /*显示查看消费记录界面*/
                showPurchaseHistory();
                break;
        }
    }

    private void showRechargeActivity() {
        Intent intent = new Intent(AccountInfoActivity.this,RechargeActivity.class);
        startActivity(intent);
    }

    private void showPurchaseHistory() {
        Intent intent = new Intent(AccountInfoActivity.this,PurchaseHistoryActivity.class);
        startActivity(intent);
    }

    private void showModifyEmailDialog() {
        final EditText et = new EditText(AccountInfoActivity.this);

        AlertDialog.Builder builder = new AlertDialog.Builder(AccountInfoActivity.this);
        builder.setView(et)
                .setTitle("请输入新的邮箱");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String input = et.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(AccountInfoActivity.this, "邮箱不能为空", Toast.LENGTH_SHORT);
                        } else {
                            /*跳转intent，同时销毁先前的AccountInfoActivity*/
                            Intent intent = new Intent();
                            intent.putExtra("content", input);
                            intent.setClass(AccountInfoActivity.this, AccountInfoActivity.class);
                            startActivity(intent);
                            finish();

                            /*new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    while (!Thread.currentThread().isInterrupted()) {
                                        try {
                                            Thread.sleep(100);
                                        } catch (InterruptedException e) {
                                            Thread.currentThread().interrupt();
                                        }
                                        //使用postInvalidate可以直接在线程中更新界面
                                        view.postInvalidate();
                                    }
                                }
                            }).start();*/
                        }
                    }
                }
        )
                .show();
    }

    /*显示修改图片的对话框*/

    private void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AccountInfoActivity.this);
        builder.setTitle("修改头像");
        builder.setNegativeButton("取消", null);
        String[] items = {"从手机相册选择", "拍照"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE:
                        //                        Toast.makeText(AccountInfoActivity.this,"CHOOSE_PICTURE",Toast.LENGTH_SHORT).show();
                        Intent openAlbumIntent = new Intent(Intent.ACTION_PICK);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE:
                        //                        Toast.makeText(AccountInfoActivity.this,"TAKE_PICTURE",Toast.LENGTH_SHORT).show();
                        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //                        将拍照所得的相片保存到根目录
                        tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "temp_image.jpg"));
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });
        builder.show();
    }

    /*重写onActivityResult*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AccountInfoActivity.RESULT_OK) {
            switch (requestCode) {
                case CHOOSE_PICTURE:
                    cutImage(data.getData());
                    break;
                case TAKE_PICTURE:
                    cutImage(tempUri);
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data);
                    }
                    break;
            }
        }
    }

    private void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            mBitmap = extras.getParcelable("data");
            mImage.setImageBitmap(mBitmap);
        }
    }

    /*裁剪图片大小*/
    private void cutImage(Uri uri) {
        if (uri == null) {
            Toast.makeText(AccountInfoActivity.this, "空文件", Toast.LENGTH_LONG).show();
        }
        tempUri = uri;
        //        com.android.camera.action.CROP这个action是裁剪图片用的
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }


}
