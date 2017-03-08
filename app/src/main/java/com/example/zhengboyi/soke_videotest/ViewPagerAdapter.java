package com.example.zhengboyi.soke_videotest;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by ZhengBoyi on 2017/3/3.
 */
public class ViewPagerAdapter extends PagerAdapter {
    private Context context; //上下文对象
    private List<String> datas;// 数据源
    private List<View> views;

    public ViewPagerAdapter(Context context, List<View> views, List<String> datas) {
        this.context = context;
        this.views = views;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final int index = position;
        View view = views.get(position);
        ((ViewPager) container).addView(view);
        try {
            String url = datas.get(position);
            ImageView iv_item_picture = (ImageView) view.findViewById(R.id.iv_item_picture);
            ImageLoaderUtil.getImage(context, iv_item_picture, url, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            iv_item_picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(context, "第" + index + "张图片", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}
