package me.pullar.tigur.api.model;

/**
 * Created by jamespullar on 11/4/15.
 */
public class GalleryImage {

    private Integer id;
    private String type;
    private String title;
    private String description;
    private String link;
    private boolean animated;
    private Integer width;
    private Integer height;
    private Integer size;
    private Integer views;
    private String gifv;
    private String mp4;
    private String webm;
    private Boolean isAlbum;

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public boolean isAnimated() {
        return animated;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getViews() {
        return views;
    }

    public String getGifv() {
        return gifv;
    }

    public String getMp4() {
        return mp4;
    }

    public String getWebm() {
        return webm;
    }

    public boolean isAlbum() {
        return isAlbum;
    }

}
