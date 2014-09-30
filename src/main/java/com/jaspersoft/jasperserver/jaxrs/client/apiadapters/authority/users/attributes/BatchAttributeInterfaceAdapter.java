package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes;

import com.jaspersoft.jasperserver.dto.authority.UserAttributesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.UsersAttributesParameter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

/**
 * @author Alexander Krasnyanskiy
 */
@Deprecated
public class BatchAttributeInterfaceAdapter extends AbstractAdapter {

    private final String uri;
    private MultivaluedMap<String, String> params;

    public BatchAttributeInterfaceAdapter(SessionStorage sessionStorage, String uri) {
        super(sessionStorage);
        this.uri = uri;
        params = new MultivaluedHashMap<String, String>();
    }

    @Deprecated
    public BatchAttributeInterfaceAdapter param(UsersAttributesParameter usersAttributesParam, String value) {
        params.add(usersAttributesParam.getParamName(), value);
        return this;
    }

    @Deprecated
    public OperationResult<UserAttributesListWrapper> get() {
        JerseyRequest<UserAttributesListWrapper> request = buildRequest();
        request.addParams(params);
        return request.get();
    }

    @Deprecated
    public <R> RequestExecution asyncGet(final Callback<OperationResult<UserAttributesListWrapper>, R> callback) {
        final JerseyRequest<UserAttributesListWrapper> request = buildRequest();
        request.addParams(params);
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
    public OperationResult createOrUpdate(UserAttributesListWrapper attributesList) {
        return buildRequest().put(attributesList);
    }

    @Deprecated
    public <R> RequestExecution asyncCreateOrUpdate(final UserAttributesListWrapper attributesList, final Callback<OperationResult<UserAttributesListWrapper>, R> callback) {
        final JerseyRequest<UserAttributesListWrapper> request = buildRequest();
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.put(attributesList));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    @Deprecated
    public OperationResult delete() {
        JerseyRequest<UserAttributesListWrapper> request = buildRequest();
        request.addParams(params);
        return request.delete();
    }

    @Deprecated
    public <R> RequestExecution asyncDelete(final Callback<OperationResult<UserAttributesListWrapper>, R> callback) {
        final JerseyRequest<UserAttributesListWrapper> request = buildRequest();
        request.addParams(params);
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
    private JerseyRequest<UserAttributesListWrapper> buildRequest() {
        return JerseyRequest.buildRequest(sessionStorage, UserAttributesListWrapper.class, new String[]{uri, "/attributes"}, new DefaultErrorHandler());
    }
}
