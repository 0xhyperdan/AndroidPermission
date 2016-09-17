package cn.bc.utils;



import com.baichang.huishoufang.model.UserData;
import com.baichang.huishoufang.model.UserInfoDetailData;

import cn.bc.base.APP;

public class MLAppDiskCache {
    private static UserInfoDetailData userInfoDetail;

    /**
     * 用户的token
     *
     * @param data
     */
    public static void setToken(String data) {
        APP.aCache.put(MLConstants.ACACHE_TOKEN, data);
    }

    public static String getToken() {
        Object obj = APP.aCache.getAsString(MLConstants.ACACHE_TOKEN);
        if (obj == null) return null;
        return (String) obj;
    }

    /**
     * 用户登陆信息
     *
     * @param data
     */
    public static void setUser(UserData data) {
        APP.aCache.put(MLConstants.ACACHE_CM_USER, data);
    }

    public static UserData getUser() {
        Object obj = APP.aCache.getAsObject(MLConstants.ACACHE_CM_USER);
        if (obj == null) return null;
        return (UserData) obj;
    }

    /**
     * 清除用户信息
     */
    public static void cleanUser() {
        APP.aCache.remove(MLConstants.ACACHE_CM_USER);
    }

    /**
     * 设置会员信息
     *
     * @param userInfoDetail
     */
    public static void setUserInfoDetail(UserInfoDetailData userInfoDetail) {
        APP.aCache.put(MLConstants.ACACHE_CM_USER_INFO, userInfoDetail);
    }

    public static UserInfoDetailData getUserInfoDetail() {
        Object obj = APP.aCache.getAsObject(MLConstants.ACACHE_CM_USER_INFO);
        if (obj == null) return null;
        return (UserInfoDetailData) obj;
    }

    /**
     * 引导页时间存储
     */
    public static void setInstallFirstTime(String time) {
        APP.aCache.put(MLConstants.ACACHE_CM_INSTALL_FIREST_STATE, time);
    }

    public static String getInstallFirstTime() {
        String str = APP.aCache.getAsString(MLConstants.ACACHE_CM_INSTALL_FIREST_STATE);
        if (str == null) return "";
        return str;
    }

    public static void cleanInstallFirstTime() {
        APP.aCache.remove(MLConstants.ACACHE_CM_INSTALL_FIREST_STATE);
    }
}
