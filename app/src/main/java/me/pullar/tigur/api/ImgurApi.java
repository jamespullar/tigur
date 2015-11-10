package me.pullar.tigur.api;

import me.pullar.tigur.api.model.Images;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;

/**
 * Created by jamespullar on 11/3/15.
 */
public interface ImgurApi {

    @Headers({"Authorization: Client-ID 0d3b867e5a368c2"})
    @GET("gallery/r/thewalkingdead")
    Call<Images> getImages();

}
