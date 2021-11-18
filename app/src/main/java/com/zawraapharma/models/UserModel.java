package com.zawraapharma.models;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.zawraapharma.tags.Tags;

import java.io.Serializable;
import java.util.List;

public class UserModel implements Serializable {

    private User data;
    private int status;
    private String phone_token;


    public void setStatus(int status) {
        this.status = status;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public String getPhone_token() {
        return phone_token;
    }

    public void setPhone_token(String phone_token) {
        this.phone_token = phone_token;
    }

    @Entity(tableName = Tags.table_users, indices = @Index(value = {"id","email","token"}, unique = true))
    public static class User implements Serializable {
        @PrimaryKey
        @NonNull
        private String id;
        private String name;
        private String email;
        private String city;
        private String access_code;
        private String image;
        private String logo;
        private String token;
        private String latitude;
        private String longitude;
        private String address;
        private String user_type;
        private String details;
        private String is_block;


        public User() {
        }

        public User(@NonNull String id, String name, String access_code, String logo, String token) {
            this.id = id;
            this.name = name;
            this.access_code = access_code;
            this.logo = logo;
            this.token = token;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getCity() {
            return city;
        }


        public String getAccess_code() {
            return access_code;
        }

        public String getImage() {
            return image;
        }

        public String getLogo() {
            return logo;
        }

        public String getToken() {
            return token;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getAddress() {
            return address;
        }

        public String getType() {
            return user_type;
        }

        public String getIs_block() {
            return is_block;
        }

        public void setIs_block(String is_block) {
            this.is_block = is_block;
        }


        public String getDetails() {
            return details;
        }


        public String getUser_type() {
            return user_type;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public void setId(@NonNull String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setAccess_code(String access_code) {
            this.access_code = access_code;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }

    public int getStatus() {
        return status;
    }
}
