package com.zawraapharma.models;

import java.io.Serializable;
import java.util.List;

public class SalesDetailsModel implements Serializable {
    private int id;
    private int user_id;
    private int client_id;
    private String date;
    private String notes;
    private UserModel.User user;
    private PharmacyModel client;
    private List<Details> details;

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public String getDate() {
        return date;
    }

    public String getNotes() {
        return notes;
    }

    public UserModel.User getUser() {
        return user;
    }

    public PharmacyModel getClient() {
        return client;
    }

    public List<Details> getDetails() {
        return details;
    }

    public static class Details implements Serializable{
        private int id;
        private int sales_id;
        private int item_id;
        private int company_id;
        private double bouns;
        private double amount;
        private Item item;
        private CompanyModel company;

        public int getId() {
            return id;
        }

        public int getSales_id() {
            return sales_id;
        }

        public int getItem_id() {
            return item_id;
        }

        public int getCompany_id() {
            return company_id;
        }

        public double getBouns() {
            return bouns;
        }

        public double getAmount() {
            return amount;
        }

        public Item getItem() {
            return item;
        }

        public CompanyModel getCompany() {
            return company;
        }
    }



    public static class Item implements Serializable{
        private int id;
        private String title;
        private String item_code;
        private int company_id;
        private double price;

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getItem_code() {
            return item_code;
        }

        public int getCompany_id() {
            return company_id;
        }

        public double getPrice() {
            return price;
        }
    }
}
