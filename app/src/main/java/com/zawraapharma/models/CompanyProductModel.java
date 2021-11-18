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

@Entity(tableName = Tags.table_products,indices = @Index(value = {"id","item_code","company_id"},unique = true),

        foreignKeys = {
        @ForeignKey(entity = CompanyModel.class,parentColumns = "id",childColumns = "company_id")
})
public class CompanyProductModel implements Serializable {
    @NonNull
    @PrimaryKey
    private String id;
    private String title;
    private String item_code;
    private String company_id;
    private String price;
    @Ignore
    private String amount="";
    @Ignore
    private String bonus = "";
    @Ignore
    private boolean isSelected = false;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getItem_code() {
        return item_code;
    }

    public String getCompany_id() {
        return company_id;
    }

    public String getPrice() {
        return price;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }
}
