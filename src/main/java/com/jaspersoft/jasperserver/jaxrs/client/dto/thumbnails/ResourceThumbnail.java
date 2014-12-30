package com.jaspersoft.jasperserver.jaxrs.client.dto.thumbnails;

import javax.xml.bind.annotation.XmlRootElement;

@Deprecated
@XmlRootElement
public class ResourceThumbnail {

    private String uri;
    private String thumbnailData;

    public ResourceThumbnail() {
    }

    public ResourceThumbnail(ResourceThumbnail source) {
        this.uri = source.getUri();
        this.thumbnailData = source.getThumbnailData();
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getThumbnailData() {
        return this.thumbnailData;
    }

    public void setThumbnailData(String thumbnailData) {
        this.thumbnailData = thumbnailData;
    }

    public int hashCode() {
        int result = this.uri.hashCode();
        result = 31 * result + (this.thumbnailData != null ? this.thumbnailData.hashCode() : 0);
        return result;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            ResourceThumbnail that;
            label33:
            {
                that = (ResourceThumbnail) o;
                if (this.uri != null) {
                    if (this.uri.equals(that.uri)) {
                        break label33;
                    }
                } else if (that.uri == null) {
                    break label33;
                }

                return false;
            }

            if (this.thumbnailData != null) {
                if (this.thumbnailData.equals(that.thumbnailData)) {
                    return true;
                }
            } else if (that.thumbnailData == null) {
                return true;
            }

            return false;
        } else {
            return false;
        }
    }

    public String toString() {
        return "Thumbnail{uri=\'" + this.uri + '\'' + ", thumbnailData=\'" + this.thumbnailData + '\'' + "} " + super.toString();
    }
}
