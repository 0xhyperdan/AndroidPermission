package cn.bc.retrofit;


import com.baichang.huishoufang.model.ImageData;
import com.baichang.huishoufang.model.UserData;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by iscod.
 * Time:2016/6/21-9:46.
 */
public interface HttpService {


    @POST("member/memberLogin")
    Observable<UserData> login(@Body Map<String, String> Parameters);

    @POST("village/findDoorsByBuild")
    Observable<List<UserData>> getDoors(@Body Map<String, String> Parameters);

    @POST("app/findimages")
    Observable<List<ImageData>> getImage(@Body Map<String, String> Parameters);
}
