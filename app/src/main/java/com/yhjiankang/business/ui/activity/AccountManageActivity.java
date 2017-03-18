package com.yhjiankang.business.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.yhjiankang.business.R;
import com.yhjiankang.business.adapter.AccountManageAdapter;
import com.yhjiankang.business.api.HttpApi;
import com.yhjiankang.business.bean.AccountManageItemBean;
import com.yhjiankang.business.bean.GoodsManageItemBean;
import com.yhjiankang.business.common.AppHelper;
import com.yhjiankang.business.common.Constants;
import com.yhjiankang.business.common.UIHelper;
import com.yhjiankang.business.http.IRequest;
import com.yhjiankang.business.http.RequestListener;
import com.yhjiankang.business.http.RequestParams;
import com.yhjiankang.business.ui.BaseActivity;
import com.yhjiankang.business.utils.DensityUtil;
import com.yhjiankang.business.utils.JsonUtils;
import com.yhjiankang.business.widegt.DividerItemDecoration;
import com.yhjiankang.business.widegt.MyCheckBox;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 子账户管理
 */

public class AccountManageActivity extends BaseActivity implements View.OnClickListener, AccountManageAdapter.OnAccountItemClickListener {
    @Bind(R.id.tv_title_common)
    TextView tvTitleCommon;
    @Bind(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.ll_nodata)
    LinearLayout llNodata;
    @Bind(R.id.tv_delete)
    TextView tvDelete;
    @Bind(R.id.fl_list)
    FrameLayout flList;
    @Bind(R.id.tv_quanxuan)
    TextView tvQuanxuan;
    @Bind(R.id.rl_edit)
    RelativeLayout rlEdit;
    @Bind(R.id.cb_select)
    MyCheckBox cbSelect;

