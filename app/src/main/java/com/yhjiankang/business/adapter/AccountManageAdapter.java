package com.yhjiankang.business.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yhjiankang.business.App;
import com.yhjiankang.business.R;
import com.yhjiankang.business.bean.AccountManageItemBean;
import com.yhjiankang.business.ui.activity.AccountManageActivity;
import com.yhjiankang.business.widegt.MyCheckBox;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Project : 91健康商城
 * Program Name : com.yhjiankang.business.adapter.AccountManageAdapter.java
 * Author :马庆龙
 * Creation Date : 2015-06-27 上午9:33
 * Description：
 * ModificationHistory ：
 */
public class AccountManageAdapter extends RecyclerView.Adapter {

    private ArrayList<AccountManageItemBean.ListBean> list = new ArrayList<>();

    private OnAccountItemClickListener onAccountItemClickListener;

    private AccountManageActivity accountManageActivity;

    public void setOnAccountItemClickListener(OnAccountItemClickListener onAccountItemClickListener) {
        this.onAccountItemClickListener = onAccountItemClickListener;
    }

    public AccountManageAdapter(ArrayList<AccountManageItemBean.ListBean> list, AccountManageActivity accountManageActivity) {
        this.list = list;
        this.accountManageActivity = accountManageActivity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_addaccount, parent, false);
            return new BottomViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accountmanage, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            final AccountManageItemBean.ListBean itemBean = list.get(position);
            if (itemBean.isEdit()) {
                viewHolder.cbSelect.setVisibility(View.VISIBLE);
            } else {
                viewHolder.cbSelect.setVisibility(View.GONE);
            }
            viewHolder.cbSelect.setOnCheckedChangeListener(null);
            viewHolder.cbSelect.setChecked(itemBean.isSelect());
            viewHolder.cbSelect.setOnCheckedChangeListener(new MyCheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(MyCheckBox checkBox, boolean isChecked) {
                    itemBean.setSelect(isChecked);
                    accountManageActivity.canDelete();
                }
            });
            viewHolder.tvName.setText(itemBean.getName());
            viewHolder.tvZhiwei.setText(itemBean.getPosition());
            //1 启用 0禁用
            if (itemBean.getState().equals("1")) {
                viewHolder.btnZhuangtai.setText("禁用");
                viewHolder.btnZhuangtai.setBackgroundColor(App.getInstance().getResources().getColor(R.color.red));
            } else {
                viewHolder.btnZhuangtai.setText("启用");
                viewHolder.btnZhuangtai.setBackgroundColor(App.getInstance().getResources().getColor(R.color.colorPrimary));
            }
            viewHolder.btnZhuangtai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAccountItemClickListener.onItemButtonClick(itemBean.getId(), position, itemBean.getState());
                }
            });

        } else {
            BottomViewHolder viewHolder = (BottomViewHolder) holder;
            viewHolder.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAccountItemClickListener.onAddButtonClick();
                }
            });
            viewHolder.tvCount.setText(getItemCount() - 1 + "/10");
        }

    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == list.size()) {
            return 0;
        }
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.cb_select)
        MyCheckBox cbSelect;
        @Bind(R.id.tv_zhiwei)
        TextView tvZhiwei;
        @Bind(R.id.btn_zhuangtai)
        Button btnZhuangtai;
        @Bind(R.id.tv_name)
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public class BottomViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_count)
        TextView tvCount;
        @Bind(R.id.btn_add)
        Button btnAdd;

        public BottomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnAccountItemClickListener {

        void onItemClick(String id);

        void onAddButtonClick();

        void onItemButtonClick(String id, int position, String state);
    }
}
