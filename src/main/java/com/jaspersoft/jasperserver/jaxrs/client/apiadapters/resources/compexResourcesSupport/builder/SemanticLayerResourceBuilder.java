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
import com.jaspersoft.jasperserver.dto.resources.ClientReferenceableDataSource;
import com.jaspersoft.jasperserver.dto.resources.ClientReferenceableFile;
import com.jaspersoft.jasperserver.dto.resources.ClientSemanticLayerDataSource;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.decorator.SemanticLayerResourceOperationProcessorDecorator;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;

/**
 * @author Alexander Krasnyanskiy
 */
public class SemanticLayerResourceBuilder extends SemanticLayerResourceOperationProcessorDecorator {

    private int bundleCounter = 0;

    public SemanticLayerResourceBuilder(ClientSemanticLayerDataSource domain, SessionStorage sessionStorage) {
        super(sessionStorage, domain);
    }

    @Deprecated
    public SemanticLayerResourceBuilder withSchema(String schema, ClientFile schemaRef) {
        super.multipart.field("schema", schema, MediaType.APPLICATION_XML_TYPE);
        super.domain.setSchema(schemaRef);
        return this;
    }

    @Deprecated
    public SemanticLayerResourceBuilder withSchema(InputStream schema, ClientFile schemaRef) {
        super.multipart.field("schema", schema, MediaType.APPLICATION_XML_TYPE);
        super.domain.setSchema(schemaRef);
        return this;
    }

    public SemanticLayerResourceBuilder withSchema(String schemaContent, String label, String description) {
        super.multipart.field("schema", schemaContent, MediaType.APPLICATION_XML_TYPE);
        super.domain.setSchema(new ClientFile().setLabel(label).setDescription(description).setType(ClientFile.FileType.xml));
        return this;
    }

    public SemanticLayerResourceBuilder withSchema(InputStream schema, String label, String description) {
        StreamDataBodyPart streamDataBodyPart = new StreamDataBodyPart("schema", schema, label, MediaType.APPLICATION_XML_TYPE);
        super.domain.setSchema(new ClientFile().setLabel(label).setDescription(description).setType(ClientFile.FileType.xml));
        super.multipart.bodyPart(streamDataBodyPart);
        return this;
    }

    public SemanticLayerResourceBuilder withSchema(File schema, String label, String description) {
        FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("schema", schema, MediaType.APPLICATION_XML_TYPE);
        super.multipart.bodyPart(fileDataBodyPart);
        super.domain.setSchema(new ClientFile().setLabel(label).setDescription(description).setType(ClientFile.FileType.xml));
        return this;
    }

    public SemanticLayerResourceBuilder withSchema(ClientReferenceableFile schemaUri) {
        this.domain.setSchema(schemaUri);
        return this;
    }


    @Deprecated
    public SemanticLayerResourceBuilder withSecurityFile(InputStream securityFile, ClientFile securityFileRef) {
        super.multipart.field("securityFile", securityFile, MediaType.APPLICATION_XML_TYPE);
        super.domain.setSecurityFile(securityFileRef);
        return this;
    }

    @Deprecated
    public SemanticLayerResourceBuilder withSecurityFile(String securityFile, ClientFile securityFileRef) {
        super.multipart.field("securityFile", securityFile, MediaType.APPLICATION_XML_TYPE);
        super.domain.setSecurityFile(securityFileRef);
        return this;
    }

    public SemanticLayerResourceBuilder withSecurityFile(InputStream securityFile, String label, String description) {
        StreamDataBodyPart streamDataBodyPart = new StreamDataBodyPart("securityFile", securityFile, label,  MediaType.APPLICATION_XML_TYPE);
        super.multipart.bodyPart(streamDataBodyPart);
        super.domain.setSecurityFile(new ClientFile().setLabel(label).setDescription(description).setType(ClientFile.FileType.xml));
        return this;
    }

    public SemanticLayerResourceBuilder withSecurityFile(File securityFile, String label, String description) {
        FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("securityFile", securityFile,  MediaType.APPLICATION_XML_TYPE);
        super.multipart.bodyPart(fileDataBodyPart);
        super.domain.setSecurityFile(new ClientFile().setLabel(label).setDescription(description).setType(ClientFile.FileType.xml));
        return this;
    }

