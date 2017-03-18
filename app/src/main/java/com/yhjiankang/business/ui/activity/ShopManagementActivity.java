package com.yhjiankang.business.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.VolleyError;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.yhjiankang.business.App;
import com.yhjiankang.business.R;
import com.yhjiankang.business.adapter.MaterialSimpleListAdapter;
import com.yhjiankang.business.adapter.MaterialSimpleListItem;
import com.yhjiankang.business.api.HttpApi;
import com.yhjiankang.business.bean.HomeStoreInfoBean;
import com.yhjiankang.business.common.AppHelper;
import com.yhjiankang.business.common.Constants;
import com.yhjiankang.business.common.UIHelper;
import com.yhjiankang.business.http.IRequest;
import com.yhjiankang.business.http.RequestListener;
import com.yhjiankang.business.http.RequestParams;
import com.yhjiankang.business.ui.BaseActivity;
import com.yhjiankang.business.utils.JsonUtils;
import com.yhjiankang.business.utils.TimesUtil;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 店铺管理页面
 */
public class ShopManagementActivity extends BaseActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {
    public static final String TAG = "ShopManagementActivity";
    @Bind(R.id.tv_title_common)
    TextView mTvTitleCommon;
    @Bind(R.id.toolbar_common)
    Toolbar mToolbarCommon;
    @Bind(R.id.civ_head)
    CircularImageView mCivHead;
    @Bind(R.id.tv_store_name)
    TextView mTvStoreName;
    @Bind(R.id.tv_store_state)
    TextView mTvStoreState;
    @Bind(R.id.tv_store_id)
    TextView mTvStoreId;
    @Bind(R.id.tv_business_category)
    TextView mTvBusinessCategory;
    @Bind(R.id.tv_classify_one)
    TextView mTvClassifyOne;
    @Bind(R.id.tv_classify_two)
    TextView mTvClassifyTwo;
    @Bind(R.id.tv_classify_three)
    TextView mTvClassifyThree;
    @Bind(R.id.tv_brand)
    TextView mTvBrand;
    @Bind(R.id.tv_brand_one)
    TextView mTvBrandOne;
    @Bind(R.id.tv_brand_two)
    TextView mTvBrandTwo;
    @Bind(R.id.tv_brand_three)
    TextView mTvBrandThree;
    @Bind(R.id.ll_bank_card_information)
    LinearLayout mLlBankCardInformation;
    @Bind(R.id.ll_price_management)
    LinearLayout mLlPriceManagement;
    @Bind(R.id.ll_shop_qualification)
    LinearLayout mLlShopQualification;
    @Bind(R.id.ll_shop_two_dimensional_code)
    LinearLayout mLlShopTwoDimensionalCode;
    @Bind(R.id.ll_shop_evaluation)
    LinearLayout mLlShopEvaluation;
    @Bind(R.id.ll_evaluation_commodity)
    LinearLayout mLlEvaluationCommodity;
    @Bind(R.id.tv_start_time)
    TextView mTvStartTime;
    @Bind(R.id.tv_end_time)
    TextView mTvEndTime;
    private static final int IMAGE_REQUEST_CODE = 0;

    private static final int CAMERA_REQUEST_CODE = 1;

    private static final int RESULT_REQUEST_CODE = 2;

    private HomeStoreInfoBean mHomeStoreInfoBean;

    private String mFileName;

    private MaterialDialog materialDialog;

