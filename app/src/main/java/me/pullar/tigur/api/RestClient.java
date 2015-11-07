package me.pullar.tigur.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by jamespullar on 11/4/15.
 */
public class RestClient {

    private static final String BASE_URL = "https://api.imgur.com/3/";
    private static OkHttpClient httpClient = new OkHttpClient();

    public static ImgurApi getClient() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient).build();

        return retrofit.create(ImgurApi.class);
    }

}
