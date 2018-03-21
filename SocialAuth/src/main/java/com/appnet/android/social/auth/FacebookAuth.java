package com.appnet.android.social.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

class FacebookAuth implements FacebookCallback<LoginResult> {
    private static final List<String> PERMISSIONS = Arrays.asList("email", "public_profile");

    private OnLoginListener mOnLoginListener;
    private final Activity mActivity;

    private CallbackManager mCallbackManager;

    FacebookAuth(Activity activity) {
        mActivity = activity;
        mCallbackManager = CallbackManager.Factory.create();

    }

    void login(OnLoginListener onLoginListener) {
        LoginManager.getInstance().logOut();
        mOnLoginListener = onLoginListener;
        LoginManager.getInstance().registerCallback(mCallbackManager, this);
        LoginManager.getInstance().logInWithReadPermissions(mActivity, PERMISSIONS);
    }

    void logout() {
        LoginManager.getInstance().logOut();
    }

    void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        final AccessToken accessToken = loginResult.getAccessToken();
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        SocialAccount account = getSignInAccount(object, accessToken.getToken());
                        if(mOnLoginListener != null) {
                            mOnLoginListener.onLoginSuccess(account);
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,first_name,last_name,email,gender,birthday"); // id,first_name,last_name,email,gender,birthday,cover,picture.type(large)
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onCancel() {
    }

    @Override
    public void onError(FacebookException error) {
        if(mOnLoginListener != null) {
            mOnLoginListener.onLoginError(0, error.getMessage());
        }
    }

    private SocialAccount getSignInAccount(JSONObject object, String accessToken) {
        SocialAccount.Builder builder = new SocialAccount.Builder();
        builder.provider("facebook");
        builder.id(object.optString("id"));
        builder.idToken(accessToken);
        builder.email(object.optString("email"));
        builder.givenName(object.optString("last_name"));
        builder.familyName(object.optString("first_name"));
        builder.displayName(object.optString("name"));
        builder.photo("http://graph.facebook.com/" + object.optString("id") + "/picture?type=large");
        return builder.build();
    }
}
