package com.zawraapharma.models;

import java.io.Serializable;

public class PrinterModel implements Serializable {
    private String name;
    private String address;
    private String mac;

    public PrinterModel(String name, String address, String mac) {
        this.name = name;
        this.address = address;
        this.mac = mac;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getMac() {
        return mac;
    }
}
