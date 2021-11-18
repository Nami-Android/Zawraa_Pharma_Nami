package com.zawraapharma.models;

import java.io.Serializable;

public class DebtsModel implements Serializable {
    private String id;
    private String code;
    private String client_id;
    private String user_id;
    private String company_id;
    private String date;
    private double total;
    private double debt_amount;
    private double paid;
    private double remaining;
    private String status;
    private String is_exceed_90_days;
    private String notes;
    private String created_at;
    private String updated_at;
    private PharmacyModel client_fk;

    public DebtsModel() {
    }

    public DebtsModel(String id, String code, String client_id, String user_id, String company_id, String date, double total, double debt_amount, double paid, double remaining, String status, String is_exceed_90_days, String notes, String created_at, String updated_at, PharmacyModel client_fk) {
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
        this.client_fk = client_fk;
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

    public PharmacyModel getClient_fk() {
        return client_fk;
    }

    public static class BillCode {
        private String code;

        public String getCode() {
            return code;
        }
    }

}
