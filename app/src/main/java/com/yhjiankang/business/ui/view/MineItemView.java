package com.yhjiankang.business.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhjiankang.business.R;

/**
 * Created by maxiaobu on 2015/6/7.
 */
public class MineItemView extends LinearLayout {
    public static String TAG = "MineItemView";
    public static String NAME_SPACE = "http://schemas.android.com/apk/res-auto";

    private TextView textView;
    private ImageView imageView;
    private String title;
    private int image;

    public MineItemView(Context context) {
        super(context);

    }

    public MineItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        title = attrs.getAttributeValue(NAME_SPACE, "mine_title");
        image = attrs.getAttributeResourceValue(NAME_SPACE, "image", R.mipmap.ic_back);
        Log.i(TAG, "MineItemView: " + title + "######" + image);
        initView(attrs);

    }

    public MineItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs,defStyleAttr);


    }



    private void initView(AttributeSet attrs) {
        Log.i(TAG, "MineItemView: 构造调用");

        View.inflate(getContext(), R.layout.view_item_mine, this);
        imageView = (ImageView) findViewById(R.id.image_view);
        textView = (TextView) findViewById(R.id.text_view);

        textView.setText(title);
        imageView.setImageResource(image);
    }


}
