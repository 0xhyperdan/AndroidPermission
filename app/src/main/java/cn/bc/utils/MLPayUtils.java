package cn.bc.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;

/**
 * Created by Marcello on 2015/6/24.
 */
public class MLPayUtils {

    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;

    /**
     * 支付宝支付
     */
    public static void payForAlipay(final Activity context, final String payInfo, final Handler handler) {
        if (payInfo == null) return;
//        String sign = data.rsa_private;
//        String orderInfo ="";
//        try {
//            // 仅需对sign 做URL编码
//            orderInfo = URLDecoder.decode(data.rsa_public, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        // 完整的符合支付宝参数规范的订单信息
//        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
//                +"sign_type=\"RSA\"";

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(context);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
