package com.appnet.android.social.auth;

public class SocialAccount {
    private String provider;
    private String id;
    private String email;
    private String displayName;
    private String familyName;
    private String givenName;
    private String idToken;
    private String photo;
    private String serverAuthCode;

    private SocialAccount() {

    }

    public String getProvider() {
        return provider;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getIdToken() {
        return idToken;
    }

    public String getPhoto() {
        return photo;
    }

    public String getServerAuthCode() {
        return serverAuthCode;
    }

    static class Builder {
        private String provider;
        private String id;
        private String email;
        private String displayName;
        private String familyName;
        private String givenName;
        private String idToken;
        private String photo;
        private String serverAuthCode;

        Builder provider(String provider) {
            this.provider = provider;
            return this;
        }

        Builder id(String id) {
            this.id = id;
            return this;
        }

        Builder email(String email) {
            this.email = email;
            return this;
        }

        Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        Builder givenName(String givenName) {
            this.givenName = givenName;
            return this;
        }

        Builder familyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        Builder idToken(String idToken) {
            this.idToken = idToken;
            return this;
        }

        Builder photo(String photo) {
            this.photo = photo;
            return this;
        }

        Builder serverAuthCode(String serverAuthCode) {
            this.serverAuthCode = serverAuthCode;
            return this;
        }

        SocialAccount build() {
            SocialAccount account = new SocialAccount();
            account.provider = provider;
            account.id = id;
            account.email = email;
            account.displayName = displayName;
            account.familyName = familyName;
            account.givenName = givenName;
            account.idToken = idToken;
            account.photo = photo;
            account.serverAuthCode = serverAuthCode;
            return account;
        }
    }
}
