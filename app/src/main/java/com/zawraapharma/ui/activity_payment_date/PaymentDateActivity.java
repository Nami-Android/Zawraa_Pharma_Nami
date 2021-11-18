package com.zawraapharma.ui.activity_payment_date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.zawraapharma.R;
import com.zawraapharma.databinding.ActivityPaymentDateBinding;
import com.zawraapharma.language.Language;
import com.zawraapharma.models.LocationModel;
import com.zawraapharma.models.PharmacyModel;
import com.zawraapharma.mvp.activity_date_mvp.ActivityDatePresenter;
import com.zawraapharma.mvp.activity_date_mvp.ActivityDateView;

import io.paperdb.Paper;

public class PaymentDateActivity extends AppCompatActivity implements ActivityDateView {
    private ActivityPaymentDateBinding binding;
    private ActivityDatePresenter presenter;
    private String lang;
    private PharmacyModel pharmacyModel;
    private boolean isForUpdate = false;
    private String date="";
    private LocationModel locationModel;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase,Paper.book().read("lang","ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_date);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        pharmacyModel = (PharmacyModel) intent.getSerializableExtra("data");
        if (intent.hasExtra("date")){
            date = intent.getStringExtra("date");
            isForUpdate = true;
        }

    }


    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        presenter = new ActivityDatePresenter(this,this);
        binding.cardViewDate.setOnClickListener(view -> presenter.showDateDialog(getFragmentManager()));

        if (!date.isEmpty()){
            onDateSelected(date);
        }

        binding.imageBack.setOnClickListener(view -> finish());
        binding.btnSave.setOnClickListener(view -> {
            if (isForUpdate){
                Intent intent = getIntent();
                intent.putExtra("date", date);
                setResult(RESULT_OK,intent);
                finish();
            }else {
                presenter.createAppointment(date, String.valueOf(pharmacyModel.getId()));

            }
        });
        binding.btnBack.setOnClickListener(view -> finish());

    }

    @Override
    public void onDateSelected(String date) {
        try {
            this.date = date;
            if (!date.isEmpty()){
                binding.llActions.setVisibility(View.VISIBLE);
                binding.tvDate.setText(date);
            }

        }catch (Exception e){}

    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, getString(R.string.suc), Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    public void onLocationSuccess(LocationModel locationModel) {
        this.locationModel =locationModel;
        binding.progBar2.setVisibility(View.GONE);
        binding.scrollView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.stopLocationUpdate();
    }
}