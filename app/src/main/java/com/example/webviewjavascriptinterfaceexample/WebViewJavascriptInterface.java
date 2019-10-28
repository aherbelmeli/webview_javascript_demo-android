package com.example.webviewjavascriptinterfaceexample;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.lang.ref.WeakReference;

public class WebViewJavascriptInterface {

    public interface JavascriptInterfaceCallback {

        void onPermissionsTreeCalled(final String permissionsTree);
    }

    private final WeakReference<Context> wkContext;
    private final JavascriptInterfaceCallback callback;

    public WebViewJavascriptInterface(final Context context, final JavascriptInterfaceCallback callback) {
        wkContext = new WeakReference<>(context);
        this.callback = callback;
    }

    @JavascriptInterface
    public void showToast(final String text) {
        Toast.makeText(wkContext.get(), text, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void showSSS(final String text) {
        Toast.makeText(wkContext.get(), text, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void postMessage(final String message) {
        final JsonObject jsonMessage = new Gson().fromJson(message, JsonObject.class);

        final String messageType = jsonMessage.get("type").getAsString();
        if (!TextUtils.isEmpty(messageType)) {
            switch (messageType) {
                case "permissions_tree": {
                    if (callback != null) {
                        try {
                            final String data = jsonMessage.get("data").toString();
                            callback.onPermissionsTreeCalled(data);
                        } catch (final Exception e) {
                            Log.e("TAG", e.getMessage(), e);
                        }
                    }
                }
                break;
            }
        }
    }
}
