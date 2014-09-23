package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.dto.authority.UserAttributesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes.BatchAttributeAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes.SingleAttributeAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

public class SingleUserRequestAdapter extends AbstractAdapter {

    private String userUriPrefix;

    private StringBuilder uri = new StringBuilder();

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
     * Use this constructor when you don't need inFolder use v2/attributes Service in your request.
     *
     * @param sessionStorage
     * @param organizationId
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
     * This constructor is used inFolder retrieve resources by attributes specifying. It's interacts
     * with v2/attributes Service.
     *
     * @param userId
     * @param organizationId
     * @param sessionStorage
     */
    public SingleUserRequestAdapter(String userId, String organizationId, SessionStorage sessionStorage) {
        super(sessionStorage);
        if (organizationId != null && !organizationId.equals("")
                && userId != null && !userId.equals("")) {
            uri.append("/organizations/").append(organizationId).append("/users/").append(userId);
        } else if (organizationId == null && userId != null
                && !userId.equals("")){
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
        return new SingleAttributeInterfaceAdapter(attributeName);
    }

    @Deprecated
    public BatchAttributeInterfaceAdapter attributes() {
        return new BatchAttributeInterfaceAdapter();
    }

    @Deprecated
    public OperationResult<ClientUser> get() {
        return buildRequest().get();
    }

    public OperationResult<ClientUser> get(String userId) {
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
        uri.append(userId);
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
        if (!uri.toString().contains("organizations") && user.getTenantId() != null){
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

    public <R> RequestExecution asyncCreateOrUpdate(final ClientUser user, final Callback<OperationResult<ClientUser>, R> callback, String userId) {
        uri.append(userId);
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
        return JerseyRequest.buildRequest(sessionStorage, ClientUser.class, new String[]{uri.toString()}, new DefaultErrorHandler());
    }

    @Deprecated
    public class SingleAttributeInterfaceAdapter {
        private final String attributeName;

        public SingleAttributeInterfaceAdapter(String attributeName) {
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

        private JerseyRequest<ClientUserAttribute> buildRequest() {
            return JerseyRequest.buildRequest(sessionStorage, ClientUserAttribute.class, new String[]{userUriPrefix, "/attributes", attributeName}, new DefaultErrorHandler());
        }
    }

    @Deprecated
    public class BatchAttributeInterfaceAdapter {
        private MultivaluedMap<String, String> params;

        public BatchAttributeInterfaceAdapter() {
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
            return JerseyRequest.buildRequest(sessionStorage, UserAttributesListWrapper.class, new String[]{userUriPrefix, "/attributes"}, new DefaultErrorHandler());
        }
    }
}