package com.zawraapharma.ui.activity_login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.zawraapharma.R;
import com.zawraapharma.databinding.ActivityLoginBinding;
import com.zawraapharma.language.Language;
import com.zawraapharma.models.LoginModel;
import com.zawraapharma.models.UserModel;
import com.zawraapharma.mvp.activity_login_presenter.ActivityLoginPresenter;
import com.zawraapharma.mvp.activity_login_presenter.ActivityLoginView;
import com.zawraapharma.preferences.Preferences;
import com.zawraapharma.share.Common;
import com.zawraapharma.ui.activity_home.HomeActivity;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity implements ActivityLoginView {
    private ActivityLoginBinding binding;
    private LoginModel model;
    private ActivityLoginPresenter presenter;
    private double lat=0.0,lng=0.0;
    private Preferences preferences;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase,Paper.book().read("lang","ar")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        lat = intent.getDoubleExtra("lat",0.0);
        lng = intent.getDoubleExtra("lng",0.0);
    }

    private void initView() {
        preferences = Preferences.getInstance();
        model = new LoginModel();
        binding.tv1.setText(Html.fromHtml(getString(R.string.login)));
        binding.setModel(model);
        presenter = new ActivityLoginPresenter(this,this);
        binding.btnLogin.setOnClickListener(view -> {
            presenter.checkData(model);
        });



    }

  /*  @Override
    public void onLoginValid() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("access_code",model.getAccess_code());
        startActivity(intent);
        finish();

    }*/



    @Override
    public void onUserFound(UserModel userModel) {
            preferences.create_update_userdata(LoginActivity.this, userModel);
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();

    }

    @Override
    public void onUserNoFound() {
        Toast.makeText(this,  getString(R.string.user_not_found), Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onUserBlocked() {
        Toast.makeText(this,  getString(R.string.user_blocked), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
    }



}