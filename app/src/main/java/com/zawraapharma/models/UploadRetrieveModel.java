package com.zawraapharma.models;

import java.io.Serializable;
import java.util.List;

public class UploadRetrieveModel implements Serializable {
    private String user_id;
    private List<Backs> backs;

    public UploadRetrieveModel(String user_id, List<Backs> backs) {
        this.user_id = user_id;
        this.backs = backs;
    }

    public static class Backs implements Serializable{
        private String client_id;
        private String company_id;
        private String bill_code;
        private List<Items> items;

        public Backs(String client_id, String company_id, String bill_code, List<Items> items) {
            this.client_id = client_id;
            this.company_id = company_id;
            this.bill_code = bill_code;
            this.items = items;
        }
    }

    public static class Items implements Serializable{
        private String item_id;
        private double back_amount;

        public Items(String item_id, double back_amount) {
            this.item_id = item_id;
            this.back_amount = back_amount;
        }
    }
}
