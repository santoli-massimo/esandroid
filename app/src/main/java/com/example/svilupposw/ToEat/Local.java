package com.example.svilupposw.ToEat;

/**
 * Created by svilupposw on 02/05/16.
 */
public class Local {

    protected String id;
    protected String name;
    protected String address;
    protected String type;
    protected String money;
    protected String contact;
    protected String hours;
    protected String userId;

    public Local() {}

    public Local(String id) {
        this.id = id;
    }

    public Local(String name, String address, String type, String money, String contact, String hours, String userId) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.money = money;
        this.contact = contact;
        this.hours = hours;
        this.userId = userId;
    }

    public Local(String id, String name, String address, String type, String money, String contact, String hours, String userId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.type = type;
        this.money = money;
        this.contact = contact;
        this.hours = hours;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
