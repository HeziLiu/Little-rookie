package com.example.he.coolwwather.model;

/**
 * 表City对应的实体类
 * Created by he on 2016/5/16.
 */
public class City {
    private int id;
    private String cityName;
    private String cityCode;
    private int provinceId;

    public String getCityCode() {

        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
