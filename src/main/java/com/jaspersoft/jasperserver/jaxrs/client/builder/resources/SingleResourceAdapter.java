package com.jaspersoft.jasperserver.jaxrs.client.builder.resources;

import com.jaspersoft.jasperserver.dto.common.PatchDescriptor;
import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.dto.resources.ClientResource;
import com.jaspersoft.jasperserver.jaxrs.client.builder.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.builder.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.builder.SessionStorage;
import com.sun.jersey.core.util.Base64;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.io.*;

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

    public <Response extends ClientResource> OperationResult<Response> details(Class<Response> responseClass) {
        JerseyRequestBuilder<Response> builder =
                new JerseyRequestBuilder<Response>(sessionStorage, responseClass);
        builder
                .setPath("resources")
                .setPath(resourceUri);
        builder.addParams(params);
        builder.setAccept(ResourcesTypeResolverUtil.getMimeType(responseClass));
        return builder.get();
    }

    public OperationResult<InputStream> downloadBinary(ResourceFilesMimeType mimeType) {
        JerseyRequestBuilder<InputStream> builder =
                new JerseyRequestBuilder<InputStream>(sessionStorage, InputStream.class);
        builder
                .setPath("resources")
                .setPath(resourceUri);
        builder.setAccept(mimeType.getType());
        return builder.get();
    }

    public <ResourceType extends ClientResource> OperationResult<ResourceType> createOrUpdate(
            boolean generateId, Class<ResourceType> resourceTypeClass, ResourceType resource) {

        JerseyRequestBuilder<ResourceType> builder =
                new JerseyRequestBuilder<ResourceType>(sessionStorage, resourceTypeClass);
        builder
                .setPath("resources")
                .setPath(resourceUri);
        builder.addParams(params);
        builder.setContentType(ResourcesTypeResolverUtil.getMimeType(resourceTypeClass));

        if (generateId)
            return builder.post(resource);
        else
            return builder.put(resource);
    }

    public <ResourceType extends ClientResource> OperationResult<ResourceType> copy(
            Class<ResourceType> resourceTypeClass, String fromUri) {
        return copyOrMove(false, resourceTypeClass, fromUri);
    }

    public <ResourceType extends ClientResource> OperationResult<ResourceType> move(
            Class<ResourceType> resourceTypeClass, String fromUri) {
        return copyOrMove(true, resourceTypeClass, fromUri);
    }

    private  <ResourceType extends ClientResource> OperationResult<ResourceType> copyOrMove(
            boolean moving, Class<ResourceType> resourceTypeClass, String fromUri) {

        JerseyRequestBuilder<ResourceType> builder =
                new JerseyRequestBuilder<ResourceType>(sessionStorage, resourceTypeClass);
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

    public OperationResult<ClientFile> uploadFile(ClientFile file, File fileContent){

        byte[] fileByteArray = null;
        try {
            FileInputStream fis = new FileInputStream(fileContent);
            int available = fis.available();
            fileByteArray = new byte[available];
            fis.read(fileByteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JerseyRequestBuilder<ClientFile> builder =
                new JerseyRequestBuilder<ClientFile>(sessionStorage, ClientFile.class);
        builder
                .setPath("resources")
                .setPath(resourceUri);
        builder.addParams(params);

        String mimeType = ResourcesTypeResolverUtil.getMimeType(ClientFile.class);
        builder.setContentType(mimeType);

        file.setContent(new String(Base64.encode(fileByteArray)));

        return builder.post(file);
    }

    public OperationResult delete(){
        JerseyRequestBuilder builder =
                new JerseyRequestBuilder(sessionStorage, Object.class);
        builder
                .setPath("resources")
                .setPath(resourceUri);
        return builder.delete();
    }

    public <ResourceType extends ClientResource> OperationResult<ResourceType> patchResource(
            Class<ResourceType> resourceTypeClass, PatchDescriptor descriptor){

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
