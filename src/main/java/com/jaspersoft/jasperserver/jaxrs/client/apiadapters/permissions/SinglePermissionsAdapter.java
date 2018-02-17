package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.permissions;

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermission;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author tetiana.iefimenko
 * @version $Id$
\ */
public class SinglePermissionsAdapter  extends AbstractAdapter {

    public static final String PERMISSIONS_SERVICE_URI = "permissions";
    private ArrayList<String> path = new ArrayList<>();
    private String recipientUri;
    private RepositoryPermission permission;

    public SinglePermissionsAdapter(SessionStorage sessionStorage, RepositoryPermission permission) {
        super(sessionStorage);
        this.permission = permission;
        path.add(PERMISSIONS_SERVICE_URI);
    }

    public SinglePermissionsAdapter(SessionStorage sessionStorage, String resourceUri) {
        super(sessionStorage);
        this.recipientUri = resourceUri;
        path.add(PERMISSIONS_SERVICE_URI);
        path.addAll(Arrays.asList(resourceUri.split("/")));
    }

    public SinglePermissionsAdapter permissionRecipient(PermissionRecipient recipient, String name) {
        String protocol = recipient.getProtocol();
        this.recipientUri = protocol + ":%2F" + name;
        return this;
    }

    public SinglePermissionsAdapter permissionRecipient(String recipient) {
        this.recipientUri = recipient.replaceAll("/", "%2F");
        return this;
    }

    public OperationResult<RepositoryPermission> get(){
        return buildRequest(RepositoryPermission.class).get();
    }

    public <R> RequestExecution asyncGet(final Callback<OperationResult<RepositoryPermission>, R> callback) {
        final JerseyRequest<RepositoryPermission> request = buildRequest(RepositoryPermission.class);
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<RepositoryPermission> create() {
        return JerseyRequest.buildRequest(sessionStorage, RepositoryPermission.class, new String[]{PERMISSIONS_SERVICE_URI})
                .post(permission);
    }

    public <R> RequestExecution asyncCreate(final Callback<OperationResult, R> callback) {
        final JerseyRequest request = JerseyRequest.buildRequest(sessionStorage, Object.class, new String[]{PERMISSIONS_SERVICE_URI});
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.post(permission));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }


    public OperationResult<RepositoryPermission> createOrUpdate(RepositoryPermission permission) {
        return buildRequest(RepositoryPermission.class).put(permission);
    }

    public <R> RequestExecution asyncCreateOrUpdate(final RepositoryPermission permission, final Callback<OperationResult, R> callback) {
        final JerseyRequest request = buildRequest(Object.class);
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.put(permission));
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
        jerseyRequest.addMatrixParam("recipient", recipientUri);
        return jerseyRequest;
    }

}
