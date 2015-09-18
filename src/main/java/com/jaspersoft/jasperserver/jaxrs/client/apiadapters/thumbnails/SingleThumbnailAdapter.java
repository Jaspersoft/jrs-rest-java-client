package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.thumbnails;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import javax.ws.rs.core.MultivaluedHashMap;
import java.io.InputStream;

/**
 * @author Alex Krasnyanskiy
 * @author Tetiana Iefimenko
 * @since 6.0.1-ALPHA
 */
public class SingleThumbnailAdapter extends AbstractAdapter {

    private final MultivaluedHashMap<String, String> params = new MultivaluedHashMap<String, String>();

    public SingleThumbnailAdapter(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public SingleThumbnailAdapter report(String uri) {
        params.add("uri", uri);
        return this;
    }

    public SingleThumbnailAdapter defaultAllowed(Boolean value) {
        params.add("defaultAllowed", value.toString());
        return this;
    }

    public OperationResult<InputStream> get() {
        return request().setAccept("image/jpeg").get();
    }

    private JerseyRequest<InputStream> request() {
        return JerseyRequest.buildRequest(sessionStorage, InputStream.class,
                new String[]{"/thumbnails" + params.get("uri").get(0)}, new DefaultErrorHandler());
    }
}
