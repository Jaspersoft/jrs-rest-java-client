package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.thumbnails;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.RequestMethod;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.dto.thumbnails.ResourceThumbnailsListWrapper;

import jakarta.ws.rs.core.MultivaluedHashMap;
import java.util.Collection;

/**
 * @author Alex Krasnyanskiy
 * @author Tetiana Iefimenko
 * @since 6.0.1-ALPHA
 */
public class BatchThumbnailAdapter extends AbstractAdapter {

    public static final String SERVICE_URI = "thumbnails";
    private final MultivaluedHashMap<String, String> params = new MultivaluedHashMap<String, String>();
    private RequestMethod requestMethod;
    private Boolean defaultAllowed = false;

    public BatchThumbnailAdapter(SessionStorage sessionStorage) {
        super(sessionStorage);
        requestMethod = RequestMethod.POST;
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

    public BatchThumbnailAdapter defaultAllowed(Boolean value) {
       this.defaultAllowed = value;
        return this;
    }

    public BatchThumbnailAdapter requestMethod(RequestMethod requestMethod){
        this.requestMethod = requestMethod;
        return  this;
    }

    public OperationResult<ResourceThumbnailsListWrapper> get() {
        if (requestMethod == RequestMethod.POST) {
            return  request()
                    .setContentType("application/x-www-form-urlencoded")
                    .post(params);
        }
        return  request().addParams(params).get();
    }

    private JerseyRequest<ResourceThumbnailsListWrapper> request() {
        JerseyRequest<ResourceThumbnailsListWrapper> jerseyRequest = JerseyRequest.buildRequest(sessionStorage, ResourceThumbnailsListWrapper.class,
                new String[]{SERVICE_URI}, new DefaultErrorHandler());
        jerseyRequest.addParam("defaultAllowed", defaultAllowed.toString());
        return jerseyRequest;
    }
}
