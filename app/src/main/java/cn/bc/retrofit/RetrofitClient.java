package cn.bc.retrofit;



import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by iscod.
 * Time:2016/6/21-9:50.
 */
public class RetrofitClient {
    private static RetrofitClient INSTANCE;
    private Retrofit retrofit;
    private static String BaseUrl;

    private RetrofitClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //自定义请求拦截器
        RequestInterceptor interceptor = new RequestInterceptor();
        //设置头
        builder.addInterceptor(interceptor);
        //官方请求拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //builder.addInterceptor(loggingInterceptor);
        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(false);
        OkHttpClient client = builder.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(ResponseConverterFactory.create(GsonConverterFactory.create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
    }

    /**
     * 默认的BaseUrl = APIConstant.BASE_URL
     *
     * @return
     */
    public static RetrofitClient getInstance() {
        BaseUrl = APIConstants.BASE_URL;
        if (INSTANCE == null) {
            synchronized (RetrofitClient.class) {
                INSTANCE = new RetrofitClient();
            }
        }
        return INSTANCE;
    }

    /**
     * 针对以后上传Base_Url会改变的情况
     *
     * @param baseUrl
     * @return
     */
    public static RetrofitClient getInstance(String baseUrl) {
        BaseUrl = baseUrl;
        if (INSTANCE == null) {
            synchronized (RetrofitClient.class) {
                INSTANCE = new RetrofitClient();
            }
        }
        return INSTANCE;
    }

    /**
     * 自定义Service
     *
     * @param service 传入自定义的Service
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }

    /**
     * 默认的service
     *
     * @return Service
     */
    public HttpService create() {
        return retrofit.create(HttpService.class);
    }

}
