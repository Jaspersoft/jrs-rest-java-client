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
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes;

import com.jaspersoft.jasperserver.dto.authority.hypermedia.HypermediaAttribute;
import com.jaspersoft.jasperserver.dto.authority.hypermedia.HypermediaAttributesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeTypeUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import static java.util.Arrays.asList;

/**
 * @author Alex Krasnyanskiy
 * @author Tetiana Iefimenko
 * @since 6.0.1-ALPHA
 */
public class BatchAttributeAdapter extends AbstractAdapter {

    public static final String SERVICE_URI = "attributes";
    private static final String SEPARATOR = "/";
    private MultivaluedMap<String, String> params = new MultivaluedHashMap<String, String>();
    private Boolean includePermissions = false;
    private String userName;
    private String organizationId;
    private ArrayList<String> path = new ArrayList<String>();

    public BatchAttributeAdapter(String organizationId, String userName, SessionStorage sessionStorage) {
        super(sessionStorage);

        if (!"/".equals(organizationId) && organizationId != null) {
            path.add("organizations");
            path.add(organizationId);
        }
        if (userName != null) {
            path.add("users");
            path.add(userName);
        }
        this.organizationId = organizationId;
        this.userName = userName;
    }

    public BatchAttributeAdapter(String organizationId, String userName, SessionStorage sessionStorage, String... attributesNames) {
        this(organizationId, userName, sessionStorage, asList(attributesNames));
    }

    public BatchAttributeAdapter(String organizationId, String userName, SessionStorage sessionStorage, Collection<String> attributesNames) {
        this(organizationId, userName, sessionStorage);
        for (String attributeName : attributesNames) {
            if (attributeName.equals("")) {
                throw new IllegalArgumentException("Names of attributes are not valid.");
            }
            params.add("name", attributeName);
        }
    }

    public BatchAttributeAdapter parameter(AttributesSearchParameter parameter, String value) {
        if (parameter.equals(AttributesSearchParameter.HOLDER)) {
            String prefix;
            if (value.contains(SEPARATOR)) {
                if (value.equals(SEPARATOR)) {
                    prefix = "tenant:";
                } else {
                    prefix = "user:/";
                }
            } else {
                prefix = "tenant:/";
            }
            value = prefix + value;
        }
        params.add(parameter.getName(), value);
        return this;
    }

    public BatchAttributeAdapter parameter(AttributesSearchParameter parameter, boolean value) {
        return this.parameter(parameter, String.valueOf(value));
    }

    public BatchAttributeAdapter parameter(AttributesSearchParameter parameter, Integer value) {
        return this.parameter(parameter, value.toString());
    }

    public BatchAttributeAdapter parameter(AttributesSearchParameter parameter, AttributesGroupParameter value) {
        return this.parameter(parameter, value.getName());
    }

    public BatchAttributeAdapter setIncludePermissions(Boolean includePermissions) {
        this.includePermissions = includePermissions;
        return this;
    }


    public OperationResult<HypermediaAttributesListWrapper> get() {
        return buildRequest().get();
    }

    public OperationResult<HypermediaAttributesListWrapper> search() {
        JerseyRequest<HypermediaAttributesListWrapper> jerseyRequest = buildSearchRequest();
        return jerseyRequest.get();
    }

    public <R> RequestExecution asyncGet(final Callback<OperationResult<HypermediaAttributesListWrapper>, R> callback) {
        final JerseyRequest<HypermediaAttributesListWrapper> request = buildRequest();
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

    public OperationResult<HypermediaAttributesListWrapper> delete() {
        return buildRequest().delete();
    }

    public <R> RequestExecution asyncDelete(final Callback<OperationResult<HypermediaAttributesListWrapper>, R> callback) {
        final JerseyRequest<HypermediaAttributesListWrapper> request = buildRequest();
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

    public OperationResult<HypermediaAttributesListWrapper> createOrUpdate(HypermediaAttributesListWrapper attributesListWrapper) {
        if (attributesListWrapper == null) {
            throw new MandatoryParameterNotFoundException("Attributes are required");
        }
        if (params.get("name") != null && params.get("name").size() != 0) {
            LinkedList<HypermediaAttribute> list = new LinkedList<HypermediaAttribute>(attributesListWrapper.getProfileAttributes());
            for (Iterator<HypermediaAttribute> iterator = list.iterator(); iterator.hasNext(); ) {
                HypermediaAttribute current = iterator.next();
                if (!params.get("name").contains(current.getName())) {
                    iterator.remove();
                }
            }
            attributesListWrapper.setProfileAttributes(list);
        }
        return buildRequest()
                .setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), "application/hal+{mime}"))
                .put(attributesListWrapper);
    }

    public <R> RequestExecution asyncCreateOrUpdate(final HypermediaAttributesListWrapper attributesList,
                                                    final Callback<OperationResult<HypermediaAttributesListWrapper>, R> callback) {
        final JerseyRequest<HypermediaAttributesListWrapper> request = buildRequest();
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.put(attributesList));
            }
        });

        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    private JerseyRequest<HypermediaAttributesListWrapper> buildRequest() {
        path.add(SERVICE_URI);
        JerseyRequest<HypermediaAttributesListWrapper> request = JerseyRequest.buildRequest(
                sessionStorage,
                HypermediaAttributesListWrapper.class,
                path.toArray(new String[path.size()]), new DefaultErrorHandler());
        if (includePermissions) {
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/hal+{mime}"));
            request.addParam("_embedded", "permission");
        }
        request.addParams(params);
        return request;
    }

    private JerseyRequest<HypermediaAttributesListWrapper> buildSearchRequest() {
        JerseyRequest<HypermediaAttributesListWrapper> request = JerseyRequest.buildRequest(
                sessionStorage,
                HypermediaAttributesListWrapper.class,
                new String[]{SERVICE_URI}, new DefaultErrorHandler());
        if (includePermissions) {
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/attributes.collection.hal+{mime}"));
            request.addParam("_embedded", "permission");
        } else {
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/attributes.collection+{mime}"));
        }
        request.addParams(params);

        // if user did not specified holder make it form organizationId and userName variables

        if (!params.containsKey("holder") && (organizationId != null || userName != null)) {
            StringBuilder holderId = new StringBuilder();
            holderId.append((userName == null) ? "tenant:" : "user:");
            if (organizationId != null) {
                holderId.append(SEPARATOR);
                if (!SEPARATOR.equals(organizationId)) holderId.append(organizationId);
            }
            if (userName != null) {
                holderId.append(SEPARATOR);
                holderId.append(userName);
            }
            ;
            request.addParam("holder", holderId.toString());
        }
        return request;
    }
}
