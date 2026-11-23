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
import com.jaspersoft.jasperserver.dto.resources.ClientFolder;
import com.jaspersoft.jasperserver.dto.resources.ClientResource;
import com.jaspersoft.jasperserver.dto.resources.ClientSemanticLayerDataSource;
import com.jaspersoft.jasperserver.dto.resources.ResourceMediaType;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.ResourceServiceParameter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.ResourcesTypeResolverUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeTypeUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.UrlUtils;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class SingleResourceAdapter extends AbstractAdapter {
    public static final String SERVICE_URI = "resources";
    public static final String REGEX = "/";
    private String resourceUri;
    private String parentUri;
    private MultivaluedMap<String, String> params = new MultivaluedHashMap<>();
    private ArrayList<String> path = new ArrayList<>();

    private ClientResource resource;

    public SingleResourceAdapter(SessionStorage sessionStorage, String resourceUri) {
        super(sessionStorage);
        this.resourceUri = resourceUri;
    }

    public SingleResourceAdapter(SessionStorage sessionStorage, ClientResource resource) {
        super(sessionStorage);
        this.resource = resource;
    }

    public SingleResourceAdapter inFolder(String parentUri) {
        this.parentUri = parentUri;
        return this;
    }

    public SingleResourceAdapter parameter(ResourceServiceParameter param, String value) {
        params.add(param.getName(), UrlUtils.encode(value));
        return this;
    }

    public SingleResourceAdapter parameter(ResourceServiceParameter param, Boolean value) {
        params.add(param.getName(), value.toString());
        return this;
    }

    public SingleResourceAdapter parameter(String param, String value) {
        params.add(param, UrlUtils.encode(value));
        return this;
    }

    public OperationResult<ClientResource> details() {
        JerseyRequest<ClientResource> request = prepareDetailsRequest();
        return request.get();
    }

    public <T extends ClientResource<T>> OperationResult<T> detailsForType(Class<T> clazz) {
        JerseyRequest<T> request = buildRequest(clazz);
        request.setAccept(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(),
                ResourcesTypeResolverUtil.extractClientType(clazz)));
        request.addParams(params);
        return request.get();
    }

    public <R> RequestExecution asyncDetails(final Callback<OperationResult<ClientResource>, R> callback) {
        final JerseyRequest<ClientResource> request = prepareDetailsRequest();
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    private JerseyRequest<ClientResource> prepareDetailsRequest() {
        JerseyRequest<ClientResource> request = buildRequest(ClientResource.class);
        request.addParams(params);
        if (isRootFolder(resourceUri)) {
            request.setAccept(sessionStorage.getConfiguration().getAcceptMimeType().equals(MimeType.JSON) ?
                    ResourceMediaType.FOLDER_JSON : ResourceMediaType.FOLDER_XML);
        } else {
            request.setAccept(sessionStorage.getConfiguration().getAcceptMimeType().equals(MimeType.JSON) ?
                    ResourceMediaType.FILE_JSON : ResourceMediaType.FILE_XML);
        }
        return request;
    }

    private boolean isRootFolder(String resourceUri) {
        return "/".equals(resourceUri) || "".equals(resourceUri);
    }

    public OperationResult<InputStream> downloadBinary() {
        return buildRequest(InputStream.class).get();
    }

    public <R> RequestExecution asyncDownloadBinary(final Callback<OperationResult<InputStream>, R> callback) {
        final JerseyRequest<InputStream> request = buildRequest(InputStream.class);
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }


    public <T extends ClientResource<T>> OperationResult<T> createOrUpdate(T resourceDescriptor) {
        return prepareCreateOrUpdateRequest(resourceDescriptor).put(resourceDescriptor);
    }

    public <R> RequestExecution asyncCreateOrUpdate(final ClientResource resource, final Callback<OperationResult<ClientResource>, R> callback) {
        final JerseyRequest<ClientResource> request = prepareCreateOrUpdateRequest(resource);
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.put(resource));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    @SuppressWarnings("unchecked")
    public <T extends ClientResource<T>> OperationResult<T> create() {
        return (OperationResult<T>) prepareCreateOrUpdateRequest(resource).post(resource);
    }

    private <T extends ClientResource<T>> JerseyRequest<T> prepareCreateOrUpdateRequest(T resource) {
        Class resourceType = ResourcesTypeResolverUtil.getResourceType(resource);
        JerseyRequest<T> request = buildRequest(resourceType);
        String resourceMimeType = ResourcesTypeResolverUtil.getMimeType(resourceType);
        request.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), resourceMimeType));
        request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), resourceMimeType));
        request.addParams(params);
        return request;
    }

    public OperationResult<ClientResource> copy() {
        return buildCopyMovieRequest().post(null);
    }

    public OperationResult<ClientResource> move() {
        return buildCopyMovieRequest().put("");
    }

    public <T extends ClientResource<T>> OperationResult<T> copy(Class<T> clazz) {
        return buildCopyMovieRequest(clazz).post(null);
    }

    public <T extends ClientResource<T>> OperationResult<T> move(Class<T> clazz) {
        return buildCopyMovieRequest(clazz).put("");
    }

    private OperationResult<ClientResource> copyOrMove(boolean moving, String fromUri) {
        JerseyRequest<ClientResource> request = prepareCopyOrMoveRequest(fromUri);
        if (moving) {
            return request.put("");
        } else {
            return request.post(null);
        }
    }

    public <R> RequestExecution asyncCopyOrMove(final boolean moving, final String fromUri, final Callback<OperationResult<ClientResource>, R> callback) {
        final JerseyRequest<ClientResource> request = prepareCopyOrMoveRequest(fromUri);

        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                OperationResult<ClientResource> result;
                if (moving) {
                    result = request.put("");
                } else {
                    result = request.post(null);
                }
                callback.execute(result);
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    private JerseyRequest<ClientResource> prepareCopyOrMoveRequest(String fromUri) {
        JerseyRequest<ClientResource> request = buildRequest(ClientResource.class);
        request.addParams(params);
        request.addHeader("Content-Location", fromUri);
        return request;
    }

    /**
     * Allows to upload resource with MultiPart request.
     *
     * @param multipartResource form
     * @param clazz             entity class
     * @param <T>               type of entity class
     * @return result instance
     */
    public <T extends ClientResource> OperationResult<T> uploadMultipartResource(FormDataMultiPart multipartResource, Class<T> clazz) {
        JerseyRequest<T> request = buildRequest(clazz);
        request.setContentType(MediaType.MULTIPART_FORM_DATA);
        request.addParams(params);
        return request.post(multipartResource);
    }

    /**
     * Allows to put resource with MultiPart request.
     *
     * @param multipartResource form
     * @param clazz             entity class
     * @param <T>               type of entity class
     * @return result instance
     */
    public <T extends ClientResource> OperationResult<T> putMultipartResource(FormDataMultiPart multipartResource, Class<T> clazz) {
        JerseyRequest<T> request = buildRequest(clazz);
        request.setContentType(MediaType.MULTIPART_FORM_DATA);
        request.addParams(params);
        return request.put(multipartResource);
    }

    private FormDataMultiPart prepareUploadForm(File fileContent,
                                                ClientFile.FileType fileType,
                                                String label,
                                                String description) {
        FormDataMultiPart form = new FormDataMultiPart();
        form
                .field("data", fileContent, MediaType.WILDCARD_TYPE)
                .field("label", label)
                .field("description", description)
                .field("type", fileType.name());
        return form;
    }

    private JerseyRequest<ClientFile> prepareUploadFileRequest() {
        JerseyRequest<ClientFile> request = buildRequest(ClientFile.class);
        request.addParams(params);
        request.setContentType(MediaType.MULTIPART_FORM_DATA);
        return request;
    }

    /**
     * Jersey request setup. Generified with a proper entity.
     *
     * @return JerseyRequest instance
     */
    private JerseyRequest<ClientSemanticLayerDataSource> prepareUploadResourcesRequest() {
        JerseyRequest<ClientSemanticLayerDataSource> request = buildRequest(ClientSemanticLayerDataSource.class);
        request.setContentType(MediaType.MULTIPART_FORM_DATA);
        return request;
    }

    public OperationResult delete() {
        JerseyRequest request = buildRequest(Object.class);
        return request.delete();
    }

    public <R> RequestExecution asyncDelete(final Callback<OperationResult, R> callback) {
        final JerseyRequest request = buildRequest(Object.class);
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.delete());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<ClientResource> patchResource(PatchDescriptor descriptor) {
        throw new UnsupportedOperationException("Server doesn't return proper MIME-type inFolder resolve entity type");
    }

    public <ResourceType extends ClientResource<ResourceType>> OperationResult<ResourceType> patchResource(Class<ResourceType> resourceTypeClass, PatchDescriptor descriptor) {
        JerseyRequest<ResourceType> request = preparePatchResourceRequest(resourceTypeClass);
        return request.post(descriptor);
    }

    public <ResourceType extends ClientResource<ResourceType>, R> RequestExecution asyncPatchResource(final Class<ResourceType> resourceTypeClass,
                                                                                                      final PatchDescriptor descriptor,
                                                                                                      final Callback<OperationResult<ResourceType>, R> callback) {
        final JerseyRequest request = preparePatchResourceRequest(resourceTypeClass);
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.post(descriptor));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    private <ResourceType extends ClientResource<ResourceType>> JerseyRequest<ResourceType> preparePatchResourceRequest(Class<ResourceType> resourceTypeClass) {
        JerseyRequest<ResourceType> request = buildRequest(resourceTypeClass);
        request.setAccept(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), ResourcesTypeResolverUtil.getMimeType(resourceTypeClass)));
        request.addHeader("X-HTTP-Method-Override", "PATCH");
        request.addParams(params);
        return request;
    }

    private <P> JerseyRequest<P> buildRequest(Class<P> clazz) {
        buildPath();
        return JerseyRequest.buildRequest(sessionStorage,
                clazz,
                path.toArray(new String[path.size()]),
                new DefaultErrorHandler());
    }

    private JerseyRequest<ClientResource> buildCopyMovieRequest() {
        buildPath();
        final JerseyRequest<ClientResource> request = JerseyRequest.buildRequest(sessionStorage,
                ClientResource.class,
                path.toArray(new String[path.size()]));
        request.addParams(params);
        request.addHeader("Content-Location", resourceUri);
        return request;
    }

    private <T extends ClientResource<T>> JerseyRequest<T> buildCopyMovieRequest(Class<T> clazz) {
        buildPath();
        final JerseyRequest<T> request = JerseyRequest.buildRequest(sessionStorage,
                clazz,
                path.toArray(new String[path.size()]));
        request.addParams(params);
        request.addHeader("Content-Location", resourceUri);
        return request;
    }

    private void buildPath() {
        path.add(SERVICE_URI);
        String targetUri = (parentUri != null) ? parentUri : resourceUri;
        if (!REGEX.equals(targetUri)) {
            path.addAll(Arrays.asList(targetUri.split(REGEX)));
        }
    }

}