package com.zawraapharma.mvp.activity_date_mvp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.zawraapharma.R;
import com.zawraapharma.internet.InternetManager;
import com.zawraapharma.models.LocationModel;
import com.zawraapharma.models.ResponseData;
import com.zawraapharma.models.UserModel;
import com.zawraapharma.preferences.Preferences;
import com.zawraapharma.remote.Api;
import com.zawraapharma.share.Common;
import com.zawraapharma.tags.Tags;
import com.zawraapharma.ui.activity_payment_date.PaymentDateActivity;
import com.zawraapharma.ui.activity_retrieve_bill.RetrieveBillActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDatePresenter  implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, DatePickerDialog.OnDateSetListener {
    private ActivityDateView view;
    private Context context;
    private DatePickerDialog datePickerDialog;
    private Preferences preferences;
    private UserModel userModel;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private final String gps_perm = Manifest.permission.ACCESS_FINE_LOCATION;
    private final int loc_req = 1255;
    private PaymentDateActivity activity;
    private LocationModel locationModel;

    public ActivityDatePresenter(ActivityDateView view, Context context) {
        this.view = view;
        this.context = context;
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(context);
        createDateDialog();
        activity = (PaymentDateActivity) context;
        checkPermissions();
    }

    private void createDateDialog() {
        try {
            Calendar calendar = Calendar.getInstance(Locale.ENGLISH);

            datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.setOkText(context.getString(R.string.select));
            datePickerDialog.setCancelText(context.getString(R.string.cancel));
            datePickerDialog.setAccentColor(ContextCompat.getColor(context, R.color.colorPrimary));
            datePickerDialog.setOkColor(ContextCompat.getColor(context, R.color.colorPrimary));
            datePickerDialog.setCancelColor(ContextCompat.getColor(context, R.color.gray4));
            datePickerDialog.setLocale(Locale.ENGLISH);
            datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_1);

        }catch (Exception e){}

    }

    public void showDateDialog(FragmentManager fragmentManager){
        try {
            datePickerDialog.show(fragmentManager,"");
        }catch (Exception e){}
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            calendar.set(Calendar.MONTH, monthOfYear);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            String date = dateFormat.format(new Date(calendar.getTimeInMillis()));
            ActivityDatePresenter.this.view.onDateSelected(date);
        }catch (Exception e){}

    }


    public void createAppointment(String date,String client_id)
    {


        if (userModel==null){
            return;
        }
        ProgressDialog dialog = Common.createProgressDialog(context,context.getString(R.string.wait));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .createAppointment(userModel.getData().getToken(), String.valueOf(userModel.getData().getId()),client_id,date,locationModel.getLat(),locationModel.getLng())
                .enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                view.onSuccess();

                            }else {
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
                    public void onFailure(Call<ResponseData> call, Throwable t) {
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
