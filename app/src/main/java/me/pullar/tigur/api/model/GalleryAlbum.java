package me.pullar.tigur.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jamespullar on 11/4/15.
 */
public class GalleryAlbum {

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("cover_width")
    private Integer coverWidth;

    @SerializedName("cover_height")
    private Integer coverHeight;

    @SerializedName("link")
    private String link;

    @SerializedName("is_album")
    private boolean isAlbum;

    @SerializedName("images")
    private List<Image> images;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCoverWidth() {
        return coverWidth;
    }

    public void setCoverWidth(Integer coverWidth) {
        this.coverWidth = coverWidth;
    }

    public Integer getCoverHeight() {
        return coverHeight;
    }

    public void setCoverHeight(Integer coverHeight) {
        this.coverHeight = coverHeight;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isAlbum() {
        return isAlbum;
    }

    public void setIsAlbum(boolean isAlbum) {
        this.isAlbum = isAlbum;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
