package com.ufrn.angele.apotheca.api;

import android.util.Log;

import com.ufrn.angele.apotheca.dominio.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UsuarioServiceUFRN {
    private String caminhoUsuarios = "usuario/v1/usuarios/info";
    private static Usuario mUser = new Usuario();
    public Usuario getUsuario(String urlBase, String token, String apiKey) throws IOException {
        String url = urlBase + caminhoUsuarios;

        OkHttpClient client = new OkHttpClient();
        Log.d("token_req",token);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "bearer " + token)
                .addHeader("x-api-key", apiKey)
                .build();

        Response response = client.newCall(request).execute();

        JSONArray mArray;
        try {
            String responseData = response.body().string();
            Log.d("responseData", responseData);
//            mArray = new JSONArray(responseData);
            Usuario user = new Usuario();
//            for (int i = 0; i < mArray.length(); i++) {
//                 JSONObject mJsonObject = mArray.getJSONObject(i);
//                 user.setLogin(mJsonObject.getString("login"));
//                 user.setNome(mJsonObject.getString("nome-pessoa"));
//                 user.setCpf_cnpj( mJsonObject.getLong("cpf-cnpj"));
//                 user.setUrl_foto(mJsonObject.getString("url-foto"));
//                 user.setEmail(mJsonObject.getString("email"));
//                 user.setId_usuario(mJsonObject.getInt("id-usuario"));
//                 Log.d("user2", user.toString());
//                 mUser = user;
//            }

            JSONObject jsonObject = new JSONObject(responseData);
            user.setLogin(jsonObject.getString("login"));
            user.setNome(jsonObject.getString("nome-pessoa"));
            user.setCpf_cnpj( jsonObject.getLong("cpf-cnpj"));
            user.setUrl_foto(jsonObject.getString("url-foto"));
            user.setEmail(jsonObject.getString("email"));
            user.setId_usuario(jsonObject.getInt("id-usuario"));
            Log.d("user2", user.toString());
            mUser = user;

          } catch (JSONException e) {
                 e.printStackTrace();
          }

        Log.d("userService", mUser.toString());
        return mUser;
    }
}
