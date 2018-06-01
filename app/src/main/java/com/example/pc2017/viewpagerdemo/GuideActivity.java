package com.example.pc2017.viewpagerdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/*
* 引导界面
*
* */
public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private List<View> views;
    //底部小点图片
    private ImageView[] dots;
    //记录当前选中位置
    private int currentIndex;
    boolean isFirst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initViews();//初始化界面
        initDots();//初始化底部小点
    }

    private void initDots() {
        LinearLayout ll = findViewById(R.id.ll);
        dots = new ImageView[views.size()];
        //循环取得小点图片
        for (int i = 0;i < views.size();i++){
                dots[i] = (ImageView) ll.getChildAt(i);
                dots[i].setEnabled(true);//都设为灰色
        }
        currentIndex = 0;
        dots[currentIndex].setEnabled(false);//设置为白色，即选中状态
    }
    private void setCurrentDot(int position){
        if (position < 0 || position > views.size() -1 || currentIndex == position){
                    return;
        }
        dots[position].setEnabled(false);
        dots[currentIndex].setEnabled(true);
        currentIndex = position;
    }

    private void initViews() {
        SharedPreferences sharedPreferences = getSharedPreferences("first", Activity.MODE_PRIVATE);
        isFirst = sharedPreferences.getBoolean("status",true);
        if (!isFirst){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        LayoutInflater inflater = LayoutInflater.from(this);
        RelativeLayout guidefour = (RelativeLayout) inflater.inflate(R.layout.guide_four,null);
        guidefour.findViewById(R.id.toMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        views = new ArrayList<View>();
        //初始化引导图片列表
        views.add(inflater.inflate(R.layout.guide_one,null));
        views.add(inflater.inflate(R.layout.guide_two,null));
        views.add(inflater.inflate(R.layout.guide_three,null));
        views.add(guidefour);
        //初始化Adapter
        viewPagerAdapter = new ViewPagerAdapter(views,this);
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(viewPagerAdapter);
        //绑定回调
        viewPager.setOnPageChangeListener(this);
    }
    //当当前页面被滑动时调用
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //设置底部小点选中状态
        setCurrentDot(position);
    }
    //当新的页面被选中时调用
    @Override
    public void onPageSelected(int position) {

    }
    //当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
