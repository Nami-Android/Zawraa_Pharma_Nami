package com.zawraapharma.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.esotericsoftware.kryo.NotNull;
import com.zawraapharma.tags.Tags;

import java.io.Serializable;

@Entity(tableName = Tags.table_pharmacy,indices = {@Index(value = {"id","code"},unique = true)})
public class PharmacyModel implements Serializable {
    @NonNull
    @PrimaryKey
    private String id;
    private String title;
    private String category_title;
    private double balance;
    private String address;
    private String latitude;
    private String longitude;
    private String logo;
    private String code;
    private String bills_count;
    private String created_at;
    private String updated_at;



    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory_title() {
        return category_title;
    }

    public double getBalance() {
        return balance;
    }

    public String getAddress() {
        return address;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLogo() {
        return logo;
    }

    public String getCode() {
        return code;
    }

    public String getBills_count() {
        return bills_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory_title(String category_title) {
        this.category_title = category_title;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setBills_count(String bills_count) {
        this.bills_count = bills_count;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }


}
