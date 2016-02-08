package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain.metadata.DomainMetadataAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain.newDomain.DomainAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain.schema.DomainSchemaAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class DomainService extends AbstractAdapter {
    private String uri;

    public DomainService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public DomainService forDomain(String uri) {
        if (uri == null) {
            throw new MandatoryParameterNotFoundException("URI must be specified");
        }
        this.uri = uri;
        return this;
    }

    public DomainAdapter domain(String uri) {
        if (uri == null) {
            throw new MandatoryParameterNotFoundException("URI must be specified");
        }
        return new DomainAdapter(sessionStorage, uri);
    }

    public DomainMetadataAdapter metadata() {
        return new DomainMetadataAdapter(sessionStorage, uri);
    }

    public DomainSchemaAdapter schema() {
        return new DomainSchemaAdapter(sessionStorage, uri);
    }
}
