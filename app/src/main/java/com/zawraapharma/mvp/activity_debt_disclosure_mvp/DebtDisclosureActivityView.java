package com.zawraapharma.mvp.activity_debt_disclosure_mvp;

import com.zawraapharma.models.DebtsModel;

import java.util.List;

public interface DebtDisclosureActivityView {
    void onSuccess(List<DebtsModel> debtsModelList);
    void onFailed(String msg);
    void onProgressShow();
    void onProgressHide();
    void onFinished();

}
