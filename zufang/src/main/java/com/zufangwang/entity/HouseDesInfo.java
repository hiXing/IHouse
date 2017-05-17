package com.zufangwang.entity;

import java.io.Serializable;

/**
 * Created by Francis on 2016/4/28.
 */
public class HouseDesInfo implements Serializable {
    private String house_renovation;//装修
    private String house_orientation;//朝向
    private String house_limit;//限制
    private boolean broadband;//宽带
    private boolean video;//电视
    private boolean sofa;//沙发
    private boolean washing;//洗衣机
    private boolean bed;//床
    private boolean refrigerator;//冰箱
    private boolean air;//空调
    private boolean heating;//暖气
    private boolean wardrobe;//衣柜
    private boolean heater;//热水器

    @Override
    public String toString() {
        return "HouseDesInfo{" +
                "house_renovation='" + house_renovation + '\'' +
                ", house_orientation='" + house_orientation + '\'' +
                ", house_limit='" + house_limit + '\'' +
                ", broadband=" + broadband +
                ", video=" + video +
                ", sofa=" + sofa +
                ", washing=" + washing +
                ", bed=" + bed +
                ", refrigerator=" + refrigerator +
                ", air=" + air +
                ", heating=" + heating +
                ", wardrobe=" + wardrobe +
                ", heater=" + heater +
                '}';
    }

    public String getHouse_renovation() {
        return house_renovation;
    }

    public void setHouse_renovation(String house_renovation) {
        this.house_renovation = house_renovation;
    }

    public String getHouse_orientation() {
        return house_orientation;
    }

    public void setHouse_orientation(String house_orientation) {
        this.house_orientation = house_orientation;
    }

    public String getHouse_limit() {
        return house_limit;
    }

    public void setHouse_limit(String house_limit) {
        this.house_limit = house_limit;
    }

    public boolean isBroadband() {
        return broadband;
    }

    public void setBroadband(boolean broadband) {
        this.broadband = broadband;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public boolean isSofa() {
        return sofa;
    }

    public void setSofa(boolean sofa) {
        this.sofa = sofa;
    }

    public boolean isWashing() {
        return washing;
    }

    public void setWashing(boolean washing) {
        this.washing = washing;
    }

    public boolean isBed() {
        return bed;
    }

    public void setBed(boolean bed) {
        this.bed = bed;
    }

    public boolean isRefrigerator() {
        return refrigerator;
    }

    public void setRefrigerator(boolean refrigerator) {
        this.refrigerator = refrigerator;
    }

    public boolean isAir() {
        return air;
    }

    public void setAir(boolean air) {
        this.air = air;
    }

    public boolean isHeating() {
        return heating;
    }

    public void setHeating(boolean heating) {
        this.heating = heating;
    }

    public boolean isWardrobe() {
        return wardrobe;
    }

    public void setWardrobe(boolean wardrobe) {
        this.wardrobe = wardrobe;
    }

    public boolean isHeater() {
        return heater;
    }

    public void setHeater(boolean heater) {
        this.heater = heater;
    }
}
