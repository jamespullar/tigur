package me.pullar.tigur.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jamespullar on 11/4/15.
 */
public class Images {

    @SerializedName("data")
    private List<Image> images;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(Images images) {
        for (int i = 0; i <= images.images.size(); i++) {
            this.images.add(i, images.images.get(i));
        }
    }

}
