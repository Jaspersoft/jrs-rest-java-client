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

import com.jaspersoft.jasperserver.dto.resources.domain.ClientDomain;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.decorator.DomainOperationProcessorDecorator;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;

import jakarta.ws.rs.core.MediaType;
import java.io.File;
import java.io.InputStream;

/**
 * @author Alexander Krasnyanskiy
 */
public class DomainResourceBuilder extends DomainOperationProcessorDecorator {

    private int bundleCounter = 0;

    public DomainResourceBuilder(ClientDomain domain, SessionStorage sessionStorage) {
        super(sessionStorage, domain);
    }

    public DomainResourceBuilder withSecurityFile(InputStream securityFile, String label) {
        StreamDataBodyPart streamDataBodyPart = new StreamDataBodyPart("securityFile", securityFile, label,  MediaType.APPLICATION_XML_TYPE);
        super.multipart.bodyPart(streamDataBodyPart);
        return this;
    }

    public DomainResourceBuilder withSecurityFile(File securityFile) {
        FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("securityFile", securityFile,  MediaType.APPLICATION_XML_TYPE);
        super.multipart.bodyPart(fileDataBodyPart);
        return this;
    }

    public DomainResourceBuilder withSecurityFile(String securityFile) {
        super.multipart.field("securityFile", securityFile, MediaType.APPLICATION_XML_TYPE);
        return this;
    }

    public DomainResourceBuilder withBundle(File bundle) {
        FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("bundles.bundle[" + bundleCounter++ + "]", bundle, MediaType.TEXT_PLAIN_TYPE);
        super.multipart.bodyPart(fileDataBodyPart);
        return this;
    }

    public DomainResourceBuilder withBundle(String bundle) {
        super.multipart.field("bundles.bundle[" + bundleCounter++ + "]", bundle, MediaType.TEXT_PLAIN_TYPE);
        return this;
    }

}
