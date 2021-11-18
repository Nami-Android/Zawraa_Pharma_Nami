package com.zawraapharma.models;

import java.io.Serializable;

public class AppointmentModel implements Serializable {
    private int id;
    private int user_id;
    private int client_id;
    private String notes;
    private String fired_at;
    private String created_at;
    private String  updated_at;

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public String getNotes() {
        return notes;
    }

    public String getFired_at() {
        return fired_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
