package me.pullar.tigur.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jamespullar on 11/4/15.
 */
public class GalleryImage {

    @SerializedName("id")
    private Integer id;

    @SerializedName("type")
    private String type;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("link")
    private String link;

    @SerializedName("animated")
    private boolean animated;

    @SerializedName("width")
    private Integer width;

    @SerializedName("height")
    private Integer height;

    @SerializedName("size")
    private Integer size;

    @SerializedName("views")
    private Integer views;

    @SerializedName("gifv")
    private String gifv;

    @SerializedName("mp4")
    private String mp4;

    @SerializedName("webm")
    private String webm;

    @SerializedName("is_album")
    private Boolean isAlbum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getGifv() {
        return gifv;
    }

    public void setGifv(String gifv) {
        this.gifv = gifv;
    }

    public String getMp4() {
        return mp4;
    }

    public void setMp4(String mp4) {
        this.mp4 = mp4;
    }

    public String getWebm() {
        return webm;
    }

    public void setWebm(String webm) {
        this.webm = webm;
    }

    public boolean isAlbum() {
        return isAlbum;
    }

    public void setIsAlbum(Boolean isAlbum) {
        this.isAlbum = isAlbum;
    }

}
