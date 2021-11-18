package com.zawraapharma.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class RetrieveModelBillModel {
    @Embedded
    public RetrieveModel retrieveModel;
    @Relation(parentColumn ="local_id" ,entityColumn = "retrieve_local_id")
    public List<Product_Bill_Model> billModelList;


}
