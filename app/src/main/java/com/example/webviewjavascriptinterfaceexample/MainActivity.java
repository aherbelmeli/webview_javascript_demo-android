package com.example.webviewjavascriptinterfaceexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.webkit.ValueCallback;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        final WebView webView = findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);

        final WebViewJavascriptInterface webViewJavascriptInterface = new WebViewJavascriptInterface(this);
        webView.addJavascriptInterface(webViewJavascriptInterface, "interOp");

        webView.loadUrl("file:///android_asset/demo.html");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript("showText(\'" + "un text" + "\')", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(final String value) {

                }
            });
        }
    }
}
