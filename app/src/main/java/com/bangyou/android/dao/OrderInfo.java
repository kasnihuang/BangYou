package com.bangyou.android.dao;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by kasni.huang on 4/27/16.
 */
public class OrderInfo {
    private String orderTitle;
    private int orderType;

    public OrderInfo() {
    }

    public String getContactUser() {
        return contactUser;
    }

    public void setContactUser(String contactUser) {
        this.contactUser = contactUser;
    }

    public boolean isInstalled() {
        return installed;
    }

    public void setInstalled(boolean installed) {
        this.installed = installed;
    }

    public boolean isContacted() {
        return contacted;
    }

    public void setContacted(boolean contacted) {
        this.contacted = contacted;
    }

    private String uuid;

    public enum State {
        NO_ACCEPTTED,
        ACCEPTED
    }

    public enum WallType {
        MarbleWall,    //大理石墙
        ceramicileWall,     //瓷砖墙
        WoodenWall,   //木板墙
        BearingWall,  //称重墙
        otherWall
    }

    public enum SkuType {
        FunctionPendant,
        NoramlPendant,
        HangerPendant,
        PushChairPendant
    }

    public enum TvSize {
        SevenTeenForty,
        Forty2Fifty,
        Fifty2Sixty,
        Sixty5Beyond
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date installTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date contactTime;
    private int drawableId;
    private String addr;
    private String teleNo;
    private String contactUser;
    private boolean contacted;
    private boolean installed;

    private WallType wallType;
    private SkuType skyType;
    private TvSize tvSize;

    public OrderInfo(String orderTitle, Date installTime, Date contactTime, boolean contacted, boolean installed, int orderType) {
        this.orderTitle = orderTitle;
        this.installTime = installTime;
        this.contactTime = contactTime;
        this.contacted = contacted;
        this.installed = installed;
        this.orderType = orderType;
        this.addr = "上海浦东新区";
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getTeleNo() {
        return teleNo;
    }

    public void setTeleNo(String teleNo) {
        this.teleNo = teleNo;
    }

    public WallType getWallType() {
        return wallType;
    }

    public void setWallType(WallType wallType) {
        this.wallType = wallType;
    }

    public SkuType getSkyType() {
        return skyType;
    }

    public void setSkyType(SkuType skyType) {
        this.skyType = skyType;
    }

    public TvSize getTvSize() {
        return tvSize;
    }

    public void setTvSize(TvSize tvSize) {
        this.tvSize = tvSize;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public OrderInfo(String orderTitle, int orderType) {
        this.orderTitle = orderTitle;
        this.orderType = orderType;
    }

    public Date getContactTime() {
        return contactTime;
    }

    public void setContactTime(Date contactTime) {
        this.contactTime = contactTime;
    }

    public Date getInstallTime() {
        return installTime;
    }

    public void setInstallTime(Date installTime) {
        this.installTime = installTime;
    }


    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }
}
