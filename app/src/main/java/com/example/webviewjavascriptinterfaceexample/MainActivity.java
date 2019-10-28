package com.example.webviewjavascriptinterfaceexample;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
}
