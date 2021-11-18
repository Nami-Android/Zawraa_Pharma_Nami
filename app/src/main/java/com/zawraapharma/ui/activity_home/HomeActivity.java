package com.zawraapharma.ui.activity_home;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.zawraapharma.R;
import com.zawraapharma.databinding.ActivityHomeBinding;
import com.zawraapharma.language.Language;
import com.zawraapharma.location_service.LocationService;
import com.zawraapharma.mvp.activity_home_mvp.ActivityHomePresenter;
import com.zawraapharma.mvp.activity_home_mvp.HomeActivityView;
import com.zawraapharma.tags.Tags;
import com.zawraapharma.ui.activity_calender.CalenderActivity;
import com.zawraapharma.ui.activity_dept_disclosure.DebtDisclosureActivity;
import com.zawraapharma.ui.activity_find_pharmacy.FindPharmacyActivity;
import com.zawraapharma.ui.activity_login.LoginActivity;
import com.zawraapharma.ui.activity_new_client.NewClientActivity;
import com.zawraapharma.ui.activity_pay_pill.PayPillActivity;
import com.zawraapharma.ui.activity_pharmacy_details.PharmacyDetailsActivity;

import java.util.List;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements HomeActivityView {
    private ActivityHomeBinding binding;
    private ActivityHomePresenter presenter;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        initView();
    }


    private void initView() {
        presenter = new ActivityHomePresenter(this, this);
        //online offline
        //presenter.syncData();
        binding.llBillPay.setOnClickListener(view -> presenter.pillPay());
        binding.llFindPharmacy.setOnClickListener(view -> presenter.findPharmacy());
        binding.llDebtDisclosure.setOnClickListener(view -> presenter.debtDisclosure());
        binding.llCalender.setOnClickListener(view -> presenter.calender());
        binding.llNewClient.setOnClickListener(view -> presenter.sales());

        binding.llLogout.setOnClickListener(view -> {
            presenter.setCanUpload(false);
            presenter.getRetrieve();
        });
        binding.llRefresh.setOnClickListener(v -> presenter.refreshData());
        startUpdateLocation();

    }

    @Override
    protected void onResume() {
        presenter.updateTokenFireBase();
        super.onResume();
    }

    private void startUpdateLocation() {
        Intent intent = new Intent(this, LocationService.class);
        startService(intent);
    }

    private void stopUpdateLocation() {
        Intent intent = new Intent(this, LocationService.class);
        stopService(intent);
    }


    @Override
    public void onBackPressed() {
        presenter.backPress();
    }


    @Override
    public void onNavigateToLoginActivity() {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onNavigateToPillPayActivity() {
        Intent intent = new Intent(this, PayPillActivity.class);
        intent.putExtra("id", "not sales");
        startActivity(intent);
    }
    @Override
    public void onNavigateToSalesActivity() {
        Intent intent = new Intent(this, FindPharmacyActivity.class);
        intent.putExtra("id", "sales");
        startActivity(intent);
    }
    @Override
    public void onNavigateToFindPharmacyActivity() {
        Intent intent = new Intent(this, FindPharmacyActivity.class);
        intent.putExtra("id", "not sales");
        startActivity(intent);
    }

    @Override
    public void onNavigateToCalenderActivity() {
        Intent intent = new Intent(this, CalenderActivity.class);
        startActivity(intent);
    }

    @Override
    public void onNavigateToNewClientActivity() {
        Intent intent = new Intent(this, NewClientActivity.class);
        startActivity(intent);
    }

    @Override
    public void onNavigateToDebtDisclosureActivity() {
        Intent intent = new Intent(this, DebtDisclosureActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFinished() {
        finish();
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLogoutSuccess() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.cancel(Tags.not_tag, Tags.not_id);
        }

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
        stopUpdateLocation();
    }


}