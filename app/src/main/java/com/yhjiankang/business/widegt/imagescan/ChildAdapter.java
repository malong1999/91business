package com.yhjiankang.business.widegt.imagescan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;


import com.yhjiankang.business.R;
import com.yhjiankang.business.common.Constants;

import java.util.HashMap;
import java.util.List;


public class ChildAdapter extends BaseAdapter {
    private Point mPoint = new Point(0, 0);//用来封装ImageView的宽和高的对象
    /**
     * 用来存储图片的选中情况
     */
    private HashMap<String, String> mSelectMap = null;
    private GridView mGridView;
    private List<String> list;
    protected LayoutInflater mInflater;
    private ChildAdapterListener childAdapterListener = null;
    private String ptag;

    public ChildAdapter(Context context, List<String> list, GridView mGridView, HashMap<String, String> mSelectMap, String ptag) {
        this.list = list;
        this.mGridView = mGridView;
        this.mSelectMap = mSelectMap;
        this.ptag = ptag;
        mInflater = LayoutInflater.from(context);
    }

    public void setTabViewListener(ChildAdapterListener childAdapterListener) {
        this.childAdapterListener = childAdapterListener;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        try {
            final ViewHolder viewHolder;
            final String path = list.get(position);
            final String mapkey = ptag + "_" + position;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.imagespan_grid_child_item, null);
                viewHolder = new ViewHolder();
                viewHolder.mImageView = (MyImageView) convertView.findViewById(R.id.child_image);
                viewHolder.mCheckBox = (CheckBox) convertView.findViewById(R.id.child_checkbox);
                viewHolder.mFill = (View) convertView.findViewById(R.id.child_fill);
                //用来监听ImageView的宽和高
                viewHolder.mImageView.setOnMeasureListener(new MyImageView.OnMeasureListener() {

                    @Override
                    public void onMeasureSize(int width, int height) {
                        mPoint.set(width, height);
                    }
                });

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.mImageView.setTag(path);
            viewHolder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Boolean isChecked = viewHolder.mCheckBox.isChecked();
                    isChecked = !isChecked;
                    if (isChecked && mSelectMap.size() >= Constants.ADDGOODSIMAGE_COUNT) {
                        return;
                    }
                    selectImage(true, viewHolder, isChecked, mapkey, path);
                }
            });

            selectImage(false, viewHolder, mSelectMap.containsKey(mapkey), mapkey, path);
            //利用NativeImageLoader类加载本地图片
            Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(path, mPoint, new NativeImageLoader.NativeImageCallBack() {

                @Override
                public void onImageLoader(Bitmap bitmap, String path) {
                    ImageView mImageView = (ImageView) mGridView.findViewWithTag(path);
                    if (bitmap != null && mImageView != null) {
                        mImageView.setImageBitmap(bitmap);
                    }
                }
            });

            if (bitmap != null) {
                viewHolder.mImageView.setImageBitmap(bitmap);
            } else {
//                viewHolder.mImageView.setImageResource(R.drawable.defaultinfo);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return convertView;
    }

    private void selectImage(Boolean isSaveDate, ViewHolder viewHolder, Boolean isChecked, String mapkey, String path) {
        viewHolder.mFill.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        viewHolder.mCheckBox.setChecked(isChecked);
        if (isSaveDate) {
            if (isChecked) {
                mSelectMap.put(mapkey, path);
            } else {
                mSelectMap.remove(mapkey);
            }
            if (childAdapterListener != null) {
                childAdapterListener.onSelect(mSelectMap.size());
            }
        }
    }

    public HashMap<String, String> getSelectMap() {
        return mSelectMap;
    }

    /**
     * 给CheckBox加点击动画，利用开源库nineoldandroids设置动画
     *
     * @param view
     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    private void addAnimation(View view) {
//        float[] vaules = new float[]{0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f, 1.1f, 1.2f, 1.3f, 1.25f, 1.2f, 1.15f, 1.1f, 1.0f};
//        AnimatorSet set = new AnimatorSet();
//        set.playTogether(ObjectAnimator.ofFloat(view, "scaleX", vaules),
//                ObjectAnimator.ofFloat(view, "scaleY", vaules));
//        set.setDuration(150);
//        set.start();
//    }

    public static class ViewHolder {
        public MyImageView mImageView;
        public CheckBox mCheckBox;
        public View mFill;
    }

    public interface ChildAdapterListener {
        public void onSelect(int i);
    }

}
