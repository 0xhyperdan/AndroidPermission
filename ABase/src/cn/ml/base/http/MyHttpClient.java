package cn.ml.base.http;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.IOException;

/*
 * 定制自己的HttpClient,确保唯一实例，提供全局访问接口
 * 自定义timeout时间等参数
 */
public class MyHttpClient {
    private static final int TIMEOUT = 15000;
    private static final int TIMEOUT_SOCKET = 15000;

    public static final String CTWAP = "ctwap";
    public static final String CMWAP = "cmwap";
    public static final String WAP_3G = "3gwap";
    public static final String UNIWAP = "uniwap";
    /**
     * @Fields TYPE_NET_WORK_DISABLED : 网络不可用
     */
    public static final int TYPE_NET_WORK_DISABLED = 0;
    /**
     * @Fields TYPE_CM_CU_WAP : 移动联通wap10.0.0.172
     */
    public static final int TYPE_CM_CU_WAP = 4;
    /**
     * @Fields TYPE_CT_WAP : 电信wap 10.0.0.200
     */
    public static final int TYPE_CT_WAP = 5;
    /**
     * @Fields TYPE_OTHER_NET : 电信,移动,联通,wifi 等net网络
     */
    public static final int TYPE_OTHER_NET = 6;
    public static Uri PREFERRED_APN_URI = Uri
            .parse("content://telephony/carriers/preferapn");

    private MyHttpClient() {
    }

    // 每次返回同一实例
    // public static synchronized HttpClient getInstance(Context mContext){
    //
    // if(null == singleStance){
    // singleStance = getNewInstance(mContext);
    // }
    // return singleStance ;
    // }

    // 每次都返回新的HttpClient实例
    public static DefaultHttpClient getNewInstance(Context mContext) {
        DefaultHttpClient newInstance;

        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params,
                HTTP.UTF_8);
        HttpProtocolParams.setUseExpectContinue(params, true);

        // 自定义三个timeout参数

		/*
         * 1.set a timeout for the connection manager,it defines how long we
		 * should wait to get a connection out of the connection pool managed by
		 * the connection manager
		 */
        ConnManagerParams.setTimeout(params, 15000);

		/*
         * 2.The second timeout value defines how long we should wait to make a
		 * connection over the network to the server on the other end
		 */
        HttpConnectionParams.setConnectionTimeout(params, TIMEOUT);

		/*
		 * 3.we set a socket timeout value to 4 seconds to define how long we
		 * should wait to get data back for our request.
		 */
        HttpConnectionParams.setSoTimeout(params, TIMEOUT_SOCKET);

        SchemeRegistry schReg = new SchemeRegistry();
        schReg.register(new Scheme("http", PlainSocketFactory
                .getSocketFactory(), 80));
        schReg.register(new Scheme("https", new EasySSLSocketFactory(), 443));

        ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
                params, schReg);

        newInstance = new DefaultHttpClient(conMgr, params);
        BasicCookieStore cookieStore = new BasicCookieStore();
        newInstance.setCookieStore(cookieStore);


//		newInstance = AndroidHttpClient.newInstance("aaa");

        switch (checkNetworkType(mContext)) {
            case TYPE_CT_WAP: {
                // 通过代理解决中国移动联通GPRS中wap无法访问的问题
                HttpHost proxy = new HttpHost("10.0.0.200", 80, "http");
                newInstance.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
                        proxy);

            }
            break;
            case TYPE_CM_CU_WAP: {
                // 通过代理解决中国移动联通GPRS中wap无法访问的问题
                HttpHost proxy = new HttpHost("10.0.0.172", 80, "http");
                newInstance.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
                        proxy);

            }
            break;
        }
        return newInstance;
    }

    public static HttpResponse execute(Context context,
                                       HttpUriRequest paramHttpUriRequest) throws
            IOException {
        // HttpClient httpClient = getNewInstance(context);
        HttpResponse response = getNewInstance(context).execute(
                paramHttpUriRequest);
//		try {
//			org.apache.http.Header[] dateHeader = response.getHeaders("Date");
//			if (dateHeader != null && dateHeader.length > 0) {
//				String value = dateHeader[0].getValue();
//				Date date = DateParser.parseRFC822(value);
//				Constants.onlineDate = date;
//			}	
//		} catch (Exception e) {
//			LogUtil.e(new Throwable().getStackTrace()[0].toString()
//					+ " Exception ", e);
//
//		}
        return response;
    }

    /**
     * 判断Network具体类型（联通移动wap，电信wap，其他net）
     */
    public static int checkNetworkType(Context mContext) {
        try {
            final ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo networkInfo = connectivityManager
                    .getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isAvailable()) {
                // 注意一：
                // NetworkInfo 为空或者不可以用的时候正常情况应该是当前没有可用网络，
                // 但是有些电信机器，仍可以正常联网，
                // 所以当成net网络处理依然尝试连接网络。
                // （然后在socket中捕捉异常，进行二次判断与用户提示）。

                return TYPE_NET_WORK_DISABLED;
            } else {
                // NetworkInfo不为null开始判断是网络类型
                int netType = networkInfo.getType();
                if (netType == ConnectivityManager.TYPE_WIFI) {
                    // wifi net处理

                    return TYPE_OTHER_NET;
                } else if (netType == ConnectivityManager.TYPE_MOBILE) {
                    // 注意二：
                    // 判断是否电信wap:
                    // 不要通过getExtraInfo获取接入点名称来判断类型，
                    // 因为通过目前电信多种机型测试发现接入点名称大都为#777或者null，
                    // 电信机器wap接入点中要比移动联通wap接入点多设置一个用户名和密码,
                    // 所以可以通过这个进行判断！
                    final Cursor c = mContext.getContentResolver().query(
                            PREFERRED_APN_URI, null, null, null, null);
                    if (c != null) {
                        c.moveToFirst();
                        final String user = c.getString(c
                                .getColumnIndex("user"));
                        if (!TextUtils.isEmpty(user)) {

                            if (user.startsWith(CTWAP)) {

                                return TYPE_CT_WAP;
                            }
                        }
                    }
                    c.close();

                    // 注意三：
                    // 判断是移动联通wap:
                    // 其实还有一种方法通过getString(c.getColumnIndex("proxy")获取代理ip
                    // 来判断接入点，10.0.0.172就是移动联通wap，10.0.0.200就是电信wap，但在
                    // 实际开发中并不是所有机器都能获取到接入点代理信息，例如魅族M9 （2.2）等...
                    // 所以采用getExtraInfo获取接入点名字进行判断
                    String netMode = networkInfo.getExtraInfo();
                    if (netMode != null) {
                        // 通过apn名称判断是否是联通和移动wap
                        netMode = netMode.toLowerCase();
                        if (netMode.equals(CMWAP) || netMode.equals(WAP_3G)
                                || netMode.equals(UNIWAP)) {

                            return TYPE_CM_CU_WAP;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return TYPE_OTHER_NET;
        }
        return TYPE_OTHER_NET;
    }

}
