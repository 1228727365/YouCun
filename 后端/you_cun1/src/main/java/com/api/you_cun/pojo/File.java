package com.api.you_cun.pojo;

public class File {

    private int id;
    private int uid;
    private String content;
    private String date;
    private String token;
    private int state;
    private String password;
    private String AesKey;


    public File(int id, int uid, String content, String date, String token, int state, String password, String aesKey) {
        this.id = id;
        this.uid = uid;
        this.content = content;
        this.date = date;
        this.token = token;
        this.state = state;
        this.password = password;
        AesKey = aesKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getdate() {
        return date;
    }

    public void setdate(String date) {
        this.date = date;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAesKey() {
        return AesKey;
    }

    public void setAesKey(String aesKey) {
        AesKey = aesKey;
    }
}
