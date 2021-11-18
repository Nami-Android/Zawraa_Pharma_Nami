package com.zawraapharma.models;

import android.nfc.Tag;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.esotericsoftware.kryo.NotNull;
import com.zawraapharma.tags.Tags;

import java.io.Serializable;
@Entity(tableName = Tags.table_company,indices = @Index(value = {"id","code"},unique = true))
public class CompanyModel implements Serializable {
    @NonNull
    @PrimaryKey
    private String id;
    private String title;
    private String code;
    private String logo;



    public CompanyModel(String id, String title) {
        this.id = id;
        this.title = title;
    }


    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public String getLogo() {
        return logo;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }


}
