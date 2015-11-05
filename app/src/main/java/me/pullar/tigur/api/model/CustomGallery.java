package me.pullar.tigur.api.model;

import java.util.List;

/**
 * Created by jamespullar on 11/4/15.
 */
public class CustomGallery {

    private String link;
    private Integer itemCount;
    private List<GalleryImage> galleryImages;
    private List<GalleryAlbum> galleryAlbums;

    public String getLink() {
        return link;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public List<GalleryImage> getGalleryImages() {
        return galleryImages;
    }

    public List<GalleryAlbum> getGalleryAlbums() {
        return galleryAlbums;
    }

}
