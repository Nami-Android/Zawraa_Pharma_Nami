package com.zawraapharma.models;

import java.io.Serializable;
import java.util.List;

public class AppointmentDataModel implements Serializable {
    private List<AppointmentModel> data;
    private int status;

    public List<AppointmentModel> getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }
}
