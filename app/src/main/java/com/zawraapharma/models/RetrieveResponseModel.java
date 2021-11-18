package com.zawraapharma.models;

import java.io.Serializable;
import java.util.List;

public class RetrieveResponseModel implements Serializable {
    private int id;
    private String bill_id;
    private String company_id;
    private String client_id;
    private String user_id;
    private String bill_code;
    private double paid_amount;
    private String date;
    private String created_at;
    private String updated_at;
    private PharmacyModel client;
    private CompanyModel company;
    private List<BackItemsFk> back_items_fk;

    public RetrieveResponseModel() {
    }

    public RetrieveResponseModel(int id, String bill_id, String company_id, String client_id, String user_id, String bill_code, double paid_amount, String date, String created_at, String updated_at, PharmacyModel client, CompanyModel company, List<BackItemsFk> back_items_fk) {
        this.id = id;
        this.bill_id = bill_id;
        this.company_id = company_id;
        this.client_id = client_id;
        this.user_id = user_id;
        this.bill_code = bill_code;
        this.paid_amount = paid_amount;
        this.date = date;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.client = client;
        this.company = company;
        this.back_items_fk = back_items_fk;
    }

    public int getId() {
        return id;
    }

    public String getBill_id() {
        return bill_id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getBill_code() {
        return bill_code;
    }

    public double getPaid_amount() {
        return paid_amount;
    }

    public String getDate() {
        return date;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public PharmacyModel getClient() {
        return client;
    }

    public CompanyModel getCompany() {
        return company;
    }

    public List<BackItemsFk> getBack_items_fk() {
        return back_items_fk;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setBill_code(String bill_code) {
        this.bill_code = bill_code;
    }

    public void setPaid_amount(double paid_amount) {
        this.paid_amount = paid_amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public void setClient(PharmacyModel client) {
        this.client = client;
    }

    public void setCompany(CompanyModel company) {
        this.company = company;
    }

    public void setBack_items_fk(List<BackItemsFk> back_items_fk) {
        this.back_items_fk = back_items_fk;
    }

    public static class ItemFk implements Serializable
    {
        private int id;
        private String title;
        private String item_code;
        private String company_id;
        private String price;
        private String created_at;
        private String updated_at;

        public ItemFk() {
        }

        public ItemFk(int id, String title, String item_code, String company_id, String price, String created_at, String updated_at) {
            this.id = id;
            this.title = title;
            this.item_code = item_code;
            this.company_id = company_id;
            this.price = price;
            this.created_at = created_at;
            this.updated_at = updated_at;
        }

        public int getId() {
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

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }
    public static class BackItemsFk implements Serializable
    {
        private int id;
        private String back_bill_id;
        private String bill_item_id;
        private int item_id;
        private double original_amount;
        private double back_amount;
        private double original_price;
        private double back_price;
        private String created_at;
        private String updated_at;
        private ItemFk item_fk;

        public BackItemsFk() {
        }

        public BackItemsFk(int id, String back_bill_id, String bill_item_id, int item_id, double back_amount, String created_at, String updated_at, ItemFk item_fk) {
            this.id = id;
            this.back_bill_id = back_bill_id;
            this.bill_item_id = bill_item_id;
            this.item_id = item_id;
            this.back_amount = back_amount;
            this.created_at = created_at;
            this.updated_at = updated_at;
            this.item_fk = item_fk;
        }

        public int getId() {
            return id;
        }

        public String getBack_bill_id() {
            return back_bill_id;
        }

        public String getBill_item_id() {
            return bill_item_id;
        }

        public int getItem_id() {
            return item_id;
        }

        public double getBack_amount() {
            return back_amount;
        }

        public double getOriginal_price() {
            return original_price;
        }

        public double getBack_price() {
            return back_price;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public ItemFk getItem_fk() {
            return item_fk;
        }
    }


}
