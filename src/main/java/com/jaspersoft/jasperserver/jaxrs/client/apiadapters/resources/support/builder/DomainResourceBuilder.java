/*
 * Copyright © 2014-2019. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.support.builder;

import com.jaspersoft.jasperserver.dto.resources.ClientBundle;
import com.jaspersoft.jasperserver.dto.resources.ClientFile;
import com.jaspersoft.jasperserver.dto.resources.ClientReference;
import com.jaspersoft.jasperserver.dto.resources.ClientSemanticLayerDataSource;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.support.decorator.DomainResourceOperationProcessorDecorator;
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

    public DomainResourceBuilder withSecurityFile(InputStream securityFile, ClientFile securityFileRef) {
        super.multipart.field("securityFile", securityFile, MediaType.APPLICATION_XML_TYPE);
        super.domain.setSecurityFile(securityFileRef);
        return this;
    }

    public DomainResourceBuilder withSecurityFile(String securityFile, ClientFile securityFileRef) {
        super.multipart.field("securityFile", securityFile, MediaType.APPLICATION_XML_TYPE);
        super.domain.setSecurityFile(securityFileRef);
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

    public DomainResourceBuilder withBundles(List<InputStream> bundles, List<ClientBundle> bundlesRef) {
        for (InputStream bundle : bundles) {
            this.multipart.field("bundles.bundle[" + bundleCounter++ + "]", bundle, MediaType.TEXT_PLAIN_TYPE);
        }
        super.domain.setBundles(bundlesRef);
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
