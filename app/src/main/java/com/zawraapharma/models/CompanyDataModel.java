package com.zawraapharma.models;

import java.io.Serializable;
import java.util.List;

public class CompanyDataModel implements Serializable {
    private List<CompanyModel> data;
    private int status;

    public List<CompanyModel> getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }
}