    private ArrayList<AccountManageItemBean.ListBean> list = new ArrayList<>();
    private TranslateAnimation mShowAction, mHiddenAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountmanage);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_edit) {
            menuEdit(item);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private AccountManageAdapter accountManageAdapter;

    private void init() {
        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(500);
        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        mHiddenAction.setDuration(500);
        setCommonBackToolBar(toolbarCommon, tvTitleCommon, "账户管理");
        accountManageAdapter = new AccountManageAdapter(list, this);
        accountManageAdapter.setOnAccountItemClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL, getResources().getColor(R.color.white));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(accountManageAdapter);
        loadData();
        cbSelect.setOnCheckedChangeListener(new MyCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyCheckBox checkBox, boolean isChecked) {
                setCheckboxAll(isChecked);
            }
        });
    }

    @OnClick({R.id.tv_delete})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_delete:
                deleteAccount();
                break;
            default:
                break;
        }
    }

    /**
     * 全选设置
     *
     * @param isCheckedAll
     */
    private void setCheckboxAll(boolean isCheckedAll) {
        for (AccountManageItemBean.ListBean bean : list) {
            bean.setSelect(isCheckedAll);
        }
        canDelete();
        accountManageAdapter.notifyDataSetChanged();
    }

    /**
     * 编辑操作
     *
     * @param item
     */
    private void menuEdit(MenuItem item) {
        String title = item.getTitle().toString();
        if (title.equals("编辑")) {
            editItem(true);
            item.setTitle("取消");
            rlEdit.startAnimation(mShowAction);
            rlEdit.setVisibility(View.VISIBLE);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) flList.getLayoutParams();
            int pxTop = toolbarCommon.getHeight();
            int pxBottom = DensityUtil.dip2px(this, 40);
            lp.setMargins(0, pxTop, 0, pxBottom);
            flList.setLayoutParams(lp);
        } else {
            int pxTop = toolbarCommon.getHeight();
            item.setTitle("编辑");
            editItem(false);
            rlEdit.startAnimation(mHiddenAction);
            rlEdit.setVisibility(View.GONE);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) flList.getLayoutParams();
            lp.setMargins(0, pxTop, 0, 0);
            flList.setLayoutParams(lp);
        }
    }

    private void editItem(boolean isEdit) {
        for (AccountManageItemBean.ListBean bean : list) {
            bean.setEdit(isEdit);
        }
        accountManageAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(String id) {

    }

    @Override
    public void onAddButtonClick() {
        UIHelper.showCreateAccountActivity(this);
    }

    @Override
    public void onItemButtonClick(String id, int position, String state) {
        if (state.equals("1")) {
            closeAccountState(id, position);
        } else {
            openAccountState(id, position);
        }
    }

    private void loadData() {
        list.clear();
        RequestParams requestParams = new RequestParams();
        requestParams.put(Constants.KEY_VOUCHER, Constants.KEY_TEST);
        IRequest.post(this, HttpApi.URL_ACCOUNTLIST, requestParams, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AccountManageItemBean accountManageItemBean = JsonUtils.object(json, AccountManageItemBean.class);
                list.addAll(accountManageItemBean.getList());
                accountManageAdapter.notifyDataSetChanged();
            }

            @Override
            public void requestError(VolleyError e) {

            }

            @Override
            public void resultNoData(String json) {

            }

            @Override
            public void resultiInvalidKey(String json) {

            }
        });
    }


    /**
     * 启动
     *
     * @param id
     * @param position
     */
    private void openAccountState(String id, final int position) {
        RequestParams requestParams = new RequestParams();
        requestParams.put(Constants.KEY_VOUCHER, Constants.KEY_TEST);
        requestParams.put("id", id);
        IRequest.post(this, HttpApi.URL_OPENACCOUNT, requestParams, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                list.get(position).setState("1");
                accountManageAdapter.notifyItemChanged(position);
            }

            @Override
            public void requestError(VolleyError e) {

            }

            @Override
            public void resultNoData(String json) {

            }

            @Override
            public void resultiInvalidKey(String json) {

            }
        });
    }

    /**
     * 禁用
     *
     * @param id
     * @param position
     */
    private void closeAccountState(String id, final int position) {
        RequestParams requestParams = new RequestParams();
        requestParams.put(Constants.KEY_VOUCHER, Constants.KEY_TEST);
        requestParams.put("id", id);
        IRequest.post(this, HttpApi.URL_CLOSEACCOUNT, requestParams, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                list.get(position).setState("2");
                accountManageAdapter.notifyItemChanged(position);
            }

            @Override
            public void requestError(VolleyError e) {

            }

            @Override
            public void resultNoData(String json) {

            }

            @Override
            public void resultiInvalidKey(String json) {

            }
        });
    }


    public void canDelete() {
        for (AccountManageItemBean.ListBean bean : list) {
            if (bean.isSelect()) {
                tvDelete.setClickable(true);
                tvDelete.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            } else {
                tvDelete.setClickable(false);
                tvDelete.setTextColor(getResources().getColor(R.color.gray_light));
            }
        }
    }

    private int position;

    private void deleteAccount() {
        RequestParams requestParams = new RequestParams();
        requestParams.put(Constants.KEY_VOUCHER, Constants.KEY_TEST);
        String id = "";
        for (int i = 0; i < list.size(); i++) {
            AccountManageItemBean.ListBean bean = list.get(i);
            if (bean.isSelect()) {
                id = bean.getId();
                position = i;
            }
        }
        requestParams.put("ids", id);
        IRequest.post(this, HttpApi.URL_DELETEACCOUNT, requestParams, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AppHelper.showMessage(AccountManageActivity.this, "删除成功");
                accountManageAdapter.notifyItemRemoved(position);
                list.remove(position);
                accountManageAdapter.notifyItemChanged(list.size());
            }

            @Override
            public void requestError(VolleyError e) {

            }

            @Override
            public void resultNoData(String json) {

            }

            @Override
            public void resultiInvalidKey(String json) {

            }
        });
    }
}
