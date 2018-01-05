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
import com.jaspersoft.jasperserver.dto.resources.ClientReference;
import com.jaspersoft.jasperserver.dto.resources.ClientReferenceableDataSource;
import com.jaspersoft.jasperserver.dto.resources.ClientReferenceableFile;
import com.jaspersoft.jasperserver.dto.resources.domain.ClientDomain;
import com.jaspersoft.jasperserver.dto.resources.domain.Schema;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.decorator.DomainOperationProcessorDecorator;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.ResourceUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import java.io.File;
import java.io.InputStream;
import java.util.Locale;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;

/**
 * @author Alexander Krasnyanskiy
 */
public class DomainResourceBuilder extends DomainOperationProcessorDecorator {

    private int bundleCounter = 0;

    public DomainResourceBuilder(ClientDomain domain, SessionStorage sessionStorage) {
        super(sessionStorage, domain);
    }

    public DomainResourceBuilder withSchema(Schema  schema) {
        super.domain.setSchema(schema);
        return this;
    }

    public DomainResourceBuilder withSecurityFile(InputStream securityFile, String label, String description) {
        StreamDataBodyPart streamDataBodyPart = new StreamDataBodyPart("securityFile", securityFile, label,  MediaType.APPLICATION_XML_TYPE);
        super.multipart.bodyPart(streamDataBodyPart);
        super.domain.setSecurityFile(new ClientFile().setLabel(label).setDescription(description).setType(ClientFile.FileType.xml));
        return this;
    }

    public DomainResourceBuilder withSecurityFile(File securityFile, String label, String description) {
        FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("securityFile", securityFile,  MediaType.APPLICATION_XML_TYPE);
        super.multipart.bodyPart(fileDataBodyPart);
        super.domain.setSecurityFile(new ClientFile().setLabel(label).setDescription(description).setType(ClientFile.FileType.xml));
        return this;
    }

    public DomainResourceBuilder withSecurityFile(String securityFile, String label, String description) {
        super.multipart.field("securityFile", securityFile, MediaType.APPLICATION_XML_TYPE);
        super.domain.setSecurityFile(new ClientFile().setLabel(label).setDescription(description).setType(ClientFile.FileType.xml));
        return this;
    }

    public DomainResourceBuilder withSecurityFile(ClientReferenceableFile securityFileUri) {
        this.domain.setSecurityFile(securityFileUri);
        return this;
    }

    public DomainResourceBuilder withSecurityFileBase64Encoded(String securityFileContent) {
        this.domain.setSecurityFile(new ClientFile().setContent(ResourceUtil.toBase64EncodedContent(securityFileContent)).setType(ClientFile.FileType.xml));
        return this;
    }

    public DomainResourceBuilder withBundle(File bundle, String label, String description) {
        FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("bundles.bundle[" + bundleCounter++ + "]", bundle, MediaType.TEXT_PLAIN_TYPE);
        super.multipart.bodyPart(fileDataBodyPart);
        super.domain.getBundles().add(new ClientBundle().setFile(new ClientFile().setLabel(label).setDescription(description).setType(ClientFile.FileType.prop)));
        return this;
    }

    public DomainResourceBuilder withBundle(String bundle, String label, String description) {
        super.multipart.field("bundles.bundle[" + bundleCounter++ + "]", bundle, MediaType.TEXT_PLAIN_TYPE);
        super.domain.getBundles().add(new ClientBundle().setFile(new ClientFile().setLabel(label).setDescription(description).setType(ClientFile.FileType.prop)));
        return this;
    }

    public DomainResourceBuilder withBundle(ClientBundle bundle) {
        this.domain.getBundles().add(bundle);
        return this;
    }

    public DomainResourceBuilder withBundleBase64Encoded(String bundleContent, Locale locale) {
        this.domain.getBundles().add(new ClientBundle().
                setLocale(locale.toString()).
                setFile(new ClientFile().
                        setContent(ResourceUtil.toBase64EncodedContent(bundleContent))));
        return this;
    }

    public DomainResourceBuilder withDataSource(ClientReferenceableDataSource dataSource) {
        super.domain.setDataSource(dataSource);
        return this;
    }

    public DomainResourceBuilder withDataSource(String dataSourceUri) {
        super.domain.setDataSource(new ClientReference().setUri(dataSourceUri));
        return this;
    }

    public DomainResourceBuilder withLabel(String label) {
        super.domain.setLabel(label);
        return this;
    }

    public DomainResourceBuilder withDescription(String description) {
        super.domain.setDescription(description);
        return this;
    }

}
