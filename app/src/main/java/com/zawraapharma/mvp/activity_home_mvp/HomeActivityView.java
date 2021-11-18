package com.zawraapharma.mvp.activity_home_mvp;

public interface HomeActivityView {
    void onNavigateToLoginActivity();
    void onNavigateToPillPayActivity();
    void onNavigateToSalesActivity();
    void onNavigateToFindPharmacyActivity();
    void onNavigateToCalenderActivity();
    void onNavigateToNewClientActivity();
    void onNavigateToDebtDisclosureActivity();
    void onFinished();
    void onFailed(String msg);
    void onLogoutSuccess();
}
