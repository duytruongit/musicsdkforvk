package com.lopei.musicsdk;

/**
 * Created by alan on 15.12.16.
 */

public interface Constants {
    String AUTH_URL = "https://oauth.vk.com/authorize?" +
            "client_id=" + "%s" + "&" +
            "display=page&" +
            "redirect_uri=https://oauth.vk.com/blank.html&" +
            "response_type=token&" +
            "v=5.53";
    String BASE_URL = "https://api.vk.com/";
    String ARGUMENT_LOGOUT = "argLogout";

    String EXTRA_APP_ID = "extraAppId";
    String EXTRA_LOGOUT = "extraLogout";
}
