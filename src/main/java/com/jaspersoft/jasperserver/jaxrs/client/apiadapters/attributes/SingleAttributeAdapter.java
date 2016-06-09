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

import com.jaspersoft.jasperserver.dto.authority.ClientAttribute;
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
import java.util.ArrayList;

/**
 * @author Alex Krasnyanskiy
 * @author Tetiana Iefimenko
 * @since 6.0.1-ALPHA
 */

public class SingleAttributeAdapter extends AbstractAdapter {

    private Boolean includePermissions = false;
    private ArrayList<String> path = new ArrayList<String>();

    public SingleAttributeAdapter(String organizationId, String userName, SessionStorage sessionStorage, String attributeName) {
        super(sessionStorage);

        if (!"/".equals(organizationId) && organizationId != null) {
            path.add("organizations");
            path.add(organizationId);
        }
        if (userName != null) {
            path.add("users");
            path.add(userName);
        }
        this.path.add("attributes");
        this.path.add(attributeName);
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

    /**
     * @deprecated Replaced by {@link SingleAttributeAdapter#createOrUpdate(HypermediaAttribute)}.
     */
    public OperationResult<HypermediaAttribute> createOrUpdate(ClientAttribute attribute) {

        return this.createOrUpdate(new HypermediaAttribute(attribute));
    }


    public OperationResult<HypermediaAttribute> createOrUpdate(HypermediaAttribute attribute) {
        JerseyRequest<HypermediaAttribute> request = buildRequest();
        if (includePermissions) {
            request.setContentType(MimeTypeUtil.toCorrectContentMime(sessionStorage.getConfiguration(), "application/hal+{mime}"));
        }
        return request.put(attribute);
    }

    public <R> RequestExecution asyncCreateOrUpdate(final HypermediaAttribute userAttribute,
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

        String[] uri = path.toArray(new String[path.size()]);
        JerseyRequest<HypermediaAttribute> request = JerseyRequest.buildRequest(sessionStorage,HypermediaAttribute.class,
                uri, new DefaultErrorHandler());
        if (includePermissions) {
            request.setAccept(MimeTypeUtil.toCorrectAcceptMime(sessionStorage.getConfiguration(), "application/hal+{mime}"));
            request.addParam("_embedded", "permission");
        }
        return request;
    }
}
