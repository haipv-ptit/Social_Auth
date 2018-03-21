package com.appnet.android.social.auth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

class GoogleAuth implements GoogleApiClient.OnConnectionFailedListener {
    private final FragmentActivity mActivity;
    private GoogleApiClient mGoogleApiClient;
    private OnConnectionFailedListener mOnConnectionFailedListener;

    GoogleAuth(FragmentActivity activity, OnConnectionFailedListener listener, String clientId) {
        mActivity = activity;
        mOnConnectionFailedListener = listener;
        init(clientId);
    }

    private void init(String clientId) {
        GoogleSignInOptions.Builder builder = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN);
        if(!TextUtils.isEmpty(clientId)) {
            builder.requestIdToken(clientId);
        }
        builder.requestEmail();
        GoogleSignInOptions gso = builder.build();
        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .enableAutoManage(mActivity, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if(mOnConnectionFailedListener != null) {
            mOnConnectionFailedListener.onConnectionFailed(connectionResult.getErrorCode(), connectionResult.getErrorMessage());
        }
    }

    void signIn(int requestCode) {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        mActivity.startActivityForResult(signInIntent, requestCode);
    }

    void signOut(final OnLogoutListener listener) {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
    }

    SocialAccount getSignInAccount(Intent data) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        GoogleSignInAccount acc = result.getSignInAccount();
        if(result.isSuccess() && acc != null) {
            SocialAccount.Builder builder = new SocialAccount.Builder();
            builder.provider("google");
            builder.email(acc.getEmail());
            builder.id(acc.getId());
            builder.displayName(acc.getDisplayName());
            builder.familyName(acc.getFamilyName());
            builder.givenName(acc.getGivenName());
            builder.idToken(acc.getIdToken());
            if(acc.getPhotoUrl() != null) {
                builder.photo(acc.getPhotoUrl().toString());
            }
            builder.serverAuthCode(acc.getServerAuthCode());
            return builder.build();
        } else {
            return null;
        }
    }
}
