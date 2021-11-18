package com.zawraapharma.mvp.activity_retrieve_bill_mvp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.zawraapharma.R;
import com.zawraapharma.internet.InternetManager;
import com.zawraapharma.local_database.AccessDatabase;
import com.zawraapharma.local_database.DataBaseInterfaces;
import com.zawraapharma.models.CompanyDataModel;
import com.zawraapharma.models.CompanyModel;
import com.zawraapharma.models.CompanyProductDataModel;
import com.zawraapharma.models.CompanyProductModel;
import com.zawraapharma.models.LocationModel;
import com.zawraapharma.models.LogoutModel;
import com.zawraapharma.models.PharmacyModel;
import com.zawraapharma.models.Product_Bill_Model;
import com.zawraapharma.models.RetrieveDataModel;
import com.zawraapharma.models.RetrieveModel;
import com.zawraapharma.models.RetrieveResponseModel;
import com.zawraapharma.models.UserModel;
import com.zawraapharma.preferences.Preferences;
import com.zawraapharma.remote.Api;
import com.zawraapharma.share.Common;
import com.zawraapharma.tags.Tags;
import com.zawraapharma.ui.activity_retrieve_bill.RetrieveBillActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityRetrievePresenter implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, DataBaseInterfaces.CompanyInterface, DataBaseInterfaces.ProductsInterface, DataBaseInterfaces.RetrieveInsertInterface, DataBaseInterfaces.BillInsertInterface {
    private Context context;
    private RetrieveActivityView view;
    private Preferences preference;
    private UserModel userModel;
    private AccessDatabase accessDatabase;
    private RetrieveModel retrieveModel;
    private RetrieveResponseModel retrieveResponseModel;

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private final String gps_perm = Manifest.permission.ACCESS_FINE_LOCATION;
    private final int loc_req = 1255;
    private RetrieveBillActivity activity;
    private LocationModel locationModel;
    public ActivityRetrievePresenter(Context context, RetrieveActivityView view) {
        this.context = context;
        this.view = view;
        preference = Preferences.getInstance();
        userModel = preference.getUserData(context);
        activity = (RetrieveBillActivity) context;
        accessDatabase = new AccessDatabase(context);
        checkPermissions();
    }

    public void getCompany() {
        if (userModel == null) {
            return;
        }
        //offline online
       // accessDatabase.getCompanies(this);

        Api.getService(Tags.base_url)
                .getCompanies(userModel.getData().getToken())
                .enqueue(new Callback<CompanyDataModel>() {
                    @Override
                    public void onResponse(Call<CompanyDataModel> call, Response<CompanyDataModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                view.onCompanySuccess(response.body().getData());

                            }


                        } else {
                            view.onProgressHide();
                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                view.onFailed("Server Error");

                            } else {
                                view.onFailed(context.getString(R.string.failed));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CompanyDataModel> call, Throwable t) {
                        try {
                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    view.onFailed(context.getString(R.string.something));
                                } else {
                                    view.onFailed(context.getString(R.string.failed));

                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });

    }


    public void search(String company_id) {
        if (userModel == null) {
            return;
        }

        //offline online
        //accessDatabase.getProductsByCompanyId(this, company_id);

        Api.getService(Tags.base_url)
                .getCompanyProduct(userModel.getData().getToken(), company_id)
                .enqueue(new Callback<CompanyProductDataModel>() {
                    @Override
                    public void onResponse(Call<CompanyProductDataModel> call, Response<CompanyProductDataModel> response) {
                        view.onProgressHide();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                view.onProductSuccess(response.body().getData());

                            }


                        } else {
                            view.onProgressHide();
                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                view.onFailed("Server Error");

                            } else {
                                view.onFailed(context.getString(R.string.failed));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CompanyProductDataModel> call, Throwable t) {
                        try {
                            view.onProgressHide();


                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    view.onFailed(context.getString(R.string.something));
                                } else {
                                    view.onFailed(context.getString(R.string.failed));

                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });


    }

    public void retrieveBill(String bill_number, String company_id, String client_id, List<Product_Bill_Model> paidBillList, PharmacyModel pharmacyModel,CompanyModel companyModel) {

        if (userModel == null) {
            return;
        }


        retrieveModel = new RetrieveModel(bill_number, company_id, String.valueOf(userModel.getData().getId()), client_id, paidBillList,locationModel.getLat(),locationModel.getLng());
        Calendar calendar = Calendar.getInstance();
        Date m_date = new Date();
        m_date.setTime(calendar.getTimeInMillis());
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(m_date);
        retrieveResponseModel = new RetrieveResponseModel();
        retrieveResponseModel.setBill_id(null);
        retrieveResponseModel.setClient_id(client_id);
        retrieveResponseModel.setCompany_id(company_id);
        retrieveResponseModel.setClient(pharmacyModel);
        retrieveResponseModel.setCompany(companyModel);
        retrieveResponseModel.setPaid_amount(0);
        retrieveResponseModel.setDate(date);
        retrieveResponseModel.setCreated_at(date);
        retrieveResponseModel.setUpdated_at(date);
        retrieveResponseModel.setBill_code(bill_number);
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .retrieveBill(userModel.getData().getToken(), retrieveModel)
                .enqueue(new Callback<RetrieveDataModel>() {
                    @Override
                    public void onResponse(Call<RetrieveDataModel> call, Response<RetrieveDataModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatus() == 200) {
                                    view.onRetrieveSuccess(response.body().getData());

                                } else if (response.body().getStatus() == 422) {
                                    view.onFailed(context.getString(R.string.bill_num_inc));
                                }

                            } else {
                                view.onFailed(context.getString(R.string.failed));
                            }


                        } else {
                            dialog.dismiss();
                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                view.onFailed("Server Error");

                            } else {
                                view.onFailed(context.getString(R.string.failed));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RetrieveDataModel> call, Throwable t) {
                        try {
                            dialog.dismiss();


                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    view.onFailed(context.getString(R.string.something));
                                } else {
                                    view.onFailed(context.getString(R.string.failed));

                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });

        //offline online
       /* if (InternetManager.isConnected(context)) {
            ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
            Api.getService(Tags.base_url)
                    .retrieveBill(userModel.getData().getToken(), retrieveModel)
                    .enqueue(new Callback<RetrieveDataModel>() {
                        @Override
                        public void onResponse(Call<RetrieveDataModel> call, Response<RetrieveDataModel> response) {
                            dialog.dismiss();
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    if (response.body().getStatus() == 200) {
                                        view.onRetrieveSuccess(response.body().getData());

                                    } else if (response.body().getStatus() == 422) {
                                        view.onFailed(context.getString(R.string.bill_num_inc));
                                    }

                                } else {
                                    view.onFailed(context.getString(R.string.failed));
                                }


                            } else {
                                dialog.dismiss();
                                try {
                                    Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if (response.code() == 500) {
                                    view.onFailed("Server Error");

                                } else {
                                    view.onFailed(context.getString(R.string.failed));

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<RetrieveDataModel> call, Throwable t) {
                            try {
                                dialog.dismiss();


                                if (t.getMessage() != null) {
                                    Log.e("error_not_code", t.getMessage() + "__");

                                    if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                        view.onFailed(context.getString(R.string.something));
                                    } else {
                                        view.onFailed(context.getString(R.string.failed));

                                    }
                                }
                            } catch (Exception e) {
                                Log.e("Error", e.getMessage() + "__");
                            }
                        }
                    });
        } else {

            accessDatabase.insertRetrieve(retrieveModel, this);


        }*/


    }

    @Override
    public void onCompanyDataSuccess(List<CompanyModel> companyModelList) {
        view.onCompanySuccess(companyModelList);
    }

    @Override
    public void onProductsDataSuccess(List<CompanyProductModel> companyProductModelList) {
        if (userModel != null && companyProductModelList != null) {
            view.onProductSuccess(companyProductModelList);
        }
    }

    @Override
    public void onRetrieveDataSuccess(long id) {
        retrieveResponseModel.setId((int) id);
        List<RetrieveResponseModel.BackItemsFk> backItemsFkList = new ArrayList<>();


        List<Product_Bill_Model> productBillModelList = new ArrayList<>();
        for (Product_Bill_Model product_bill_model : retrieveModel.getItems()) {
            int back_id = (int) System.currentTimeMillis();
            product_bill_model.setRetrieve_local_id((int) id);
            productBillModelList.add(product_bill_model);

            RetrieveResponseModel.BackItemsFk backItemsFk = new RetrieveResponseModel.BackItemsFk(back_id,String.valueOf(retrieveResponseModel.getId()),null,Integer.parseInt(product_bill_model.getItem_id()),product_bill_model.getBack_amount(),retrieveResponseModel.getDate(),retrieveResponseModel.getDate(),product_bill_model.getItemFk());
            backItemsFkList.add(backItemsFk);


        }
        retrieveResponseModel.setBack_items_fk(backItemsFkList);
        accessDatabase.insertBill(productBillModelList, this);





    }

    @Override
    public void onBillDataSuccess() {
        view.onRetrieveSuccess(retrieveResponseModel);

    }


    public void checkPermissions() {
        if (InternetManager.isConnected(context)) {
            checkPermission();

        } else {
            view.onLocationSuccess(new LocationModel(0.0, 0.0));
        }
    }


    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(context, gps_perm) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{gps_perm}, loc_req);
        } else {
            initGoogleApiClient();
        }
    }

    public void initGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        initLocationRequest();
    }

    private void initLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(60000);
        locationRequest.setFastestInterval(60000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(false);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(result1 -> {

            Status status = result1.getStatus();
            switch (status.getStatusCode()) {
                case LocationSettingsStatusCodes.SUCCESS:
                    startLocationUpdate();
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    Log.e("not available", "not available");
                    break;
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    try {
                        status.startResolutionForResult(activity, 1255);
                    } catch (Exception e) {
                    }
                    break;
            }
        });

    }

    @Override
    public void onConnectionSuspended(int i) {
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @SuppressLint("MissingPermission")
    public void startLocationUpdate() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                onLocationChanged(locationResult.getLastLocation());
            }
        };
        LocationServices.getFusedLocationProviderClient(context)
                .requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    @Override
    public void onLocationChanged(Location location) {

        LocationModel locationModel = new LocationModel(location.getLatitude(), location.getLongitude());
        this.locationModel = locationModel;
        view.onLocationSuccess(locationModel);

    }

    public void stopLocationUpdate() {
        if (googleApiClient != null && locationCallback != null) {
            LocationServices.getFusedLocationProviderClient(context).removeLocationUpdates(locationCallback);
            googleApiClient.disconnect();
            googleApiClient = null;

        }
    }
}
