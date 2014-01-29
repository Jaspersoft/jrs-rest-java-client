package com.jaspersoft.jasperserver.jaxrs.client.builder.resources;

import com.jaspersoft.jasperserver.dto.common.PatchDescriptor;
import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.dto.resources.ClientResource;
import com.jaspersoft.jasperserver.dto.resources.ResourceMediaType;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;
import com.sun.jersey.multipart.FormDataMultiPart;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.io.InputStream;

public class SingleResourceAdapter {


    private final SessionStorage sessionStorage;
    private final String resourceUri;
    private final MultivaluedMap<String, String> params;

    public SingleResourceAdapter(SessionStorage sessionStorage, String resourceUri) {
        this.sessionStorage = sessionStorage;
        this.resourceUri = resourceUri;
        this.params = new MultivaluedHashMap<String, String>();
    }

    public SingleResourceAdapter parameter(ResourceServiceParameter param, String value) {
        params.add(param.getName(), value);
        return this;
    }

    public OperationResult<ClientResource> details() {
        JerseyRequestBuilder<ClientResource> builder =
                new JerseyRequestBuilder<ClientResource>(sessionStorage, ClientResource.class);
        builder
                .setPath("resources")
                .setPath(resourceUri);
        builder.addParams(params);

        if (isRootFolder(resourceUri))
            builder.setAccept(ResourceMediaType.FOLDER_JSON);
        else
            builder.setAccept(ResourceMediaType.FILE_JSON);

        return builder.get();
    }

    private boolean isRootFolder(String resourceUri) {
        return "/".equals(resourceUri);
    }

    public OperationResult<InputStream> downloadBinary() {
        JerseyRequestBuilder<InputStream> builder =
                new JerseyRequestBuilder<InputStream>(sessionStorage, InputStream.class);
        builder
                .setPath("resources")
                .setPath(resourceUri);
        return builder.get();
    }

    public OperationResult<ClientResource> createOrUpdate(ClientResource resource) {
        return getBuilderForCreateOrUpdate(resource).put(resource);
    }

    public OperationResult<ClientResource> createNew(ClientResource resource) {
        return getBuilderForCreateOrUpdate(resource).post(resource);
    }

    private JerseyRequestBuilder<ClientResource> getBuilderForCreateOrUpdate(ClientResource resource) {
        Class<? extends ClientResource> resourceType = ResourcesTypeResolverUtil.getResourceType(resource);

        JerseyRequestBuilder<ClientResource> builder =
                new JerseyRequestBuilder<ClientResource>(sessionStorage, resourceType);
        builder
                .setPath("resources")
                .setPath(resourceUri);
        builder.addParams(params);
        builder.setContentType(ResourcesTypeResolverUtil.getMimeType(resourceType));

        return builder;
    }

    public OperationResult<ClientResource> copyFrom(String fromUri) {
        return copyOrMove(false, fromUri);
    }

    public OperationResult<ClientResource> moveFrom(String fromUri) {
        return copyOrMove(true, fromUri);
    }

    private OperationResult<ClientResource> copyOrMove(boolean moving, String fromUri) {

        JerseyRequestBuilder<ClientResource> builder =
                new JerseyRequestBuilder<ClientResource>(sessionStorage, ClientResource.class);
        builder
                .setPath("resources")
                .setPath(resourceUri);
        builder.addParams(params);
        builder.addHeader("Content-Location", fromUri);

        if (moving)
            return builder.put(0);
        else
            return builder.post(null);
    }

    public OperationResult<ClientFile> uploadFile(File fileContent,
                                                  ClientFile.FileType fileType,
                                                  String label,
                                                  String description) {

        FormDataMultiPart form = new FormDataMultiPart();
        form
                .field("data", fileContent, MediaType.WILDCARD_TYPE)
                .field("label", label)
                .field("description", description)
                .field("type", fileType.name());

        JerseyRequestBuilder<ClientFile> builder =
                new JerseyRequestBuilder<ClientFile>(sessionStorage, ClientFile.class);
        builder
                .setPath("resources")
                .setPath(resourceUri);
        builder.addParams(params);
        builder.setContentType(MediaType.MULTIPART_FORM_DATA);

        return builder.post(form);
    }

    public OperationResult delete() {
        JerseyRequestBuilder builder =
                new JerseyRequestBuilder(sessionStorage, Object.class);
        builder
                .setPath("resources")
                .setPath(resourceUri);
        return builder.delete();
    }

    public OperationResult<ClientResource> patchResource(PatchDescriptor descriptor) {
        throw new UnsupportedOperationException("Server doesn't return proper MIME-type to resolve entity type");
    }

    public <ResourceType extends ClientResource> OperationResult<ResourceType> patchResource(
            Class<ResourceType> resourceTypeClass, PatchDescriptor descriptor) {

        JerseyRequestBuilder<ResourceType> builder =
                new JerseyRequestBuilder<ResourceType>(sessionStorage, resourceTypeClass);
        builder
                .setPath("resources")
                .setPath(resourceUri);
        builder.setAccept(ResourcesTypeResolverUtil.getMimeType(resourceTypeClass));
        builder.addHeader("X-HTTP-Method-Override", "PATCH");

        return builder.post(descriptor);
    }

}
