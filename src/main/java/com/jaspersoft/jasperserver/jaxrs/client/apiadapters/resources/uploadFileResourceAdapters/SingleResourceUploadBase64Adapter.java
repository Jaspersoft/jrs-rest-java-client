package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.uploadFileResourceAdapters;

import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.ResourceServiceParameter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.ResourceUtil;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.ResourcesTypeResolverUtil;
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
public class SingleResourceUploadBase64Adapter extends AbstractAdapter{
    public SingleResourceUploadBase64Adapter(SessionStorage sessionStorage) {
        super(sessionStorage);

    }

    public static final String SERVICE_URI = "resources";
    public static final String REGEX = "/";
    private String targetResourceUri;
    private MultivaluedMap<String, String> params = new MultivaluedHashMap<String, String>();;
    private ArrayList<String> path = new ArrayList<String>();

    private ClientFile resource;
    private InputStream inputStream;

    public SingleResourceUploadBase64Adapter(SessionStorage sessionStorage, InputStream inputStream, ClientFile resource) {
        super(sessionStorage);
        this.inputStream = inputStream;
        this.resource = resource;
    }
    public SingleResourceUploadBase64Adapter(SessionStorage sessionStorage, InputStream inputStream, String resourceUri) {
        super(sessionStorage);
        this.inputStream = inputStream;
        this.targetResourceUri = resourceUri;
    }

    public SingleResourceUploadBase64Adapter inFolder(String parentUri) {
        this.targetResourceUri = parentUri;
        return this;
    }

    public SingleResourceUploadBase64Adapter createFolders(boolean value) {
        this.params.add(ResourceServiceParameter.CREATE_FOLDERS.getName(), Boolean.toString(value));
        return this;
    }

    public SingleResourceUploadBase64Adapter withParameter(String name, String value) {
        this.params.add(name, value);
        return this;
    }

    public OperationResult<ClientFile> create() {

        final ClientFile entity = new ClientFile().
                setLabel(resource.getLabel()).
                setDescription(resource.getDescription()).
                setType((resource).getType()).
                setContent(ResourceUtil.toBase64EncodedContent(inputStream));

        return  prepareCreateBase64Request().post(entity);
    }

    public OperationResult<ClientFile> createOrUpdate(InputStream inputStream, ClientFile resourceDescriptor) {
        resourceDescriptor.setContent(ResourceUtil.toBase64EncodedContent(inputStream));
        resource = resourceDescriptor;
        return  prepareCreateBase64Request().put(resourceDescriptor);
    }

    private JerseyRequest<ClientFile> prepareCreateBase64Request() {
        JerseyRequest<ClientFile> request = buildRequest();
        String resourceMimeType = ResourcesTypeResolverUtil.getMimeType(ClientFile.class);
        request.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), resourceMimeType));
        request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), resourceMimeType));
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
