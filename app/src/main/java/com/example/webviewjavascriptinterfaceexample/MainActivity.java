package com.example.webviewjavascriptinterfaceexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    /* default */ WebView webView;
    /* default */ String permissionsTreeJson;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView.setWebContentsDebuggingEnabled(true);

        webView = findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);

        setupWebViewClient();
        setupWebViewJavascriptInterface();

        webView.loadUrl("file:///android_asset/demo.html");

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String script = "window.receivePermissions('" + permissionsTreeJson + "')";
                webView.evaluateJavascript(script, null);
            }
        });
    }

    private void setupWebViewJavascriptInterface() {
        final WebViewJavascriptInterface webViewJavascriptInterface = new WebViewJavascriptInterface(this, new WebViewJavascriptInterface.JavascriptInterfaceCallback() {
            @Override
            public void onPermissionsTreeCalled(final String permissionsTree) {
                Toast.makeText(MainActivity.this, "Árbol de permisos recibido", Toast.LENGTH_SHORT).show();
                permissionsTreeJson = permissionsTree;
            }
        });
        webView.addJavascriptInterface(webViewJavascriptInterface, "interOp");
    }

    private void setupWebViewClient() {
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(final WebView view, final String url) {
                super.onPageFinished(view, url);
                matchiOSJavascriptInterfaceRoute();
            }
        });
    }

    void matchiOSJavascriptInterfaceRoute() {
        final byte[] fileBytes = getFileBytes("script.js");
        injectScriptFile(fileBytes);
    }

    byte[] getFileBytes(final String fileName) {
        final InputStream input;
        final byte[] buffer;
        try {
            input = getAssets().open(fileName);
            buffer = new byte[input.available()];
            input.read(buffer);
            input.close();
        } catch (final IOException e) {
            Log.e("TAG", e.getMessage(), e);
            return new byte[0];
        }
        return buffer;
    }

    void injectScriptFile(final byte[] fileBytes) {
        // String-ify the script byte-array using BASE64 encoding !!!
        final String encoded = Base64.encodeToString(fileBytes, Base64.NO_WRAP);
        webView.loadUrl("javascript:(function() {" +
                "var parent = document.getElementsByTagName('head').item(0);" +
                "var script = document.createElement('script');" +
                "script.type = 'text/javascript';" +
                // Tell the browser to BASE64-decode the string into your script !!!
                "script.innerHTML = window.atob('" + encoded + "');" +
                "parent.appendChild(script)" +
                "})()");
    }
}
