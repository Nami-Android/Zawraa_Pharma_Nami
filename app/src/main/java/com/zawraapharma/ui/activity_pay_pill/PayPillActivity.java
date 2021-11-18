package com.zawraapharma.ui.activity_pay_pill;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.zawraapharma.R;
import com.zawraapharma.adapters.PharmacyAdapter;
import com.zawraapharma.databinding.ActivityHomeBinding;
import com.zawraapharma.databinding.ActivityPayPillBinding;
import com.zawraapharma.language.Language;
import com.zawraapharma.models.PharmacyModel;
import com.zawraapharma.mvp.activity_home_mvp.ActivityHomePresenter;
import com.zawraapharma.mvp.activity_home_mvp.HomeActivityView;
import com.zawraapharma.mvp.activity_pay_pill_mvp.ActivityPayPillPresenter;
import com.zawraapharma.mvp.activity_pay_pill_mvp.PayPillActivityView;
import com.zawraapharma.share.Common;
import com.zawraapharma.ui.activity_login.LoginActivity;
import com.zawraapharma.ui.activity_new_client.NewClientActivity;
import com.zawraapharma.ui.activity_pharmacy_details.PharmacyDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class PayPillActivity extends AppCompatActivity implements PayPillActivityView {
    private ActivityPayPillBinding binding;
    private ActivityPayPillPresenter presenter;
    private List<PharmacyModel> pharmacyModelList;
    private PharmacyAdapter adapter;
    private String lang;
    private String id;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase,Paper.book().read("lang","ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pay_pill);
        getDataFromIntent();
        initView();
    }


    private void getDataFromIntent() {
        try {
            Intent intent = getIntent();
            id = intent.getStringExtra("id");

        }catch (Exception e){

            id="not sales";
        }
    }
    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        pharmacyModelList = new ArrayList<>();
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PharmacyAdapter(pharmacyModelList,this);
        binding.recView.setAdapter(adapter);
        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty())
                {
                    binding.tvNoData.setVisibility(View.VISIBLE);
                    pharmacyModelList.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        });
        binding.edtSearch.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                String query = binding.edtSearch.getText().toString();
                Log.e("cccccccc", query);

                if (!query.isEmpty()) {
                    Common.CloseKeyBoard(this,binding.edtSearch);
                    presenter.search(query);
                }else {
                    presenter.search("all");
                }
            }
            return false;
        });

        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    presenter.search("all");
                }
            }
        });

        binding.imgSearch.setOnClickListener(view -> {
            String query = binding.edtSearch.getText().toString();
            if (!query.isEmpty()){
                Common.CloseKeyBoard(this,binding.edtSearch);
                presenter.search(query);
            }
        });

        if (id.equals("sales")){
            binding.fab.setVisibility(View.VISIBLE);
        }else {
            binding.fab.setVisibility(View.GONE);

        }
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayPillActivity.this, NewClientActivity.class);
                startActivity(intent);
            }
        });

        binding.llBack.setOnClickListener(view -> presenter.backPress());

        presenter = new ActivityPayPillPresenter(this, this);

    }




    @Override
    public void onBackPressed() {
        presenter.backPress();
    }


    @Override
    public void onSuccess(List<PharmacyModel> data) {
        if (data.size()>0){
            binding.tvNoData.setVisibility(View.GONE);
            pharmacyModelList.clear();
            pharmacyModelList.addAll(data);
            adapter.notifyDataSetChanged();
        }else {
            pharmacyModelList.clear();
            adapter.notifyDataSetChanged();
            binding.tvNoData.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressShow() {
        binding.tvNoData.setVisibility(View.GONE);
        pharmacyModelList.clear();
        adapter.notifyDataSetChanged();
        binding.progBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressHide() {
        binding.progBar.setVisibility(View.GONE);

    }

    @Override
    public void onFinished() {
        finish();
    }

    public void setItemData(PharmacyModel model) {
        Intent intent = new Intent(this, PharmacyDetailsActivity.class);
        intent.putExtra("data",model);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}