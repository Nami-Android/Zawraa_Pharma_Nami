package com.zawraapharma.ui.activity_splash;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.DataBindingUtil;

import com.zawraapharma.R;
import com.zawraapharma.databinding.ActivitySplashBinding;
import com.zawraapharma.language.Language;
import com.zawraapharma.models.UserSettingsModel;
import com.zawraapharma.mvp.activity_splash_mvp.SplashPresenter;
import com.zawraapharma.mvp.activity_splash_mvp.SplashView;
import com.zawraapharma.preferences.Preferences;
import com.zawraapharma.ui.activity_home.HomeActivity;
import com.zawraapharma.ui.activity_login.LoginActivity;

import io.paperdb.Paper;


public class SplashActivity extends AppCompatActivity implements SplashView {
    private ActivitySplashBinding binding;
    private SplashPresenter presenter;
    private Preferences preferences;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        initView();
    }

    private void initView() {
        presenter = new SplashPresenter(this, this);
        preferences = Preferences.getInstance();
    }


    @Override
    public void onNavigateToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onNavigateToHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }


    private void refreshActivity(String lang) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Paper.init(this);
            Paper.book().write("lang", lang);
            Language.updateResources(this, lang);
            UserSettingsModel model = preferences.getUserSettings(this);
            if (preferences.getUserSettings(this) == null) {
                model = new UserSettingsModel();
            }
            model.setLanguageSelected(true);
            preferences.create_update_user_settings(this, model);
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }, 1500);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            String lang = data.getStringExtra("lang");
            refreshActivity(lang);
        }
    }


}