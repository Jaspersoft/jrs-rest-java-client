package com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling;

import org.testng.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.testng.Assert.assertNotNull;

public class JRSExceptionsMappingTest {

    @Test
    public void magic () throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<JRSExceptionsMapping> constructor = JRSExceptionsMapping.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        JRSExceptionsMapping instance = constructor.newInstance();
        assertNotNull(instance);
    }
}