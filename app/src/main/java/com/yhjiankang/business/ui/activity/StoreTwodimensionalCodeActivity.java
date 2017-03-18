package com.yhjiankang.business.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yhjiankang.business.R;
import com.yhjiankang.business.api.HttpApi;
import com.yhjiankang.business.common.Constants;
import com.yhjiankang.business.http.IRequest;
import com.yhjiankang.business.http.RequestListener;
import com.yhjiankang.business.http.RequestParams;
import com.yhjiankang.business.ui.BaseActivity;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 店铺二维码
 */
public class StoreTwodimensionalCodeActivity extends BaseActivity {
    public static String TAG = "StoreTwodimensionalCodeActivity";

    @Bind(R.id.tv_title_common)
    TextView mTvTitleCommon;
    @Bind(R.id.toolbar_common)
    Toolbar mToolbarCommon;
    @Bind(R.id.iv_two_dimensional)
    ImageView mIvTwoDimensional;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_twodimensional_code);
        ButterKnife.bind(this);
        setCommonBackToolBar(mToolbarCommon, mTvTitleCommon, "店铺二维码");

        initData();
//        loadData();
        try {
            Bitmap bitmap = getImageBitmap();
            mIvTwoDimensional.setImageBitmap(bitmap);
        } catch (Exception e) {

        }

    }

    private void initData() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void loadData() {
        RequestParams requestParams = new RequestParams();
        requestParams.put(Constants.KEY_VOUCHER, Constants.KEY_TEST);
        IRequest.post(this, HttpApi.URL_TWO_DIMENSIONAL, requestParams, new RequestListener() {
            @Override
            public void requestSuccess(String json) {

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

    public Bitmap getImageBitmap() throws Exception {
        Bitmap bitmap = null;
        List<NameValuePair> list = new ArrayList<NameValuePair>();      //封装请求体参数
        NameValuePair pair = new BasicNameValuePair(Constants.KEY_VOUCHER
                , Constants.KEY_TEST);
        list.add(pair);

        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, HTTP.UTF_8);    //对请求体参数进行URL编码
            HttpPost httpPost = new HttpPost(HttpApi.URL_TWO_DIMENSIONAL);           //创建一个POST方式的HttpRequest对象
            httpPost.setEntity(entity);                       //设置POST方式的请求体
            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse httpResponse = client.execute(httpPost);                      //执行POST请求
            int reponseCode = httpResponse.getStatusLine().getStatusCode();            //获得服务器的响应码
            if (reponseCode == HttpStatus.SC_OK) {
                InputStream inputStream = httpResponse.getEntity().getContent();   //获得服务器的响应内容
                bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;


    }

}
