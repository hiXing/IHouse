package com.zufangwang.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Francis on 2016/4/27.
 */
public class HouseInfo implements Serializable {
    private int house_no;
    private String house_type;
    private String house_address;
    private String house_address_detail;
    private String house_apartment;
    private String house_floor;
    private String house_all_floor;
    private String house_area;
    private String house_price;
    private String house_title;
    private String house_des;
    private String house_contacts;
    private String house_contacts_tel;
    private String house_imgs;
    private String house_date;
    private String house_des_info;
    private String house_publish_id;

    @Override
    public String toString() {
        return "HouseInfo{" +
                "house_no=" + house_no +
                ", house_type='" + house_type + '\'' +
                ", house_address='" + house_address + '\'' +
                ", house_address_detail='" + house_address_detail + '\'' +
                ", house_apartment='" + house_apartment + '\'' +
                ", house_floor='" + house_floor + '\'' +
                ", house_all_floor='" + house_all_floor + '\'' +
                ", house_area='" + house_area + '\'' +
                ", house_price='" + house_price + '\'' +
                ", house_title='" + house_title + '\'' +
                ", house_des='" + house_des + '\'' +
                ", house_contacts='" + house_contacts + '\'' +
                ", house_contacts_tel='" + house_contacts_tel + '\'' +
                ", house_imgs='" + house_imgs + '\'' +
                '}';
    }

    public HouseInfo(int house_no, String house_type, String house_address, String house_address_detail, String house_apartment, String house_floor, String house_all_floor, String house_area, String house_price, String house_title, String house_des, String house_contacts, String house_contacts_tel,
                     String house_imgs, String house_date, String house_des_info, String house_publish_id) {
        this.house_no = house_no;
        this.house_type = house_type;
        this.house_address = house_address;
        this.house_address_detail = house_address_detail;
        this.house_apartment = house_apartment;
        this.house_floor = house_floor;
        this.house_all_floor = house_all_floor;
        this.house_area = house_area;
        this.house_price = house_price;
        this.house_title = house_title;
        this.house_des = house_des;
        this.house_contacts = house_contacts;
        this.house_contacts_tel = house_contacts_tel;
        this.house_imgs = house_imgs;
        this.house_date = house_date;
        this.house_des_info = house_des_info;
        this.house_publish_id = house_publish_id;
    }

    public String getHouse_publish_id() {
        return house_publish_id;
    }

    public void setHouse_publish_id(String house_publish_id) {
        this.house_publish_id = house_publish_id;
    }

    public String getHouse_des_info() {
        return house_des_info;
    }

    public void setHouse_des_info(String house_des_info) {
        this.house_des_info = house_des_info;
    }

    public void setHouse_date(String house_date) {
        this.house_date = house_date;
    }

    public String getHouse_date() {
        return house_date;
    }

    public void setHouse_type(String house_type) {
        this.house_type = house_type;
    }

    public String getHouse_type() {
        return house_type;
    }

    public String getHouse_imgs() {
        return house_imgs;
    }

    public void setHouse_imgs(String house_imgs) {
        this.house_imgs = house_imgs;
    }

    public void setHouse_no(int house_no) {
        this.house_no = house_no;
    }

    public void setHouse_address(String house_address) {
        this.house_address = house_address;
    }

    public void setHouse_address_detail(String house_address_detail) {
        this.house_address_detail = house_address_detail;
    }

    public void setHouse_apartment(String house_apartment) {
        this.house_apartment = house_apartment;
    }

    public void setHouse_floor(String house_floor) {
        this.house_floor = house_floor;
    }

    public void setHouse_all_floor(String house_all_floor) {
        this.house_all_floor = house_all_floor;
    }

    public void setHouse_area(String house_area) {
        this.house_area = house_area;
    }

    public void setHouse_price(String house_price) {
        this.house_price = house_price;
    }

    public void setHouse_title(String house_title) {
        this.house_title = house_title;
    }

    public void setHouse_des(String house_des) {
        this.house_des = house_des;
    }

    public void setHouse_contacts(String house_contacts) {
        this.house_contacts = house_contacts;
    }

    public void setHouse_contacts_tel(String house_contacts_tel) {
        this.house_contacts_tel = house_contacts_tel;
    }

    public int getHouse_no() {
        return house_no;
    }

    public String getHouse_address() {
        return house_address;
    }

    public String getHouse_address_detail() {
        return house_address_detail;
    }

    public String getHouse_apartment() {
        return house_apartment;
    }

    public String getHouse_floor() {
        return house_floor;
    }

    public String getHouse_all_floor() {
        return house_all_floor;
    }

    public String getHouse_area() {
        return house_area;
    }

    public String getHouse_price() {
        return house_price;
    }

    public String getHouse_title() {
        return house_title;
    }

    public String getHouse_des() {
        return house_des;
    }

    public String getHouse_contacts() {
        return house_contacts;
    }

    public String getHouse_contacts_tel() {
        return house_contacts_tel;
    }
}
