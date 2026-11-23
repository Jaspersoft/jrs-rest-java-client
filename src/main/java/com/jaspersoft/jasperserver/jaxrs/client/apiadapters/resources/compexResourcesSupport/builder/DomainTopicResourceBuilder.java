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

import com.jaspersoft.jasperserver.dto.resources.ClientDomainTopic;
import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.decorator.DomainTopicOperationProcessorDecorator;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.MediaTypeUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;

import jakarta.ws.rs.core.MediaType;
import java.io.File;
import java.io.InputStream;

/**
 * @author Alexander Krasnyanskiy
 */
public class DomainTopicResourceBuilder extends DomainTopicOperationProcessorDecorator {
    public DomainTopicResourceBuilder(ClientDomainTopic domainTopic, SessionStorage sessionStorage) {
        super(domainTopic, sessionStorage);
    }

    public DomainTopicResourceBuilder withJrxml(String jrxmlContent) {
        super.multipart.field("jrxml", jrxmlContent, new MediaType("application", "jrxml"));
        return this;
    }

    public DomainTopicResourceBuilder withJrxml(InputStream jrxml, String label) {
        StreamDataBodyPart streamDataBodyPart = new StreamDataBodyPart("jrxml", jrxml, label, new MediaType("application", "jrxml"));
        super.multipart.bodyPart(streamDataBodyPart);
        return this;
    }

    public DomainTopicResourceBuilder withJrxml(File jrxml) {
        FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("jrxml", jrxml, new MediaType("application", "jrxml"));
        super.multipart.bodyPart(fileDataBodyPart);
        return this;
    }

    public DomainTopicResourceBuilder withFile(InputStream fileData, String label, ClientFile.FileType fileType) {
        StreamDataBodyPart streamDataBodyPart = new StreamDataBodyPart("files." + label,
                fileData,
                label,
                MediaTypeUtil.stringToMediaType(fileType.getMimeType()));
        super.multipart.bodyPart(streamDataBodyPart);
        return this;
    }

    public DomainTopicResourceBuilder withFile(File fileData, String label, ClientFile.FileType fileType) {
        FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("files." + label,
                fileData,
                MediaTypeUtil.stringToMediaType(fileType.getMimeType()));
        super.multipart.bodyPart(fileDataBodyPart);
        return this;
    }

    public DomainTopicResourceBuilder withFile(String fileData, String label, ClientFile.FileType fileType) {
        super.multipart.field("file." + label,
                fileData,
                MediaTypeUtil.stringToMediaType(fileType.getMimeType()));
        return this;
    }


}
