package com.zawraapharma.mvp.activity_sales_mvp;

import com.zawraapharma.models.CompanyModel;
import com.zawraapharma.models.CompanyProductModel;
import com.zawraapharma.models.LocationModel;
import com.zawraapharma.models.SalesDetailsModel;

import java.util.List;

public interface SalesActivityView {
    void onCompanySuccess(List<CompanyModel> data);
    void onProductSuccess(List<CompanyProductModel> data);
    void onLocationSuccess(LocationModel locationModel);
    void onAddSuccess(SalesDetailsModel salesDetailsModel);
    void onFailed(String msg);
    void onProgressShow();
    void onProgressHide();

}
