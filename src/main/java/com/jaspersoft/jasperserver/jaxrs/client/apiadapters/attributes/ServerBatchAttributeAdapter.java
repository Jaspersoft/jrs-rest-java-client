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

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.attributes.ServerAttributesListWrapper;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Collection;
import java.util.Collections;

import static java.util.Arrays.asList;

/**
 * @author Alex Krasnyanskiy
 * @since 6.0.1
 */
public class ServerBatchAttributeAdapter extends AbstractAdapter {

    private MultivaluedMap<String, String> params;

    public ServerBatchAttributeAdapter(SessionStorage sessionStorage, Collection<String> attributesNames) {
        super(sessionStorage);
        this.params = new MultivaluedHashMap<String, String>();
        for (String attributeName : attributesNames) {
            params.add("name", attributeName);
        }
    }

    public ServerBatchAttributeAdapter(SessionStorage sessionStorage) {
        this(sessionStorage, Collections.<String>emptyList());
    }

    public ServerBatchAttributeAdapter(SessionStorage sessionStorage, String... attributesNames) {
        this(sessionStorage, asList(attributesNames));
    }

    public OperationResult<ServerAttributesListWrapper> get() {
        return request().addParams(params).get();
    }

    public OperationResult<ServerAttributesListWrapper> delete() {
        return request().addParams(params).delete();
    }

    public OperationResult<ServerAttributesListWrapper> createOrUpdate(ServerAttributesListWrapper attributesListWrapper) {
        return request().put(attributesListWrapper);
    }

    private JerseyRequest<ServerAttributesListWrapper> request() {
        return JerseyRequest.buildRequest(sessionStorage, ServerAttributesListWrapper.class,
                new String[]{"/attributes"}, new DefaultErrorHandler());
    }
}
