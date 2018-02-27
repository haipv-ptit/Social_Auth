package com.appnet.android.social.auth;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

public class AuthManager implements OnConnectionFailedListener {
    public static final int RC_LOGIN_GOOGLE = 1000;
    public static final int RC_LOGIN_FACEBOOK = 2000;
    private final FragmentActivity mActivity;
    private GoogleAuth mGoogleAuth;
    private FacebookAuth mFacebookAuth;
    private int mCurrentRequestCode = 0;
    private OnLoginListener mOnLoginListener;

    public AuthManager(FragmentActivity activity) {
        mActivity = activity;
    }

    public void signOut(int requestCode, final OnLogoutListener listener) {
        switch (requestCode) {
            case RC_LOGIN_GOOGLE:
                signOutGoogle(listener);
                break;
            case RC_LOGIN_FACEBOOK:
                signOutFacebook();
                break;
        }
    }

    public void signIn(int requestCode, OnLoginListener loginListener) {
        mOnLoginListener = loginListener;
        switch (requestCode) {
            case RC_LOGIN_GOOGLE:
                signInGoogle();
                break;
            case RC_LOGIN_FACEBOOK:
                signInFacebook();
                break;
        }
    }

    private void signInGoogle() {
        mCurrentRequestCode = RC_LOGIN_GOOGLE;
        if(mGoogleAuth == null) {
            mGoogleAuth = new GoogleAuth(mActivity, this);
        }
        mGoogleAuth.signIn(mCurrentRequestCode);
    }

    private void signInFacebook() {
        mCurrentRequestCode = RC_LOGIN_FACEBOOK;
        if(mFacebookAuth == null) {
            mFacebookAuth = new FacebookAuth(mActivity);
        }
        mFacebookAuth.login(mOnLoginListener);
    }

    private void signOutGoogle(final OnLogoutListener listener) {
        if(mGoogleAuth == null) {
            mGoogleAuth = new GoogleAuth(mActivity, this);
        }
        mGoogleAuth.signOut(listener);
    }

    private void signOutFacebook() {
        if(mFacebookAuth == null) {
            mFacebookAuth = new FacebookAuth(mActivity);
        }
        mFacebookAuth.logout();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (mCurrentRequestCode) {
            case RC_LOGIN_GOOGLE:
                handleLoginGoogle(requestCode, resultCode, data);
                break;
            case RC_LOGIN_FACEBOOK:
                handleLoginFacebook(requestCode, resultCode, data);
                break;
        }
        mCurrentRequestCode = 0;
    }

    private void handleLoginGoogle(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        if(mGoogleAuth != null) {
            callResult(requestCode, mGoogleAuth.getSignInAccount(data));
        }
    }

    private void handleLoginFacebook(int requestCode, int resultCode, Intent data) {
        if(mFacebookAuth != null) {
            mFacebookAuth.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void callResult(int requestCode, SocialAccount data) {
        if(mOnLoginListener == null) {
            return;
        }
        if(data != null) {
            mOnLoginListener.onLoginSuccess(data);
        } else {
            mOnLoginListener.onLoginError(requestCode, "");
        }
    }

    @Override
    public void onConnectionFailed(int errorCode, String message) {
        if(mOnLoginListener == null) {
            return;
        }
        mOnLoginListener.onLoginError(errorCode, message);
    }
}
