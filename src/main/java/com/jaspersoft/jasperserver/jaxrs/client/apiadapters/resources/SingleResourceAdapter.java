/*
* Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights  reserved.
* http://www.jaspersoft.com.
*
* Unless you have purchased  a commercial license agreement from Jaspersoft,
* the following license terms  apply:
*
* This program is free software: you can redistribute it and/or  modify
* it under the terms of the GNU Affero General Public License  as
* published by the Free Software Foundation, either version 3 of  the
* License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Affero  General Public License for more details.
*
* You should have received a copy of the GNU Affero General Public  License
* along with this program.&nbsp; If not, see <http://www.gnu.org/licenses/>.
*/

package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources;

import com.jaspersoft.jasperserver.dto.common.PatchDescriptor;
import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.dto.resources.ClientResource;
import com.jaspersoft.jasperserver.dto.resources.ResourceMediaType;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.sun.jersey.multipart.FormDataMultiPart;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.io.InputStream;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequestBuilder.buildRequest;

public class SingleResourceAdapter extends AbstractAdapter {

    private final String resourceUri;
    private final MultivaluedMap<String, String> params;

    public SingleResourceAdapter(SessionStorage sessionStorage, String resourceUri) {
        super(sessionStorage);
        this.resourceUri = resourceUri;
        this.params = new MultivaluedHashMap<String, String>();
    }

    public SingleResourceAdapter parameter(ResourceServiceParameter param, String value) {
        params.add(param.getName(), value);
        return this;
    }

    public OperationResult<ClientResource> details() {
        JerseyRequestBuilder<ClientResource> builder =
                buildRequest(sessionStorage, ClientResource.class, new String[]{"/resources", resourceUri});
        builder.addParams(params);

        if (isRootFolder(resourceUri))
            builder.setAccept(ResourceMediaType.FOLDER_JSON);
        else
            builder.setAccept(ResourceMediaType.FILE_JSON);

        return builder.get();
    }

    private boolean isRootFolder(String resourceUri) {
        return "/".equals(resourceUri) || "".equals(resourceUri);
    }

    public OperationResult<InputStream> downloadBinary() {
        return buildRequest(sessionStorage, InputStream.class, new String[]{"/resources", resourceUri})
                .get();
    }

    public OperationResult<ClientResource> createOrUpdate(ClientResource resource) {
        return getBuilderForCreateOrUpdate(resource).put(resource);
    }

    public OperationResult<ClientResource> createNew(ClientResource resource) {
        return getBuilderForCreateOrUpdate(resource).post(resource);
    }

    private JerseyRequestBuilder<ClientResource> getBuilderForCreateOrUpdate(ClientResource resource) {
        Class<? extends ClientResource> resourceType = ResourcesTypeResolverUtil.getResourceType(resource);
        JerseyRequestBuilder<? extends ClientResource> builder =
                buildRequest(sessionStorage, resourceType, new String[]{"/resources", resourceUri});
        builder.setContentType(ResourcesTypeResolverUtil.getMimeType(resourceType));
        builder.addParams(params);
        return (JerseyRequestBuilder<ClientResource>) builder;
    }

    public OperationResult<ClientResource> copyFrom(String fromUri) {
        return copyOrMove(false, fromUri);
    }

    public OperationResult<ClientResource> moveFrom(String fromUri) {
        return copyOrMove(true, fromUri);
    }

    private OperationResult<ClientResource> copyOrMove(boolean moving, String fromUri) {

        JerseyRequestBuilder<ClientResource> builder =
                buildRequest(sessionStorage, ClientResource.class, new String[]{"/resources", resourceUri});
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
                buildRequest(sessionStorage, ClientFile.class, new String[]{"/resources", resourceUri});
        builder.addParams(params);
        builder.setContentType(MediaType.MULTIPART_FORM_DATA);

        return builder.post(form);
    }

    public OperationResult delete() {
        JerseyRequestBuilder builder =
                buildRequest(sessionStorage, Object.class, new String[]{"/resources", resourceUri});
        return builder.delete();
    }

    public OperationResult<ClientResource> patchResource(PatchDescriptor descriptor) {
        throw new UnsupportedOperationException("Server doesn't return proper MIME-type to resolve entity type");
    }

    public <ResourceType extends ClientResource> OperationResult<ResourceType> patchResource(
            Class<ResourceType> resourceTypeClass, PatchDescriptor descriptor) {

        JerseyRequestBuilder<ResourceType> builder =
                buildRequest(sessionStorage, resourceTypeClass, new String[]{"/resources", resourceUri});
        builder.setAccept(ResourcesTypeResolverUtil.getMimeType(resourceTypeClass));
        builder.addHeader("X-HTTP-Method-Override", "PATCH");

        return builder.post(descriptor);
    }

}
