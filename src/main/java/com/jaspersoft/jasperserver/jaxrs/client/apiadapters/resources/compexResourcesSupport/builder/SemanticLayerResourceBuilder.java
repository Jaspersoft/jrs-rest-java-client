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

import com.jaspersoft.jasperserver.dto.resources.ClientBundle;
import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.dto.resources.ClientSemanticLayerDataSource;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.decorator.SemanticLayerResourceOperationProcessorDecorator;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;

import jakarta.ws.rs.core.MediaType;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Krasnyanskiy
 */
public class SemanticLayerResourceBuilder extends SemanticLayerResourceOperationProcessorDecorator {

    private int bundleCounter = 0;

    public SemanticLayerResourceBuilder(ClientSemanticLayerDataSource domain, SessionStorage sessionStorage) {
        super(sessionStorage, domain);
    }

    public SemanticLayerResourceBuilder withSchema(String schemaContent) {
        super.multipart.field("schema", schemaContent, MediaType.APPLICATION_XML_TYPE);
        return this;
    }

    public SemanticLayerResourceBuilder withSchema(InputStream schema, String label) {
        StreamDataBodyPart streamDataBodyPart = new StreamDataBodyPart("schema", schema, label, MediaType.APPLICATION_XML_TYPE);
        super.multipart.bodyPart(streamDataBodyPart);
        return this;
    }

    public SemanticLayerResourceBuilder withSchema(File schema) {
        FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("schema", schema, MediaType.APPLICATION_XML_TYPE);
        super.multipart.bodyPart(fileDataBodyPart);
        return this;
    }

    public SemanticLayerResourceBuilder withSecurityFile(InputStream securityFile, String label) {
        StreamDataBodyPart streamDataBodyPart = new StreamDataBodyPart("securityFile", securityFile, label, MediaType.APPLICATION_XML_TYPE);
        super.multipart.bodyPart(streamDataBodyPart);
        return this;
    }

    public SemanticLayerResourceBuilder withSecurityFile(File securityFile) {
        FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("securityFile", securityFile, MediaType.APPLICATION_XML_TYPE);
        super.multipart.bodyPart(fileDataBodyPart);
        return this;
    }

    public SemanticLayerResourceBuilder withSecurityFile(String securityFile) {
        super.multipart.field("securityFile", securityFile, MediaType.APPLICATION_XML_TYPE);
        return this;
    }

    public SemanticLayerResourceBuilder withBundle(InputStream bundle, String label) {
        StreamDataBodyPart streamDataBodyPart = new StreamDataBodyPart("bundles.bundle[" + bundleCounter++ + "]", bundle, label, MediaType.TEXT_PLAIN_TYPE);
        super.multipart.bodyPart(streamDataBodyPart);
        return this;
    }

    public SemanticLayerResourceBuilder withBundle(File bundle) {
        FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("bundles.bundle[" + bundleCounter++ + "]", bundle, MediaType.TEXT_PLAIN_TYPE);
        super.multipart.bodyPart(fileDataBodyPart);
        return this;
    }

    public SemanticLayerResourceBuilder withBundle(String bundle) {
        super.multipart.field("bundles.bundle[" + bundleCounter++ + "]", bundle, MediaType.TEXT_PLAIN_TYPE);
        return this;
    }

}
