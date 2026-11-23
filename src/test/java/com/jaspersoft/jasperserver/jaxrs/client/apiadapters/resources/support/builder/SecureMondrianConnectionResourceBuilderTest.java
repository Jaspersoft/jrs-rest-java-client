package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.support.builder;

import com.jaspersoft.jasperserver.dto.resources.ClientSecureMondrianConnection;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.builder.SecureMondrianConnectionResourceBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.compexResourcesSupport.processor.CommonOperationProcessorImpl;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link SecureMondrianConnectionResourceBuilder}
 */
public class SecureMondrianConnectionResourceBuilderTest {

    @Test
    public void should_pass_params_to_parent_class() throws Exception {
        SessionStorage storageMock = Mockito.mock(SessionStorage.class);
        ClientSecureMondrianConnection dummyMondrianConnection = new ClientSecureMondrianConnection();

        SecureMondrianConnectionResourceBuilder created =
                new SecureMondrianConnectionResourceBuilder(dummyMondrianConnection, storageMock);

        CommonOperationProcessorImpl processor = getField(created, "processor");
        ClientSecureMondrianConnection retrievedConnection = getField(created, "connection");

        assertSame(processor.getSessionStorage(), storageMock);
        assertSame(retrievedConnection, dummyMondrianConnection);
    }

    @SuppressWarnings("unchecked")
    private static <T> T getField(Object target, String fieldName) throws Exception {
        Class<?> clazz = target.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return (T) field.get(target);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }
}
