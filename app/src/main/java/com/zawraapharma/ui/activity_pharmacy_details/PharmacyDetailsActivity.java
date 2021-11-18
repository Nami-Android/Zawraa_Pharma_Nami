package com.zawraapharma.ui.activity_pharmacy_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zawraapharma.R;
import com.zawraapharma.databinding.ActivityPharmacyDetailsBinding;
import com.zawraapharma.language.Language;
import com.zawraapharma.models.PharmacyModel;
import com.zawraapharma.ui.activity_pay_bill.PayBillActivity;
import com.zawraapharma.ui.activity_payment_date.PaymentDateActivity;
import com.zawraapharma.ui.activity_retrieve_bill.RetrieveBillActivity;
import com.zawraapharma.ui.activity_sales.SalesActivity;

import io.paperdb.Paper;

public class PharmacyDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ActivityPharmacyDetailsBinding binding;
    private String lang;
    private GoogleMap mMap;
    private PharmacyModel pharmacyModel;
    private float zoom = 15.0f;
    private String id;



    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase,Paper.book().read("lang","ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pharmacy_details);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        pharmacyModel = (PharmacyModel) intent.getSerializableExtra("data");
        id=intent.getStringExtra("id");
    }


    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        binding.setTitle(pharmacyModel.getTitle());

        if (id.equals("sales")){
            binding.cardSales.setVisibility(View.VISIBLE);
        }else {
            binding.cardPaymentDate.setVisibility(View.VISIBLE);
            binding.llFirst.setVisibility(View.VISIBLE);
        }
        binding.cardPayBill.setOnClickListener(view -> {
            Intent intent = new Intent(this, PayBillActivity.class);
            intent.putExtra("data",pharmacyModel);
            startActivity(intent);
        });

        binding.cardRetrieveInvoice.setOnClickListener(view -> {
            Intent intent = new Intent(this, RetrieveBillActivity.class);
            intent.putExtra("data",pharmacyModel);

            startActivity(intent);
        });

        binding.cardPaymentDate.setOnClickListener(view -> {
            Intent intent = new Intent(this, PaymentDateActivity.class);
            intent.putExtra("data",pharmacyModel);

            startActivity(intent);
        });

        binding.cardSales.setOnClickListener(view -> {
            Intent intent = new Intent(this, SalesActivity.class);
            intent.putExtra("data",pharmacyModel);
            startActivity(intent);
        });

        binding.imageBack.setOnClickListener(view -> finish());
        updateUI();

    }


    private void updateUI() {

        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (googleMap != null) {
            mMap = googleMap;
            mMap.setTrafficEnabled(false);
            mMap.setBuildingsEnabled(false);
            mMap.setIndoorEnabled(true);
            if (pharmacyModel.getLatitude()!=null&&pharmacyModel.getLongitude()!=null){
                addMarker(new LatLng(Double.parseDouble(pharmacyModel.getLatitude()),Double.parseDouble(pharmacyModel.getLongitude())));

            }

        }
    }

    private void addMarker(LatLng latLng) {
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

    }

}