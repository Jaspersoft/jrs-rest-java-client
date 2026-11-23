package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.uploadFileResourceAdapters;

import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.ResourceServiceParameter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeTypeUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;

/**
 * <p>
 * <p>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class SingleResourceUploadStreamAdapter extends AbstractAdapter{
    public static final String SERVICE_URI = "resources";
    public static final String REGEX = "/";

    private ClientFile resource;
    private InputStream inputStream;
    private String targetResourceUri;
    private MultivaluedMap<String, String> params = new MultivaluedHashMap<String, String>();
    private ArrayList<String> path = new ArrayList<String>();

    public SingleResourceUploadStreamAdapter(SessionStorage sessionStorage, InputStream inputStream, ClientFile resource) {
        super(sessionStorage);
        this.inputStream = inputStream;
        this.resource = resource;
    }

    public SingleResourceUploadStreamAdapter(SessionStorage sessionStorage, InputStream inputStream, String resourceUri) {
        super(sessionStorage);
        this.inputStream = inputStream;
        this.targetResourceUri = resourceUri;
    }

    public SingleResourceUploadStreamAdapter inFolder(String parentUri) {
        this.targetResourceUri = parentUri;
        return this;
    }

    public SingleResourceUploadStreamAdapter createFolders(Boolean value) {
        params.add(ResourceServiceParameter.CREATE_FOLDERS.getName(), value.toString());
        return this;
    }

    public SingleResourceUploadStreamAdapter overwrite(Boolean value) {
        params.add(ResourceServiceParameter.OVERWRITE.getName(), value.toString());
        return this;
    }

    public SingleResourceUploadStreamAdapter withParameter(String name, String value) {
        this.params.add(name, value);
        return this;
    }

    private boolean isRootFolder(String resourceUri) {
        return "/".equals(resourceUri) || "".equals(resourceUri);
    }

    public OperationResult<ClientFile> create() {
        return prepareCreateBinaryRequest().post(inputStream);
    }

    public OperationResult<ClientFile> createOrUpdate(InputStream inputStream, ClientFile resourceDescriptor) {
        this.resource = resourceDescriptor;
        return prepareCreateBinaryRequest().put(inputStream);
    }

    private JerseyRequest<ClientFile> prepareCreateBinaryRequest() {
        JerseyRequest<ClientFile> request = buildRequest();
        String resourceMimeType = resource.getType().getMimeType();
        request.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), resourceMimeType));
        request.addHeader("Content-Description", resource.getDescription());
        request.addHeader("Content-Disposition", "attachment; filename=" + resource.getLabel());
        request.addParams(params);
        return request;
    }

    private JerseyRequest<ClientFile> buildRequest() {
        path.add(SERVICE_URI);
        if (!targetResourceUri.equals(REGEX)) {
            path.addAll(Arrays.asList(targetResourceUri.split(REGEX)));
        }
        return JerseyRequest.buildRequest(sessionStorage,
                ClientFile.class,
                path.toArray(new String[path.size()]),
                new DefaultErrorHandler());
    }


}
