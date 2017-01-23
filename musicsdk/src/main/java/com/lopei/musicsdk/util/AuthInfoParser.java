package com.lopei.musicsdk.util;

import android.net.Uri;

import com.google.gson.Gson;
import com.lopei.musicsdk.model.AuthInfo;
import com.lopei.musicsdk.model.Error;
import com.lopei.musicsdk.model.AuthResult;

/**
 * Created by alan on 15.12.16.
 */

public class AuthInfoParser {
    public static AuthResult parseAuthUrl(String url, String html) {
        AuthResult result;
        if (url.contains("access_token")) {
            Uri uri = Uri.parse(url.replaceFirst("#", "?"));
            String accessToken = uri.getQueryParameter("access_token");
            String userId = uri.getQueryParameter("user_id");
            result = new AuthResult();
            AuthInfo.update(accessToken, userId);
        } else {
            Error error;
            try {
                Gson gson = new Gson();
                error = gson.fromJson(html, Error.class);
                if (html.length() == 0) {
                    error = new Error("continue", "Continue (Redirect)");
                }
            } catch (Exception e) {
                error = new Error("unknown_error", "Unknown Error");
            }
            result = new AuthResult(error);
        }
        return result;
    }
}
