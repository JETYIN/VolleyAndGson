package com.work.http_volley;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

/**
 * Created by Administrator on 2016/12/6.
 */
public class GsonRequest<T> extends Request<T> {


    public GsonRequest(String url, Response.ErrorListener listener) {
        super(url, listener);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        return null;
    }

    @Override
    protected void deliverResponse(T t) {

    }
}
