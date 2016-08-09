package com.ymca.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ymca.R;


public class TwitterWebView extends Activity {
    static final String TWITTER_CALLBACK_URL = "oauth://cartrizeProduction";
    private WebView webView;
    static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
    public static String EXTRA_URL = "extra_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.twitter_web_view);

        setTitle("Login");

        final String url = this.getIntent().getStringExtra(EXTRA_URL);
        if (null == url) {
            Log.e("Twitter", "URL cannot be null");
            finish();
        }

        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(url);
    }


    class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (url.toString().startsWith(TWITTER_CALLBACK_URL)) {
                Uri uri = Uri.parse(url);
				
				/* Sending results back */
                String verifier = uri.getQueryParameter(URL_TWITTER_OAUTH_VERIFIER);
                Intent resultIntent = new Intent();
                resultIntent.putExtra(URL_TWITTER_OAUTH_VERIFIER, verifier);
                setResult(RESULT_OK, resultIntent);
				
				/* closing webview */
                finish();
                return true;
            }
            return false;
        }
    }
}