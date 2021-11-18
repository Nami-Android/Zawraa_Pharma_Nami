package com.zawraapharma.mvp.activity_retrieve_bill_mvp;

import com.zawraapharma.models.CompanyModel;
import com.zawraapharma.models.CompanyProductModel;
import com.zawraapharma.models.LocationModel;
import com.zawraapharma.models.PharmacyModel;
import com.zawraapharma.models.RetrieveResponseModel;

import java.util.List;

public interface RetrieveActivityView {
    void onCompanySuccess(List<CompanyModel> data);
    void onProductSuccess(List<CompanyProductModel> data);
    void onRetrieveSuccess(RetrieveResponseModel retrieveResponseModel);
    void onLocationSuccess(LocationModel locationModel);
    void onFailed(String msg);
    void onProgressShow();
    void onProgressHide();

}
