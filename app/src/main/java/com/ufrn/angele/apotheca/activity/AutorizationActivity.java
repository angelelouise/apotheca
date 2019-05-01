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
import com.ufrn.angele.apotheca.api.DiscenteServiceUFRN;
import com.ufrn.angele.apotheca.api.TurmaServiceUFRN;
import com.ufrn.angele.apotheca.api.UsuarioServiceUFRN;
import com.ufrn.angele.apotheca.dominio.Discente;
import com.ufrn.angele.apotheca.dominio.InformacoesUFRN;
import com.ufrn.angele.apotheca.dominio.Turma;
import com.ufrn.angele.apotheca.dominio.Usuario;
import com.ufrn.angele.apotheca.outros.Constants;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
    private Usuario mUser = new Usuario();
    private ArrayList<Discente> mDiscentes= new ArrayList<>();
    private ArrayList<Turma> mTurmas = new ArrayList<>();
    private String cpf;
    private String accessToken;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autorization);

        webView = findViewById(R.id.main_activity_web_view);
        webView.requestFocus(View.FOCUS_DOWN);

        //pegar no itent o cpf
        Intent itent = getIntent();
        flag =  itent.getBooleanExtra("flag",false);
        cpf= itent.getStringExtra("cpf");

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

    private class ServiceTask extends AsyncTask<String, Void, InformacoesUFRN> {

        protected void onPreExecute() {
            //pd = ProgressDialog.show(AutorizationActivity.this, "", "loading", true);
        }

        protected InformacoesUFRN doInBackground(String... params) {
            try {
                UsuarioServiceUFRN usuarioServiceUFRN = new UsuarioServiceUFRN();
                DiscenteServiceUFRN discenteServiceUFRN = new DiscenteServiceUFRN();
                TurmaServiceUFRN turmaServiceUFRN = new TurmaServiceUFRN();

                try {

                    InformacoesUFRN info = new InformacoesUFRN();
                    Usuario aux = usuarioServiceUFRN.getUsuario(urlBase, params[0], apiKey, cpf);
                    ArrayList<Discente> dis = discenteServiceUFRN.getDiscentes(urlBase, params[0], apiKey, cpf);
                    ArrayList<Turma> turmas = null;

                    if (dis != null) {
                        turmas = turmaServiceUFRN.getTurmas(urlBase, params[0], apiKey, dis);
                    }
                    Log.d("aux", aux.toString());
                    info.setUsuario(aux);
                    info.setDiscentes(dis);
                    info.setTurmas(turmas);

                    return info;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(InformacoesUFRN result) {
            super.onPostExecute(result);
            pd.dismiss();
            Log.d("result", result.toString());
            if (result != null) {

                mUser= result.getUsuario();
                mDiscentes = result.getDiscentes();
                mTurmas = result.getTurmas();

                Log.d("user", mUser.toString());
                Log.d("discentes", mDiscentes.toString());
                Log.d("turmas", mTurmas.toString());
            }
            //envia o user
            Intent startProfileActivity = new Intent(AutorizationActivity.this, MainActivity.class);
            startProfileActivity.putExtra("usuario", (Serializable) mUser);
            startProfileActivity.putExtra("discentes",  mDiscentes);
            startProfileActivity.putExtra("turmas",  mTurmas);
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
                //executa serviço para trazer informações do usuário
                new ServiceTask().execute(accessToken);

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
