package com.zawraapharma.models;

import java.io.Serializable;
import java.util.List;

public class UploadPaidModel implements Serializable {
    private String user_id;
    private List<Pay> pay;

    public UploadPaidModel(String user_id, List<Pay> pay) {
        this.user_id = user_id;
        this.pay = pay;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<Pay> getPay() {
        return pay;
    }

    public void setPay(List<Pay> pay) {
        this.pay = pay;
    }

    public static class Pay implements Serializable {
        private String client_id;
        private String total;
        private String discount;
        private String total_after_discount;
        private String notes;
        private List<Bill> bills;

        public Pay(String client_id, String total, String discount, String total_after_discount, String notes, List<Bill> bills) {
            this.client_id = client_id;
            this.total = total;
            this.discount = discount;
            this.total_after_discount = total_after_discount;
            this.notes = notes;
            this.bills = bills;
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

        public List<Bill> getBills() {
            return bills;
        }

        public void setBills(List<Bill> bills) {
            this.bills = bills;
        }
    }

    public static class Bill implements Serializable{
        private String code;
        private String paid_amount;
        private String company_title;


        public Bill(String code, String paid_amount, String company_title) {
            this.code = code;
            this.paid_amount = paid_amount;
            this.company_title = company_title;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getPaid_amount() {
            return paid_amount;
        }

        public void setPaid_amount(String paid_amount) {
            this.paid_amount = paid_amount;
        }

        public String getCompany_title() {
            return company_title;
        }

        public void setCompany_title(String company_title) {
            this.company_title = company_title;
        }
    }
}
