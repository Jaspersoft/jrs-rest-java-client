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

import com.jaspersoft.jasperserver.dto.resources.ClientBundle;
import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.dto.resources.ClientReference;
import com.jaspersoft.jasperserver.dto.resources.ClientSemanticLayerDataSource;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.util.decorator.DomainResourceOperationProcessorDecorator;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Krasnyanskiy
 */
public class DomainResourceBuilder extends DomainResourceOperationProcessorDecorator {

    private int bundleCounter = 0;

    public DomainResourceBuilder(ClientSemanticLayerDataSource domain, SessionStorage sessionStorage) {
        super(sessionStorage, domain);
    }

    @Deprecated
    public DomainResourceBuilder withSchema(InputStream schema) {
        multipart.field("schema", schema, MediaType.APPLICATION_XML_TYPE);
        return this;
    }

    @Deprecated
    public DomainResourceBuilder withSchema(String schema) {
        multipart.field("schema", schema, MediaType.APPLICATION_XML_TYPE);
        return this;
    }

    public DomainResourceBuilder withSchema(String schema, ClientFile schemaRef) {
        super.multipart.field("schema", schema, MediaType.APPLICATION_XML_TYPE);
        super.domain.setSchema(schemaRef);
        return this;
    }

    public DomainResourceBuilder withSchema(InputStream schema, ClientFile schemaRef) {
        super.multipart.field("schema", schema, MediaType.APPLICATION_XML_TYPE);
        super.domain.setSchema(schemaRef);
        return this;
    }

    @Deprecated
    public DomainResourceBuilder withSecurityFile(InputStream securityFile) {
        multipart.field("securityFile", securityFile, MediaType.APPLICATION_XML_TYPE);
        return this;
    }

    public DomainResourceBuilder withSecurityFile(InputStream securityFile, ClientFile securityFileRef) {
        super.multipart.field("securityFile", securityFile, MediaType.APPLICATION_XML_TYPE);
        super.domain.setSecurityFile(securityFileRef);
        return this;
    }

    @Deprecated
    public DomainResourceBuilder withSecurityFile(String securityFile) {
        multipart.field("securityFile", securityFile, MediaType.APPLICATION_XML_TYPE);
        return this;
    }

    public DomainResourceBuilder withSecurityFile(String securityFile, ClientFile securityFileRef) {
        super.multipart.field("securityFile", securityFile, MediaType.APPLICATION_XML_TYPE);
        super.domain.setSecurityFile(securityFileRef);
        return this;
    }

    @Deprecated
    public DomainResourceBuilder withBundle(InputStream bundle) {
        multipart.field("bundles.bundle[" + bundleCounter++ + "]", bundle, MediaType.TEXT_PLAIN_TYPE);
        return this;
    }

    public DomainResourceBuilder withBundle(InputStream bundle, ClientBundle bundleRef) {
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

    public DomainResourceBuilder withBundle(String bundle, ClientBundle bundleRef) {
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
    public DomainResourceBuilder withBundle(String bundle) {
        multipart.field("bundles.bundle[" + bundleCounter++ + "]", bundle, MediaType.TEXT_PLAIN_TYPE);
        return this;
    }

    @Deprecated
    public DomainResourceBuilder withBundles(List<InputStream> bundles) {
        for (InputStream bundle : bundles) {
            this.multipart.field("bundles.bundle[" + bundleCounter++ + "]", bundle, MediaType.TEXT_PLAIN_TYPE);
        }
        return this;
    }

    public DomainResourceBuilder withBundles(List<InputStream> bundles, List<ClientBundle> bundlesRef) {
        for (InputStream bundle : bundles) {
            this.multipart.field("bundles.bundle[" + bundleCounter++ + "]", bundle, MediaType.TEXT_PLAIN_TYPE);
        }
        super.domain.setBundles(bundlesRef);
        return this;
    }

    @Deprecated
    public DomainResourceBuilder withDataSource(String uri) {
        super.domain.setDataSource(new ClientReference().setUri(uri));
        return this;
    }

    public DomainResourceBuilder withDataSource(ClientReference dataSource) {
        super.domain.setDataSource(dataSource);
        return this;
    }

    public DomainResourceBuilder withUri (String uri){
        super.domain.setUri(uri);
        return this;
    }
}
