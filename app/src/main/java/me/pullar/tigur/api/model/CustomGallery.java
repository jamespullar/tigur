package me.pullar.tigur.api.model;

import java.util.List;

/**
 * Created by jamespullar on 11/4/15.
 */
public class CustomGallery {

    private String link;
    private Integer itemCount;
    private GalleryImage[] galleryImages;
    private GalleryAlbum[] galleryAlbums;

    public String getLink() {
        return link;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public GalleryImage[] getGalleryImages() {
        return galleryImages;
    }

    public GalleryAlbum[] getGalleryAlbums() {
        return galleryAlbums;
    }

}
