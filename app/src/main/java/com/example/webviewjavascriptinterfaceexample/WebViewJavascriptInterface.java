package com.example.webviewjavascriptinterfaceexample;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class WebViewJavascriptInterface {

    private final WeakReference<Context> wkContext;

    public WebViewJavascriptInterface(final Context context) {
        wkContext = new WeakReference<>(context);
    }

    @JavascriptInterface
    public void showToast(final String text) {
        Toast.makeText(wkContext.get(), text, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void showSSS(final String text) {
        Toast.makeText(wkContext.get(), text, Toast.LENGTH_SHORT).show();
    }
}
