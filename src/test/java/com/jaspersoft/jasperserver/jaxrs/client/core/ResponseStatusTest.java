package com.jaspersoft.jasperserver.jaxrs.client.core;

import com.jaspersoft.jasperserver.jaxrs.client.core.enums.ResponseStatus;
import org.testng.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.testng.Assert.assertNotNull;

public class ResponseStatusTest {

    @Test
    public void magic() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {

        Constructor<ResponseStatus> constructor = ResponseStatus.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        ResponseStatus instance = constructor.newInstance();

        assertNotNull(instance);
    }

}