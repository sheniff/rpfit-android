package com.sheniff.rpfit.app;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.util.List;

public class RpfitRestClient {
    private static final String TAG = "RpfitRestClient";
    private static final String BASE_URL = "http://10.73.197.254:1337";

    private static AsyncHttpClient client = new AsyncHttpClient();
    private static PersistentCookieStore myCookieStore;

    public static void initCookieStorage(Context context) {
        myCookieStore = new PersistentCookieStore(context);
        client.setCookieStore(myCookieStore);
    }

    public static void get(String url, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static String getCookie(String key) {
        List cookies = myCookieStore.getCookies();

        for (Object cookie : cookies) {
            Cookie c = (Cookie) cookie;
            if (c.getName().equals(key)) {
                return c.getValue();
            }
        }

        return null;
    }

    public static void deleteCookie(String key) {
        myCookieStore.addCookie(new BasicClientCookie(key, ""));
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}