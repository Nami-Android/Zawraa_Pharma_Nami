package com.zawraapharma.mvp.activity_pay_bill_mvp;

import com.zawraapharma.models.BillResponse;
import com.zawraapharma.models.InvoiceModel;
import com.zawraapharma.models.LocationModel;

import java.util.List;

public interface PayBillActivityView {
    void onSuccess(List<InvoiceModel> data);
    void onLocationSuccess(LocationModel locationModel);
    void onCartSendSuccess(BillResponse.Data data);
    void onFailed(String msg);
    void onProgressShow();
    void onProgressHide();

}
