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
import com.jaspersoft.jasperserver.dto.resources.ClientMondrianConnection;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.decorator.MondrianConnectionResourceOperationProcessorDecorator;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;

import jakarta.ws.rs.core.MediaType;
import java.io.File;
import java.io.InputStream;

/**
 * @author Alexander Krasnyanskiy
 */
public class MondrianConnectionResourceBuilder extends MondrianConnectionResourceOperationProcessorDecorator {
    public MondrianConnectionResourceBuilder(ClientMondrianConnection entity, SessionStorage storage) {
        super(storage, entity);
    }

    public MondrianConnectionResourceBuilder withMondrianSchema(InputStream schema, String label) {
        StreamDataBodyPart streamDataBodyPart = new StreamDataBodyPart("schema", schema, label, new MediaType("application", "olapMondrianSchema+xml"));
        super.multipart.bodyPart(streamDataBodyPart);
        return this;
    }

    public MondrianConnectionResourceBuilder withMondrianSchema(File schema) {
        FileDataBodyPart streamDataBodyPart = new FileDataBodyPart("schema", schema, new MediaType("application", "olapMondrianSchema+xml"));
        super.multipart.bodyPart(streamDataBodyPart);
        return this;
    }

    public MondrianConnectionResourceBuilder withMondrianSchema(String schema) {
        multipart.field("schema", schema, new MediaType("application", "olapMondrianSchema+xml"));
        return this;
    }

}
