package me.pullar.tigur.api.model;

/**
 * Created by jamespullar on 11/4/15.
 */
public class GalleryAlbum {

    private String id;
    private String title;
    private String description;
    private Integer coverWidth;
    private Integer coverHeight;
    private String link;
    private boolean isAlbum;
    private Image[] images;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getCoverWidth() {
        return coverWidth;
    }

    public Integer getCoverHeight() {
        return coverHeight;
    }

    public String getLink() {
        return link;
    }

    public boolean isAlbum() {
        return isAlbum;
    }

    public Image[] getImages() {
        return images;
    }

}
