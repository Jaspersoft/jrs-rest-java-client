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
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain.metadata;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.dto.domain.DomainMetaData;

/**
 * @deprecated Replaced by {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.datadiscovery.DomainContextManager}.
 */
public class DomainMetadataAdapter extends AbstractAdapter {
    private final String domainURI;

    public DomainMetadataAdapter(SessionStorage sessionStorage, String domainURI) {
        super(sessionStorage);
        this.domainURI = domainURI;
    }

    public OperationResult<DomainMetaData> retrieve() {
        return JerseyRequest.buildRequest(
                sessionStorage,
                DomainMetaData.class,
                new String[]{new StringBuilder(domainURI).insert(0, "/domains").append("/metadata").toString()},
                new DefaultErrorHandler()
        ).get();
    }

    public String getDomainURI() {
        return domainURI;
    }
}
