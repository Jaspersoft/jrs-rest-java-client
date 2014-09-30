package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes;

import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

public class SingleAttributeAdapter extends AbstractAdapter {

    private String attributeName;
    private final SessionStorage sessionStorage;
    private StringBuilder uri;

    public SingleAttributeAdapter(final SessionStorage sessionStorage, final StringBuilder uri) {
        super(sessionStorage);
        if (sessionStorage == null || uri == null) {
            throw new IllegalArgumentException("URI cannot be null.");
        }
        this.sessionStorage = sessionStorage;
        this.uri = uri;
    }

    public OperationResult<ClientUserAttribute> get(final String attributeName) {
        this.attributeName = attributeName;
        return request().get();
    }

    public <R> RequestExecution asyncGet(final Callback<OperationResult<ClientUserAttribute>, R> callback, final String attributeName) {
        this.attributeName = attributeName;
        final JerseyRequest<ClientUserAttribute> request = request();
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult delete(String attributeName) {
        this.attributeName = attributeName;
        return request().delete();
    }

    public <R> RequestExecution asyncDelete(final Callback<OperationResult, R> callback, String attributeName) {
        this.attributeName = attributeName;
        final JerseyRequest request = request();
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.delete());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult updateOrCreate(ClientUserAttribute attribute) {
        attributeName = attribute.getName();
        return request().put(attribute);
    }

    public <R> RequestExecution asyncUpdateOrCreate(final ClientUserAttribute userAttribute,
                                                    final Callback<OperationResult<ClientUserAttribute>, R> callback,
                                                    final String attributeName) {
        this.attributeName = attributeName;
        final JerseyRequest<ClientUserAttribute> request = request();
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.put(userAttribute));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    private JerseyRequest<ClientUserAttribute> request() {
        return JerseyRequest.buildRequest(sessionStorage, ClientUserAttribute.class, new String[]{uri.toString(), "/attributes", attributeName}, new DefaultErrorHandler());
    }
}
