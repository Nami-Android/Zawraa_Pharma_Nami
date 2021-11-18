package com.zawraapharma.models;

import java.io.Serializable;
import java.util.List;

public class BillResponse implements Serializable {
    private int status;
    private Data data;

    public int getStatus() {
        return status;
    }

    public Data getData() {
        return data;
    }

    public static class Data implements Serializable {
        private String id;
        private String client_id;
        private String user_id;
        private String date;
        private String time;
        private double total;
        private double discount;
        private double total_after_discount;
        private String is_paid_to_admin_at;
        private String notes;
        private String created_at;
        private String updated_at;
        private List<Bill> bills;
        private UserFk user_fk;
        private PharmacyModel client_fk;


        public Data(String id, String date, String time, double total, double discount, double total_after_discount, String notes, PharmacyModel client_fk,List<Bill> bills) {
            this.id = id;
            this.date = date;
            this.time = time;
            this.total = total;
            this.discount = discount;
            this.total_after_discount = total_after_discount;
            this.notes = notes;
            this.client_fk = client_fk;
            this.bills = bills;
        }

        public String  getId() {
            return id;
        }

        public String getClient_id() {
            return client_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public double getTotal() {
            return total;
        }

        public double getDiscount() {
            return discount;
        }

        public double getTotal_after_discount() {
            return total_after_discount;
        }

        public String getIs_paid_to_admin_at() {
            return is_paid_to_admin_at;
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

        public List<Bill> getBills() {
            return bills;
        }

        public UserFk getUser_fk() {
            return user_fk;
        }

        public PharmacyModel getClient_fk() {
            return client_fk;
        }


        public static class BillFk implements Serializable{
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
            private CompanyModel company_fk;

            public BillFk() {
            }

            public BillFk(String id, String code, String client_id, String user_id, String company_id, String date, double total, double debt_amount, double paid, double remaining, String status, String is_exceed_90_days, String notes, String created_at, String updated_at, CompanyModel company_fk) {
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
        }

        public static class Bill implements Serializable{
            private String id;
            private String payment_id;
            private String bill_id;
            private String client_id;
            private String user_id;
            private double paid_amount;
            private String date;
            private String created_at;
            private String updated_at;
            private BillFk bill_fk;

            public Bill(String bill_id, String client_id, double paid_amount, String date, BillFk bill_fk) {
                this.bill_id = bill_id;
                this.client_id = client_id;
                this.paid_amount = paid_amount;
                this.date = date;
                this.bill_fk = bill_fk;

            }

            public String getId() {
                return id;
            }

            public String getPayment_id() {
                return payment_id;
            }

            public String getBill_id() {
                return bill_id;
            }

            public String getClient_id() {
                return client_id;
            }

            public String getUser_id() {
                return user_id;
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

            public BillFk getBill_fk() {
                return bill_fk;
            }
        }

        public static class UserFk implements Serializable{
            private String id;
            private String name;
            private String phone;
            private String access_code;
            private String balance;
            private String email;
            private String logo;
            private String address;
            private String latitude;
            private String longitude;
            private String is_block;
            private String is_login;
            private String logout_time;
            private String notification_status;
            private String email_verification_code;
            private String email_verified_at;
            private String deleted_at;
            private String created_at;
            private String updated_at;

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getPhone() {
                return phone;
            }

            public String getAccess_code() {
                return access_code;
            }

            public String getBalance() {
                return balance;
            }

            public String getEmail() {
                return email;
            }

            public String getLogo() {
                return logo;
            }

            public String getAddress() {
                return address;
            }

            public String getLatitude() {
                return latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public String getIs_block() {
                return is_block;
            }

            public String getIs_login() {
                return is_login;
            }

            public String getLogout_time() {
                return logout_time;
            }

            public String getNotification_status() {
                return notification_status;
            }

            public String getEmail_verification_code() {
                return email_verification_code;
            }

            public String getEmail_verified_at() {
                return email_verified_at;
            }

            public String getDeleted_at() {
                return deleted_at;
            }

            public String getCreated_at() {
                return created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }
        }



    }
}
