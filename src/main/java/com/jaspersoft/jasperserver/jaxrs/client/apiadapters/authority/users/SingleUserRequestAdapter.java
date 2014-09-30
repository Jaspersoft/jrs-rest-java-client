package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes.BatchAttributeAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes.BatchAttributeInterfaceAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes.SingleAttributeAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes.SingleAttributeInterfaceAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import static java.util.regex.Pattern.compile;

public class SingleUserRequestAdapter extends AbstractAdapter {

    private String userUriPrefix;
    private StringBuilder uri = new StringBuilder("");

    @Deprecated
    public SingleUserRequestAdapter(SessionStorage sessionStorage, String organizationId, String username) {
        super(sessionStorage);
        if (organizationId != null) {
            userUriPrefix = "/organizations/" + organizationId + "/users/" + username;
        } else {
            userUriPrefix = "/users/" + username;
        }
    }

    /**
     * Use this constructor only if you don't need to use v2/attributes Service in your request.
     */
    public SingleUserRequestAdapter(SessionStorage sessionStorage, String organizationId) {
        super(sessionStorage);
        if (organizationId != null) {
            uri.append("/organizations/").append(organizationId).append("/users/");
        } else {
            uri.append("/users/");
        }
    }

    /**
     * This constructor is used to retrieve resources by attributes specifying. It's interacts
     * with v2/attributes Service.
     */
    public SingleUserRequestAdapter(String userId, String organizationId, SessionStorage sessionStorage) {
        super(sessionStorage);
        if (organizationId != null && !organizationId.equals("") && userId != null && !userId.equals("")) {
            uri.append("/organizations/").append(organizationId).append("/users/").append(userId);
        } else if (organizationId == null && userId != null && !userId.equals("")) {
            uri.append("/users/").append(userId);
        } else {
            throw new IllegalArgumentException("Wrong parameters has been passed!");
        }
    }

    public SingleAttributeAdapter attribute() {
        return new SingleAttributeAdapter(sessionStorage, uri);
    }

    public BatchAttributeAdapter multipleAttributes() {
        return new BatchAttributeAdapter(sessionStorage, uri);
    }

    @Deprecated
    public SingleAttributeInterfaceAdapter attribute(String attributeName) {
        if ("".equals(attributeName) || "/".equals(attributeName)) {
            throw new IllegalArgumentException("'attributeName' mustn't be an empty string");
        }
        return new SingleAttributeInterfaceAdapter(sessionStorage, userUriPrefix, attributeName);
    }

    @Deprecated
    public BatchAttributeInterfaceAdapter attributes() {
        return new BatchAttributeInterfaceAdapter(sessionStorage, userUriPrefix);
    }

    @Deprecated
    public OperationResult<ClientUser> get() {
        return buildRequest().get();
    }

    public OperationResult<ClientUser> get(String userId) {

        /* checks if we already have setted userId */
        if (compile("^.*?users/([^/]+)$").matcher(uri.toString()).find()) {
            return request().get();
        }

        uri.append(userId);
        return request().get();
    }

    @Deprecated
    public <R> RequestExecution asyncGet(final Callback<OperationResult<ClientUser>, R> callback) {
        final JerseyRequest<ClientUser> request = buildRequest();
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public <R> RequestExecution asyncGet(final Callback<OperationResult<ClientUser>, R> callback, String userId) {
        if (!compile("^.*?users/([^/]+)$").matcher(uri.toString()).find()) {
            uri.append(userId);
        }
        final JerseyRequest<ClientUser> request = request();
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
    public OperationResult<ClientUser> createOrUpdate(ClientUser user) {
        return buildRequest().put(user);
    }

    public OperationResult<ClientUser> updateOrCreate(ClientUser user) {
        uri.append(user.getUsername());
        if (!uri.toString().contains("organizations") && user.getTenantId() != null) {
            uri.insert(0, "/organizations/" + user.getTenantId());
        }
        return request().put(user);
    }

    @Deprecated
    public <R> RequestExecution asyncCreateOrUpdate(final ClientUser user, final Callback<OperationResult<ClientUser>, R> callback) {
        final JerseyRequest<ClientUser> request = buildRequest();
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.put(user));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public <R> RequestExecution asyncCreateOrUpdate(final ClientUser user, final Callback<OperationResult<ClientUser>, R> callback, final String userId) {
        if (!compile("^.*?users/([^/]+)$").matcher(uri.toString()).find()) {
            uri.append(userId);
        }
        final JerseyRequest<ClientUser> request = request();
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.put(user));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    @Deprecated
    public OperationResult delete() {
        return buildRequest().delete();
    }

    public OperationResult delete(String userId) {
        uri.append(userId);
        return request().delete();
    }

    public OperationResult delete(ClientUser user) {
        uri.append(user.getUsername());
        return request().delete();
    }

    @Deprecated
    public <R> RequestExecution asyncDelete(final Callback<OperationResult<ClientUser>, R> callback) {
        final JerseyRequest<ClientUser> request = buildRequest();
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.delete());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public <R> RequestExecution asyncDelete(final Callback<OperationResult<ClientUser>, R> callback, String userId) {
        uri.append(userId);
        final JerseyRequest<ClientUser> request = request();
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
    private JerseyRequest<ClientUser> buildRequest() {
        return JerseyRequest.buildRequest(sessionStorage, ClientUser.class, new String[]{userUriPrefix}, new DefaultErrorHandler());
    }

    private JerseyRequest<ClientUser> request() {
        //System.out.println(uri.toString());
        return JerseyRequest.buildRequest(sessionStorage, ClientUser.class, new String[]{uri.toString()}, new DefaultErrorHandler());
    }
}