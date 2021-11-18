package com.zawraapharma.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.zawraapharma.tags.Tags;

import java.io.Serializable;
import java.util.List;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = Tags.table_cart,indices = @Index(value = {"id","client_id"}, unique = true),
        foreignKeys = {
                @ForeignKey(entity = PharmacyModel.class, parentColumns = "id", childColumns = "client_id")
        }
)
public class CartModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String user_id;
    private String client_id;
    private String total;
    private String discount;
    private String total_after_discount;
    private String notes="";
    @NonNull
    private String latitude="0.0";
    @NonNull
    private String longitude="0.0";
    @Ignore
    private List<BillModel> bills;

    public CartModel() {
    }

    public CartModel( String user_id, String client_id, String total, String discount, String total_after_discount, String notes, @NonNull String latitude, @NonNull String longitude, List<BillModel> bills) {
        this.user_id = user_id;
        this.client_id = client_id;
        this.total = total;
        this.discount = discount;
        this.total_after_discount = total_after_discount;
        this.notes = notes;
        this.latitude = latitude;
        this.longitude = longitude;
        this.bills = bills;
    }



    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTotal_after_discount() {
        return total_after_discount;
    }

    public void setTotal_after_discount(String total_after_discount) {
        this.total_after_discount = total_after_discount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public List<BillModel> getBills() {
        return bills;
    }

    public void setBills(List<BillModel> bills) {
        this.bills = bills;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }


}
