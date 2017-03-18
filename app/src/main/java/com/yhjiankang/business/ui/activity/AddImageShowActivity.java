package com.yhjiankang.business.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.yhjiankang.business.R;
import com.yhjiankang.business.ui.BaseActivity;
import com.yhjiankang.business.widegt.imagescan.ImageShow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class AddImageShowActivity extends BaseActivity {
    private ImageShow mViewPager;
    private ImageView ImageTab;
    private TextView ImageBack;
    private TextView ImageText;
    private int type = 0;
    private ArrayList<String> urls;
    private int page = 0;
    int pos;
    private PagerAdapter adapter;
    HashMap<String, String> allSelectMap;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_image_show);
        Intent intent = getIntent();
        allSelectMap = (HashMap<String, String>) intent.getSerializableExtra("imgs");
        urls = intent.getStringArrayListExtra("url");
        page = intent.getIntExtra("page", 0);
        type = intent.getIntExtra("type", 0);
        mViewPager = (ImageShow) findViewById(R.id.id_viewPager);
        ImageBack = (TextView) findViewById(R.id.deleteImageBack);
        ImageTab = (ImageView) findViewById(R.id.deleteImage);
        ImageText = (TextView) findViewById(R.id.deleteImageText);

        ImageBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.putExtra("imgs", allSelectMap);
                setResult(1, intent);
                onBackPressed();
            }
        });

        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
                ImageText.setText(arg0 + 1 + "/" + urls.size());
                pos = arg0;
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }
        });

        adapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView((View) object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                ImageView imageView = new ImageView(AddImageShowActivity.this);
                imageView.setScaleType(ScaleType.CENTER_CROP);
                myApplication.mImageLoader.displayImage(urls.get(position), imageView, myApplication.options);
                container.addView(imageView);
                mViewPager.setObjectForPosition(imageView, position);

                return imageView;
            }

            @Override
            public int getCount() {
                return urls.size();
            }
        };
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(page);
        ImageTab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                @SuppressWarnings("rawtypes")
                Iterator it = allSelectMap.entrySet().iterator();
                while (it.hasNext()) {
                    @SuppressWarnings("rawtypes")
                    Entry entry = (Entry) it.next();
                    if (type == 1) {
                        if (entry.getValue().equals(UploadGoodsActivity.itemList.get(pos).get("path"))) {
                            allSelectMap.remove(entry.getKey());
                            break;
                        }
                    } else {
                        if (entry.getValue().equals(UploadGoodsActivity.itemList.get(pos).get("path"))) {
                            allSelectMap.remove(entry.getKey());
                            break;
                        }
                    }
                }
                urls.remove(pos);
                if (type == 1) {
                    UploadGoodsActivity.itemList.remove(pos);
                } else {
                    UploadGoodsActivity.itemList.remove(pos);
                }

                mViewPager.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                if (urls.size() == 0) {
                    Intent intent = new Intent();
                    intent.putExtra("imgs", allSelectMap);
                    setResult(1, intent);
                    AddImageShowActivity.this.finish();
                }
            }
        });
    }

    @SuppressWarnings("static-access")
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            intent.putExtra("imgs", allSelectMap);
            setResult(1, intent);
            AddImageShowActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}  
