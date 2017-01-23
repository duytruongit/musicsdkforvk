package com.lopei.musicsdk.model;

/**
 * Created by alan on 15.12.16.
 */

public class AuthResult {
    private Error error;

    public AuthResult(Error error) {
        this.error = error;
    }

    public AuthResult() {
        this.error = null;
    }

    public Error getAuthError() {
        return error;
    }

    public boolean isSuccess() {
        return error == null;
    }
}
