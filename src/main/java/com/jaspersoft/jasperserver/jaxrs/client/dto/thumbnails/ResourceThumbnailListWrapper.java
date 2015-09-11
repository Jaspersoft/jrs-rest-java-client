package com.jaspersoft.jasperserver.jaxrs.client.dto.thumbnails;

import java.util.List;

/**
 * Fake wrapper class for ResourceThumbnail collection.
 */
@Deprecated
public class ResourceThumbnailListWrapper {

    private List<ResourceThumbnail> thumbnail;

    public ResourceThumbnailListWrapper() {
    }

    public ResourceThumbnailListWrapper(List<ResourceThumbnail> thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<ResourceThumbnail> getThumbnail() {
        return thumbnail;
    }
    public void setThumbnail(List<ResourceThumbnail> thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceThumbnailListWrapper that = (ResourceThumbnailListWrapper) o;
        return !(thumbnail != null
                ? !thumbnail.equals(that.thumbnail)
                : that.thumbnail != null);
    }

    @Override
    public int hashCode() {
        return thumbnail != null
                ? thumbnail.hashCode()
                : 0;
    }

    @Override
    public String toString() {
        return "ResourceThumbnailListWrapper{" +
                "thumbnails=" + thumbnail +
                '}';
    }
}
