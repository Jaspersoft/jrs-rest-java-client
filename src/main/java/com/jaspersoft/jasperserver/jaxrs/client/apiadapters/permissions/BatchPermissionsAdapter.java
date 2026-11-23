package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.permissions;

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermissionListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeTypeUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.UrlUtils;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * <p>
 * <p>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class BatchPermissionsAdapter  extends AbstractAdapter {

    public static final String PERMISSIONS_SERVICE_URI = "permissions";
    private ArrayList<String> path = new ArrayList<>();
    private RepositoryPermissionListWrapper permissions;
    private MultivaluedMap<String, String> params;

    public BatchPermissionsAdapter(SessionStorage sessionStorage, RepositoryPermissionListWrapper permissions) {
        super(sessionStorage);
        this.params = new MultivaluedHashMap<>();
        this.permissions = permissions;
        path.add(PERMISSIONS_SERVICE_URI);
    }

    public BatchPermissionsAdapter(SessionStorage sessionStorage, String resourceUri) {
        super(sessionStorage);
        this.params = new MultivaluedHashMap<>();
        path.add(PERMISSIONS_SERVICE_URI);
        path.addAll(Arrays.asList(resourceUri.split("/")));
    }

    public BatchPermissionsAdapter param(PermissionResourceParameter resourceParam, String value) {
        params.add(resourceParam.getParamName(), UrlUtils.encode(value));
        return this;
    }

    public OperationResult<RepositoryPermissionListWrapper> get(){
        return buildRequest(RepositoryPermissionListWrapper.class).get();
    }

    public <R> RequestExecution asyncGet(final Callback<OperationResult<RepositoryPermissionListWrapper>, R> callback) {
        final JerseyRequest<RepositoryPermissionListWrapper> request = buildRequest(RepositoryPermissionListWrapper.class);
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<RepositoryPermissionListWrapper> create() {
        JerseyRequest<RepositoryPermissionListWrapper> request = JerseyRequest.buildRequest(sessionStorage, RepositoryPermissionListWrapper.class, new String[]{PERMISSIONS_SERVICE_URI});
        request.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), "application/collection+{mime}"));
        return request
                .post(permissions);
    }

    public <R> RequestExecution asyncCreate(final Callback<OperationResult, R> callback) {
        final JerseyRequest request = JerseyRequest.buildRequest(sessionStorage, Object.class, new String[]{PERMISSIONS_SERVICE_URI});
        request.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), "application/collection+{mime}"));
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.post(permissions));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<RepositoryPermissionListWrapper> createOrUpdate(RepositoryPermissionListWrapper permissions) {
        return buildRequest(RepositoryPermissionListWrapper.class).put(permissions);
    }

    public <R> RequestExecution asyncCreateOrUpdate(final RepositoryPermissionListWrapper permissions, final Callback<OperationResult, R> callback) {
        final JerseyRequest request = buildRequest(Object.class);
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.put(permissions));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult delete(){
        return buildRequest(Object.class).delete();
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

    protected <T> JerseyRequest<T> buildRequest(Class<T> responceEntityClass) {
        final JerseyRequest<T> jerseyRequest = JerseyRequest.buildRequest(sessionStorage,
                responceEntityClass,
                path.toArray(new String[path.size()]));
        jerseyRequest.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), "application/collection+{mime}"));
        jerseyRequest.addParams(params);
        return jerseyRequest;
    }
}
