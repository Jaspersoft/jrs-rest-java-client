package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources;

import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.uploadFileResourceAdapters.SingleResourceUploadBase64Adapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.uploadFileResourceAdapters.SingleResourceUploadMultipartAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.uploadFileResourceAdapters.SingleResourceUploadStreamAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import java.io.InputStream;

/**
 * <p>
 * <p>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class SingleFileResourceUploadAdapter {

    private ClientFile resource;
    private String resourceUri;
    private InputStream inputStream;
    private SessionStorage sessionStorage;

    public SingleFileResourceUploadAdapter(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
    }

    public SingleFileResourceUploadAdapter(SessionStorage sessionStorage, InputStream inputStream, ClientFile resourceDescriptor) {
        this.sessionStorage = sessionStorage;
        this.inputStream = inputStream;
        this.resource = resourceDescriptor;
    }

    public SingleFileResourceUploadAdapter(SessionStorage sessionStorage, String fileResourceUri) {
        this.sessionStorage = sessionStorage;
        this.resourceUri = fileResourceUri;
    }

    public SingleResourceUploadStreamAdapter asInputStream() {
        return (resource == null)
                ? new SingleResourceUploadStreamAdapter(sessionStorage, inputStream, resourceUri)
                : new SingleResourceUploadStreamAdapter(sessionStorage, inputStream, resource);
    }

    public SingleResourceUploadBase64Adapter asBase64EncodedContent() {
        return (resource == null)
                ? new SingleResourceUploadBase64Adapter(sessionStorage, inputStream, resourceUri)
                : new SingleResourceUploadBase64Adapter(sessionStorage, inputStream, resource);
    }

    public SingleResourceUploadMultipartAdapter asMultipartForm() {
        return (resource == null)
                ? new SingleResourceUploadMultipartAdapter(sessionStorage, inputStream, resourceUri)
                : new SingleResourceUploadMultipartAdapter(sessionStorage, inputStream, resource);
    }

}
