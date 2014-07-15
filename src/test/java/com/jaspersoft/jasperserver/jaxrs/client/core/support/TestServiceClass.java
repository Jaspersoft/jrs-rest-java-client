package com.jaspersoft.jasperserver.jaxrs.client.core.support;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;

/**
 * @author Alexander Krasnyanskiy
 */
public class TestServiceClass extends AbstractAdapter {
    private boolean constructorActivityFlag;

    public TestServiceClass(SessionStorage sessionStorage) {
        super(sessionStorage);
        constructorActivityFlag = true;
    }

    public TestServiceClass(SessionStorage storage, Object anotherOneParameter) {
        super(storage);
        throw new RuntimeException("Unsupported constructor invocation!");
    }

    public boolean isConstructorActivityFlag() {
        return constructorActivityFlag;
    }
}
