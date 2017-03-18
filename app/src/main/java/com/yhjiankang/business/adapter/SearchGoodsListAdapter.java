package com.yhjiankang.business.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Project : 91健康商城
 * Program Name : com.yhjiankang.business.adapter.SearchGoodsListAdapter.java
 * Author :马庆龙
 * Creation Date : 2015-05-21 下午2:31
 * Description：收索商品列表
 * ModificationHistory ：
 */
public class SearchGoodsListAdapter extends RecyclerView.Adapter {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /*private Context context;
    *//**
     * commodity information data set
     *//*
    private List<ClassesGoodsBean.ClassesGoodsDetailedBean> goodsList;


    *//**
     * constructor
     * @param context
     * @param goodsList
     *//*
    public SearchGoodsListAdapter(Context context, List<ClassesGoodsBean.ClassesGoodsDetailedBean> goodsList) {
        this.context = context;
        this.goodsList = goodsList;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if (viewType == Constants.GOODSLIST_IMG_SMALL) {

            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_goodslist_item_small, parent, false);
            return new FirstViewHolder(v);
        } else if (viewType == Constants.GOODLIST_FOOTER_VIEW) {
            Log.e("askjdhkasjdh","alksjdlkasjdlkasjdlkjaslkdj");
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_goodslist_item_footer, parent, false);
            return new FooterViewHolder(v);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_goodslist_item_big, parent, false);
            return new SecondViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof FirstViewHolder) {
            FirstViewHolder firstViewHolder = (FirstViewHolder) holder;
            firstViewHolder.setIsRecyclable(false);
            firstViewHolder.mGoodsName.setText(goodsList.get(position).getGoods_name());
            firstViewHolder.mGoodsPrice.setText(goodsList.get(position).getGoods_price());
            String path = Constants.URL_IMAGEPATH + goodsList.get(position).getGoods_image();
            App.getInstance().mImageLoader.displayImage(path, firstViewHolder.mImageView, App.getInstance().options);
            firstViewHolder.mrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, GoodsDetailedActivity.class);
                    intent.putExtra("goodsID", goodsList.get(position).getGoods_id());
                    context.startActivity(intent);
                }
            });
        } else if (holder instanceof SecondViewHolder) {
            SecondViewHolder secondViewHolder = (SecondViewHolder) holder;
            secondViewHolder.setIsRecyclable(false);
            secondViewHolder.mGoodsName.setText(goodsList.get(position).getGoods_name());
            secondViewHolder.mGoodsPrice.setText(goodsList.get(position).getGoods_price());
            String path = Constants.URL_IMAGEPATH + goodsList.get(position).getGoods_image();
            App.getInstance().mImageLoader.displayImage(path, secondViewHolder.mImageView, App.getInstance().options);
            secondViewHolder.mrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, GoodsDetailedActivity.class);
                    intent.putExtra("goodsID", goodsList.get(position).getGoods_id());
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {

        return goodsList.size() ;
    }


    @Override
    public int getItemViewType(int position) {
        if (goodsList.get(position).getIs_recommend().equals("footer")) {
            return Constants.GOODLIST_FOOTER_VIEW;
        } else if (goodsList.get(position).getIs_recommend() == null) {
            return 0;
        }
        return Integer.parseInt(goodsList.get(position).getIs_recommend());
    }


    public class FirstViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public MaterialRippleLayout mrl;
        public TextView mGoodsName;
        public TextView mGoodsPrice;

        *//**
     * small item
     * @param v
     *//*
        public FirstViewHolder(View v) {
            super(v);
            mImageView = (ImageView) v.findViewById(R.id.ivGoods);
            mrl = (MaterialRippleLayout) v.findViewById(R.id.mrlayout);
            mGoodsName = (TextView) v.findViewById(R.id.tvName);
            mGoodsPrice = (TextView) v.findViewById(R.id.tvPrice);
        }
    }

    public class SecondViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public MaterialRippleLayout mrl;
        public TextView mGoodsName;
        public TextView mGoodsPrice;

        *//**
     * big item
     * @param v
     *//*
        public SecondViewHolder(View v) {

            super(v);
            mImageView = (ImageView) v.findViewById(R.id.ivGoods);
            mrl = (MaterialRippleLayout) v.findViewById(R.id.mrlayout);
            mGoodsName = (TextView) v.findViewById(R.id.tvName);
            mGoodsPrice = (TextView) v.findViewById(R.id.tvPrice);
        }
    }

    *//**
     * footer view
     *//*
    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }*/


}
