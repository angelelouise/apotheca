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
import com.ufrn.angele.apotheca.api.RetrofitBuilder;
import com.ufrn.angele.apotheca.api.UsuarioServiceUFRN;
import com.ufrn.angele.apotheca.dominio.Usuario;
import com.ufrn.angele.apotheca.dominio.UsuarioUFRN;
import com.ufrn.angele.apotheca.outros.Constants;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ca.mimic.oauth2library.OAuth2Client;
import ca.mimic.oauth2library.OAuthResponse;

public class AutorizationActivity extends AppCompatActivity {
    private static final String CLIENT_ID_VALUE = "app-imd0033-id";
    private static final String SECRET_KEY = "segredo";
    private static final String STATE = "PNAGndLwyjQh3SifoO840HH7FrhWwHOhR9FssfxK";
    private static final String STATE_PARAM = "state";

    private static final String REDIRECT_URI = "http://api.info.ufrn.br";
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

    private final String urlBase = "https://api.info.ufrn.br/";
    private final String apiKey = "PNAGndLwyjQh3SifoO840HH7FrhWwHOhR9FssfxK";

    private WebView webView;
    private ProgressDialog pd;
    private Usuario user ;
    private String cpf;
    private String accessToken;
    RetrofitBuilder retrofit = new RetrofitBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autorization);

        webView = findViewById(R.id.main_activity_web_view);
        webView.requestFocus(View.FOCUS_DOWN);

        //pegar no itent o cpf
        cpf="10461573458";

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
//    private static String getAuthorizationUrl() {
//        return AUTHORIZATION_URL
//                + QUESTION_MARK + RESPONSE_TYPE_PARAM + EQUALS + RESPONSE_TYPE_VALUE
//                + AMPERSAND + CLIENT_ID_PARAM + EQUALS + CLIENT_ID_VALUE
//                + AMPERSAND + STATE_PARAM + EQUALS + STATE
//                + AMPERSAND + REDIRECT_URI_PARAM + EQUALS + REDIRECT_URI;
//    }
//    private void requestUser(OAuthResponse response){
//
//        final UsuarioServiceUFRN service = retrofit.retroBuilder(response,apiKey,urlBase).create(UsuarioServiceUFRN.class);
//
//        final Call<UsuarioUFRN> getUsuario = service.getUser(cpf_builder(cpf));
//
//        getUsuario.enqueue(new Callback<UsuarioUFRN>() {
//            @Override
//            public void onResponse(Call<UsuarioUFRN> call, Response<UsuarioUFRN> response_user) {
//
//                if (response_user.body() !=null){
//                    Log.d("response", response_user.body().toString());
//
//                    user.setLogin(response_user.body().getLogin());
//                    user.setNome(response_user.body().getNome_pessoa());
//                    user.setCpf_cnpj((int)response_user.body().getCpf_cnpj());
//                    user.setUrl_foto(response_user.body().getUrl_foto());
//                    user.setEmail(response_user.body().getEmail());
//                    user.setId_usuario(response_user.body().getId_usuario());
//
//                    Log.d("user", user.toString());
//
//                    Intent startProfileActivity = new Intent(AutorizationActivity.this, MainActivity.class);
//                    startActivity(startProfileActivity);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<UsuarioUFRN> call, Throwable t) {
//                Toast.makeText(AutorizationActivity.this,
//                        "Usuario não existe",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
    private class ServiceTask extends AsyncTask<String, Void, UsuarioUFRN> {

    protected void onPreExecute() {
        //pd = ProgressDialog.show(AutorizationActivity.this, "", "loading", true);
    }

    protected UsuarioUFRN doInBackground(String... params) {
        try {
            UsuarioServiceUFRN get = new UsuarioServiceUFRN();
            try {
                UsuarioUFRN aux = get.getUsuario(urlBase, params[0], apiKey, cpf);
                Log.d("aux", aux.toString());
                return aux;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(UsuarioUFRN result) {
        super.onPostExecute(result);
        pd.dismiss();
        Log.d("result", result.toString());
        if (result != null) {


                    user.setLogin(result.getLogin());
                    user.setNome(result.getNome_pessoa());
                    user.setCpf_cnpj((int)result.getCpf_cnpj());
                    user.setUrl_foto(result.getUrl_foto());
                    user.setEmail(result.getEmail());
                    user.setId_usuario(result.getId_usuario());

                    Log.d("user", user.toString());

        }
        Intent startProfileActivity = new Intent(AutorizationActivity.this, MainActivity.class);
        startActivity(startProfileActivity);
    }
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

                final OAuthResponse response = client.requestAccessToken();
                if (response.isSuccessful()) {
                    Log.d("acesso",response.getBody().toString());
                    savePreferences(response);

                    new ServiceTask().execute(accessToken);
                    //executa serviço para trazer informações do usuário
                    //requestUser(response);
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


            }
        }

    }

    private void savePreferences(OAuthResponse response) {
        accessToken = response.getAccessToken();
        String refreshToken = response.getRefreshToken();
        Long expiresIn = response.getExpiresIn();

        SharedPreferences preferences = this.getSharedPreferences("user_info", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.KEY_ACCESS_TOKEN, accessToken);
        editor.putString(Constants.KEY_REFRESH_TOKEN, refreshToken);
        editor.putLong(Constants.KEY_EXPIRES_IN, expiresIn);
        editor.commit();
        Log.d("token", accessToken);
        Log.d("refresh_token", refreshToken);
    }
    private String cpf_builder(String cpf){
        return "?cpf-cnpj="+cpf;
    }
}
