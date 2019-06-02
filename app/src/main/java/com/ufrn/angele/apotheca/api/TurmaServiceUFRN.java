package com.ufrn.angele.apotheca.api;

import android.util.Log;

import com.ufrn.angele.apotheca.dominio.Discente;
import com.ufrn.angele.apotheca.dominio.Turma;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TurmaServiceUFRN {

    private String caminhoDiscentes = "turma/v1/turmas?id-discente=";
    private static ArrayList<Turma> mTurmas = new ArrayList<>();

    public ArrayList<Turma> getTurmas(String urlBase, String token, String apiKey, ArrayList<Discente> discentes) throws IOException {

        for (Discente d:discentes) {
            String url = urlBase + caminhoDiscentes + d.getId_discente();

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
                mArray = new JSONArray(responseData);


                for (int i = 0; i < mArray.length(); i++) {
                    JSONObject mJsonObject = mArray.getJSONObject(i);
                    Turma turma = new Turma();
                    turma.setAno(mJsonObject.getInt("ano"));
                    turma.setId_turma(mJsonObject.getInt("id-turma"));
                    turma.setCodigo_componente(mJsonObject.getString("codigo-componente"));
                    turma.setId_componente(mJsonObject.getInt("id-componente"));
                    turma.setId_discente(mJsonObject.getInt("id-discente"));
                    turma.setNome_componente(mJsonObject.getString("nome-componente"));

                    mTurmas.add(turma);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        //Log.d("userService", mUser.toString());
        return mTurmas;
    }
}
