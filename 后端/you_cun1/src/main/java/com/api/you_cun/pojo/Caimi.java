package com.api.you_cun.pojo;

public class Caimi {

    private int id;
    private String km;
    private  String type;
    private String value;

    public Caimi(int id, String km, String type, String value) {
        this.id = id;
        this.km = km;
        this.type = type;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
