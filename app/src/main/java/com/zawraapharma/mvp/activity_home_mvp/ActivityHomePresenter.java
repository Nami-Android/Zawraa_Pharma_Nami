package com.zawraapharma.mvp.activity_home_mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.zawraapharma.R;
import com.zawraapharma.internet.InternetManager;
import com.zawraapharma.local_database.AccessDatabase;
import com.zawraapharma.local_database.DataBaseInterfaces;
import com.zawraapharma.models.AllUsersModel;
import com.zawraapharma.models.BillModel;
import com.zawraapharma.models.CartModelBillModel;
import com.zawraapharma.models.CompanyDataModel;
import com.zawraapharma.models.CompanyProductDataModel;
import com.zawraapharma.models.InvoiceDataModel;
import com.zawraapharma.models.LogoutModel;
import com.zawraapharma.models.PharmacyDataModel;
import com.zawraapharma.models.PharmacyModel;
import com.zawraapharma.models.Product_Bill_Model;
import com.zawraapharma.models.ResponseData;
import com.zawraapharma.models.RetrieveModel;
import com.zawraapharma.models.RetrieveModelBillModel;
import com.zawraapharma.models.UploadPaidModel;
import com.zawraapharma.models.UploadRetrieveModel;
import com.zawraapharma.models.UserModel;
import com.zawraapharma.preferences.Preferences;
import com.zawraapharma.remote.Api;
import com.zawraapharma.share.Common;
import com.zawraapharma.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityHomePresenter implements DataBaseInterfaces.UserInterface, DataBaseInterfaces.PharmacyInterface, DataBaseInterfaces.RetrieveInterface, DataBaseInterfaces.CartInterface, DataBaseInterfaces.CompanyInsertedInterface, DataBaseInterfaces.PharmacyInsertInterface, DataBaseInterfaces.CompanyProductInsertedInterface, DataBaseInterfaces.InvoiceInsertedInterface {
    private Context context;
    private HomeActivityView view;
    private Preferences preference;
    private UserModel userModel;
    private AccessDatabase accessDatabase;
    private List<RetrieveModel> uploadList;
    private ProgressDialog dialog;
    private boolean canUpload = true;
    private List<CartModelBillModel> cartModelBillModelList;

    public ActivityHomePresenter(Context context, HomeActivityView view) {
        this.context = context;
        this.view = view;
        preference = Preferences.getInstance();
        userModel = preference.getUserData(context);
        //updateTokenFireBase();
        accessDatabase = new AccessDatabase(context);
        cartModelBillModelList = new ArrayList<>();
        uploadList = new ArrayList<>();

    }


    public boolean isCanUpload() {
        return canUpload;
    }

    public void setCanUpload(boolean canUpload) {
        this.canUpload = canUpload;
    }

    public List<RetrieveModel> getUploadList() {
        return uploadList;
    }

    public List<CartModelBillModel> getCartModelBillModelList() {
        return cartModelBillModelList;
    }

    public void pillPay() {
        view.onNavigateToPillPayActivity();


    }
    public void sales() {
        view.onNavigateToSalesActivity();


    }
    public void findPharmacy() {
        view.onNavigateToFindPharmacyActivity();


    }

    public void debtDisclosure() {
        view.onNavigateToDebtDisclosureActivity();


    }

    public void calender() {
        view.onNavigateToCalenderActivity();


    }
    public void newClient() {
        view.onNavigateToNewClientActivity();


    }
    public void backPress() {
        if (userModel == null) {
            view.onNavigateToLoginActivity();
        } else {
            view.onFinished();
        }

    }
    public void getProfile(String token)
    {


        Api.getService(Tags.base_url)
                .getProfile(userModel.getData().getToken(),userModel.getData().getId())
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                Log.e("eddd", response.body().getData().getIs_block()+"__");
                                if (response.body().getData().getIs_block().equals("blocked")){
                                    logout(token);
                                }

                            }


                        } else {
                            try {
                                Log.e("wwwerrr", response.code() + "__" + response.errorBody().string());
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
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        try {


                            if (t.getMessage() != null) {

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
    public void updateTokenFireBase() {
        userModel = preference.getUserData(context);
        if (userModel != null) {
            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                @Override
                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                    if (task.isSuccessful()) {
                        String token = task.getResult().getToken();
                        Log.e("token", token);
                        Api.getService(Tags.base_url)
                                .updateFirebaseToken(userModel.getData().getToken(), userModel.getData().getId(), token, "android")
                                .enqueue(new Callback<LogoutModel>() {
                                    @Override
                                    public void onResponse(Call<LogoutModel> call, Response<LogoutModel> response) {
                                        if (response.isSuccessful() && response.body() != null && response.body().getStatus() == 200) {
                                            userModel.setPhone_token(token);
                                            preference.create_update_userdata(context, userModel);
                                            getProfile(token);
                                            Log.e("sssstoken", userModel.getPhone_token());
                                        } else {
                                            try {

                                                Log.e("errorToken", response.code() + "_" + response.errorBody().string());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<LogoutModel> call, Throwable t) {
                                        try {

                                            if (t.getMessage() != null) {
                                                Log.e("errorToken2", t.getMessage());
                                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                                    Toast.makeText(context, R.string.something, Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                        } catch (Exception e) {
                                        }
                                    }
                                });
                    }
                }
            });

        }
    }

    public void logout(String token) {
        if (userModel == null) {
            view.onNavigateToLoginActivity();
            return;
        }

        UserModel userModel = preference.getUserData(context);

        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .logout(userModel.getData().getToken(), userModel.getData().getId(), token, "android")
                .enqueue(new Callback<LogoutModel>() {
                    @Override
                    public void onResponse(Call<LogoutModel> call, Response<LogoutModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatus() == 200) {
                                    preference.clear(context);
                                    view.onLogoutSuccess();
                                } else {
                                    view.onFailed(context.getString(R.string.failed));
                                }
                            }


                        } else {

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
                    public void onFailure(Call<LogoutModel> call, Throwable t) {
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

        //online offline
       /* if (InternetManager.isConnected(context)){
            ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
            Api.getService(Tags.base_url)
                    .logout(userModel.getData().getToken(), userModel.getData().getId(), userModel.getData().getFireBaseToken(), "android")
                    .enqueue(new Callback<LogoutModel>() {
                        @Override
                        public void onResponse(Call<LogoutModel> call, Response<LogoutModel> response) {
                            dialog.dismiss();
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    if (response.body().getStatus() == 200) {
                                        preference.clear(context);
                                        accessDatabase.deleteCart();
                                        accessDatabase.deleteCartBill();
                                        accessDatabase.deleteInvoices();
                                        accessDatabase.deletePharmacies();
                                        accessDatabase.deleteProducts();
                                        accessDatabase.deleteRetrieve();
                                        view.onLogoutSuccess();
                                    } else {
                                        view.onFailed(context.getString(R.string.failed));
                                    }
                                }


                            } else {

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
                        public void onFailure(Call<LogoutModel> call, Throwable t) {
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

        }else {
            preference.clear(context);
            accessDatabase.deleteCart();
            accessDatabase.deleteCartBill();
            accessDatabase.deleteInvoices();
            accessDatabase.deletePharmacies();
            accessDatabase.deleteProducts();
            accessDatabase.deleteRetrieve();

            view.onLogoutSuccess();
        }*/


    }

    public void syncData() {
        if (userModel == null) {
            return;
        }
        dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        getPharmacies();


    }

    public void refreshData() {

        if (userModel == null) {
            return;
        }
        canUpload = true;
        if (InternetManager.isConnected(context)) {
            dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            getPharmacies();
        }


    }


    private void getPharmacies() {
        accessDatabase.getPharmacies(this);

    }

    @Override
    public void onPharmacyDataSuccess(List<PharmacyModel> pharmacyModelList) {


        Log.e("1", "1");
        if (pharmacyModelList == null || pharmacyModelList.size() == 0) {

            Log.e("2", "2");

            Api.getService(Tags.base_url)
                    .search_bill_number(userModel.getData().getToken(), userModel.getData().getId(), "all")
                    .enqueue(new Callback<PharmacyDataModel>() {
                        @Override
                        public void onResponse(Call<PharmacyDataModel> call, Response<PharmacyDataModel> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                    Log.e("size", response.body().getData().size()+"__");
                                    Log.e("3", "3");
                                    if (response.body().getData().size()>0){
                                        accessDatabase.insertPharmacies(response.body().getData(), ActivityHomePresenter.this);

                                    }else {
                                        Log.e("33", "33");

                                        getCompanies(dialog);
                                        //getRetrieve();
                                    }

                                } else {
                                    getCompanies(dialog);
                                    //getRetrieve();
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
                        public void onFailure(Call<PharmacyDataModel> call, Throwable t) {
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

            Log.e("size", pharmacyModelList.size() + "__");
            getRetrieve();

        }
    }

    @Override
    public void onPharmacyDataInsertedSuccess() {
        getCompanies(dialog);

    }

    private void getCompanies(ProgressDialog dialog) {

        Api.getService(Tags.base_url)
                .getCompanies(userModel.getData().getToken())
                .enqueue(new Callback<CompanyDataModel>() {
                    @Override
                    public void onResponse(Call<CompanyDataModel> call, Response<CompanyDataModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                if (response.body().getData().size()>0){
                                    accessDatabase.insertCompany(response.body().getData(), ActivityHomePresenter.this);

                                }else {
                                    getInvoices(dialog);
                                }

                            } else {
                                getInvoices(dialog);
                                //getRetrieve();
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
                    public void onFailure(Call<CompanyDataModel> call, Throwable t) {
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

    @Override
    public void onCompanyDataInsertedSuccess() {
        getProducts(dialog);
    }

    private void getProducts(ProgressDialog dialog) {
        Api.getService(Tags.base_url)
                .getProducts(userModel.getData().getToken())
                .enqueue(new Callback<CompanyProductDataModel>() {
                    @Override
                    public void onResponse(Call<CompanyProductDataModel> call, Response<CompanyProductDataModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                accessDatabase.insertCompanyProducts(response.body().getData(), ActivityHomePresenter.this);


                            } else {
                                getInvoices(dialog);
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
                    public void onFailure(Call<CompanyProductDataModel> call, Throwable t) {
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

    private void getUsers(ProgressDialog dialog) {
        Api.getService(Tags.base_url)
                .getUsers(userModel.getData().getToken())
                .enqueue(new Callback<AllUsersModel>() {
                    @Override
                    public void onResponse(Call<AllUsersModel> call, Response<AllUsersModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                if (response.body().getData().size()>0){
                                    accessDatabase.insertUsers(response.body().getData(),ActivityHomePresenter.this);

                                }else {
                                    getRetrieve();

                                }

                            } else {
                                getRetrieve();
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
                    public void onFailure(Call<AllUsersModel> call, Throwable t) {
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


    @Override
    public void onCompanyProductDataInsertedSuccess() {
        getInvoices(dialog);

    }

    private void getInvoices(ProgressDialog dialog) {
        Api.getService(Tags.base_url)
                .getUserBills(userModel.getData().getToken(), userModel.getData().getId())
                .enqueue(new Callback<InvoiceDataModel>() {
                    @Override
                    public void onResponse(Call<InvoiceDataModel> call, Response<InvoiceDataModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                if (response.body().getData().size()>0){
                                    accessDatabase.insertInvoice(response.body().getData(), ActivityHomePresenter.this);

                                }else {
                                    getRetrieve();

                                }

                            } else {
                                getRetrieve();
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
                    public void onFailure(Call<InvoiceDataModel> call, Throwable t) {
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

    @Override
    public void onInvoiceDataInsertedSuccess() {

        getUsers(dialog);

    }

    @Override
    public void onUserDataSuccess() {
        getRetrieve();
    }

    public void getRetrieve() {
        Log.e("4", "4");

        accessDatabase.getRetrieve(this);
    }


    @Override
    public void onRetrieveDataSuccess(List<RetrieveModelBillModel> retrieveModelList) {

        Log.e("5", "5");

        List<RetrieveModel> list = new ArrayList<>();
        for (RetrieveModelBillModel model : retrieveModelList) {
            RetrieveModel retrieveModel = model.retrieveModel;
            retrieveModel.setItems(model.billModelList);
            list.add(retrieveModel);
        }
        uploadList = new ArrayList<>(list);
        getPaidBill();


    }

    private void getPaidBill() {
        accessDatabase.getCart(this);
    }

    @Override
    public void onCartDataSuccess(List<CartModelBillModel> cartModelBillModelList) {
        Log.e("6", "6");

        this.cartModelBillModelList.clear();
        this.cartModelBillModelList.addAll(cartModelBillModelList);
        if (canUpload){
            Log.e("7", "7");

            uploadRetrieveData(uploadList, cartModelBillModelList);

        }else {
            Log.e("8", "8");

            if (uploadList.size()>0||cartModelBillModelList.size()>0){
               Toast.makeText(context, R.string.up_data, Toast.LENGTH_SHORT).show();
           }else {
                userModel = preference.getUserData(context);
                if (userModel!=null&&userModel.getPhone_token()!=null){
                    logout(userModel.getPhone_token());

                }else {
                    logout("");
                }
           }
        }


    }

    private void uploadRetrieveData(List<RetrieveModel> uploadList, List<CartModelBillModel> cartModelBillModelList) {
        Log.e("9", "9");

        if (uploadList.size() > 0) {
            List<UploadRetrieveModel.Backs> backsList = new ArrayList<>();
            for (RetrieveModel retrieveModel : uploadList) {
                List<UploadRetrieveModel.Items> itemsList = new ArrayList<>();

                for (Product_Bill_Model product_bill_model : retrieveModel.getItems()) {
                    UploadRetrieveModel.Items items = new UploadRetrieveModel.Items(product_bill_model.getItem_id(), product_bill_model.getBack_amount());
                    itemsList.add(items);


                }

                UploadRetrieveModel.Backs backs = new UploadRetrieveModel.Backs(retrieveModel.getClient_id(), retrieveModel.getCompany_id(), retrieveModel.getBill_code(), itemsList);
                backsList.add(backs);
            }

            UploadRetrieveModel uploadRetrieveModel = new UploadRetrieveModel(userModel.getData().getId(), backsList);

            Api.getService(Tags.base_url)
                    .uploadRetrieveData(userModel.getData().getToken(), uploadRetrieveModel)
                    .enqueue(new Callback<ResponseData>() {
                        @Override
                        public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null && response.body().getStatus() == 200) {
                                    accessDatabase.deleteRetrieve();
                                    if (cartModelBillModelList.size() > 0) {
                                        uploadPaidData(cartModelBillModelList, dialog);

                                    } else {
                                        dialog.dismiss();
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
        } else {
            if (cartModelBillModelList.size() > 0) {
                uploadPaidData(cartModelBillModelList, dialog);

            } else {
                dialog.dismiss();
            }
        }


    }

    private void uploadPaidData(List<CartModelBillModel> cartModelBillModelList, ProgressDialog dialog) {
        Log.e("10", "10");

        List<UploadPaidModel.Pay> payList = new ArrayList<>();

        for (CartModelBillModel model : cartModelBillModelList) {
            List<UploadPaidModel.Bill> bills = new ArrayList<>();

            for (BillModel billModel : model.billModelList) {
                UploadPaidModel.Bill bill = new UploadPaidModel.Bill(billModel.getCode(), billModel.getPaid_amount(), billModel.getCompany_name());
                bills.add(bill);


            }
            UploadPaidModel.Pay pay = new UploadPaidModel.Pay(model.cartModel.getClient_id(), model.cartModel.getTotal(), model.cartModel.getDiscount(), model.cartModel.getTotal_after_discount(), model.cartModel.getNotes(), bills);
            payList.add(pay);
        }


        UploadPaidModel uploadPaidModel = new UploadPaidModel(userModel.getData().getId(), payList);

        Api.getService(Tags.base_url)
                .uploadPaidBills(userModel.getData().getToken(), uploadPaidModel)
                .enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                Toast.makeText(context, context.getString(R.string.suc), Toast.LENGTH_SHORT).show();
                                accessDatabase.deleteCart();
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




}
