package com.example.simon.help;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.graphics.Typeface;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity  {

    private ViewPager viewPager;
    private ImageView[] tips;
    private ImageView[] mImageViews;
    private int[] imgIdArray ;
    private ImageButton SeeRequestButton;
    private ImageButton AddRequestButton;
    private ImageButton SearchRequestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ViewGroup group = (ViewGroup)findViewById(R.id.viewGroup);

        Typeface Mias = Typeface.createFromAsset(getAssets(), "fonts/MiasScribblings.ttf");
        TextView custom1 = (TextView)findViewById(R.id.hometitle);
        custom1.setTypeface(Mias);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        imgIdArray = new int[]{R.drawable.chicken, R.drawable.coffee, R.drawable.mai, R.drawable.dumpling};

        //�������뵽ViewGroup��
        tips = new ImageView[imgIdArray.length];
        for(int i=0; i<tips.length; i++){
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(10,10));
            tips[i] = imageView;
            if(i == 0){
                tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
            }else{
                tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
            group.addView(imageView);
        }
        //��ͼƬװ�ص�������
        mImageViews = new ImageView[imgIdArray.length];
        for(int i=0; i<mImageViews.length; i++){
            ImageView imageView = new ImageView(this);
            mImageViews[i] = imageView;
            imageView.setBackgroundResource(imgIdArray[i]);
        }

        //����Adapter
        viewPager.setAdapter(new MyAdapter());
        //���ü�������Ҫ�����õ��ı���
//        viewPager.setOnPageChangeListener(this);
        //����ViewPager��Ĭ����, ����Ϊ���ȵ�100���������ӿ�ʼ�������󻬶�
        viewPager.setCurrentItem((mImageViews.length) * 100);

        Typeface avenger = Typeface.createFromAsset(getAssets(), "fonts/The_Avengers.ttf");
        TextView custom = (TextView)findViewById(R.id.welcome);
        custom.setTypeface(avenger);

        getViews();
        processControllers();
    }

    private void getViews() {

        SeeRequestButton = (ImageButton)findViewById(R.id.see_request);
        AddRequestButton = (ImageButton)findViewById(R.id.add_request);
        SearchRequestButton = (ImageButton)findViewById(R.id.search_request);
    }

    private void processControllers() {

        //add SeeRequest button listener
        View.OnClickListener SeeRequestListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(HomeActivity.this,MainListActivity.class);
                startActivity(i);
            }
        };
        SeeRequestButton.setOnClickListener(SeeRequestListener);

        //add AddRequest button listener
        View.OnClickListener AddRequestListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(HomeActivity.this,NewRequest.class);
                startActivity(i);
            }
        };
        AddRequestButton.setOnClickListener(AddRequestListener);

        //add SearchRequest button listener
        View.OnClickListener SearchRequestListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(HomeActivity.this,Search.class);
                startActivity(i);
            }
        };
        SearchRequestButton.setOnClickListener(SearchRequestListener);
    }

    public class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager)container).removeView(mImageViews[position % mImageViews.length]);

        }

        /**
         * ����ͼƬ��ȥ���õ�ǰ��position ���� ͼƬ���鳤��ȡ�����ǹؼ�
         */
        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager)container).addView(mImageViews[position % mImageViews.length], 0);
            return mImageViews[position % mImageViews.length];
        }



    }

    public void onPageScrollStateChanged(int arg0) {

    }
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    public void onPageSelected(int arg0) {
        setImageBackground(arg0 % mImageViews.length);
    }

    /**
     * ����ѡ�е�tip�ı���
     * @param selectItems
     */
    private void setImageBackground(int selectItems){
        for(int i=0; i<tips.length; i++){
            if(i == selectItems){
                tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
            }else{
                tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
        }
    }



}







