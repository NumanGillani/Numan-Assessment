package com.ny.articles.WebView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import com.ny.articles.MainActivity;
import com.ny.articles.R;
import com.ny.articles.Utils;


public class WebviewActivity extends Activity implements AdvancedWebView.Listener {

    String TAG = "WebviewActivity";
    private AdvancedWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        Log.e("Page URL", Utils.Companion.getArticleURL());
        mWebView = (AdvancedWebView) findViewById(R.id.webview);

        mWebView.setListener(this, this);

        mWebView.setMixedContentAllowed(false);
        mWebView.setCookiesEnabled(true);
        mWebView.setThirdPartyCookiesEnabled(true);

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
//				Toast.makeText(WebviewActivity.this, title, Toast.LENGTH_SHORT).show();
                Log.e("2 webView Task", title);
            }

        });
        mWebView.addHttpHeader("X-Requested-With", "");
        mWebView.loadUrl(Utils.Companion.getArticleURL());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
        // ...
    }

    @Override
    public void onBackPressed() {
        if (!mWebView.onBackPressed()) {
            return;
        }
        // ...
        super.onBackPressed();
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(String url) {
        Log.e("7 webView Task", "Finished loading");
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
        Toast.makeText(WebviewActivity.this, "onPageError(errorCode = " + errorCode + ",  description = " + description + ",  failingUrl = " + failingUrl + ")", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {
        Toast.makeText(WebviewActivity.this, "onDownloadRequested(url = " + url + ",  suggestedFilename = " + suggestedFilename + ",  mimeType = " + mimeType + ",  contentLength = " + contentLength + ",  contentDisposition = " + contentDisposition + ",  userAgent = " + userAgent + ")", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onExternalPageRequest(String url) {
        Toast.makeText(WebviewActivity.this, "onExternalPageRequest(url = " + url + ")", Toast.LENGTH_SHORT).show();
    }


    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        mWebView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mWebView.onDestroy();
        super.onDestroy();
    }

    public void exit(View view) {
        Intent abc = new Intent(WebviewActivity.this, MainActivity.class);
        startActivity(abc);
        finish();
    }

}