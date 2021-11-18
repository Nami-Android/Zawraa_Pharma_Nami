package com.zawraapharma.mvp.activity_sales_mvp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

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
import com.zawraapharma.models.AddSalesModel;
import com.zawraapharma.models.CompanyDataModel;
import com.zawraapharma.models.CompanyModel;
import com.zawraapharma.models.CompanyProductDataModel;
import com.zawraapharma.models.CompanyProductModel;
import com.zawraapharma.models.LocationModel;
import com.zawraapharma.models.PharmacyModel;
import com.zawraapharma.models.Product_Bill_Model;
import com.zawraapharma.models.ResponseData;
import com.zawraapharma.models.RetrieveDataModel;
import com.zawraapharma.models.RetrieveModel;
import com.zawraapharma.models.RetrieveResponseModel;
import com.zawraapharma.models.SalesDetailsDataModel;
import com.zawraapharma.models.UserModel;
import com.zawraapharma.mvp.activity_retrieve_bill_mvp.RetrieveActivityView;
import com.zawraapharma.preferences.Preferences;
import com.zawraapharma.remote.Api;
import com.zawraapharma.share.Common;
import com.zawraapharma.tags.Tags;
import com.zawraapharma.ui.activity_sales.SalesActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivitySalesPresenter implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private Context context;
    private SalesActivityView view;
    private Preferences preference;
    private UserModel userModel;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private final String gps_perm = Manifest.permission.ACCESS_FINE_LOCATION;
    private final int loc_req = 1255;
    private SalesActivity activity;

    public ActivitySalesPresenter(Context context, SalesActivityView view) {
        this.context = context;
        this.view = view;
        preference = Preferences.getInstance();
        userModel = preference.getUserData(context);
        activity = (SalesActivity) context;
        checkPermission();

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

        view.onProgressShow();
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

    public void addSales(AddSalesModel model) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .addSales(userModel.getData().getToken(), model)
                .enqueue(new Callback<SalesDetailsDataModel>() {
                    @Override
                    public void onResponse(Call<SalesDetailsDataModel> call, Response<SalesDetailsDataModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                view.onAddSuccess(response.body().getData());
                                Toast.makeText(context, context.getString(R.string.suc), Toast.LENGTH_SHORT).show();
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
                    public void onFailure(Call<SalesDetailsDataModel> call, Throwable t) {
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
