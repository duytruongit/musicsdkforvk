package com.lopei.musicsdk.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.lopei.musicsdk.Constants;
import com.lopei.musicsdk.LoadListener;
import com.lopei.musicsdk.R;
import com.lopei.musicsdk.VKMusicSDK;
import com.lopei.musicsdk.model.AuthInfo;
import com.lopei.musicsdk.model.AuthResult;
import com.lopei.musicsdk.util.AuthInfoParser;

/**
 * Created by alan on 15.12.16.
 */

public class AuthActivity extends AppCompatActivity implements LoadListener.OnHtmlFinishedListener {
    WebView authWebView;
    private LoadListener loadListener;
    private boolean logout = false;
    private String appId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        authWebView = (WebView) findViewById(R.id.authWebView);

        logout = getIntent().getBooleanExtra(Constants.ARGUMENT_LOGOUT, false);
        appId = getIntent().getStringExtra(Constants.EXTRA_APP_ID);


        if (AuthInfo.getAccessToken() == null || AuthInfo.getAccessToken().length() == 0) {
            if (logout) {
                clearPasswords();
            }
            loadListener = new LoadListener(this);
            authWebView.getSettings().setJavaScriptEnabled(true);
            authWebView.addJavascriptInterface(loadListener, "HTMLOUT");
            authWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            authWebView.setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    loadListener.setUrl(url);

                    if (url.startsWith("https://oauth.vk.com/blank.html")) {
                        view.loadUrl("javascript:window.HTMLOUT.processHTML(document.getElementsByTagName('html')[0].innerHTML);");
                    }
                }
            });

            String id = appId == null ? VKMusicSDK.DEFAULT_APP_ID : appId;

            authWebView.loadUrl(String.format(Constants.AUTH_URL, id));
        } else {
            loginUser();
        }
    }

    private void clearPasswords() {
        authWebView.clearCache(true);
        authWebView.clearHistory();
        authWebView.clearFormData();
        authWebView.clearMatches();
        authWebView.clearSslPreferences();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(this);
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }

    private void loginUser() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onHtmlFinished(String html) {
        AuthResult result = AuthInfoParser.parseAuthUrl(loadListener.getUrl(), Html.fromHtml(html).toString());
        if (!result.isSuccess()) {
            if (!result.getAuthError().getName().equals("continue")) {
                Toast.makeText(this, "Error loading data.\n" + result.getAuthError().getDescription(), Toast.LENGTH_SHORT).show();
            }
        } else {
            loginUser();
        }
    }
}
