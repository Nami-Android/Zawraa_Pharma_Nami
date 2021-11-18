package com.zawraapharma.mvp.activity_date_mvp;

import com.zawraapharma.models.LocationModel;

public interface ActivityDateView {
    void onDateSelected(String date);
    void onSuccess();
    void onLocationSuccess(LocationModel locationModel);
    void onFailed(String msg);
}
