package com.zawraapharma.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import com.esotericsoftware.kryo.NotNull;
import com.google.gson.annotations.SerializedName;
import com.zawraapharma.tags.Tags;

import java.io.Serializable;
import java.sql.Blob;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = Tags.table_invoice, indices = @Index(value = {"id","client_id","company_id", "code"}, unique = true),

        foreignKeys = {
                @ForeignKey(entity = PharmacyModel.class, parentColumns = "id", childColumns = "client_id"),
                @ForeignKey(entity = CompanyModel.class, parentColumns = "id", childColumns = "company_id")
        }
)
public class InvoiceModel implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;
    @NonNull
    private String code;
    private String client_id;
    private String user_id;
    private String company_id;
    private String date;
    private double total;
    private double debt_amount;
    private double paid;
    @SerializedName("remaining_denar")
    private double remaining;
    private String status;
    private String is_exceed_90_days;
    private String notes;
    private String created_at;
    private String updated_at;
    @Ignore
    private String amount = "";
    @Ignore
    private CompanyModel company_fk;
    @Ignore
    private boolean isSelected = false;

    public InvoiceModel() {
    }

    public InvoiceModel(String id, @NonNull String code, String client_id, String user_id, String company_id, String date, double total, double debt_amount, double paid, double remaining, String status, String is_exceed_90_days, String notes, String created_at, String updated_at, String amount, CompanyModel company_fk) {
        this.id = id;
        this.code = code;
        this.client_id = client_id;
        this.user_id = user_id;
        this.company_id = company_id;
        this.date = date;
        this.total = total;
        this.debt_amount = debt_amount;
        this.paid = paid;
        this.remaining = remaining;
        this.status = status;
        this.is_exceed_90_days = is_exceed_90_days;
        this.notes = notes;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.amount = amount;
        this.company_fk = company_fk;
    }


    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public String getDate() {
        return date;
    }

    public double getTotal() {
        return total;
    }

    public double getDebt_amount() {
        return debt_amount;
    }

    public double getPaid() {
        return paid;
    }

    public double getRemaining() {
        return remaining;
    }

    public String getStatus() {
        return status;
    }

    public String getIs_exceed_90_days() {
        return is_exceed_90_days;
    }

    public String getNotes() {
        return notes;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public CompanyModel getCompany_fk() {
        return company_fk;
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

    public void setCode(String code) {
        this.code = code;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setDebt_amount(double debt_amount) {
        this.debt_amount = debt_amount;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public void setRemaining(double remaining) {
        this.remaining = remaining;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setIs_exceed_90_days(String is_exceed_90_days) {
        this.is_exceed_90_days = is_exceed_90_days;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public void setCompany_fk(CompanyModel company_fk) {
        this.company_fk = company_fk;
    }
}
