package com.example.zhengboyi.soke_videotest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.zhengboyi.soke_videotest.volley.RequestManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /*
        初始化各组件
    */
    /*ViewPager*/
    NoScrollViewPager viewPager;
    RadioGroup radioGroup;
    RadioButton rb_recommend, rb_video, rb_chat, rb_recipe, rb_course, rb_userinfo;
    /*ToolBar*/
    Toolbar mToolBar;
    TextView mToolBarTitle;
    /*NavigationView*/
    DrawerLayout mDrawerLayout = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        /*设置置顶的Toolbar,并实现设置按钮*/
        setTitle("");
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mToolBarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolBarTitle.setText("尚课乐园");
        setSupportActionBar(mToolBar);

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        /*NavigationView部分
        * 设置抽屉DrawerLayout
        * 需要添加包依赖com.android.support:design
        */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        /*设置Navigation点击事件*/
        NavigationView mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_tv_modifypassword:
                        //                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new FragmentOne()).commit();
                        //                        mToolBar.setTitle("修改密码");
                        break;
                    case R.id.item_about:
                        //                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new FragmentTwo()).commit();
                        //                        mToolBar.setTitle("关于");
                        break;
                    case R.id.item_newversion:
                        //                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new FragmentThree()).commit();
                        //                        mToolBar.setTitle("检查新版本");
                        break;
                    case R.id.item_logout:
                        //                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new FragmentThree()).commit();
                        //                        mToolBar.setTitle("退出");
                        break;
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });



        /*
            RadioGruop部分
        */
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        rb_recommend = (RadioButton) findViewById(R.id.rb_recommend);
        rb_video = (RadioButton) findViewById(R.id.rb_video);
        rb_chat = (RadioButton) findViewById(R.id.rb_chat);
        rb_recipe = (RadioButton) findViewById(R.id.rb_recipe);
        rb_course = (RadioButton) findViewById(R.id.rb_course);
        rb_userinfo = (RadioButton) findViewById(R.id.rb_userinfo);


        //        RadioGruop选中状态改变监听
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_recommend:
                        viewPager.setCurrentItem(0, false);
                        break;
                    case R.id.rb_video:
                        viewPager.setCurrentItem(1, false);
                        break;
                    case R.id.rb_chat:
                        viewPager.setCurrentItem(2, false);
                        break;
                    case R.id.rb_recipe:
                        viewPager.setCurrentItem(3, false);
                        break;
                    case R.id.rb_course:
                        viewPager.setCurrentItem(4, false);
                        break;
                    case R.id.rb_userinfo:
                        viewPager.setCurrentItem(5, false);
                        /*跳转到用户信息Activity*/
                        TextView tv_userinfo = (TextView) viewPager.findViewById(R.id.tv_userinfo);
                        tv_userinfo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this, AccountInfoActivity.class);
                                startActivity(intent);
                            }
                        });
                        break;
                }
            }
        });

        /*
            ViewPager部分
        */
        viewPager = (NoScrollViewPager) findViewById(R.id.viewPager);

        List<Fragment> allFragment = new ArrayList<Fragment>();
        /*
        * 各界面Fragment
        * */
        RecommendFragment recommendFragment = new RecommendFragment();
        VideoFragment videoFragment = new VideoFragment();
        ChatFragment chatFragment = new ChatFragment();
        RecipeFragment recipeFragment = new RecipeFragment();
        CourseFragment courseFragment = new CourseFragment();
        UserinfoFragment userinfoFragment = new UserinfoFragment();
        allFragment.add(recommendFragment);
        allFragment.add(videoFragment);
        allFragment.add(chatFragment);
        allFragment.add(recipeFragment);
        allFragment.add(courseFragment);
        allFragment.add(userinfoFragment);

        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), allFragment));
        viewPager.setCurrentItem(0);

        //        ViewPager页面监听
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        radioGroup.check(R.id.rb_recommend);
                        break;
                    case 1:
                        radioGroup.check(R.id.rb_video);
                        break;
                    case 2:
                        radioGroup.check(R.id.rb_chat);
                        break;
                    case 3:
                        radioGroup.check(R.id.rb_recipe);
                        break;
                    case 4:
                        radioGroup.check(R.id.rb_course);
                        break;
                    case 5:
                        radioGroup.check(R.id.rb_userinfo);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
