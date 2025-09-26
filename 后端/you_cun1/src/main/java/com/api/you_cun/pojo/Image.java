package com.api.you_cun.pojo;


public class Image {

    private Integer id;
    private Integer uid;
    private String name;
    private String size;
    private String date;
    private String url; // 确保有此属性

    public Image(Integer id, Integer uid, String name, String size, String date) {
        this.id = id;
        this.uid = uid;
        this.name = name;
        this.size = size;
        this.date = date;
    }

    public Image(Integer id, Integer uid, String name, String size, String date,String baseUrl) {
        this.id = id;
        this.uid = uid;
        this.name = name;
        this.size = size;
        this.date = date;
        this.url = baseUrl + "/api/user/getImage?Tid=" + id; // 生成完整URL
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
