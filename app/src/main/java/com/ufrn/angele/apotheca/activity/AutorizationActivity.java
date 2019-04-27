package com.ufrn.angele.apotheca.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ufrn.angele.apotheca.R;
import com.ufrn.angele.apotheca.outros.Constants;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ca.mimic.oauth2library.OAuth2Client;
import ca.mimic.oauth2library.OAuthResponse;

public class AutorizationActivity extends AppCompatActivity {
    private static final String CLIENT_ID_VALUE = "app-imd0033-id";
    private static final String SECRET_KEY = "segredo";

    private static final String REDIRECT_URI = "https://api.info.ufrn.br";
    private static final String AUTHORIZATION_URL = "https://autenticacao.info.ufrn.br/authz-server/oauth/authorize";
    private static final String ACCESS_TOKEN_URL = "https://autenticacao.info.ufrn.br/authz-server/oauth/token";
    private static final String RESPONSE_TYPE_PARAM = "response_type";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String RESPONSE_TYPE_VALUE = "code";
    private static final String CLIENT_ID_PARAM = "client_id";
    private static final String REDIRECT_URI_PARAM = "redirect_uri";

    private static final String QUESTION_MARK = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUALS = "=";

    private WebView webView;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autorization);

        webView = findViewById(R.id.main_activity_web_view);
        webView.requestFocus(View.FOCUS_DOWN);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String authorizationUrl) {
                if (authorizationUrl.startsWith(REDIRECT_URI)) {
                    Uri uri = Uri.parse(authorizationUrl);

                    String authorizationToken = uri.getQueryParameter(RESPONSE_TYPE_VALUE);
                    if (authorizationToken == null) {
                        return true;
                    }

                    new PostRequestAsyncTask().execute(authorizationToken);

                } else {
                    webView.loadUrl(authorizationUrl);
                }
                return true;
            }
        });

        String authUrl = getAuthorizationUrl();
        webView.loadUrl(authUrl);
    }
    private static String getAuthorizationUrl(){
        return AUTHORIZATION_URL
                + QUESTION_MARK + CLIENT_ID_PARAM + EQUALS + CLIENT_ID_VALUE
                + AMPERSAND + RESPONSE_TYPE_PARAM + EQUALS + RESPONSE_TYPE_VALUE
                + AMPERSAND  + REDIRECT_URI_PARAM + EQUALS + REDIRECT_URI;
    }
    private class PostRequestAsyncTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(AutorizationActivity.this, "", "loading", true);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            Log.i("doInBackground", "doInBackground");
            try {
                OAuth2Client client;
                Map<String, String> map = new HashMap<>();
                map.put(REDIRECT_URI_PARAM, REDIRECT_URI);
                map.put(RESPONSE_TYPE_VALUE, params[0]);

                client = new OAuth2Client.Builder(CLIENT_ID_VALUE, SECRET_KEY, ACCESS_TOKEN_URL)
                        .grantType(GRANT_TYPE)
                        .parameters(map)
                        .build();

                OAuthResponse response = client.requestAccessToken();
                if (response.isSuccessful()) {
                    savePreferences(response);
                    //executa serviço para trazer informações do usuário
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean status) {
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }
            if (status) {
                Intent startProfileActivity = new Intent(AutorizationActivity.this, MainActivity.class);
                startActivity(startProfileActivity);
            }
        }

    }

    private void savePreferences(OAuthResponse response) {
        String accessToken = response.getAccessToken();
        String refreshToken = response.getRefreshToken();
        Long expiresIn = response.getExpiresIn();

        SharedPreferences preferences = this.getSharedPreferences("user_info", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_ACCESS_TOKEN, accessToken);
        editor.putString(Constants.KEY_REFRESH_TOKEN, refreshToken);
        editor.putLong(Constants.KEY_EXPIRES_IN, expiresIn);
        editor.commit();
    }
}
