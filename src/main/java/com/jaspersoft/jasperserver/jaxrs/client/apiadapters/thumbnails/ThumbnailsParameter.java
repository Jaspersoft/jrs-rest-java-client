package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.thumbnails;

/**
 * @author Alex Krasnyanskiy
 * @since 6.0.1-ALPHA
 */
public enum ThumbnailsParameter {

    DEFAULT_ALLOWED("defaultAllowed");

    private String param;

    private ThumbnailsParameter(String param) {
        this.param = param;
    }
}