    private static final int MY_CAMMER = 122;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_management);
        ButterKnife.bind(this);
        setCommonBackToolBar(mToolbarCommon, mTvTitleCommon, "店铺管理");
        initDta();
    }

    private void initDta() {
        String homeStoreInfo = getIntent().getStringExtra("homeStoreInfo");
        if (!"".equals(homeStoreInfo)) {
            if (!"".equals(homeStoreInfo)) {
                mHomeStoreInfoBean = JsonUtils.object(homeStoreInfo, HomeStoreInfoBean.class);
                myApplication.mImageLoader.displayImage(HttpApi.URL_IMAGEPATH + mHomeStoreInfoBean.getStore().getStore_label(), mCivHead, myApplication.options);
            } else {
                onBackPressed();
            }
            String storeName = "店铺名称：" + "<font color='green'>营业中</font>";
            mTvStoreName.setText(mHomeStoreInfoBean.getStore().getStore_name());
            mTvStoreState.setText(Html.fromHtml(storeName));
            mTvStoreId.setText(mHomeStoreInfoBean.getStore().getStore_id());
            mTvStartTime.setText(TimesUtil.timestampToStringO(mHomeStoreInfoBean.getStore().getStart_time()));
            mTvEndTime.setText(TimesUtil.timestampToStringO(mHomeStoreInfoBean.getStore().getEnd_time()));
            mTvClassifyOne.setText(mHomeStoreInfoBean.getStore().getSc_id_1());
            int scSize = mHomeStoreInfoBean.getStore().getSc_id_2().size();
            String s = "";
            for (int i = 0; i < 3; i++) {
                s = s + mHomeStoreInfoBean.getStore().getSc_id_2().get(i) + " ; ";
            }
            mTvClassifyTwo.setText(s);
            s = null;

            if (scSize > 3) {
                for (int i = 3; i < (scSize > 6 ? 6 : scSize); i++) {
                    s = s + mHomeStoreInfoBean.getStore().getSc_id_2().get(i) + " ; ";
                }
                mTvClassifyThree.setVisibility(View.VISIBLE);
                mTvClassifyThree.setText(s);
                s = "";
            }

            String s1 = "";
            for (int i = 0; i < mHomeStoreInfoBean.getStore().getBrand().size(); i++) {
                s1 = s1 + mHomeStoreInfoBean.getStore().getBrand().get(i).getGc_name() + " ; ";
            }
            mTvBrandOne.setText(s1);
            s1 = null;
            if (mHomeStoreInfoBean.getStore().getBrand().size() > 3) {
                for (int i = 3; i < 6; i++) {
                    s1 = s1 + mHomeStoreInfoBean.getStore().getBrand().get(i).getGc_name() + " ; ";

                }
                mTvBrandTwo.setVisibility(View.VISIBLE);
                mTvBrandTwo.setText(s1);
                s1 = null;
            }

            if (mHomeStoreInfoBean.getStore().getBrand().size() > 6) {
                for (int i = 6; i < 9; i++) {
                    s1 = s1 + mHomeStoreInfoBean.getStore().getBrand().get(i).getGc_name() + " ; ";
                }
                mTvBrandThree.setVisibility(View.VISIBLE);
                mTvBrandThree.setText(s1);
            }
        } else {
            this.finish();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    @OnClick({R.id.ll_bank_card_information, R.id.ll_price_management, R.id.ll_shop_qualification
            , R.id.ll_shop_two_dimensional_code, R.id.ll_shop_evaluation, R.id.ll_evaluation_commodity, R.id.civ_head})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_bank_card_information://银行卡信息
                UIHelper.showBankInformationActivity(this);
                break;
            case R.id.ll_price_management://资费管理

                break;
            case R.id.ll_shop_qualification://店铺资质

                break;
            case R.id.ll_shop_two_dimensional_code://店铺二维码
                UIHelper.showStoreTwodimensionalCodeActivity(this);
                break;
            case R.id.ll_shop_evaluation://店铺评价
                UIHelper.showStroeCommentListActivity(this);
                break;
            case R.id.ll_evaluation_commodity://商品评价
                UIHelper.showGoodsCommentListActivity(this);
                break;
            case R.id.civ_head:
                showHeadDialog();
                break;
            default:
                break;
        }
    }

    /**
     * 头像选择
     */
    private void showHeadDialog() {
        final MaterialSimpleListAdapter adapter = new MaterialSimpleListAdapter(this);
        adapter.add(new MaterialSimpleListItem.Builder(this)
                .content("相册")
                .backgroundColor(Color.WHITE)
                .build());
        adapter.add(new MaterialSimpleListItem.Builder(this)
                .content("拍照")
                .backgroundColor(Color.WHITE)
                .build());

        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title("修改头像")
                .adapter(adapter, new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        switch (which) {
                            //相册
                            case 0:
                                Intent intentFromGallery = new Intent();
                                intentFromGallery.setType("image/*"); // 设置文件类型
                                intentFromGallery.setAction(Intent.ACTION_PICK);
                                startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
                                break;
                            //拍照
                            case 1:
                                getCamera();
                                break;
                            default:
                                break;
                        }
                    }
                });
        materialDialog = builder.build();
        materialDialog.show();
    }


    @AfterPermissionGranted(MY_CAMMER)
    private void getCamera() {
        if (EasyPermissions.hasPermissions(myApplication, Manifest.permission.CAMERA)) {
            Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Constants.CACHE_DIR + "temp.jpg")));
            startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
        } else {
            EasyPermissions.requestPermissions(this, "需要相机权限",
                    MY_CAMMER, Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    /**
     * 裁剪
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    /**
     * 显示图片
     *
     * @param data
     */
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            mFileName = saveBmp(photo);
            mCivHead.setImageURI(Uri.parse(mFileName));
            saveImage();
        }
    }

    /**
     * 保存图片到本地
     *
     * @param bmp
     * @return
     */
    private String saveBmp(Bitmap bmp) {
        String fileName = Constants.CACHE_DIR + System.currentTimeMillis() + ".jpg";
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(fileName);
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.flush();
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        bmp.recycle();
        return fileName;
    }

    /**
     * 上传图片
     */
    private void saveImage() {
        try {
            FileInputStream inputStream = new FileInputStream(mFileName);
            RequestParams requestParams = new RequestParams();
            requestParams.put("file", inputStream, mFileName);
            IRequest.postImg(this, HttpApi.URL_UPLOADIMG, requestParams, new RequestListener() {
                @Override
                public void requestSuccess(String json) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String imgHead = jsonObject.getString("name");
                        App.getInstance().setStoreLablePath(imgHead);
                        updateHead(imgHead);
                    } catch (Exception e) {

                    }
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
        } catch (Exception e) {

        }
    }

    /**
     * 更改头像
     *
     * @param imgName
     */
    private void updateHead(final String imgName) {
        RequestParams requestParams = new RequestParams();
        requestParams.put(Constants.KEY_VOUCHER, Constants.KEY_TEST);
        requestParams.put("store_avatar", imgName);
        IRequest.post(this, HttpApi.URL_UPLOADUSERIMG, requestParams, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AppHelper.showMessage(ShopManagementActivity.this, "LOGO上传成功");
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_REQUEST_CODE:
                if (resultCode == this.RESULT_CANCELED) {
                } else {
                    startPhotoZoom(data.getData());
                }
                break;
            case CAMERA_REQUEST_CODE:
                if (resultCode == this.RESULT_CANCELED) {
                } else {
                    File tempFile = new File(Constants.CACHE_DIR + "temp.jpg");
                    startPhotoZoom(Uri.fromFile(tempFile));
                }
                break;
            case RESULT_REQUEST_CODE:
                if (resultCode == this.RESULT_CANCELED) {
                } else {
                    if (data != null) {
                        getImageToView(data);
                    }
                }
                break;
        }
    }
}
