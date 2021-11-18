package com.zawraapharma.mvp.activity_splash_mvp;

import android.content.Context;
import android.os.Handler;

import com.zawraapharma.models.UserModel;
import com.zawraapharma.models.UserSettingsModel;
import com.zawraapharma.preferences.Preferences;

public class SplashPresenter {
    private Context context;
    private SplashView view;
    private Preferences preferences;
    private UserModel userModel;
    private UserSettingsModel userSettingsModel;

    public SplashPresenter(Context context, SplashView view) {
        this.context = context;
        this.view = view;
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(context);
        userSettingsModel = preferences.getUserSettings(context);
        delaySplash();
    }

    private void delaySplash(){
        new Handler().postDelayed(()->{

            if (userModel!=null){
                view.onNavigateToHomeActivity();
            }else {
                view.onNavigateToLoginActivity();

            }



        },2000);
    }
}
