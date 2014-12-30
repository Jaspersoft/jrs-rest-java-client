package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

import java.util.Collection;

/**
 * @author Alex Krasnyanskiy
 * @since 6.0.1
 */
public class ServerAttributesService extends AbstractAdapter {

    public ServerAttributesService(SessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public ServerSingleAttributeAdapter attribute() {
        return new ServerSingleAttributeAdapter(sessionStorage);
    }

    public ServerSingleAttributeAdapter attribute(String attributeName) {
        return new ServerSingleAttributeAdapter(sessionStorage, attributeName);
    }

    public ServerBatchAttributeAdapter attributes() {
        return new ServerBatchAttributeAdapter(sessionStorage);
    }

    public ServerBatchAttributeAdapter attributes(Collection<String> attributesNames) {
        return new ServerBatchAttributeAdapter(sessionStorage, attributesNames);
    }

    public ServerBatchAttributeAdapter attributes(String... attributesNames) {
        return new ServerBatchAttributeAdapter(sessionStorage, attributesNames);
    }
}
