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
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeTypeUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
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

    public BatchAttributeAdapter(SessionStorage sessionStorage, String holderUri) {
        super(sessionStorage);
        this.holderUri = holderUri;
    }

    public BatchAttributeAdapter(SessionStorage sessionStorage, String holderUri, String... attributesNames) {
        this(sessionStorage, holderUri, asList(attributesNames));
    }

    public BatchAttributeAdapter(SessionStorage sessionStorage,  String holderUri, Collection<String> attributesNames) {
        this(sessionStorage, holderUri);
        for (String attributeName : attributesNames) {
            params.add("name", attributeName);
        }
    }

    public BatchAttributeAdapter setIncludePermissions(Boolean includePermissions) {
        this.includePermissions = includePermissions;
        return this;
    }

    public OperationResult<HypermediaAttributesListWrapper> get() {
        return request().addParams(params).get();
    }

    public OperationResult<HypermediaAttributesListWrapper> delete() {
        return request().addParams(params).delete();
    }

    public OperationResult<HypermediaAttributesListWrapper> createOrUpdate(HypermediaAttributesListWrapper attributesListWrapper) {
        return request().put(attributesListWrapper);
    }

    private JerseyRequest<HypermediaAttributesListWrapper> request() {
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
