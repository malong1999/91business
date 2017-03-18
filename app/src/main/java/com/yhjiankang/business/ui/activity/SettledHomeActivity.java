package com.yhjiankang.business.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.VolleyError;
import com.balysv.materialripple.MaterialRippleLayout;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.yhjiankang.business.App;
import com.yhjiankang.business.R;
import com.yhjiankang.business.adapter.MaterialSimpleListAdapter;
import com.yhjiankang.business.adapter.MaterialSimpleListItem;
import com.yhjiankang.business.api.HttpApi;
import com.yhjiankang.business.bean.LoginBean;
import com.yhjiankang.business.common.AppHelper;
import com.yhjiankang.business.common.Constants;
import com.yhjiankang.business.common.UIHelper;
import com.yhjiankang.business.http.IRequest;
import com.yhjiankang.business.http.RequestListener;
import com.yhjiankang.business.http.RequestParams;
import com.yhjiankang.business.ui.BaseActivity;
import com.yhjiankang.business.utils.JsonUtils;

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
 * 入驻首页
 * Created by 马小布 on 2015-06-06.
 */

public class SettledHomeActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, View.OnClickListener {

    private static final int IMAGE_REQUEST_CODE = 0;

    private static final int CAMERA_REQUEST_CODE = 1;

    private static final int RESULT_REQUEST_CODE = 2;
    @Bind(R.id.civ_head)
    CircularImageView civHead;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_state)
    TextView tvState;
    @Bind(R.id.iv_ruzhu)
    ImageView ivRuzhu;
    @Bind(R.id.mrl_ruzhu)
    MaterialRippleLayout mrlRuzhu;
    @Bind(R.id.mrl_zhaoshang)
    MaterialRippleLayout mrlZhaoshang;

    private String mFileName;

    private MaterialDialog materialDialog;

    private static final int MY_CAMMER = 122;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settledhome);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void init() {
        getUserInfo();
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

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
            civHead.setImageURI(Uri.parse(mFileName));
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
        requestParams.put(Constants.KEY_VOUCHER, App.getInstance().getLoginKey());
        requestParams.put("user_avatar", imgName);
        IRequest.post(this, HttpApi.URL_UPLOADUSERIMG, requestParams, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                AppHelper.showMessage(SettledHomeActivity.this, "上传头像成功");
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

    @OnClick({R.id.civ_head, R.id.mrl_ruzhu})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.civ_head:
                showHeadDialog();
                break;
            case R.id.mrl_ruzhu:
                UIHelper.showWebViewNoTitleActivity(this, "www.baidu.com");
                break;
            default:
                break;
        }
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        RequestParams requestParams = new RequestParams();
        requestParams.put(Constants.KEY_VOUCHER, myApplication.getLoginKey());
        IRequest.post(this, HttpApi.URL_USERINFO, requestParams, new RequestListener() {
            @Override
            public void requestSuccess(String json) {
                LoginBean loginBean = JsonUtils.object(json, LoginBean.class);
                tvName.setText(loginBean.getUsername());
//                myApplication.mImageLoader.displayImage();
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
