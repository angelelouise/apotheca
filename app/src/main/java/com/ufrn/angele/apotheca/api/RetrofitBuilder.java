package com.ufrn.angele.apotheca.api;

import java.io.IOException;

import ca.mimic.oauth2library.OAuthResponse;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    private static OAuthResponse response = null;
    private static String apiKey = "api";
    public Retrofit retroBuilder(final OAuthResponse response, final String apiKey, final String urlBase){
        this.response = response;
        this.apiKey =apiKey;
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain
                                                      chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Authorization", "bearer " + response.getAccessToken())
                        .header("x-api-key", apiKey)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
        OkHttpClient client = new OkHttpClient();

//        Request request = new Request.Builder()
//                .addHeader("content-type", "application/json")
//                .addHeader("authorization", "bearer " + token)
//                .addHeader("x-api-key", apiKey)
//                .build();
        OkHttpClient client2 = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client2)
                .build();
//
    return retrofit;
    }


    }

