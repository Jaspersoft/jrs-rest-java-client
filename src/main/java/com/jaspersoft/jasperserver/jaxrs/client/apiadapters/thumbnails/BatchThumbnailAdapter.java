package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.thumbnails;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.thumbnails.ResourceThumbnailListWrapper;

import javax.ws.rs.core.MultivaluedHashMap;
import java.util.Collection;

/**
 * @author Alex Krasnyanskiy
 * @since 6.0.1-ALPHA
 */
public class BatchThumbnailAdapter extends AbstractAdapter {

    private final MultivaluedHashMap<String, String> params = new MultivaluedHashMap<String, String>();

    public BatchThumbnailAdapter(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public BatchThumbnailAdapter report(String uri) {
        params.add("uri", uri);
        return this;
    }

    public BatchThumbnailAdapter reports(String... uris) {
        for (String uri : uris) {
            params.add("uri", uri);
        }
        return this;
    }

    public BatchThumbnailAdapter reports(Collection<String> uris) {
        for (String uri : uris) {
            params.add("uri", uri);
        }
        return this;
    }

    public BatchThumbnailAdapter parameter(ThumbnailsParameter param, Boolean value) {
        params.add(param.toString().toLowerCase(), value.toString());
        return this;
    }

    public OperationResult<ResourceThumbnailListWrapper> get() {
        return request().setContentType("application/x-www-form-urlencoded").post(params);
    }

    private JerseyRequest<ResourceThumbnailListWrapper> request() {
        return JerseyRequest.buildRequest(sessionStorage, ResourceThumbnailListWrapper.class,
                new String[]{"/thumbnails"}, new DefaultErrorHandler());
    }
}
