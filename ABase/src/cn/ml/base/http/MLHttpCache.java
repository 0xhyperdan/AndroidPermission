/*
 * Copyright (c) 2013. wyouflf (wyouflf@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.ml.base.http;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.HttpRequest;

import android.text.TextUtils;
import cn.ml.base.cache.LruMemoryCache;

/**
 * Author: wyouflf
 * Date: 13-8-1
 * Time: 下午12:04
 */
public class MLHttpCache {

    /**
     * key: url
     * value: response result
     */
    private final LruMemoryCache<String, String> mMemoryCache;

    private final static int DEFAULT_CACHE_SIZE = 1024 * 100;// string length
    private final static long DEFAULT_EXPIRY_TIME = 1000 * 60; // 60 seconds

    private int cacheSize = DEFAULT_CACHE_SIZE;

    private static long defaultExpiryTime = DEFAULT_EXPIRY_TIME;

    /**
     * HttpCache(HttpCache.DEFAULT_CACHE_SIZE, HttpCache.DEFAULT_EXPIRY_TIME);
     */
    public MLHttpCache() {
        this(MLHttpCache.DEFAULT_CACHE_SIZE, MLHttpCache.DEFAULT_EXPIRY_TIME);
    }

    public MLHttpCache(int strLength, long defaultExpiryTime) {
        this.cacheSize = strLength;
        MLHttpCache.defaultExpiryTime = defaultExpiryTime;

        mMemoryCache = new LruMemoryCache<String, String>(this.cacheSize) {
            @Override
            protected int sizeOf(String key, String value) {
                if (value == null) return 0;
                return value.length();
            }
        };
    }

    public void setCacheSize(int strLength) {
        mMemoryCache.setMaxSize(strLength);
    }

    public static void setDefaultExpiryTime(long defaultExpiryTime) {
        MLHttpCache.defaultExpiryTime = defaultExpiryTime;
    }

    public static long getDefaultExpiryTime() {
        return MLHttpCache.defaultExpiryTime;
    }

    public void put(String url, String result) {
        put(url, result, defaultExpiryTime);
    }

    public void put(String url, String result, long expiry) {
        if (url == null || result == null || expiry < 1) return;

        mMemoryCache.put(url, result, System.currentTimeMillis() + expiry);
    }
    
    public void remove(String url) {
    	if(url==null||url.equalsIgnoreCase(""))return;
        mMemoryCache.remove(url);
    }

    public String get(String url) {
        return (url != null) ? mMemoryCache.get(url) : null;
    }

    public void clear() {
        mMemoryCache.evictAll();
    }

  /*  public boolean isEnabled(HttpRequest.HttpMethod method) {
        if (method == null) return false;

        Boolean enabled = httpMethod_enabled_map.get(method.toString());
        return enabled == null ? false : enabled;
    }*/

    public boolean isEnabled(String method) {
        if (TextUtils.isEmpty(method)) return false;

        Boolean enabled = httpMethod_enabled_map.get(method.toUpperCase());
        return enabled == null ? false : enabled;
    }

/*    public void setEnabled(HttpRequest.HttpMethod method, boolean enabled) {
        httpMethod_enabled_map.put(method.toString(), enabled);
    }
*/
    private final static ConcurrentHashMap<String, Boolean> httpMethod_enabled_map;

    static {
        httpMethod_enabled_map = new ConcurrentHashMap<String, Boolean>(10);
    //    httpMethod_enabled_map.put(HttpRequest.HttpMethod.GET.toString(), true);
    }
}
