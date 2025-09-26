package com.api.you_cun.pojo;

public class User {
    private int id;
    private String mail;
    private String password;
    private String token;
    private int moreSize;
    private String useSize;
    private int fileNumber;
    private int imgNumber;
    private String vip_time;


    public User(int id, String mail, String password, String token, int moreSize, String useSize, int fileNumber, int imgNumber, String vip_time) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.token = token;
        this.moreSize = moreSize;
        this.useSize = useSize;
        this.fileNumber = fileNumber;
        this.imgNumber = imgNumber;
        this.vip_time = vip_time;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmail() {
        return mail;
    }

    public void setmail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
