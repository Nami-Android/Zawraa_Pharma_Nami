package com.zawraapharma.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.esotericsoftware.kryo.NotNull;
import com.zawraapharma.tags.Tags;

import java.io.Serializable;
import java.util.List;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = Tags.table_retrieve, indices = @Index(value = {"local_id","company_id","client_id","bill_code"},unique = true),
        foreignKeys = {
                @ForeignKey(entity = PharmacyModel.class, parentColumns = "id", childColumns = "client_id"),
                @ForeignKey(entity = CompanyModel.class, parentColumns = "id", childColumns = "company_id")

        }

)
public class RetrieveModel implements Serializable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int local_id;
    private String bill_code;
    private String company_id;
    private String user_id;
    private String client_id;
    @Ignore
    private double latitude;
    @Ignore
    private double longitude;
    @Ignore
    private List<Product_Bill_Model> items;

    public RetrieveModel() {
    }

    public RetrieveModel(String bill_code, String company_id, String user_id, String client_id, List<Product_Bill_Model> items,double latitude,double longitude) {
        this.bill_code = bill_code;
        this.company_id = company_id;
        this.user_id = user_id;
        this.client_id = client_id;
        this.items = items;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getBill_code() {
        return bill_code;
    }

    public String getCompany_id() {
        return company_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public List<Product_Bill_Model> getItems() {
        return items;
    }

    @NonNull
    public int getLocal_id() {
        return local_id;
    }

    public void setLocal_id(@NonNull int local_id) {
        this.local_id = local_id;
    }

    public void setBill_code(String bill_code) {
        this.bill_code = bill_code;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public void setItems(List<Product_Bill_Model> items) {
        this.items = items;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
