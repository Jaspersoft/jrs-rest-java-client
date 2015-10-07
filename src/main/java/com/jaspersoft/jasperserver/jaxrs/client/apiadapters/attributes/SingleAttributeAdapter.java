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

import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.dto.authority.hypermedia.HypermediaAttribute;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeTypeUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;

/**
 * @author Alex Krasnyanskiy
 * @author Tetiana Iefimenko
 * @since 6.0.1-ALPHA
 */

public class SingleAttributeAdapter extends AbstractAdapter {

    private String attributeName;
    private Boolean includePermissions = false;
    private String holderUri;

    public SingleAttributeAdapter(String holderUri, SessionStorage sessionStorage, String attributeName) {
        super(sessionStorage);
        if (sessionStorage == null || holderUri == null) {
            throw new IllegalArgumentException("URI cannot be null.");
        }
        this.holderUri = holderUri;
        this.attributeName = attributeName;
    }

    public SingleAttributeAdapter setIncludePermissions(Boolean includePermissions) {
        this.includePermissions = includePermissions;
        return this;
    }

    public OperationResult<HypermediaAttribute> get() {
        return buildRequest().get();
    }


    public <R> RequestExecution asyncGet(final Callback<OperationResult<HypermediaAttribute>, R> callback) {
               final JerseyRequest<HypermediaAttribute> request = buildRequest();
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.get());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }


    public OperationResult<HypermediaAttribute> delete() {
        return buildRequest().delete();
    }


    public <R> RequestExecution asyncDelete(final Callback<OperationResult<HypermediaAttribute>, R> callback) {

        final JerseyRequest<HypermediaAttribute> request = buildRequest();
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.delete());
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    public OperationResult<HypermediaAttribute> createOrUpdate(ClientUserAttribute attribute) {
        return buildRequest().put(attribute);
    }

    public <R> RequestExecution asyncCreateOrUpdate(final ClientUserAttribute userAttribute,
                                                    final Callback<OperationResult<HypermediaAttribute>, R> callback) {
        final JerseyRequest<HypermediaAttribute> request = buildRequest();
        RequestExecution task = new RequestExecution(new Runnable() {
            @Override
            public void run() {
                callback.execute(request.put(userAttribute));
            }
        });
        ThreadPoolUtil.runAsynchronously(task);
        return task;
    }

    private JerseyRequest<HypermediaAttribute> buildRequest() {

        JerseyRequest<HypermediaAttribute> request = JerseyRequest.buildRequest(sessionStorage,HypermediaAttribute.class,
                new String[]{holderUri,"attributes/",attributeName}, new DefaultErrorHandler());
        if (includePermissions) {
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(),"application/hal+{mime}"));
            request.addParam("_embedded", "permission");
        }
        return request;
    }
}
