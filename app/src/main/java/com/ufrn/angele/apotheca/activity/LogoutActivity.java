package com.ufrn.angele.apotheca.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ufrn.angele.apotheca.R;
import com.ufrn.angele.apotheca.outros.Constants;

public class LogoutActivity extends AppCompatActivity {
    private WebView webView;
    private ProgressDialog pd;
    private String logout = "https://autenticacao.info.ufrn.br/authz-server/j_spring_cas_security_logout";
    //"autenticacao.ufrn.br/sso-server/logout?service=http://api.info.ufrn.br";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        webView = findViewById(R.id.logout_activity_web_view);
        webView.requestFocus(View.FOCUS_DOWN);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(true);
        webSettings.setDefaultTextEncodingName("utf-8");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }

                SharedPreferences preferences = getApplicationContext().getSharedPreferences("user_info", 0);

                if (preferences.edit().clear().commit()){
                    Log.d("Logout", ""+ preferences.getString(Constants.KEY_ACCESS_TOKEN,""));
                    Intent intent =  new Intent(LogoutActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String logout) {

                    webView.loadUrl(logout);
                return true;
            }
        });

        webView.loadUrl(logout);

    }

}
