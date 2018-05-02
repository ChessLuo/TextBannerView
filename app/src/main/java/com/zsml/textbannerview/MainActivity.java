package com.zsml.textbannerview;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.Toast;

import com.superluo.textbannerlibrary.ITextBannerItemClickListener;
import com.superluo.textbannerlibrary.TextBannerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private Toolbar mToolbar;

    private TextBannerView mTvBanner;
    private TextBannerView mTvBanner1;
    private TextBannerView mTvBanner2;
    private TextBannerView mTvBanner3;
    private TextBannerView mTvBanner4;
    private List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        setListener();

    }


    private void initView() {
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);


        mTvBanner = (TextBannerView) findViewById(R.id.tv_banner);
        mTvBanner1 = (TextBannerView) findViewById(R.id.tv_banner1);
        mTvBanner2 = (TextBannerView) findViewById(R.id.tv_banner2);
        mTvBanner3 = (TextBannerView) findViewById(R.id.tv_banner3);
        mTvBanner4 = (TextBannerView) findViewById(R.id.tv_banner4);
    }

    private void initData() {
        initToolBar(mToolbar, false, "");

        mList = new ArrayList<>();
        mList.add("学好Java、Android、C#、C、ios、html+css+js");
        mList.add("走遍天下都不怕！！！！！");
        mList.add("不是我吹，就怕你做不到，哈哈");
        mList.add("superluo");
        mList.add("你是最棒的，奔跑吧孩子！");
        mTvBanner.setDatas(mList);
        mTvBanner1.setDatas(mList);
        mTvBanner2.setDatas(mList);
        mTvBanner3.setDatas(mList);

        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        //设置带图标的数据；第一个参数：数据 。第二参数：drawable.  第三参数drawable尺寸。第四参数图标位置
        mTvBanner4.setDatasWithDrawableIcon(mList,drawable,18, Gravity.LEFT);

    }

    private void setListener() {
        mTvBanner.setItemOnClickListener(new ITextBannerItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {
                Toast.makeText(MainActivity.this, String.valueOf(position)+">>"+data, Toast.LENGTH_SHORT).show();
            }
        });

        mTvBanner1.setItemOnClickListener(new ITextBannerItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {
                Toast.makeText(MainActivity.this, String.valueOf(position)+">>"+data, Toast.LENGTH_SHORT).show();
            }
        });

        mTvBanner2.setItemOnClickListener(new ITextBannerItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {
                Toast.makeText(MainActivity.this, String.valueOf(position)+">>"+data, Toast.LENGTH_SHORT).show();
            }
        });

        mTvBanner3.setItemOnClickListener(new ITextBannerItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {
                Toast.makeText(MainActivity.this, String.valueOf(position)+">>"+data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mTvBanner.startViewAnimator();
//        mTvBanner1.startViewAnimator();
//        mTvBanner2.startViewAnimator();
//        mTvBanner3.startViewAnimator();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mTvBanner.stopViewAnimator();
//        mTvBanner1.stopViewAnimator();
//        mTvBanner2.stopViewAnimator();
//        mTvBanner3.stopViewAnimator();
    }

}
