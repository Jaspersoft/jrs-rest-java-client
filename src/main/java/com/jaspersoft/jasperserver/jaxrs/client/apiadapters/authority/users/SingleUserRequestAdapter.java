package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.util.ArrayList;

public class SingleUserRequestAdapter extends AbstractAdapter {

    private ArrayList<String> uri = new ArrayList<String>();

    private ClientUser user;
    /**
     * The field is used for deprecated methods of the class.
     *
     * @deprecated Replaced by {@link SingleUserRequestAdapter#uri}.
     */
    private String userUriPrefix;


    public SingleUserRequestAdapter(SessionStorage sessionStorage, ClientUser user) {
        super(sessionStorage);
        this.user = user;
        if (user.getTenantId() != null && !user.getTenantId().equals("")) {
            uri.add("organizations");
            uri.add(user.getTenantId());
        }
        uri.add("users");
    }

    public OperationResult<ClientUser> get() {
        return buildRequest().get();
    }


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


    public OperationResult<ClientUser> createOrUpdate(ClientUser user) {
        return buildRequest().put(user);
    }


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


    public OperationResult<ClientUser> delete() {
        return buildRequest().delete();
    }

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


    private JerseyRequest<ClientUser> buildRequest() {
        uri.add(user.getUsername());
        return JerseyRequest.buildRequest(sessionStorage,
                ClientUser.class,
                uri.toArray(new String[uri.size()]),
                new DefaultErrorHandler());
    }

    /**
     * @deprecated Replaced by {@link SingleUserRequestAdapter#SingleUserRequestAdapter(SessionStorage, ClientUser)}.
     */
    public SingleUserRequestAdapter(SessionStorage sessionStorage, String organizationId, String username) {
        super(sessionStorage);
        if (organizationId != null) {
            uri.add("organizations");
            uri.add(organizationId);
            uri.add("users");
        } else {
            uri.add("users");
        }
        user = new ClientUser();
        user.setUsername(username);
    }

    /**
     * @deprecated Replaced by {@link SingleUserRequestAdapter#SingleUserRequestAdapter(SessionStorage, ClientUser)}.
     */
    public SingleUserRequestAdapter(SessionStorage sessionStorage, String organizationId) {
        super(sessionStorage);
        if (organizationId != null) {
            uri.add("organizations");
            uri.add(organizationId);
            uri.add("users");
        } else {
            uri.add("users");
        }
    }

    /**
     * @deprecated Replaced by {@link SingleUserRequestAdapter#SingleUserRequestAdapter(SessionStorage, ClientUser)}.
     */
    public SingleUserRequestAdapter(String userId, String organizationId, SessionStorage sessionStorage) {
        super(sessionStorage);
        if (organizationId != null && !organizationId.equals("") && userId != null && !userId.equals("")) {
            uri.add("organizations");
            uri.add(organizationId);
            uri.add("users");
            uri.add(userId);
        } else if (organizationId == null && userId != null && !userId.equals("")) {
            uri.add("users");
            uri.add(userId);
        } else {
            throw new IllegalArgumentException("Wrong parameters has been passed!");
        }
    }

    /**
     * @deprecated Replaced by {@link SingleUserRequestAdapter#get()}.
     */
    public OperationResult<ClientUser> get(String userId) {

        if (!uri.contains("users")) {
            return request().get();
        }
        if (!uri.get(uri.size() - 1).equals(userId)) {
            uri.add(userId);
        }
        return request().get();
    }

    /**
     * @deprecated Replaced by {@link SingleUserRequestAdapter#buildRequest()}.
     */
    private JerseyRequest<ClientUser> request() {
        return JerseyRequest.buildRequest(sessionStorage,
                ClientUser.class,
                uri.toArray(new String[uri.size()]),
                new DefaultErrorHandler());
    }

    /**
     * @deprecated Replaced by {@link SingleUserRequestAdapter#asyncGet(Callback)}.
     */
    public <R> RequestExecution asyncGet(final Callback<OperationResult<ClientUser>, R> callback, String userId) {
        if (uri.contains("users") && !uri.get(uri.size() - 1).equals(userId)) {
            uri.add(userId);
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

    /**
     * @deprecated Replaced by {@link SingleUserRequestAdapter#createOrUpdate(ClientUser)}.
     */
    public OperationResult<ClientUser> updateOrCreate(ClientUser user) {
        uri.add(user.getUsername());
        if ((!uri.toString().contains("organizations")) && (user.getTenantId() != null)) {
            uri.add(0, "organizations");
            uri.add(1,user.getTenantId());
        }
        return request().put(user);
    }

    /**
     * @deprecated Replaced by {@link SingleUserRequestAdapter#asyncCreateOrUpdate(ClientUser, Callback)}.
     */
    public <R> RequestExecution asyncCreateOrUpdate(final ClientUser user, final Callback<OperationResult<ClientUser>, R> callback, final String userId) {
        if (uri.contains("users")) {
            uri.add(userId);
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

    /**
     * @deprecated Replaced by {@link SingleUserRequestAdapter#delete()}.
     */
    public OperationResult delete(String userId) {
        uri.add(userId);
        return request().delete();
    }

    /**
     * @deprecated Replaced by {@link SingleUserRequestAdapter#delete()}.
     */
    public OperationResult delete(ClientUser user) {
        uri.add(user.getUsername());
        return request().delete();
    }

    /**
     * @deprecated Replaced by {@link SingleUserRequestAdapter#asyncDelete(Callback)}.
     */
    public <R> RequestExecution asyncDelete(final Callback<OperationResult<ClientUser>, R> callback, String userId) {
        uri.add(userId);
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

}
