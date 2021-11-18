package com.zawraapharma.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;

public class InvoiceCompanyPharmacyModel implements Serializable {
    @Embedded
    public InvoiceModel invoiceModel;
    @Relation(parentColumn = "company_id",entityColumn = "id")
    public CompanyModel companyModel;
    @Relation(parentColumn = "client_id",entityColumn = "id")
    public PharmacyModel pharmacyModel;

}
