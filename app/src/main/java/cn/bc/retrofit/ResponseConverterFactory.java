package cn.bc.retrofit;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by iscod on 2016/6/22.
 */
public class ResponseConverterFactory extends Converter.Factory {
    private final Gson mGson;
    private final GsonConverterFactory mGsonConverterFactory;

    public static ResponseConverterFactory create(GsonConverterFactory mGsonConverterFatcory) {
        return create(new Gson(), mGsonConverterFatcory);
    }

    public static ResponseConverterFactory create(Gson mGson, GsonConverterFactory mGsonConverterFatcory) {
        return new ResponseConverterFactory(mGson, mGsonConverterFatcory);
    }

    private ResponseConverterFactory(Gson mGson, GsonConverterFactory mGsonConverterFatcory) {
        if (mGson == null) throw new NullPointerException("mGson == null");
        this.mGson = mGson;
        this.mGsonConverterFactory = mGsonConverterFatcory;
    }

    /**
     * 服务器相应处理
     * 根据具体Result API 自定义处理逻辑
     *
     * @param mType
     * @param annotations
     * @param retrofit
     * @return 返回Data相应的实体
     */
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type mType,
                                                            Annotation[] annotations,
                                                            Retrofit retrofit) {
        TypeAdapter<?> mAdapter = mGson.getAdapter(TypeToken.get(mType));
        return new BaseResponseBodyConverter<>(mGson, mAdapter, mType);//响应
    }

    /**
     * 请求处理
     * request body 我们无需特殊处理，直接返回 GsonConverterFactory 创建的 converter。
     *
     * @param mType
     * @param parameterAnnotations
     * @param methodAnnotations
     * @param retrofit
     * @return 返回 GsonConverterFactory 创建的 converter
     */
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type mType,
                                                          Annotation[] parameterAnnotations,
                                                          Annotation[] methodAnnotations,
                                                          Retrofit retrofit) {
        return mGsonConverterFactory.requestBodyConverter(mType, parameterAnnotations,
                methodAnnotations, retrofit);
    }

    /**
     * 自定义的result Api处理逻辑
     *
     * @param <T> 泛型
     */
    public class BaseResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final TypeAdapter<T> mAdapter;
        private Gson mGson;
        private Type mType;//泛型，当服务器返回的数据为数组的时候回用到

        public BaseResponseBodyConverter(Gson mGson, TypeAdapter<T> mAdapter, Type mType) {
            this.mAdapter = mAdapter;
            this.mGson = mGson;
            this.mType = mType;
        }

        /**
         * 自定义转换器-处理服务器返回数据
         *
         * @param response
         * @return 返回data的实体or列表
         * @throws IOException
         */
        @Override
        public T convert(ResponseBody response) throws IOException {
            String strResponse = response.string();
            if (TextUtils.isEmpty(strResponse)) {
                throw new HttpException("请求服务器异常");
            }
            Log.d("Request", "服务器响应:" + "············································");
            Log.d("Request", "服务器返回:" + strResponse);
            Log.d("Request", "请求结束:" + "==============================================");
            //TODO 以后重构点，不用JSONObject解析，换成Gson。
            try {
                JSONObject jb = new JSONObject(strResponse);
                // 服务器状态
                int service_state = jb.getInt("state");
                if (service_state != 1) {
                    // 服务器异常
                    throw new HttpException(jb.getString("msg"));
                }
                // 接口状态
                int ret_state = jb.getJSONObject("res").getInt("code");
                if (ret_state == 40000) {
                    if (jb.getJSONObject("res").isNull("data")) {
                        throw new HttpException("请求服务器异常");
                    }
                    String parameters = jb.getJSONObject("res").get("data").toString();
                    if (parameters.startsWith("{")) {
                        return mAdapter.fromJson(parameters);
                    } else if (parameters.startsWith("[")) {
                        List<T> list = mGson.fromJson(parameters, mType);
                        if (list.isEmpty()) {
                            throw new HttpException("暂无数据");
                        }
                        return mGson.fromJson(parameters, mType);
                    } else {
                        throw new HttpException("请求数据异常");
                    }
                } else if (ret_state == 30000) {
                    throw new HttpException(jb.getJSONObject("res").getString("msg"));
                } else {
                    // 接口异常
                    throw new HttpException(jb.getJSONObject("res").getString("msg"));
                }
            } catch (Exception e) {
                throw new HttpException(e.getMessage());
            } finally {
                response.close();
            }
        }
    }
}


