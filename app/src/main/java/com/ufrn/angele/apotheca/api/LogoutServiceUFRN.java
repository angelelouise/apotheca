package com.ufrn.angele.apotheca.api;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LogoutServiceUFRN {
    private String logout = "authz-server/j_spring_cas_security_logout";
    //"/sso-server/logout?service=http://api.info.ufrn.br"
    public void logout(String urlBase, String token, String apiKey) throws IOException {
        String url = urlBase + logout;

        OkHttpClient client = new OkHttpClient();
        //Log.d("token_req",token);
        //RequestBody requestBody = RequestBody.create(null, new byte[0]);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("authorization", "bearer " + token)
                .addHeader("x-api-key", apiKey)
                .build();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        System.out.println(response.body().string());
    }
}
