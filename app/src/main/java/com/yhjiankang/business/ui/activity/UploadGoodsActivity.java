package com.yhjiankang.business.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhjiankang.business.R;
import com.yhjiankang.business.adapter.GoodsImageAdapter;
import com.yhjiankang.business.interfaces.OnRecyclerItemClickListener;
import com.yhjiankang.business.ui.BaseActivity;
import com.yhjiankang.business.widegt.itemtouchhelp.OnStartDragListener;
import com.yhjiankang.business.widegt.itemtouchhelp.SimpleItemTouchHelperCallback;
import com.yhjiankang.business.widegt.spinner.NiceSpinner;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 上传商品
 * Created by 马小布 on 2015-06-08.
 */

public class UploadGoodsActivity extends BaseActivity implements OnStartDragListener, View.OnClickListener {

    @Bind(R.id.tv_title_common)
    TextView tvTitleCommon;
    @Bind(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @Bind(R.id.rv_image)
    RecyclerView recyclerViewImage;
    @Bind(R.id.ns_leibie)
    NiceSpinner nsLeibie;
    @Bind(R.id.ll_cpjs)
    LinearLayout llCpjs;
    @Bind(R.id.ll_tbzc)
    LinearLayout llTbzc;
    @Bind(R.id.ll_cpzs)
    LinearLayout llCpzs;
    @Bind(R.id.iv_cpjs)
    ImageView ivCpjs;
    @Bind(R.id.et_goodsname)
    EditText etGoodsname;
    @Bind(R.id.et_pinpai)
    EditText etPinpai;
    @Bind(R.id.et_shoujia)
    EditText etShoujia;
    @Bind(R.id.et_kc)
    EditText etKc;
    @Bind(R.id.et_cpms)
    EditText etCpms;
    @Bind(R.id.iv_delete_cpjs)
    ImageView ivDeleteCpjs;
    @Bind(R.id.fl_cpjs)
    FrameLayout flCpjs;
    @Bind(R.id.et_tbzc)
    EditText etTbzc;
    @Bind(R.id.iv_tbzc)
    ImageView ivTbzc;
    @Bind(R.id.iv_delete_tbzc)
    ImageView ivDeleteTbzc;
    @Bind(R.id.fl_tbzc)
    FrameLayout flTbzc;
    @Bind(R.id.iv_cpzs)
    ImageView ivCpzs;
    @Bind(R.id.iv_delete_cpzs)
    ImageView ivDeleteCpzs;
    @Bind(R.id.fl_cpzs)
    FrameLayout flCpzs;
    @Bind(R.id.et_zysx)
    EditText etZysx;
    @Bind(R.id.et_cpgg)
    EditText etCpgg;
    private ItemTouchHelper mItemTouchHelper;

    public static ArrayList<HashMap<String, String>> itemList = new ArrayList<HashMap<String, String>>();

    private HashMap<String, String> allSelectMap = new HashMap<String, String>();

    private ArrayList<String> pathList = new ArrayList<String>();

    private GoodsImageAdapter goodsImageAdapter;

    private final int CPJSCODE = 2;

    private final int TBZCCODE = 3;

    private final int CPZSCODE = 4;

    private String cpzsFileName;

    private String cpjsFileName;

    private String tbzcFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadgoods);
        ButterKnife.bind(this);
        toolbarCommon.setTitle("");
        tvTitleCommon.setText("上传商品");
        setSupportActionBar(toolbarCommon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    /**
     * 初始化
     */
    private void init() {
        List<String> dataset = new LinkedList<>(Arrays.asList("自然健康", "Two", "Three", "Four", "Five"));
        nsLeibie.attachDataSource(dataset);
        goodsImageAdapter = new GoodsImageAdapter(this, this, itemList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewImage.setHasFixedSize(false);
        recyclerViewImage.setLayoutManager(layoutManager);
        recyclerViewImage.setAdapter(goodsImageAdapter);
        recyclerViewImage.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerViewImage) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getAdapterPosition();
                if (position == itemList.size()) {
                    addPic();
                } else {
                    browseImage(position);
                }
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                if (vh.getAdapterPosition() != itemList.size()) {
                    mItemTouchHelper.startDrag(vh);
                }
            }
        });
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(goodsImageAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerViewImage);
    }

    /**
     * 添加图片
     */
    private void addPic() {
        Intent intent = new Intent(this, ImageScanMainActivity.class);
        intent.putExtra("imgs", allSelectMap);
        this.startActivityForResult(intent, 0);
    }

    /**
     * 浏览已添加图片
     *
     * @param position
     */
    private void browseImage(int position) {
        pathList.clear();
        for (int j = 0; j < itemList.size(); j++) {
            String path_add = itemList.get(j).get("path");
            if (!path_add.equals("add")) {
                pathList.add("file://" + path_add);
            }
        }
        Intent intent = new Intent();
        intent.setClass(UploadGoodsActivity.this, AddImageShowActivity.class);
        intent.putStringArrayListExtra("url", pathList);
        intent.putExtra("imgs", allSelectMap);
        intent.putExtra("page", position);
        intent.putExtra("type", 1);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        itemList.clear();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == 1) {
                    allSelectMap = (HashMap<String, String>) data.getSerializableExtra("imgs");
                    load();
                    goodsImageAdapter.notifyDataSetChanged();
                }
                break;
            case 1:
                allSelectMap = (HashMap<String, String>) data.getSerializableExtra("imgs");
                load();
                goodsImageAdapter.notifyDataSetChanged();
                break;
            case CPJSCODE:
                getImageToView(data.getData(), ivCpjs, flCpjs);
                break;
            case CPZSCODE:
                getImageToView(data.getData(), ivCpzs, flCpzs);
                break;
            case TBZCCODE:
                getImageToView(data.getData(), ivTbzc, flTbzc);
                break;
            default:
                break;
        }
    }

    /**
     * 重载图片
     */
    private void load() {
        itemList.clear();
        Iterator<Map.Entry<String, String>> entryKeyIterator = allSelectMap.entrySet().iterator();
        while (entryKeyIterator.hasNext()) {
            Map.Entry<String, String> e = entryKeyIterator.next();
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("path", e.getValue());
            new File(e.getValue());
            itemList.add(map);
        }
    }

    /**
     * 上传图片选择
     */
    private void imagePick(int requestCode) {
        Intent intentFromGallery = new Intent();
        intentFromGallery.setType("image/*"); // 设置文件类型
        intentFromGallery.setAction(Intent.ACTION_PICK);
        startActivityForResult(intentFromGallery, requestCode);
    }

    @OnClick({R.id.ll_cpjs, R.id.ll_cpzs, R.id.ll_tbzc, R.id.iv_delete_cpjs, R.id.iv_delete_cpzs, R.id.iv_delete_tbzc})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ll_cpjs:
                imagePick(CPJSCODE);
                break;
            case R.id.ll_cpzs:
                imagePick(CPZSCODE);
                break;
            case R.id.ll_tbzc:
                imagePick(TBZCCODE);
                break;
            case R.id.iv_delete_cpjs:
                deleteImage(flCpjs);
                break;
            case R.id.iv_delete_cpzs:
                deleteImage(flCpzs);
                break;
            case R.id.iv_delete_tbzc:
                deleteImage(flTbzc);
                break;
        }
    }

    /**
     * 显示图片
     *
     * @param uri
     * @param imageView
     */
    private void getImageToView(Uri uri, ImageView imageView, FrameLayout frameLayout) {
        imageView.setImageURI(uri);
        frameLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 删除图片
     *
     * @param frameLayout
     */
    private void deleteImage(FrameLayout frameLayout) {
        frameLayout.setVisibility(View.GONE);
    }
}
