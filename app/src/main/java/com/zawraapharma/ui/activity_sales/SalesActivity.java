package com.zawraapharma.ui.activity_sales;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.zawraapharma.R;
import com.zawraapharma.adapters.CompanySpinnerAdapter;
import com.zawraapharma.adapters.InvoiceAdapter3;
import com.zawraapharma.adapters.SalesAdapter;
import com.zawraapharma.databinding.ActivityBillRetrieveBinding;
import com.zawraapharma.databinding.ActivitySalesBinding;
import com.zawraapharma.language.Language;
import com.zawraapharma.models.AddSalesModel;
import com.zawraapharma.models.BillModel;
import com.zawraapharma.models.CompanyModel;
import com.zawraapharma.models.CompanyProductModel;
import com.zawraapharma.models.LocationModel;
import com.zawraapharma.models.PharmacyModel;
import com.zawraapharma.models.RetrieveResponseModel;
import com.zawraapharma.models.SalesDetailsModel;
import com.zawraapharma.models.UserModel;
import com.zawraapharma.mvp.activity_sales_mvp.ActivitySalesPresenter;
import com.zawraapharma.mvp.activity_sales_mvp.SalesActivityView;
import com.zawraapharma.preferences.Preferences;
import com.zawraapharma.printer_utils.BluetoothUtil;
import com.zawraapharma.printer_utils.ESCUtil;
import com.zawraapharma.printer_utils.SunmiPrintHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;

public class SalesActivity extends AppCompatActivity implements SalesActivityView {

    private ActivitySalesBinding binding;
    private PharmacyModel pharmacyModel;
    private String lang = "";
    private SalesAdapter adapter;

    private List<CompanyModel> companyModelList;
    private ActivitySalesPresenter presenter;
    private CompanySpinnerAdapter companySpinnerAdapter;
    private List<CompanyProductModel> companyProductModelList;
    private List<AddSalesModel.SalesItem> salesItemList;
    private UserModel userModel;
    private Preferences preferences;
    private String company_id = "0";
    private CompanyModel companyModel;
    private LocationModel locationModel;
    private Map<Integer, AddSalesModel.SalesItem> map;




    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sales);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        pharmacyModel = (PharmacyModel) intent.getSerializableExtra("data");
    }

    private void initView() {
        map = new HashMap<>();
        companyProductModelList = new ArrayList<>();
        companyModelList = new ArrayList<>();
        CompanyModel model = new CompanyModel("0", getString(R.string.choose_company));
        companyModelList.add(model);
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        salesItemList = new ArrayList<>();
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.setTitle(pharmacyModel.getTitle());
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SalesAdapter(companyProductModelList,this);
        binding.recView.setAdapter(adapter);

        companySpinnerAdapter = new CompanySpinnerAdapter(companyModelList, this);
        binding.spinner.setAdapter(companySpinnerAdapter);
        presenter = new ActivitySalesPresenter(this,this);
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

        binding.imageBack.setOnClickListener(v -> finish());


        binding.btnShow.setOnClickListener(v -> {
            salesItemList.clear();
            salesItemList.addAll(map.values());
            String notes = binding.edtNote.getText().toString().trim();
            AddSalesModel addSalesModel = new AddSalesModel(userModel.getData().getId(),pharmacyModel.getId(),locationModel.getLat(),locationModel.getLng(),notes,salesItemList);
            presenter.addSales(addSalesModel);
        });

    }


    public void addUpdateItem(int adapterPosition, CompanyProductModel model, String amount,String bonus) {
        if (!map.containsKey(adapterPosition)){
            if (!amount.equals("0")){
                AddSalesModel.SalesItem item = new AddSalesModel.SalesItem(model.getId(),amount,bonus,model.getCompany_id());
                map.put(adapterPosition,item);
            }
        }{
            if (!amount.equals("0")){

                AddSalesModel.SalesItem item = map.get(adapterPosition);
                if (item!=null){
                    item.setAmount(amount);
                    item.setBouns(bonus);
                    map.put(adapterPosition, item);
                }


            }else {
                map.remove(adapterPosition);

            }
        }


        Log.e("size", map.size()+"__");
        if (map.size()>0){
            binding.btnShow.setVisibility(View.VISIBLE);
        }else{
            binding.btnShow.setVisibility(View.GONE);

        }

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
    public void onLocationSuccess(LocationModel locationModel) {
        this.locationModel = locationModel;
        binding.progBar2.setVisibility(View.GONE);
        binding.scrollView.setVisibility(View.VISIBLE);
        presenter.stopLocationUpdate();
        presenter.getCompany();

    }

    @Override
    public void onAddSuccess(SalesDetailsModel salesDetailsModel) {
        finish();
    }



    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProgressShow() {
        companyProductModelList.clear();
        adapter.notifyDataSetChanged();
        binding.tvNoData.setVisibility(View.GONE);
        binding.progBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressHide() {
        binding.progBar.setVisibility(View.GONE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1255) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.initGoogleApiClient();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1255 && resultCode == RESULT_OK) {
            presenter.startLocationUpdate();

        }
    }

}