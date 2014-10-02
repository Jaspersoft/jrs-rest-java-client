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

/**
 * @author Alexander Krasnyanskiy
 */
@Deprecated
public class SingleAttributeInterfaceAdapter extends AbstractAdapter{

    private final String uri;
    private final String attributeName;

    public SingleAttributeInterfaceAdapter(SessionStorage sessionStorage, String uri, String attributeName) {
        super(sessionStorage);
        this.uri = uri;
        this.attributeName = attributeName;
    }

    @Deprecated
    public OperationResult<ClientUserAttribute> get() {
        return buildRequest().get();
    }

    @Deprecated
    public <R> RequestExecution asyncGet(final Callback<OperationResult<ClientUserAttribute>, R> callback) {
        final JerseyRequest<ClientUserAttribute> request = buildRequest();
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    @Deprecated
    public OperationResult delete() {
        return buildRequest().delete();
    }

    @Deprecated
    public <R> RequestExecution asyncDelete(final Callback<OperationResult, R> callback) {
        final JerseyRequest request = buildRequest();
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.delete());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    @Deprecated
    public OperationResult createOrUpdate(ClientUserAttribute attribute) {
        return buildRequest().put(attribute);
    }

    @Deprecated
    public <R> RequestExecution asyncCreateOrUpdate(final ClientUserAttribute userAttribute, final Callback<OperationResult<ClientUserAttribute>, R> callback) {
        final JerseyRequest<ClientUserAttribute> request = buildRequest();
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.put(userAttribute));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    synchronized private JerseyRequest<ClientUserAttribute> buildRequest() {
        return JerseyRequest.buildRequest(sessionStorage, ClientUserAttribute.class, new String[]{uri, "/attributes", attributeName}, new DefaultErrorHandler());
    }
}
