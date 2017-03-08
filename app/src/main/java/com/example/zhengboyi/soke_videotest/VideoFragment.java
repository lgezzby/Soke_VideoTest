package com.example.zhengboyi.soke_videotest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhengBoyi on 2017/2/14.
 */

public class VideoFragment extends Fragment {

    ViewPager videoViewPager;
    RadioGroup videoRadioGroup;
    RadioButton rb_live, rb_VOD;
    View videoView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        videoView = inflater.inflate(R.layout.video_fragment, container, false);

        initView();
        return videoView;
    }

    private void initView() {
        videoViewPager = (ViewPager) videoView.findViewById(R.id.videoViewPager);
        videoRadioGroup = (RadioGroup) videoView.findViewById(R.id.rg_video);
        rb_live = (RadioButton) videoView.findViewById(R.id.rb_live);
        rb_VOD = (RadioButton) videoView.findViewById(R.id.rb_VOD);

        /*videoRadioGroup部分*/
        videoRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_live:
                        videoViewPager.setCurrentItem(0, false);
                        break;
                    case R.id.rb_VOD:
                        videoViewPager.setCurrentItem(1, false);
                        break;
                }
            }
        });

        /*videoViewPager部分(不滑动)*/
        List<Fragment> videoFragement = new ArrayList<Fragment>();
        LiveFragment liveFragment = new LiveFragment();
        VODFragment vodFragment = new VODFragment();
        videoFragement.add(0, liveFragment);
        videoFragement.add(1, vodFragment);

        videoViewPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), videoFragement));
        videoViewPager.setCurrentItem(0);
        videoViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        videoRadioGroup.check(R.id.rb_live);
                        break;
                    case 1:
                        videoRadioGroup.check(R.id.rb_VOD);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }
}
