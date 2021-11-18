package com.zawraapharma.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CartModelBillModel {
    @Embedded
    public CartModel cartModel;
    @Relation(parentColumn = "id",entityColumn = "cart_id")
    public List<BillModel> billModelList;
}
