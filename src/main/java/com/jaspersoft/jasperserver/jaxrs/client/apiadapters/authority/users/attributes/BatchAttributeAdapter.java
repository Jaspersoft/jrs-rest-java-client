package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes;

import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
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
import java.util.List;

public class BatchAttributeAdapter extends AbstractAdapter {

    private MultivaluedMap<String, String> params;
    private StringBuilder uri;

    public BatchAttributeAdapter(SessionStorage sessionStorage, StringBuilder uri) {
        super(sessionStorage);
        this.uri = uri;
        params = new MultivaluedHashMap<String, String>();
        if (sessionStorage == null || uri == null) {
            throw new IllegalArgumentException("Invalid parameters! Cannot be null");
        }
    }

    public BatchAttributeAdapter param(UsersAttributesParameter param, String value) {
        params.add(param.getParamName(), value);
        return this;
    }

    public OperationResult<UserAttributesListWrapper> get() {
        JerseyRequest<UserAttributesListWrapper> request = request();
        request.addParams(params);
        return request.get();
    }

    public <R> RequestExecution asyncGet(final Callback<OperationResult<UserAttributesListWrapper>, R> callback) {
        final JerseyRequest<UserAttributesListWrapper> request = request();
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

    /**
     * Add additional attributes to already available attributes which attached to the user.
     *
     * @param newAttributes additional attributes
     * @return OperationResult
     */
    public OperationResult updateOrCreate(UserAttributesListWrapper newAttributes) {
        List<ClientUserAttribute> retrievedAttributes = request().get().getEntity().getProfileAttributes();
        List<ClientUserAttribute> additionalAttributes = newAttributes.getProfileAttributes();

        for (ClientUserAttribute retrievedAttribute : retrievedAttributes) {
            for (ClientUserAttribute additionalAttribute : additionalAttributes) {
                if (additionalAttribute.getName().equals(retrievedAttribute.getName())) {
                    retrievedAttribute.setValue(additionalAttribute.getValue());
                }
            }
        }

        retrievedAttributes.addAll(additionalAttributes);
        newAttributes.setProfileAttributes(retrievedAttributes);
        return request().put(newAttributes);
    }

    public <R> RequestExecution asyncCreateOrUpdate(final UserAttributesListWrapper attributesList,
                                                    final Callback<OperationResult<UserAttributesListWrapper>, R> callback) {
        final JerseyRequest<UserAttributesListWrapper> request = request();
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.put(attributesList));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult delete() {
        JerseyRequest<UserAttributesListWrapper> request = request();
        request.addParams(params);
        return request.delete();
    }

    public <R> RequestExecution asyncDelete(final Callback<OperationResult<UserAttributesListWrapper>, R> callback) {
        final JerseyRequest<UserAttributesListWrapper> request = request();
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

    private JerseyRequest<UserAttributesListWrapper> request() {
        String u = uri.toString();
        return JerseyRequest
                .buildRequest(
                        sessionStorage,
                        UserAttributesListWrapper.class,
                        new String[]{u, "/attributes"},
                        new DefaultErrorHandler()
                );
    }
}