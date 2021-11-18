package com.zawraapharma.ui.activity_calender;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.applandeo.materialcalendarview.EventDay;
import com.zawraapharma.R;
import com.zawraapharma.adapters.PharmacyAdapter;
import com.zawraapharma.adapters.PharmacyAdapter2;
import com.zawraapharma.databinding.ActivityCalenderBinding;
import com.zawraapharma.language.Language;
import com.zawraapharma.models.AppointmentModel;
import com.zawraapharma.models.PharmacyModel;
import com.zawraapharma.models.ResponseData;
import com.zawraapharma.models.SalesDetailsDataModel;
import com.zawraapharma.models.UserModel;
import com.zawraapharma.mvp.activity_calender_mvp.ActivityCalenderPresenter;
import com.zawraapharma.mvp.activity_calender_mvp.CalenderActivityView;
import com.zawraapharma.preferences.Preferences;
import com.zawraapharma.remote.Api;
import com.zawraapharma.share.Common;
import com.zawraapharma.tags.Tags;
import com.zawraapharma.ui.activity_payment_date.PaymentDateActivity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalenderActivity extends AppCompatActivity implements CalenderActivityView {
    private ActivityCalenderBinding binding;
    private ActivityCalenderPresenter presenter;
    private List<PharmacyModel> pharmacyModelList;
    private PharmacyAdapter2 adapter;
    private String lang;
    private String todayDate;
    private List<AppointmentModel> appointmentModelList;
    private String selectedDate = "";
    private UserModel userModel;
    private Preferences preferences;
    private PharmacyModel selectedPharmacy;




    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase,Paper.book().read("lang","ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calender);
        initView();
    }



    private void initView() {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        appointmentModelList = new ArrayList<>();
        Paper.init(this);
        lang= Paper.book().read("lang","ar");
        binding.setLang(lang);
        pharmacyModelList = new ArrayList<>();
        presenter = new ActivityCalenderPresenter(this, this);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PharmacyAdapter2(pharmacyModelList,this);
        binding.recView.setAdapter(adapter);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.progBar2.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        presenter.getAppointment();

        Calendar firstSelectedDate = binding.calendarView.getFirstSelectedDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        todayDate = simpleDateFormat.format(new Date(firstSelectedDate.getTimeInMillis()));
        selectedDate = todayDate;
        presenter.searchAppointment(todayDate);

        binding.calendarView.setOnDayClickListener(eventDay -> {
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            String selectedDate = simpleDateFormat2.format(new Date(eventDay.getCalendar().getTimeInMillis()));
            this.selectedDate = selectedDate;

            presenter.searchAppointment(selectedDate);

        });

        binding.llBack.setOnClickListener(view -> finish());


    }



    private String getAppointId(){
        String id = "";
        for (AppointmentModel appointmentModel:appointmentModelList){
            if (appointmentModel.getFired_at().equals(selectedDate)){
                id = String.valueOf(appointmentModel.getId());
                return id;
            }
        }
        return id;
    }

    @Override
    public void onBackPressed() {
        presenter.backPress();
    }


    @Override
    public void onSuccess(List<AppointmentModel> data) {
        appointmentModelList.clear();
        appointmentModelList.addAll(data);

        if (data.size()>0){
            List<EventDay> eventDayList = new ArrayList<>();
            for (AppointmentModel model:data){
                String pharmacyDate = model.getFired_at();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
                try {
                    Date parse = simpleDateFormat.parse(pharmacyDate);
                    Calendar calendar = Calendar.getInstance();
                    calendar.clear();
                    calendar.setTime(parse);
                    EventDay eventDay = new EventDay(calendar,R.drawable.event_bg);
                    eventDayList.add(eventDay);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            binding.calendarView.setEvents(eventDayList);
        }
    }

    @Override
    public void onPharmacySuccess(List<PharmacyModel> data) {
        if (data.size()>0){
            pharmacyModelList.addAll(data);
            binding.tvNoData.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }else {
            binding.tvNoData.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressShow() {
        pharmacyModelList.clear();
        adapter.notifyDataSetChanged();
        binding.progBar.setVisibility(View.VISIBLE);
        binding.tvNoData.setVisibility(View.GONE);
    }

    @Override
    public void onProgressHide() {
        binding.progBar.setVisibility(View.GONE);

    }

    @Override
    public void onMainProgressShow() {
        binding.flLoad.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMainProgressHide() {
        binding.flLoad.setVisibility(View.GONE);

    }

    @Override
    public void onFinished() {
        finish();
    }


    public void deleteAppointment(int adapterPosition, PharmacyModel model) {
        ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        String id = getAppointId();
        Api.getService(Tags.base_url)
                .deleteAppointment(userModel.getData().getToken(),id,userModel.getData().getId(),model.getId(),model.getLatitude(),model.getLatitude())
                .enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                pharmacyModelList.remove(adapterPosition);
                                adapter.notifyItemRemoved(adapterPosition);

                                if (pharmacyModelList.size()>0){
                                    binding.tvNoData.setVisibility(View.GONE);
                                }else {
                                    binding.tvNoData.setVisibility(View.VISIBLE);
                                    presenter.getAppointment();
                                    presenter.searchAppointment(selectedDate);


                                }
                            }


                        } else {
                            dialog.dismiss();
                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {

                            } else {

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {
                        try {
                            dialog.dismiss();


                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");


                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }

    public void editAppointment(int adapterPosition, PharmacyModel model) {
        selectedPharmacy = model;
        Intent intent = new Intent(this, PaymentDateActivity.class);
        intent.putExtra("data", model);
        intent.putExtra("date", selectedDate);
        startActivityForResult(intent,100);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode==RESULT_OK&&data!=null){
            String newDate = data.getStringExtra("date");
            updateAppointment(newDate);
        }
    }

    private void updateAppointment(String newDate) {
        ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        String id = getAppointId();
        Api.getService(Tags.base_url)
                .updateAppointment(userModel.getData().getToken(),id,newDate,userModel.getData().getId(),selectedPharmacy.getId(),selectedPharmacy.getLatitude(),selectedPharmacy.getLatitude())
                .enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200) {
                                presenter.getAppointment();
                                presenter.searchAppointment(selectedDate);
                            }


                        } else {
                            dialog.dismiss();
                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {

                            } else {

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {
                        try {
                            dialog.dismiss();


                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");


                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }
}