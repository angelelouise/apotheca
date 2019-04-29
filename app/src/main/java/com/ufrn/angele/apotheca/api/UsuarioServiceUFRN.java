package com.ufrn.angele.apotheca.api;

import com.ufrn.angele.apotheca.dominio.UsuarioUFRN;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface UsuarioServiceUFRN {
//    private String caminhoUsuarios = "usuario/v1/usuarios?cpf-cnpj=";
//
//    public String getUsuario(String urlBase, String token, String apiKey, String cpf) throws IOException {
//        String url = urlBase + caminhoUsuarios + cpf;
//
//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url(url)
//                .get()
//                .addHeader("content-type", "application/json")
//                .addHeader("authorization", "bearer " + token)
//                .addHeader("x-api-key", apiKey)
//                .build();
//
//        Response response = client.newCall(request).execute();
//        String result = response.body().string();
//
//        System.out.println(result);
//        return result;
//    }
    @GET("usuario/v1/usuarios")
    @Headers({
            "Accept: application/json"

    })

    Call<UsuarioUFRN> getUser(@Query(value="cpf_cnpj", encoded = true) String cpf);
}
