package com.zawraapharma.models;

import java.io.Serializable;
import java.util.List;

public class DebtsDataModel implements Serializable {
    private List<DebtsModel> data;
    private int status;

    public List<DebtsModel> getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }
}
