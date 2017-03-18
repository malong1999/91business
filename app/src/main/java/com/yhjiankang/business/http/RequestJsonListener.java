package com.yhjiankang.business.http;

import com.android.volley.VolleyError;

/**
 *
 * JSON 数据请求接口
 */
public interface RequestJsonListener<T> {
    /**
     * 成功
     *
     */
    public void requestSuccess(T result);

    /**
     * 错误
     */
    public void requestError(VolleyError e);
}
