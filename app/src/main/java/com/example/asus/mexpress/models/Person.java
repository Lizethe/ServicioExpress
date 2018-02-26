package com.example.asus.mexpress.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ASUS on 11/11/2017.
 */

public class Person extends RealmObject {
    @PrimaryKey
    private String id;
    private String name;
    private String lastName;
    private Integer phoneNumber;
    private String location;
    private Date birthday;
    private byte[] photo;
    private String type;
    private String userId;
    @Ignore
    private String age;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getAge() {
        return age;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", PhoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}

