package com.zawraapharma.mvp.activity_pay_pill_mvp;

import com.zawraapharma.models.PharmacyModel;

import java.util.List;

public interface PayPillActivityView {
    void onSuccess(List<PharmacyModel> data);
    void onFailed(String msg);
    void onProgressShow();
    void onProgressHide();
    void onFinished();

}
