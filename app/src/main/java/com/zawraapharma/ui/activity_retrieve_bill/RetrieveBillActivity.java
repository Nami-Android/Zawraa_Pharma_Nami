package com.zawraapharma.ui.activity_retrieve_bill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.zawraapharma.R;
import com.zawraapharma.adapters.CompanySpinnerAdapter;
import com.zawraapharma.adapters.ProductAdapter;
import com.zawraapharma.databinding.ActivityPayBillBinding;
import com.zawraapharma.databinding.ActivityRetrieveBillBinding;
import com.zawraapharma.language.Language;
import com.zawraapharma.models.BillModel;
import com.zawraapharma.models.CompanyModel;
import com.zawraapharma.models.CompanyProductModel;
import com.zawraapharma.models.LocationModel;
import com.zawraapharma.models.PharmacyModel;
import com.zawraapharma.models.Product_Bill_Model;
import com.zawraapharma.models.RetrieveModel;
import com.zawraapharma.models.RetrieveResponseModel;
import com.zawraapharma.mvp.activity_retrieve_bill_mvp.ActivityRetrievePresenter;
import com.zawraapharma.mvp.activity_retrieve_bill_mvp.RetrieveActivityView;
import com.zawraapharma.share.Common;
import com.zawraapharma.ui.activity_bill_retrieve_details.BillRetrieveActivity;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class RetrieveBillActivity extends AppCompatActivity implements RetrieveActivityView {
    private ActivityRetrieveBillBinding binding;
    private String lang;
    private PharmacyModel pharmacyModel;
    private ActivityRetrievePresenter presenter;
    private List<CompanyModel> companyModelList;
    private CompanySpinnerAdapter companySpinnerAdapter;
    private List<CompanyProductModel> companyProductModelList;
    private ProductAdapter adapter;
    private String company_id = "0";
    private CompanyModel companyModel;
    private List<Product_Bill_Model> paidBillList;
    private LocationModel locationModel;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_retrieve_bill);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        pharmacyModel = (PharmacyModel) intent.getSerializableExtra("data");
    }


    private void initView() {
        paidBillList = new ArrayList<>();
        companyProductModelList = new ArrayList<>();
        companyModelList = new ArrayList<>();
        CompanyModel model = new CompanyModel("0", getString(R.string.choose_company));
        companyModelList.add(model);
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(companyProductModelList, this);
        binding.recView.setAdapter(adapter);
        companySpinnerAdapter = new CompanySpinnerAdapter(companyModelList, this);
        binding.spinner.setAdapter(companySpinnerAdapter);
        presenter = new ActivityRetrievePresenter(this, this);
        presenter.getCompany();
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                company_id = companyModelList.get(i).getId();
                companyModel = companyModelList.get(i);
                presenter.search(company_id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.imageBack.setOnClickListener(view -> finish());
        binding.btnBack.setOnClickListener(view -> finish());

        binding.btnSend.setOnClickListener(view -> {
            String billNumber = binding.edtBillNumber.getText().toString().trim();
            if (!billNumber.isEmpty()) {
                binding.edtBillNumber.setError(null);
                Common.CloseKeyBoard(this, binding.edtBillNumber);
                presenter.retrieveBill(billNumber, String.valueOf(company_id), String.valueOf(pharmacyModel.getId()), paidBillList, pharmacyModel, companyModel);
            } else {
                binding.edtBillNumber.setError(getString(R.string.field_req));
            }
        });
    }


    @Override
    public void onCompanySuccess(List<CompanyModel> data) {
        if (data.size() > 0) {
            companyModelList.addAll(data);
            runOnUiThread(() -> companySpinnerAdapter.notifyDataSetChanged());

        }
    }

    @Override
    public void onProductSuccess(List<CompanyProductModel> data) {
        if (data.size() > 0) {
            companyProductModelList.clear();
            companyProductModelList.addAll(data);
            adapter.notifyDataSetChanged();
            binding.tvNoData.setVisibility(View.GONE);

        } else {
            companyProductModelList.clear();
            adapter.notifyDataSetChanged();
            binding.tvNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRetrieveSuccess(RetrieveResponseModel retrieveResponseModel) {
        Toast.makeText(this, getString(R.string.suc), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, BillRetrieveActivity.class);
        intent.putExtra("data", retrieveResponseModel);
        startActivity(intent);
        finish();

    }

    @Override
    public void onLocationSuccess(LocationModel locationModel) {
        this.locationModel = locationModel;
        binding.progBar2.setVisibility(View.GONE);
        binding.scrollView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressShow() {
        paidBillList.clear();
        binding.btnSend.setVisibility(View.GONE);
        binding.space.setVisibility(View.GONE);
        companyProductModelList.clear();
        adapter.notifyDataSetChanged();
        binding.tvNoData.setVisibility(View.GONE);
        binding.progBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressHide() {
        binding.progBar.setVisibility(View.GONE);

    }


    public void addUpdateItem(int adapterPosition, CompanyProductModel model, String amount) {

        int pos = getBillPos(String.valueOf(model.getId()));
        if (pos == -1) {
            if (!amount.equals("0")) {
                RetrieveResponseModel.ItemFk itemFk = new RetrieveResponseModel.ItemFk(Integer.parseInt(model.getId()), model.getTitle(), model.getItem_code(), model.getCompany_id(), model.getPrice(), "", "");
                Product_Bill_Model billModel = new Product_Bill_Model(String.valueOf(model.getId()), Double.parseDouble(amount));
                billModel.setItemFk(itemFk);
                paidBillList.add(billModel);
            }
        } else {
            if (!amount.equals("0")) {
                Product_Bill_Model billModel = paidBillList.get(pos);
                billModel.setBack_amount(Double.parseDouble(amount));
                paidBillList.set(pos, billModel);
            } else {
                paidBillList.remove(pos);
            }
        }

        if (paidBillList.size() > 0) {
            binding.btnSend.setVisibility(View.VISIBLE);
            binding.space.setVisibility(View.VISIBLE);
        } else {
            binding.btnSend.setVisibility(View.GONE);
            binding.space.setVisibility(View.GONE);
        }
    }


    private int getBillPos(String bill_id) {
        int pos = -1;
        for (int index = 0; index < paidBillList.size(); index++) {
            Product_Bill_Model billModel = paidBillList.get(index);
            if (billModel.getItem_id().equals(bill_id)) {
                pos = index;
                return pos;
            }
        }

        return pos;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.stopLocationUpdate();
    }

}