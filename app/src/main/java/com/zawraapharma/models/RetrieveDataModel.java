package com.zawraapharma.models;

import java.io.Serializable;

public class RetrieveDataModel implements Serializable {
    private int status;
    private RetrieveResponseModel data;

    public int getStatus() {
        return status;
    }

    public RetrieveResponseModel getData() {
        return data;
    }
}
