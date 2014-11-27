package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.common;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;

/**
 * @author Alexander Krasnyanskiy
 */
public abstract class ClientServiceFactory<T extends AbstractAdapter> {

    public T service(Class<? extends T> classType) throws IllegalAccessException, InstantiationException {
        return classType.newInstance();
    }

    // Usage example
    // UsersService service = new ClientServiceFactory<UsersService>() {}.service(UsersService.class);
}
