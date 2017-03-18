package com.yhjiankang.business.ui.activity;

import android.support.v4.view.MenuItemCompat;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.yhjiankang.business.R;
import com.yhjiankang.business.adapter.GoodsListAdapter;
import com.yhjiankang.business.bean.SearchGoodsBean;
import com.yhjiankang.business.http.IRequest;
import com.yhjiankang.business.http.RequestParams;
import com.yhjiankang.business.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 收索页面
 */
public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;

    @Bind(R.id.tvNoGoods)
    TextView tvNoGoods;

    private SearchView mSearchView;

    private InputMethodManager mImm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
//        mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        mSearchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menu_search));
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setQueryHint("搜索商品");
        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.menu_search), new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                finish();
                return true;
            }
        });
        menu.findItem(R.id.menu_search).expandActionView();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        loadData(query);
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        return false;
    }

    private GoodsListAdapter goodsListAdapter;

    private List<SearchGoodsBean.ClassesGoodsDetailedBean> goodsList = new ArrayList<SearchGoodsBean.ClassesGoodsDetailedBean>();

    /**
     * 初始化
     */
    private void initView() {
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

//        goodsListAdapter = new GoodsListAdapter();
//        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return 1;
//            }
//        });
//        recyclerView.setAdapter(goodsListAdapter);
//        goodsListAdapter = new GoodsListAdapter();
//        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return 1;
//            }
//        });
//        recyclerView.setAdapter(goodsListAdapter);

    }

    /**
     * 加载数据
     *
     * @param query
     */
    private void loadData(String query) {
        RequestParams requestParams = new RequestParams();
        /*requestParams.put("goods_name", query);
        IRequest.post(this, Constants.URL_SEARCH, requestParams, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                goodsList.clear();
                List<SearchGoodsBean.ClassesGoodsDetailedBean> list = JsonUtils.getList(json, SearchGoodsBean.ClassesGoodsDetailedBean.class);
                goodsList.addAll(list);
                goodsListAdapter.notifyDataSetChanged();
                tvNoGoods.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void requestError(VolleyError e) {
                Toast.makeText(myApplication, "暂时没有该商品", Toast.LENGTH_LONG).show();
                tvNoGoods.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        });*/
    }
}
