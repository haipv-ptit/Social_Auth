# Social_Auth
Login by social account
## Activity
Initial: `AuthManager mAuthManager = new AuthManager(Activity);` in Activity<br/>
Google login: `mAuthManager.signIn(AuthManager.RC_LOGIN_GOOGLE, OnLoginListener);`<br/>
Facebook login: `mAuthManager.signIn(AuthManager.RC_LOGIN_FACEBOOK, OnLoginListener);`<br/>
onActivityResult: `mAuthManager.onActivityResult(requestCode, resultCode, data);`<br/>
OnLoginListener:<br/>
```java
@Override
public void onLoginSuccess(@NonNull SocialAccount account) {
        //TODO loginSocial(account);
}

@Override
public void onLoginError(int errorCode, String errorMessage) {
    //TODO loginFailed();
}
```
