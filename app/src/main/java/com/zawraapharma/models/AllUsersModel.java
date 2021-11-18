package com.zawraapharma.models;

import java.io.Serializable;
import java.util.List;

public class AllUsersModel implements Serializable {
    private int status;
    private List<UserModel.User> data;

    public int getStatus() {
        return status;
    }

    public List<UserModel.User> getData() {
        return data;
    }
}
