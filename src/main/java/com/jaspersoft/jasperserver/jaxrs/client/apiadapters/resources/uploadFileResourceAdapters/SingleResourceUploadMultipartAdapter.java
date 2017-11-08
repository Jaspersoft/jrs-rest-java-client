package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.uploadFileResourceAdapters;

import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.ResourceServiceParameter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;

/**
 * <p>
 * <p>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class SingleResourceUploadMultipartAdapter extends AbstractAdapter{

    public static final String SERVICE_URI = "resources";
    public static final String REGEX = "/";
    private String targetResourceUri;
    private String predefinedName;
    private MultivaluedMap<String, String> params = new MultivaluedHashMap<String, String>();;
    private ArrayList<String> path = new ArrayList<String>();

    private ClientFile resource;
    private InputStream inputStream;

    public SingleResourceUploadMultipartAdapter(SessionStorage sessionStorage, InputStream inputStream, ClientFile resource) {
        super(sessionStorage);
        this.inputStream = inputStream;
        this.resource = resource;
    }

    public SingleResourceUploadMultipartAdapter(SessionStorage sessionStorage, InputStream inputStream, String resourceUri) {
        super(sessionStorage);
        this.inputStream = inputStream;
        this.targetResourceUri = resourceUri;
    }

    public SingleResourceUploadMultipartAdapter toFolder(String destinationUri) {
        this.targetResourceUri = destinationUri;
        return this;
    }

    public SingleResourceUploadMultipartAdapter withName(String name) {
        this.predefinedName = name;
        return this;
    }

    public SingleResourceUploadMultipartAdapter overwrite(String value) {
        params.add(ResourceServiceParameter.OVERWRITE.getName(), value);
        return this;
    }

    public OperationResult<ClientFile> upload() {

        FormDataMultiPart formDataMultiPart = buildMultiPartForm();
        JerseyRequest<ClientFile> request = buildRequest();
        request.addHeader("Content-Version", "-1");
        return  (predefinedName != null)? request.put(formDataMultiPart): request.post(formDataMultiPart);
    }

    public OperationResult<ClientFile> createOrUpdate(InputStream inputStream, ClientFile resourceDescriptor) {
        this.inputStream = inputStream;
        this.resource = resourceDescriptor;
        final FormDataMultiPart formDataMultiPart = buildMultiPartForm();
        final JerseyRequest<ClientFile> request = buildRequest();
        final Integer version = resourceDescriptor.getVersion();
        if (version != null) {
            request.addHeader("Content-Version", version.toString());
        }
        return  request.put(formDataMultiPart);
    }

    protected FormDataMultiPart buildMultiPartForm() {
        FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
        StreamDataBodyPart streamDataBodyPart = new StreamDataBodyPart("data", inputStream);
        formDataMultiPart.bodyPart(streamDataBodyPart);
        formDataMultiPart.field("label", resource.getLabel());
        formDataMultiPart.field("description", resource.getDescription());
        formDataMultiPart.field("type", resource.getType().toString());
        return formDataMultiPart;
    }
    private JerseyRequest<ClientFile> buildRequest() {
        path.add(SERVICE_URI);
        if (!targetResourceUri.equals(REGEX)) {
            path.addAll(Arrays.asList(targetResourceUri.split(REGEX)));
        }
        if (predefinedName != null) path.add(predefinedName);
        final JerseyRequest<ClientFile> request = JerseyRequest.buildRequest(sessionStorage,
                ClientFile.class,
                path.toArray(new String[path.size()]));
        request.setContentType("multipart/form-data");
        return request;
    }

}
