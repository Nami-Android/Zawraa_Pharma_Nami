package com.zawraapharma.mvp.activity_calender_mvp;

import com.zawraapharma.models.AppointmentModel;
import com.zawraapharma.models.PharmacyModel;

import java.util.List;

public interface CalenderActivityView {
    void onSuccess(List<AppointmentModel> data);
    void onPharmacySuccess(List<PharmacyModel> data);

    void onFailed(String msg);

    void onProgressShow();

    void onProgressHide();

    void onMainProgressShow();

    void onMainProgressHide();

    void onFinished();
}
