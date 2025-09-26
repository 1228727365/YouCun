package com.api.you_cun.pojo;

public class Userinfo {

    private int moreSize;
    private String useSize;
    private int fileNumber;
    private int imgNumber;
    private String vip_time;


    public Userinfo(int moreSize, String useSize, int fileNumber, int imgNumber, String vip_time) {
        this.moreSize = moreSize;
        this.useSize = useSize;
        this.fileNumber = fileNumber;
        this.imgNumber = imgNumber;
        this.vip_time = vip_time;
    }

    public int getMoreSize() {
        return moreSize;
    }

    public void setMoreSize(int moreSize) {
        this.moreSize = moreSize;
    }

    public String getUseSize() {
        return useSize;
    }

    public void setUseSize(String useSize) {
        this.useSize = useSize;
    }

    public int getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(int fileNumber) {
        this.fileNumber = fileNumber;
    }

    public int getImgNumber() {
        return imgNumber;
    }

    public void setImgNumber(int imgNumber) {
        this.imgNumber = imgNumber;
    }

    public String getVip_time() {
        return vip_time;
    }

    public void setVip_time(String vip_time) {
        this.vip_time = vip_time;
    }
}
