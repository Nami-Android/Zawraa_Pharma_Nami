package com.zawraapharma.mvp.activity_login_presenter;

import com.zawraapharma.models.UserModel;

public interface ActivityLoginView {
    void onUserFound(UserModel userModel);
    void onUserNoFound();
    void onUserBlocked();
    void onFailed(String msg);


}
