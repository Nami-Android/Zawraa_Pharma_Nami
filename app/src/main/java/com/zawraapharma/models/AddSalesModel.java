package com.zawraapharma.models;

import java.io.Serializable;
import java.util.List;

public class AddSalesModel implements Serializable {
    private String user_id;
    private String client_id;
    private double latitude;
    private double longitude;
    private String notes="";
    private List<SalesItem> details;

    public AddSalesModel(String user_id, String client_id, double latitude, double longitude,String notes, List<SalesItem> details) {
        this.user_id = user_id;
        this.client_id = client_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.details = details;
        this.notes = notes;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public List<SalesItem> getDetails() {
        return details;
    }

    public String getNotes() {
        return notes;
    }

    public static class SalesItem implements Serializable{
        private String item_id;
        private String amount;
        private String bouns;
        private String company_id;

        public SalesItem(String item_id, String amount, String bouns, String company_id) {
            this.item_id = item_id;
            this.amount = amount;
            this.bouns = bouns;
            this.company_id = company_id;
        }

        public String getItem_id() {
            return item_id;
        }

        public String getAmount() {
            return amount;
        }

        public String getBouns() {
            return bouns;
        }

        public String getCompany_id() {
            return company_id;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public void setBouns(String bouns) {
            this.bouns = bouns;
        }
    }

}
