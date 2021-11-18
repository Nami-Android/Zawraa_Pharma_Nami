package com.zawraapharma.models;

import java.io.Serializable;
import java.util.List;

public class PharmacyDataModel implements Serializable {
    private List<PharmacyModel> data;
    private int status;

    public List<PharmacyModel> getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }
}
