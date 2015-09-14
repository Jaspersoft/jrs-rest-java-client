package com.jaspersoft.jasperserver.jaxrs.client.dto.thumbnails;
import com.jaspersoft.jasperserver.dto.thumbnails.ResourceThumbnail;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Fake wrapper class for ResourceThumbnail collection.
 */
@Deprecated
@XmlRootElement(name = "thumbnails")
public class ResourceThumbnailListWrapper {

    private List<ResourceThumbnail> thumbnails;

    public ResourceThumbnailListWrapper() {
    }

    public ResourceThumbnailListWrapper(List<ResourceThumbnail> thumbnail) {
        this.thumbnails = thumbnail;
    }

    public List<ResourceThumbnail> getThumbnails() {
        return thumbnails;
    }

    @XmlElement(name = "thumbnail")
    public void setThumbnails(List<ResourceThumbnail> thumbnails) {
        this.thumbnails = thumbnails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceThumbnailListWrapper that = (ResourceThumbnailListWrapper) o;
        return !(thumbnails != null
                ? !thumbnails.equals(that.thumbnails)
                : that.thumbnails != null);
    }

    @Override
    public int hashCode() {
        return thumbnails != null
                ? thumbnails.hashCode()
                : 0;
    }

    @Override
    public String toString() {
        return "ResourceThumbnailListWrapper{" +
                "thumbnails=" + thumbnails +
                '}';
    }
}
