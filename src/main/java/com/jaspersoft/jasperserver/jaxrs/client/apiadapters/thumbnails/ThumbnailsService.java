package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.thumbnails;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

/**
 * @author Alex Krasnyanskiy
 * @since 6.0.1-ALPHA
 */
public class ThumbnailsService extends AbstractAdapter {

    public ThumbnailsService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public BatchThumbnailAdapter thumbnails() {
        return new BatchThumbnailAdapter(sessionStorage);
    }

    public SingleThumbnailAdapter thumbnail() {
        return new SingleThumbnailAdapter(sessionStorage);
    }
}
