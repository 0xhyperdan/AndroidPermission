package cn.bc.utils;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.Range;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.bc.retrofit.APIConstants;
import cn.ml.base.utils.MLDialogUtils;

/**
 * Created by zhouzhuo on 12/3/15.
 */
public class DownloadFileToOSS {

    private OSS oss;
    private String bucket;

    public DownloadFileToOSS(Context mContext) {
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(APIConstants.ACCESS_KEY_ID, APIConstants.ACCESS_KEY_SECRET);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        oss = new OSSClient(mContext, APIConstants.ENDPOINT, credentialProvider, conf);
        this.bucket = APIConstants.BUCKET;
    }


    public void getObject(String file) {

        // 构造下载文件请求
        GetObjectRequest get = new GetObjectRequest(bucket, file);

        try {
            // 同步执行下载请求，返回结果
            GetObjectResult getResult = oss.getObject(get);

            Log.d("Content-Length", "" + getResult.getContentLength());

            // 获取文件输入流
            InputStream inputStream = getResult.getObjectContent();

            byte[] buffer = new byte[2048];
            int len;

            while ((len = inputStream.read(buffer)) != -1) {
                // 处理下载的数据，比如图片展示或者写入文件等
                Log.d("asyncGetObjectSample", "read length: " + len);
            }
            Log.d("asyncGetObjectSample", "download success.");

            // 下载后可以查看文件元信息
            ObjectMetadata metadata = getResult.getMetadata();
            Log.d("ContentType", metadata.getContentType());


        } catch (ClientException e) {
            // 本地异常如网络异常等
            e.printStackTrace();
        } catch (ServiceException e) {
            // 服务异常
            Log.e("RequestId", e.getRequestId());
            Log.e("ErrorCode", e.getErrorCode());
            Log.e("HostId", e.getHostId());
            Log.e("RawMessage", e.getRawMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void asyncGetObject(final String fileUrl, final File file, final DownloadResultListener callBack) {
        GetObjectRequest get = new GetObjectRequest(bucket, fileUrl);

        OSSAsyncTask task = oss.asyncGetObject(get, new OSSCompletedCallback<GetObjectRequest, GetObjectResult>() {
            @Override
            public void onSuccess(GetObjectRequest request, GetObjectResult result) {
                InputStream inputStream = null;
                FileOutputStream fileOutputStream = null;
                // 请求成功
                try {
                    inputStream = result.getObjectContent();
                    byte[] buffer = new byte[2048];
                    int len;
                    fileOutputStream = new FileOutputStream(file);
                    while ((len = inputStream.read(buffer)) != -1) {
                        // 处理下载的数据
                        Log.d("asyncGetObjectSample", "read length: " + len);
                        fileOutputStream.write(buffer, 0, len);
                    }
                    if (callBack != null) {
                        callBack.downloadSuccess(file);
                    }
                    Log.d("asyncGetObjectSample", "download success.");
                } catch (IOException e) {
                    e.printStackTrace();
                    callBack.downloadFailed(e.getMessage());
                } finally {
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(GetObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    if (callBack != null) {
                        callBack.downloadFailed(serviceException.getMessage());
                    }
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
    }

    public void asyncGetObjectRange(String file) {

        GetObjectRequest get = new GetObjectRequest(bucket, file);

        // 设置范围
        get.setRange(new Range(0, 99)); // 下载0到99共100个字节，文件范围从0开始计算

        OSSAsyncTask task = oss.asyncGetObject(get, new OSSCompletedCallback<GetObjectRequest, GetObjectResult>() {
            @Override
            public void onSuccess(GetObjectRequest request, GetObjectResult result) {
                // 请求成功
                InputStream inputStream = result.getObjectContent();

                byte[] buffer = new byte[2048];
                int len;

                try {
                    while ((len = inputStream.read(buffer)) != -1) {
                        // 处理下载的数据
                        Log.d("asyncGetObjectSample", "read length: " + len);
                    }
                    Log.d("asyncGetObjectSample", "download success.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(GetObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
    }
}
