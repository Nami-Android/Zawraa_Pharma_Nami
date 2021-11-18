package com.zawraapharma.models;

import java.io.Serializable;
import java.util.List;

public class SalesDetailsDataModel extends ResponseData implements Serializable {
    private SalesDetailsModel data;

    public SalesDetailsModel getData() {
        return data;
    }
}
