package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.thumbnails;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.thumbnails.ResourceThumbnailListWrapper;

import javax.ws.rs.core.MultivaluedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alex Krasnyanskiy
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

    public BatchThumbnailAdapter parameter(ThumbnailsParameter param, Boolean value) {
        params.add(param.toString().toLowerCase(), value.toString());
        return this;
    }

    public OperationResult<ResourceThumbnailListWrapper> get() {
        StringBuilder req = new StringBuilder("");
        for (Map.Entry<String, List<String>> entry : params.entrySet()) {
            req.append("&").append(entry.getKey()).append(entry.getValue());
        }
        return req.length() >= 2000 // todo: 2000 characters with host name and port?
                ? request().setContentType("application/x-www-form-urlencoded").post(params)
                : request().addParams(params).get();
    }

    private JerseyRequest<ResourceThumbnailListWrapper> request() {
        return JerseyRequest.buildRequest(sessionStorage, ResourceThumbnailListWrapper.class,
                new String[]{"/thumbnails"}, new DefaultErrorHandler());
    }
}
