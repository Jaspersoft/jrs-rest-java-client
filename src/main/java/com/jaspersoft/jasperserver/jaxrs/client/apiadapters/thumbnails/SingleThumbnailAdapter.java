package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.thumbnails;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Alex Krasnyanskiy
 * @author Tetiana Iefimenko
 * @since 6.0.1-ALPHA
 */
public class SingleThumbnailAdapter extends AbstractAdapter {

    public static final String SERVICE_URI = "thumbnails";
    private String reportUri;
    private Boolean defaultAllowed = false;
    private ArrayList<String> path = new ArrayList<String>();

    public SingleThumbnailAdapter(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public SingleThumbnailAdapter report(String uri) {
        if (uri != null && !uri.equals("")) {
            reportUri = uri;
        }
        return this;
    }

    public SingleThumbnailAdapter defaultAllowed(Boolean value) {
        this.defaultAllowed = value;
        return this;
    }

    public OperationResult<InputStream> get() {
        return request().get();
    }

    private JerseyRequest<InputStream> request() {
        if (reportUri == null) {
            throw new MandatoryParameterNotFoundException("URI of report should be specified");
        }
        path.add(SERVICE_URI);
        path.addAll(Arrays.asList(reportUri.split("/")));
        JerseyRequest<InputStream> request = JerseyRequest.buildRequest(sessionStorage,
                InputStream.class,
                path.toArray(new String[path.size()]),
                new DefaultErrorHandler());
        request.setAccept("image/jpeg");
        request.addParam("defaultAllowed", defaultAllowed.toString());
        return request;
    }
}
