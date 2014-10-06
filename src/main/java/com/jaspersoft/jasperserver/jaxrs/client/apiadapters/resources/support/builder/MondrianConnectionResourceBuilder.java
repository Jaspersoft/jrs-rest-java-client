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
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.support.builder;

import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.dto.resources.ClientMondrianConnection;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.support.decorator.MondrianConnectionResourceOperationProcessorDecorator;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

import javax.ws.rs.core.MediaType;
import java.io.InputStream;

/**
 * @author Alexander Krasnyanskiy
 */
public class MondrianConnectionResourceBuilder extends MondrianConnectionResourceOperationProcessorDecorator {
    public MondrianConnectionResourceBuilder(ClientMondrianConnection entity, SessionStorage storage) {
        super(entity, storage);
    }

    public MondrianConnectionResourceBuilder withMondrianSchema(InputStream schema, ClientFile schemaRef) {
        super.multipart.field("schema", schema, new MediaType("application", "olapMondrianSchema+xml"));
        super.connection.setSchema(schemaRef);
        return this;
    }

    @Deprecated
    public MondrianConnectionResourceBuilder withMondrianSchema(InputStream schema) {
        super.multipart.field("schema", schema, new MediaType("application", "olapMondrianSchema+xml"));
        return this;
    }

    public MondrianConnectionResourceBuilder withUri(String uri) {
        super.connection.setUri(uri);
        return this;
    }
}
