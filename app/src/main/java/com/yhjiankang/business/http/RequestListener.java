package com.yhjiankang.business.http;

import com.android.volley.VolleyError;

/**
 * 请求监听
 */
public interface RequestListener {

    /**
     * 成功
     */
    public void requestSuccess(String json);

    /**
     * 错误
     */
    public void requestError(VolleyError e);

    /**
     * 空数据
     *
     * @param json
     */
    public void resultNoData(String json);

    /**
     * 无效的key
     *
     * @param json
     */
    public void resultiInvalidKey(String json);


}
