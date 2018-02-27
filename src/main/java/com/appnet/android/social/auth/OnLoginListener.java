package com.appnet.android.social.auth;

import android.support.annotation.NonNull;

public interface OnLoginListener {
    void onLoginSuccess(@NonNull SocialAccount account);
    void onLoginError(int errorCode, String errorMessage);
}
