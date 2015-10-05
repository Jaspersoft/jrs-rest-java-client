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

import com.jaspersoft.jasperserver.dto.authority.hypermedia.HypermediaAttributesListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeTypeUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.util.Collection;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import static java.util.Arrays.asList;

/**
 * @author Alex Krasnyanskiy
 * @author Tetiana Iefimenko
 * @since 6.0.1-ALPHA
 */
public class BatchAttributeAdapter extends AbstractAdapter {

    private MultivaluedMap<String, String> params = new MultivaluedHashMap<String, String>();
    private Boolean includePermissions = false;
    private String holderUri;

    public BatchAttributeAdapter(String holderUri, SessionStorage sessionStorage) {
        super(sessionStorage);
        this.holderUri = holderUri;
    }

    public BatchAttributeAdapter(String holderUri, SessionStorage sessionStorage, String... attributesNames) {
        this(holderUri, sessionStorage, asList(attributesNames));
    }

    public BatchAttributeAdapter(String holderUri, SessionStorage sessionStorage, Collection<String> attributesNames) {
        this(holderUri, sessionStorage);
        for (String attributeName : attributesNames) {
            params.add("name", attributeName);
        }
    }

    public BatchAttributeAdapter setIncludePermissions(Boolean includePermissions) {
        this.includePermissions = includePermissions;
        return this;
    }


    public OperationResult<HypermediaAttributesListWrapper> get() {
        return buildRequest().addParams(params).get();
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
        return buildRequest().addParams(params).delete();
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
        return buildRequest().put(attributesListWrapper);
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
        JerseyRequest request = JerseyRequest.buildRequest(
                sessionStorage,
                HypermediaAttributesListWrapper.class,
                new String[]{holderUri, "/attributes"}, new DefaultErrorHandler());
        if (includePermissions) {
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/hal+{mime}")).addParam("_embedded", "permission");
        }
        return request;
    }
}
