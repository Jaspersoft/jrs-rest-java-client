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
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.builder;

import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.dto.resources.ClientReference;
import com.jaspersoft.jasperserver.dto.resources.ClientReferenceableDataSource;
import com.jaspersoft.jasperserver.dto.resources.ClientReferenceableFile;
import com.jaspersoft.jasperserver.dto.resources.ClientSecureMondrianConnection;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.decorator.SecureMondrianConnectionResourceOperationProcessorDecorator;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

import java.io.File;
import java.util.ArrayList;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.List;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;

/**
 * @author Alexander Krasnyanskiy
 */
public class SecureMondrianConnectionResourceBuilder extends SecureMondrianConnectionResourceOperationProcessorDecorator {

    private int schemaCounter = 0;

    public SecureMondrianConnectionResourceBuilder(ClientSecureMondrianConnection entity, SessionStorage storage) {
        super(storage, entity);
    }

    @Deprecated
    public SecureMondrianConnectionResourceBuilder withMondrianSchema(InputStream schema, ClientFile schemaDescriptor) {
        multipart.field("schema", schema, new MediaType("application", "olapMondrianSchema+xml"));
        super.connection.setSchema(schemaDescriptor);
        return this;
    }
    @Deprecated
    public SecureMondrianConnectionResourceBuilder withMondrianSchema(String schema, ClientFile schemaDescriptor) {
        multipart.field("schema", schema, new MediaType("application", "olapMondrianSchema+xml"));
        super.connection.setSchema(schemaDescriptor);
        return this;
    }
    @Deprecated
    public SecureMondrianConnectionResourceBuilder withAccessGrantSchemasAsStream(List<InputStream> schemas, List<ClientReferenceableFile> schemaDescriptors) {
        super.connection.setAccessGrants(schemaDescriptors);
        for (InputStream schema : schemas) {
            multipart.field("accessGrantSchemas.accessGrantSchema[" + schemaCounter + "]", schema, new MediaType("application", "accessGrantSchema+xml"));
        }
        return this;
    }
    @Deprecated
    public SecureMondrianConnectionResourceBuilder withAccessGrantSchemasAsString(List<String> schemas, List<ClientReferenceableFile> schemaDescriptors) {
        super.connection.setAccessGrants(schemaDescriptors);
        for (String schema : schemas) {
            multipart.field("accessGrantSchemas.accessGrantSchema[" + schemaCounter + "]", schema, new MediaType("application", "accessGrantSchema+xml"));
        }
        return this;
    }
    @Deprecated
    public SecureMondrianConnectionResourceBuilder withUri(String uri) {
        super.connection.setUri(uri);
        return this;
    }


    public SecureMondrianConnectionResourceBuilder withMondrianSchema(InputStream schema, String label, String description) {
        StreamDataBodyPart streamDataBodyPart = new StreamDataBodyPart("schema", schema, label, new MediaType("application", "olapMondrianSchema+xml"));
        super.multipart.bodyPart(streamDataBodyPart);
        super.connection.setSchema(new ClientFile().setLabel(label).setDescription(description).setType(ClientFile.FileType.olapMondrianSchema));
        return this;
    }

    public SecureMondrianConnectionResourceBuilder withMondrianSchema(File schema, String label, String description) {
        FileDataBodyPart streamDataBodyPart = new FileDataBodyPart("schema", schema, new MediaType("application", "olapMondrianSchema+xml"));
        super.multipart.bodyPart(streamDataBodyPart);
        super.connection.setSchema(new ClientFile().setLabel(label).setDescription(description).setType(ClientFile.FileType.olapMondrianSchema));
        return this;
    }

    public SecureMondrianConnectionResourceBuilder withMondrianSchema(String schema, String label, String description) {
        multipart.field("schema", schema, new MediaType("application", "olapMondrianSchema+xml"));
        super.connection.setSchema(new ClientFile().setLabel(label).setDescription(description).setType(ClientFile.FileType.olapMondrianSchema));
        return this;
    }

    public SecureMondrianConnectionResourceBuilder withDataSource(ClientReferenceableDataSource dataSource) {
        super.connection.setDataSource(dataSource);
        return this;
    }

    public SecureMondrianConnectionResourceBuilder withDataSource(String dataSourceUri) {
        super.connection.setDataSource(new ClientReference().setUri(dataSourceUri));
        return this;
    }

    public SecureMondrianConnectionResourceBuilder withLabel(String label) {
        super.connection.setLabel(label);
        return this;
    }

    public SecureMondrianConnectionResourceBuilder withDescription(String description) {
        super.connection.setDescription(description);
        return this;
    }

    public SecureMondrianConnectionResourceBuilder withAccessGrantSchema(InputStream schema, String label, String description) {
        StreamDataBodyPart streamDataBodyPart = new StreamDataBodyPart("accessGrantSchemas.accessGrantSchema[" + schemaCounter++ + "]", schema, label, new MediaType("application", "accessGrantSchema+xml"));
        if(super.connection.getAccessGrants() == null)super.connection.setAccessGrants(new ArrayList<ClientReferenceableFile>());
        super.multipart.bodyPart(streamDataBodyPart);
        super.connection.getAccessGrants().add(new ClientFile().setLabel(label).setDescription(description).setType(ClientFile.FileType.accessGrantSchema));
        return this;
    }

    public SecureMondrianConnectionResourceBuilder withAccessGrantSchema(File schema, String label, String description) {
        FileDataBodyPart streamDataBodyPart = new FileDataBodyPart("accessGrantSchemas.accessGrantSchema[" + schemaCounter++ + "]", schema, new MediaType("application", "accessGrantSchema+xml"));
        super.multipart.bodyPart(streamDataBodyPart);
        if(super.connection.getAccessGrants() == null)super.connection.setAccessGrants(new ArrayList<ClientReferenceableFile>());
        super.connection.getAccessGrants().add(new ClientFile().setLabel(label).setDescription(description).setType(ClientFile.FileType.accessGrantSchema));
        return this;
    }

}