    public SemanticLayerResourceBuilder withSecurityFile(String securityFile, String label, String description) {
        super.multipart.field("securityFile", securityFile, MediaType.APPLICATION_XML_TYPE);
        super.domain.setSecurityFile(new ClientFile().setLabel(label).setDescription(description).setType(ClientFile.FileType.xml));
        return this;
    }

    public SemanticLayerResourceBuilder withSecurityFile(ClientReferenceableFile securityFile) {
        this.domain.setSecurityFile(securityFile);
        return this;
    }

    @Deprecated
    public SemanticLayerResourceBuilder withBundle(InputStream bundle, ClientBundle bundleRef) {
        super.multipart.field("bundles.bundle[" + bundleCounter++ + "]", bundle, MediaType.TEXT_PLAIN_TYPE);
        List<ClientBundle> bundles = super.domain.getBundles();

        if (bundles != null) {
            int index = bundles.indexOf(bundleRef);
            if (index >= 0) {
                bundles.set(index, bundleRef);
            } else {
                bundles.add(bundleRef);
            }
        } else {
            bundles = new ArrayList<ClientBundle>();
            bundles.add(bundleRef);
        }

        super.domain.setBundles(bundles);
        return this;
    }

    @Deprecated
    public SemanticLayerResourceBuilder withBundle(String bundle, ClientBundle bundleRef) {
        super.multipart.field("bundles.bundle[" + bundleCounter++ + "]", bundle, MediaType.TEXT_PLAIN_TYPE);
        return this;
    }

    public SemanticLayerResourceBuilder withBundle(InputStream bundle, String label, String description) {
        StreamDataBodyPart streamDataBodyPart = new StreamDataBodyPart("bundles.bundle[" + bundleCounter++ + "]", bundle, label,  MediaType.TEXT_PLAIN_TYPE);
        super.multipart.bodyPart(streamDataBodyPart);
        if (domain.getBundles() == null)domain.setBundles(new ArrayList<ClientBundle>());
        super.domain.getBundles().add(new ClientBundle().setFile(new ClientFile().setLabel(label).setDescription(description).setType(ClientFile.FileType.prop)));
        return this;
    }

    public SemanticLayerResourceBuilder withBundle(File bundle, String label, String description) {
        FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("bundles.bundle[" + bundleCounter++ + "]", bundle, MediaType.TEXT_PLAIN_TYPE);
        super.multipart.bodyPart(fileDataBodyPart);
        if (domain.getBundles() == null)domain.setBundles(new ArrayList<ClientBundle>());
        super.domain.getBundles().add(new ClientBundle().setFile(new ClientFile().setLabel(label).setDescription(description).setType(ClientFile.FileType.prop)));
        return this;
    }

    public SemanticLayerResourceBuilder withBundle(String bundle, String label, String description) {
        super.multipart.field("bundles.bundle[" + bundleCounter++ + "]", bundle, MediaType.TEXT_PLAIN_TYPE);
        if (domain.getBundles() == null)domain.setBundles(new ArrayList<ClientBundle>());
        super.domain.getBundles().add(new ClientBundle().setFile(new ClientFile().setLabel(label).setDescription(description).setType(ClientFile.FileType.prop)));
        return this;
    }

    public SemanticLayerResourceBuilder withBundle(ClientBundle bundle) {
        if (domain.getBundles() == null) domain.setBundles(new ArrayList<ClientBundle>());
        this.domain.getBundles().add(bundle);
        return this;
    }

    public SemanticLayerResourceBuilder withBundles(List<ClientBundle> bundles) {
        if (domain.getBundles() == null) {
            domain.setBundles(bundles);
        } else {
            this.domain.getBundles().addAll(bundles);
        }
        return this;
    }

    public SemanticLayerResourceBuilder withDataSource(ClientReferenceableDataSource dataSource) {
        super.domain.setDataSource(dataSource);
        return this;
    }

    public SemanticLayerResourceBuilder withLabel(String label) {
        super.domain.setLabel(label);
        return this;
    }

    public SemanticLayerResourceBuilder withDescription(String description) {
        super.domain.setDescription(description);
        return this;
    }

    @Deprecated
    public SemanticLayerResourceBuilder withUri(String uri) {
        super.domain.setUri(uri);
        return this;
    }

}
