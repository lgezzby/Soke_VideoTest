package com.example.zhengboyi.soke_videotest;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ZhengBoyi on 2017/3/3.
 */
/*继承自ViewPager
* 设置自动循环*/
public class PictureRotator implements ViewPager.OnPageChangeListener {
    private ViewPager vp_board;
    private Context context; //传递上下文
    private LayoutInflater inflater; //布局管理器
    private int timeout;  //多长时间切换一次pager
    //自动轮播启用开关
    private boolean isAutoPlay = true;

    List<View> views; //装载图片的View列表
    private ImageView[] dots; //圆点指示器
    private TextView tv_title;//标题显示
    private String[] titles;//显示tv_title上的String集合

    private int currentIndex; //当前索引

    Timer timer;
    TimerTask task;
    int count = 0;

    /*设置定时器,handler接受消息同时更改dot*/
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    int currentPage = (Integer) msg.obj;
                    tv_title.setText(titles[currentPage]);
                    setCurrentDot(currentPage);
                    vp_board.setCurrentItem(currentPage);
                    break;
            }
        }
    };

    public PictureRotator(Context context, LayoutInflater inflater, int timeout) {
        this.context = context;
        this.inflater = inflater;
        this.timeout = timeout;
    }

    public View initView(final List<String> datas) {
        View view = inflater.inflate(R.layout.viewpager_board, null);
        vp_board = (ViewPager) view.findViewById(R.id.vp_board);
        vp_board.setOnPageChangeListener(this);
        views = new ArrayList<View>();
        LinearLayout ll_board_dot = (LinearLayout) view.findViewById(R.id.ll_board_dot);

        titles = new String[]{"睡不好会精神分裂吗？","微笑天使","下载优酷apk有好礼相送","大家好！"};
        tv_title = (TextView) view.findViewById(R.id.tv_board_text);

        for (int i = 0; i < datas.size(); i++) {
            /*生成图片视图和圆点视图*/
            View viewpager_item_picture = inflater.inflate(R.layout.viewpager_item_picture, null);
            View viewpager_board_dot = inflater.inflate(R.layout.viewpager_board_dot, null);

            views.add(viewpager_item_picture);
            ll_board_dot.addView(viewpager_board_dot);
        }

        initDots(view, ll_board_dot); //初始化点指示器
        ViewPagerAdapter adapter = new ViewPagerAdapter(context, views, datas);
        vp_board.setOffscreenPageLimit(3); //设置viewpager保留多少个显示界面
        vp_board.setAdapter(adapter);

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                int currentPage = count % datas.size();
                count++;
                Message msg = Message.obtain();
                msg.what = 0;
                msg.obj = currentPage;
                handler.sendMessage(msg);
            }
        };
        timer.schedule(task, 0, timeout); //定时切换页面
        return view;
    }

    //初始化圆点
    private void initDots(View view, LinearLayout ll_board_dot) {
        dots = new ImageView[views.size()];
        //循环获取小圆点指示器
        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) ll_board_dot.getChildAt(i);
            dots[i].setEnabled(true); //初始化设置为true 灰色
            dots[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "点击了第" + "个圆点", Toast.LENGTH_SHORT).show();
                }
            });
        }
        currentIndex = 0;
        dots[currentIndex].setEnabled(false); //设置为false 黄色

    }

    //当页面切换时，圆点也切换
    private void setCurrentDot(int currentPage) {
        if (currentPage < 0 || currentPage > views.size() - 1 || currentIndex == currentPage)
            return;
        dots[currentPage].setEnabled(false);
        dots[currentIndex].setEnabled(true);
        currentIndex = currentPage;
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        count = i;
        setCurrentDot(i);
        tv_title.setText(titles[i]);
    }

    @Override
    public void onPageScrollStateChanged(int i) {
        switch (i) {
            case 1:// 手势滑动，空闲中
                isAutoPlay = false;
                break;
            case 2:// 界面切换中
                isAutoPlay = true;
                break;
            case 0:// 滑动结束，即切换完毕或者加载完毕
                // 当前为最后一张，此时从右向左滑，则切换到第一张
                if (vp_board.getCurrentItem() == vp_board.getAdapter().getCount() - 1 && !isAutoPlay) {
                    vp_board.setCurrentItem(0);
                }
                // 当前为第一张，此时从左向右滑，则切换到最后一张
                else if (vp_board.getCurrentItem() == 0 && !isAutoPlay) {
                    vp_board.setCurrentItem(vp_board.getAdapter().getCount() - 1);
                }
                break;
        }
    }
}
