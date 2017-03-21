package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.datadiscovery;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class DataDiscoveryService extends AbstractAdapter {

    public DataDiscoveryService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public  DomainContextManager domainContext() {
        return new DomainContextManager(sessionStorage);
    }

    public TopicContextManager topicContext() {
        return new TopicContextManager(sessionStorage);
    }

    public DomElContextManager domElContext() {
        return new DomElContextManager(sessionStorage);
    }

    public DerivedTableContextManager derivedTableContext() {
        return new DerivedTableContextManager(sessionStorage);
    }

}
