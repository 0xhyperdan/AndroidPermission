package com.baichang.huishoufang.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by iscod.
 * Time:2016/7/4-9:20.
 */
public class UserInfoDetailData implements Serializable {
    @Expose
    public String auditIdcard;          //身份证是否合身  true
    @Expose
    public String kid;                  //kid
    @Expose
    public String currentIntegral;      //总积分
    @Expose
    public String dengji_persion;       //登记人姓名
    @Expose
    public String auditMobile;          //手机号是否审核
    @Expose
    public String tijianpoint;          //体检消耗积分
    @Expose
    public String dengji_date;          //登记时间
    @Expose
    public String paymethod;            //默认提现方式
    @Expose
    public String walletBalance;        //余额
    @Expose
    public String villageKid;           //小区kid
    @Expose
    public String auditAddr;            //地址是否审核
    @Expose
    public String id;                   //
    @Expose
    public String committee_kid;        //居委kid
    @Expose
    public String huzhulink;            //户主关系
    @Expose
    public String email;                //邮箱
    @Expose
    public String picpath;              //头像
    @Expose
    public String familyCode;           //家庭编号
    @Expose
    public String created;              //注册日期
    @Expose
    public String sex;                  //性别
    @Expose
    public String mobile;               //手机号
    @Expose
    public String userName;             //姓名
    @Expose
    public String doornum;              //门牌号
    @Expose
    public String station_kid;          //服务站
    @Expose
    public String build_kid;            //楼号kid
    @Expose
    public String idcard;               //身份证
    @Expose
    public String account;              //账号
    @Expose
    public String status;               //
    @Expose
    public String doorname;             //门牌
    @Expose
    public String villagename;          //小区
    @Expose
    public String buildname;            //楼名称
    @Expose
    public String dayinpoint;           //打印扣除的积分
    @Expose
    public String fuyinpoint;          //复印扣除的积分
    @Expose
    public String address;
}
