package com.zawraapharma.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.zawraapharma.tags.Tags;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;


@Entity(tableName = Tags.table_bill, indices = @Index(value = {"cart_id","invoice_id","company_id","bill_id","code"}, unique = true),
        foreignKeys = {
                @ForeignKey(entity = CartModel.class, parentColumns = "id", childColumns = "cart_id",onDelete = CASCADE) ,
                @ForeignKey(entity = CompanyModel.class, parentColumns = "id", childColumns = "company_id"),
                @ForeignKey(entity = InvoiceModel.class, parentColumns = "id", childColumns = "invoice_id")
}
)
public class BillModel implements Serializable {
    @PrimaryKey
    @NonNull
    private String bill_id;
    @NonNull
    private long cart_id;
    private String paid_amount;
    private String code;
    /////////////////////local_data_base///////////////////////////
    private String invoice_id;
    private String company_id;
    private String company_name;
    private int remain;
    //////////////////////////////////////////////////////////////
    @Ignore
    private CompanyModel companyModel;
    private String status;
    private double debt_amount;
    private String is_exceed_90_days;
    private String notes;
    private String created_at;
    private String updated_at;

    public BillModel() {
    }

    public BillModel(@NonNull String bill_id, String invoice_id, String paid_amount, String code, String company_id, CompanyModel companyModel, double debt_amount,String status,String is_exceed_90_days,String notes,String created_at, String updated_at,String company_name,int remain) {
        this.bill_id = bill_id;
        this.paid_amount = paid_amount;
        this.code = code;
        this.company_id = company_id;
        this.companyModel = companyModel;
        this.debt_amount = debt_amount;
        this.invoice_id = invoice_id;
        this.status = status;
        this.is_exceed_90_days = is_exceed_90_days;
        this.notes = notes;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.company_name = company_name;
        this.remain = remain;
    }



    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(String paid_amount) {
        this.paid_amount = paid_amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getCart_id() {
        return cart_id;
    }

    public void setCart_id(long cart_id) {
        this.cart_id = cart_id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public CompanyModel getCompanyModel() {
        return companyModel;
    }

    public void setCompanyModel(CompanyModel companyModel) {
        this.companyModel = companyModel;
    }

    public double getDebt_amount() {
        return debt_amount;
    }

    public void setDebt_amount(double debt_amount) {
        this.debt_amount = debt_amount;
    }

    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getIs_exceed_90_days() {
        return is_exceed_90_days;
    }

    public void setIs_exceed_90_days(String is_exceed_90_days) {
        this.is_exceed_90_days = is_exceed_90_days;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }
}
