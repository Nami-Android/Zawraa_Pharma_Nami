package com.zawraapharma.models;

import java.io.Serializable;
import java.util.List;

public class InvoiceDataModel implements Serializable {
    private List<InvoiceModel> data;
    private int status;

    public List<InvoiceModel> getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }
}
