package com.zawraapharma.models;

import java.io.Serializable;
import java.util.List;

public class CompanyProductDataModel implements Serializable {
    private int status;
    private List<CompanyProductModel> data;

    public List<CompanyProductModel> getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

}
