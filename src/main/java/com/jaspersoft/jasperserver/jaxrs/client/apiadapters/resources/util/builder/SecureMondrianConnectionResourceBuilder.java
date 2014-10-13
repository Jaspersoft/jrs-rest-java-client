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
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.builder;

import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.dto.resources.ClientReferenceableFile;
import com.jaspersoft.jasperserver.dto.resources.ClientSecureMondrianConnection;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.decorator.SecureMondrianConnectionResourceOperationProcessorDecorator;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.List;

/**
 * @author Alexander Krasnyanskiy
 */
public class SecureMondrianConnectionResourceBuilder extends SecureMondrianConnectionResourceOperationProcessorDecorator {
    private int schemaCounter = 0;

    public SecureMondrianConnectionResourceBuilder(ClientSecureMondrianConnection entity, SessionStorage storage) {
        super(storage, entity);
    }

    public SecureMondrianConnectionResourceBuilder withMondrianSchema(InputStream schema, ClientFile schemaDescriptor) {
        super.multipart.field("schema", schema, new MediaType("application", "olapMondrianSchema+xml"));
        super.connection.setSchema(schemaDescriptor);
        return this;
    }

    public SecureMondrianConnectionResourceBuilder withMondrianSchema(String schema, ClientFile schemaDescriptor) {
        super.multipart.field("schema", schema, new MediaType("application", "olapMondrianSchema+xml"));
        super.connection.setSchema(schemaDescriptor);
        return this;
    }

    @Deprecated
    public SecureMondrianConnectionResourceBuilder withMondrianSchema(InputStream schema) {
        super.multipart.field("schema", schema, new MediaType("application", "olapMondrianSchema+xml"));
        return this;
    }

    @Deprecated
    public SecureMondrianConnectionResourceBuilder withMondrianSchema(String schema) {
        super.multipart.field("schema", schema, new MediaType("application", "olapMondrianSchema+xml"));
        return this;
    }

    public SecureMondrianConnectionResourceBuilder withAccessGrantSchemasAsStream(List<InputStream> schemas, List<ClientReferenceableFile> schemaDescriptors) {
        super.connection.setAccessGrants(schemaDescriptors);
        for (InputStream schema : schemas) {
            super.multipart.field("accessGrantSchemas.accessGrantSchema[" + (this.schemaCounter++) + "]", schema, new MediaType("application", "accessGrantSchema+xml"));
        }
        return this;
    }

    public SecureMondrianConnectionResourceBuilder withAccessGrantSchemasAsString(List<String> schemas, List<ClientReferenceableFile> schemaDescriptors) {
        super.connection.setAccessGrants(schemaDescriptors);
        for (String schema : schemas) {
            super.multipart.field("accessGrantSchemas.accessGrantSchema[" + (this.schemaCounter++) + "]", schema, new MediaType("application", "accessGrantSchema+xml"));
        }
        return this;
    }

    @Deprecated
    public SecureMondrianConnectionResourceBuilder withAccessGrantSchemas(List<InputStream> schemas) {
        for (InputStream schema : schemas) {
            super.multipart.field("accessGrantSchemas.accessGrantSchema[" + (this.schemaCounter++) + "]", schema, new MediaType("application", "accessGrantSchema+xml"));
        }
        return this;
    }

    @Deprecated
    public SecureMondrianConnectionResourceBuilder withAccessGrantSchemasAsString(List<String> schemas) {
        for (String schema : schemas) {
            super.multipart.field("accessGrantSchemas.accessGrantSchema[" + (this.schemaCounter++) + "]", schema, new MediaType("application", "accessGrantSchema+xml"));
        }
        return this;
    }

    public SecureMondrianConnectionResourceBuilder withUri(String uri) {
        super.connection.setUri(uri);
        return this;
    }
}
