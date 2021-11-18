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

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = Tags.table_product_bill,
        indices = {@Index(value = {"item_id","retrieve_local_id"},unique = true)},
        foreignKeys = {
                @ForeignKey(entity = RetrieveModel.class, parentColumns = "local_id", childColumns = "retrieve_local_id" ,onDelete = CASCADE)

        }

)
public class Product_Bill_Model implements Serializable {


    @PrimaryKey
    @NonNull
    private String item_id;
    @NonNull
    private int retrieve_local_id;
    private double back_amount;
    @Ignore
    private RetrieveResponseModel.ItemFk itemFk;

    public Product_Bill_Model() {
    }

    public Product_Bill_Model(@NonNull String item_id, double back_amount) {
        this.item_id = item_id;
        this.back_amount = back_amount;
    }

    public String getItem_id() {
        return item_id;
    }

    public double getBack_amount() {
        return back_amount;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public void setBack_amount(double back_amount) {
        this.back_amount = back_amount;
    }

    public int getRetrieve_local_id() {
        return retrieve_local_id;
    }

    public void setRetrieve_local_id(int retrieve_local_id) {
        this.retrieve_local_id = retrieve_local_id;
    }

    public RetrieveResponseModel.ItemFk getItemFk() {
        return itemFk;
    }

    public void setItemFk(RetrieveResponseModel.ItemFk itemFk) {
        this.itemFk = itemFk;
    }
}
