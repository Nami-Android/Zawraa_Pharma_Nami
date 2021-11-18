package com.zawraapharma.mvp.activity_find_pharmacy_mvp;

import com.zawraapharma.models.PharmacyModel;

import java.util.List;

public interface FindPharmacyActivityView {
    void onFinished();

    void onSuccess(List<PharmacyModel> data);

    void onFailed(String msg);

    void onProgressShow();

    void onProgressHide();
}
