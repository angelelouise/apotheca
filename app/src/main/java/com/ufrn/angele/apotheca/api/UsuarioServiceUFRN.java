package com.ufrn.angele.apotheca.api;

import android.util.Log;

import com.ufrn.angele.apotheca.dominio.UsuarioUFRN;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UsuarioServiceUFRN {
    private String caminhoUsuarios = "usuario/v1/usuarios?cpf-cnpj=";
    private static UsuarioUFRN mUser = new UsuarioUFRN();
    public UsuarioUFRN getUsuario(String urlBase, String token, String apiKey, String cpf) throws IOException {
        String url = urlBase + caminhoUsuarios + cpf;

        OkHttpClient client = new OkHttpClient();
        Log.d("token_req",token);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "bearer " + token)
                .addHeader("x-api-key", apiKey)
                .build();

        //Response response = client.newCall(request).execute();
        //String result = response.body();

       // Get a handler that can be used to post to the main thread

        client.newCall(request).enqueue(new Callback() {
                                            @Override
                                            public void onFailure(Call call, IOException e) {
                                                Log.d("falhou", "falha");
                                            }

                                            // Parse response using gson deserializer

                                            @Override
                                            public void onResponse(Call call, final Response response) throws IOException {
                                                Log.d("response", response.toString());

                                                JSONArray mArray;
                                                try {
                                                    final String responseData = response.body().string();
                                                    mArray = new JSONArray(responseData);
                                                    UsuarioUFRN user = new UsuarioUFRN();
                                                    for (int i = 0; i < mArray.length(); i++) {
                                                        JSONObject mJsonObject = mArray.getJSONObject(i);
                                                        user.setLogin(mJsonObject.getString("login"));
                                                        user.setNome_pessoa(mJsonObject.getString("nome-pessoa"));
                                                        user.setCpf_cnpj(mJsonObject.getInt("cpf-cnpj"));
                                                        user.setUrl_foto(mJsonObject.getString("url-foto"));
                                                        user.setEmail(mJsonObject.getString("email"));
                                                        user.setId_usuario(mJsonObject.getInt("id-usuario"));
                                                        Log.d("user2", user.toString());
                                                        mUser = user;
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            });

        Log.d("userUFRN", mUser.toString());
        return mUser;
    }
}
