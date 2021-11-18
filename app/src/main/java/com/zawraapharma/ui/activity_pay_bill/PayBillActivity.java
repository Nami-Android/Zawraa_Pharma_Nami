package com.zawraapharma.ui.activity_pay_bill;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zawraapharma.R;
import com.zawraapharma.adapters.InvoiceAdapter;
import com.zawraapharma.databinding.ActivityPayBillBinding;
import com.zawraapharma.language.Language;
import com.zawraapharma.models.BillModel;
import com.zawraapharma.models.BillResponse;
import com.zawraapharma.models.InvoiceModel;
import com.zawraapharma.models.LocationModel;
import com.zawraapharma.models.PharmacyModel;
import com.zawraapharma.mvp.activity_pay_bill_mvp.ActivityPayBillPresenter;
import com.zawraapharma.mvp.activity_pay_bill_mvp.PayBillActivityView;
import com.zawraapharma.share.Common;
import com.zawraapharma.ui.activity_bill_details.BillDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class PayBillActivity extends AppCompatActivity implements PayBillActivityView {
    private ActivityPayBillBinding binding;
    private String lang;
    private PharmacyModel pharmacyModel;
    private List<InvoiceModel> invoiceModelList;
    private ActivityPayBillPresenter presenter;
    private InvoiceAdapter adapter;
    private List<BillModel> paidBillList;
    private double totalBill = 0.0;
    private double total_after_discount =0.0;
    private double total_remain =0.0;

    private double discount = 0;
    private LocationModel locationModel;

    @Override
    protected void attachBaseContext(Context newBase)
    {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase,Paper.book().read("lang","ar")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pay_bill);
        getDataFromIntent();
        initView();
    }
    private void getDataFromIntent()
    {
        Intent intent = getIntent();
        pharmacyModel = (PharmacyModel) intent.getSerializableExtra("data");
    }

    private void initView() {
        paidBillList = new ArrayList<>();
        invoiceModelList = new ArrayList<>();
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        binding.setTitle(pharmacyModel.getTitle());
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new InvoiceAdapter(invoiceModelList,this);
        binding.recView.setAdapter(adapter);

        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        binding.imageBack.setOnClickListener(view -> finish());

        binding.edtDiscount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()){
                    discount =0.0;
                }else {
                    discount = Double.parseDouble(editable.toString());

                }
                calculateTotal();

            }
        });

        binding.btnBack.setOnClickListener(view -> finish());
        binding.btnShow.setOnClickListener(view -> {
            String note = binding.edtNote.getText().toString();

            presenter.setUpData(note,paidBillList,pharmacyModel.getId(),String.valueOf(totalBill),String.valueOf(discount),String.valueOf(total_after_discount),locationModel);
        });

        presenter = new ActivityPayBillPresenter(this,this,pharmacyModel);
        presenter.checkPermissions();

    }


    @Override
    public void onSuccess(List<InvoiceModel> data) {
        if (data.size()>0){
            invoiceModelList.clear();
            invoiceModelList.addAll(data);
            adapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onLocationSuccess(LocationModel locationModel) {
        this.locationModel = locationModel;
        presenter.stopLocationUpdate();
        presenter.getBill(String.valueOf(pharmacyModel.getId()));

    }

    @Override
    public void onCartSendSuccess(BillResponse.Data data) {
        Toast.makeText(this, getString(R.string.suc), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, BillDetailsActivity.class);
        intent.putExtra("data",data);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressShow() {
        binding.flLoad.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressHide() {
        binding.flLoad.setVisibility(View.GONE);

    }

    public void addUpdateItem(int adapterPosition, InvoiceModel model, String amount) {
        int pos = getBillPos(String.valueOf(model.getId()));
        if (pos==-1){
            if (!amount.equals("0")){
                BillModel billModel = new BillModel(String.valueOf(model.getId()),model.getId(),amount,model.getCode(),model.getCompany_id(),model.getCompany_fk(),model.getDebt_amount(),model.getStatus(),model.getIs_exceed_90_days(),model.getNotes(),model.getCreated_at(),model.getUpdated_at(),model.getCompany_fk().getTitle(), (int) model.getRemaining());
                paidBillList.add(billModel);
            }
        }else {
            if (!amount.equals("0")){
                BillModel billModel = paidBillList.get(pos);
                billModel.setPaid_amount(amount);
                paidBillList.set(pos,billModel);
            }else {
                paidBillList.remove(pos);
            }
        }

        if (paidBillList.size()>0){
            binding.btnShow.setVisibility(View.VISIBLE);
            binding.space.setVisibility(View.VISIBLE);
        }else {
            binding.btnShow.setVisibility(View.GONE);
            binding.space.setVisibility(View.GONE);
        }
        calculateTotal();
    }

    private void calculateTotal() {
        totalBill = 0.0;
        total_after_discount =0.0;
        total_remain = 0.0;

        for (BillModel billModel:paidBillList){
            //totalBill +=Double.parseDouble(billModel.getPaid_amount());
            //totalBill +=billModel.getRemain();
            totalBill +=Double.parseDouble(billModel.getPaid_amount());
            total_remain +=billModel.getRemain();


        }
        binding.tvTotal.setText(String.valueOf(totalBill));
        //total_after_discount = totalBill-(totalBill*(discount/100));
        if (discount==0){
            total_after_discount = totalBill;

        }else {
            total_after_discount = total_remain-(total_remain*(discount/100));

        }
        binding.tvFinalTotal.setText(String.valueOf(total_after_discount));


    }

    private int getBillPos(String bill_id){
        int pos = -1;
        for (int index=0;index<paidBillList.size();index++){
            BillModel billModel = paidBillList.get(index);
            if (billModel.getBill_id().equals(bill_id)){
                pos = index;
                return pos;
            }
        }

        return pos;
    }

    public void createAlert() {
        Common.CreateDialogAlert(this,getString(R.string.paid_amount_grater));
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