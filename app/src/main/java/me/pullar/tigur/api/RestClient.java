package me.pullar.tigur.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by jamespullar on 11/4/15.
 */
public class RestClient {

    private static final String BASE_URL = "https://api.imgur.com/3/";
    private static ImgurApi imgurApi;

    public static ImgurApi getClient() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        imgurApi = client.create(ImgurApi.class);

        return imgurApi;
    }

}
