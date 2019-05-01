package com.ufrn.angele.apotheca.api;

import android.util.Log;

import com.ufrn.angele.apotheca.dominio.Discente;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DiscenteServiceUFRN {
    private String caminhoDiscentes = "discente/v1/discentes?sigla-nivel=G&cpf-cnpj=";
    private static ArrayList<Discente> mDiscentes = new ArrayList<>();

    public ArrayList<Discente> getDiscentes(String urlBase, String token, String apiKey, String cpf) throws IOException {
        String url = urlBase + caminhoDiscentes + cpf;

        OkHttpClient client = new OkHttpClient();
        //Log.d("token_req",token);
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
            mArray = new JSONArray(responseData);
            Log.d("mArray", mArray.toString());

            //Discente discente = new Discente();
            for (int i = 0; i < mArray.length(); i++) {
                Discente discente = new Discente();
                JSONObject mJsonObject = mArray.getJSONObject(i);
                discente.setCpf(mJsonObject.getString("cpf-cnpj"));
                discente.setId_curso(mJsonObject.getInt("id-curso"));
                discente.setId_tipo_discente(mJsonObject.getInt("id-tipo-discente"));
                discente.setNome_curso(mJsonObject.getString("nome-curso"));
                discente.setMatricula(mJsonObject.getInt("matricula"));
                discente.setId_discente(mJsonObject.getInt("id-discente"));
                discente.setTipo_vinculo(mJsonObject.getString("sigla-nivel"));

                Log.d("discente", discente.toString());
                mDiscentes.add(discente);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Log.d("userService", mUser.toString());
        return mDiscentes;
    }
}
