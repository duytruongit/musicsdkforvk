package com.lopei.musicsdk;

import android.webkit.JavascriptInterface;

/**
 * Created by alan on 15.12.16.
 */

public class LoadListener {
    public OnHtmlFinishedListener onHtmlFinishedListener;
    private String url;
    private boolean listenerCalled = false;

    public LoadListener(OnHtmlFinishedListener onHtmlFinishedListener) {
        this.onHtmlFinishedListener = onHtmlFinishedListener;
    }

    @JavascriptInterface
    public void processHTML(String html) {
        if (!listenerCalled) {
            listenerCalled = true;
            if (onHtmlFinishedListener != null) {
                onHtmlFinishedListener.onHtmlFinished(html);
            }
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public interface OnHtmlFinishedListener {
        void onHtmlFinished(String html);
    }
}
