package com.example.zhengboyi.soke_videotest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.zhengboyi.soke_videotest.volley.RequestManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhengBoyi on 2017/2/14.
 */

public class RecommendFragment extends Fragment {
    /*轮播图
    * 需要导入Volley包支持网络图片缓存与加载*/
    LayoutInflater inflater_pic;
    LinearLayout ll_board_viewpager;
    View recommendView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recommendView = inflater.inflate(R.layout.recommend_fragment,container,false);

        inflater_pic = LayoutInflater.from(this.getActivity()); //加载布局管理器
        RequestManager.init(this.getActivity());  //volley框架的初始化
        initView();
        return recommendView;
    }

    private void initView() {
        ll_board_viewpager= (LinearLayout) recommendView.findViewById(R.id.ll_board_viewpager);
        List<String> datas=new ArrayList<String>(); //模拟加载网络的图片地址
        datas.add("http://h.hiphotos.baidu.com/image/pic/item/71cf3bc79f3df8dc668cb219ce11728b46102880.jpg");
        datas.add("http://g.hiphotos.baidu.com/image/pic/item/4bed2e738bd4b31cb966705984d6277f9f2ff8fe.jpg");
        datas.add("http://b.hiphotos.baidu.com/image/pic/item/3801213fb80e7bec85dd9ae02d2eb9389b506ba8.jpg");
        datas.add("http://b.hiphotos.baidu.com/image/pic/item/3c6d55fbb2fb4316e463e37b22a4462309f7d3b7.jpg");
        ll_board_viewpager.addView(new PictureRotator(this.getActivity(),inflater_pic,3000).initView(datas)); //这里是添加图片轮播器
    }
}
