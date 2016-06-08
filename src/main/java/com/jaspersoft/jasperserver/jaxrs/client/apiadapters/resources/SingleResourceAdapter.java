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
import com.jaspersoft.jasperserver.dto.resources.ClientSemanticLayerDataSource;
import com.jaspersoft.jasperserver.dto.resources.ResourceMediaType;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeTypeUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.sun.jersey.multipart.FormDataMultiPart;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

public class SingleResourceAdapter extends AbstractAdapter {
    public static final String SERVICE_URI = "resources";
    private final String resourceUri;
    private final MultivaluedMap<String, String> params;
    private ArrayList<String> path = new ArrayList<String>();

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
        JerseyRequest<ClientResource> request = prepareDetailsRequest();
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
            request.setAccept(ResourceMediaType.FOLDER_JSON);
        } else {
            request.setAccept(ResourceMediaType.FILE_JSON);
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

    public OperationResult<ClientResource> createOrUpdate(ClientResource resource) {
        return prepareCreateOrUpdateRequest(resource).put(resource);
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

    public OperationResult<ClientResource> createNew(ClientResource resource) {
        return prepareCreateOrUpdateRequest(resource).post(resource);
    }

    public <R> RequestExecution asyncCreateNew(final ClientResource resource, final Callback<OperationResult<ClientResource>, R> callback) {
        final JerseyRequest<ClientResource> request = prepareCreateOrUpdateRequest(resource);
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.post(resource));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    private JerseyRequest<ClientResource> prepareCreateOrUpdateRequest(ClientResource resource) {
        Class<? extends ClientResource> resourceType = ResourcesTypeResolverUtil.getResourceType(resource);
        JerseyRequest<? extends ClientResource> request = buildRequest(resourceType);
        request.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), ResourcesTypeResolverUtil.getMimeType(resourceType)));
        request.addParams(params);
        return (JerseyRequest<ClientResource>) request;
    }

    public OperationResult<ClientResource> copyFrom(String fromUri) {
        return copyOrMove(false, fromUri);
    }

    public OperationResult<ClientResource> moveFrom(String fromUri) {
        return copyOrMove(true, fromUri);
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
     * @param clazz entity class
     * @param <T> type of entity class
     * @return result instance
     */
    public <T> OperationResult<T> uploadMultipartResource(FormDataMultiPart multipartResource, Class<T> clazz) {
        JerseyRequest<T> request = buildRequest(clazz);
        request.setContentType(MediaType.MULTIPART_FORM_DATA);
        return request.post(multipartResource);
    }

    public <T> OperationResult<T> get(Class<T> clazz){
        JerseyRequest<T> request = buildRequest(clazz);
        if (isRootFolder(resourceUri)) {
            request.setAccept(ResourceMediaType.FOLDER_JSON);
        } else {
            request.setAccept(ResourceMediaType.FILE_JSON);
        }
        return request.get();
    }

    public OperationResult<ClientFile> uploadFile(File fileContent,
                                                  ClientFile.FileType fileType,
                                                  String label,
                                                  String description) {
        FormDataMultiPart form = prepareUploadForm(fileContent, fileType, label, description);
        JerseyRequest<ClientFile> request = prepareUploadFileRequest();
        return request.post(form);
    }

    public <R> RequestExecution asyncUploadFile(final File fileContent,
                                                 final ClientFile.FileType fileType,
                                                 final String label,
                                                 final String description,
                                                 final Callback<OperationResult<ClientFile>, R> callback) {
        final FormDataMultiPart form = prepareUploadForm(fileContent, fileType, label, description);
        final JerseyRequest<ClientFile> request = prepareUploadFileRequest();
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.post(form));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
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

    public <ResourceType extends ClientResource> OperationResult<ResourceType> patchResource(Class<ResourceType> resourceTypeClass, PatchDescriptor descriptor) {
        JerseyRequest<ResourceType> request = preparePatchResourceRequest(resourceTypeClass);
        return request.post(descriptor);
    }

    public <ResourceType extends ClientResource, R> RequestExecution asyncPatchResource(final Class<ResourceType> resourceTypeClass,
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

    private <ResourceType extends ClientResource> JerseyRequest<ResourceType> preparePatchResourceRequest(Class<ResourceType> resourceTypeClass) {
        JerseyRequest<ResourceType> request = buildRequest(resourceTypeClass);
        request.setAccept(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), ResourcesTypeResolverUtil.getMimeType(resourceTypeClass)));
        request.addHeader("X-HTTP-Method-Override", "PATCH");
        return request;
    }

    private  <P> JerseyRequest<P> buildRequest(Class<P> clazz) {
        path.add(SERVICE_URI);
        path.addAll(Arrays.asList(resourceUri.split("/")));
        return JerseyRequest.buildRequest(sessionStorage,
                clazz,
                path.toArray(new String[path.size()]),
                new DefaultErrorHandler());
    }
}