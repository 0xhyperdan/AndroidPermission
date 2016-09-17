package com.baichang.huishoufang.JPush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;
import cn.ml.base.MLBaseConstants;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[JPushReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[JPushReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            //processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[JPushReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[JPushReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[JPushReceiver] 用户点击打开了通知");
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
//            if (TextUtils.equals("投诉建议", title)) {
//                Intent i = new Intent(context, MeSettingSuggestActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(i);
//            } else if (TextUtils.equals("快递代收", title)) {
//                Intent i = new Intent(context, ExpressActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(i);
//            } else if (TextUtils.equals("快递代发", title)) {
//                Intent i = new Intent(context, ExpressActivity.class);
//                i.putExtra(MLBaseConstants.TAG_INTENT_DATA, true);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(i);
//            } else if (TextUtils.equals("待取餐提示", title) || TextUtils.equals("已取餐提示", title)) {
//                Intent i = new Intent(context, MeOrderingActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(i);
//            } else if (TextUtils.equals("纪念日提醒", title)) {
//                Intent i = new Intent(context, MeOurMarkActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(i);
//            } else if (TextUtils.equals("体检提醒", title)) {
//
//            } else {
//                Intent i = new Intent(context, JPushMessageActivity.class);
//                String url = getUrl(bundle);
//                i.putExtra("URL", url);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(i);
//            }

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[JPushReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[JPushReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[JPushReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }
                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    private String getUrl(Bundle bundle) {
        String url = "";
        if (bundle != null) {
            if (!bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    url = json.getString("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return url;
    }
    //send msg to MainActivity
   /* private void processCustomMessage(Context context, Bundle bundle) {
        if (MainActivity.isForeground) {
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
            if (!ExampleUtil.isEmpty(extras)) {
                try {
                    JSONObject extraJson = new JSONObject(extras);
                    if (null != extraJson && extraJson.length() > 0) {
                        msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
                    }
                } catch (JSONException e) {

                }

            }
            context.sendBroadcast(msgIntent);
        }
    }*/
}
