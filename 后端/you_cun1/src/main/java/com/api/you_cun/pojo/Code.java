package com.api.you_cun.pojo;

public class Code {
    private int id;
    private String mail;
    private String code;
    private String time;

    public Code() {

    }

    public Code(int id, String mail, String code, String time) {
        this.id = id;
        this.mail = mail;
        this.code = code;
        this.time = time;
    }

    public Code(String mail, String code, String time) {
        this.mail = mail;
        this.code = code;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
