package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.permissions;

import org.testng.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.testng.Assert.assertNotNull;

public class PermissionMaskTest {

    @Test
    public void should_1() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Constructor<PermissionMask> constructor = PermissionMask.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        PermissionMask instance = constructor.newInstance();

        assertNotNull(instance);

    }
}