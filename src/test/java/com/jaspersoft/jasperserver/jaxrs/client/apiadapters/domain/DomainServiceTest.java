package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.domain.metadata.DomainMetadataAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.reset;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link DomainService}
 */
public class DomainServiceTest {

    public static final String URI = "uri";

    @Mock
    private SessionStorage sessionStorageMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        mockitoSession.close();
        reset(sessionStorageMock);
    }

    private Object getInternalState(Object obj, String fieldName) throws Exception {
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(obj);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }

    @Test
    public void should_pass_session_storage_to_parent_adapter() {
        DomainService domainService = new DomainService(sessionStorageMock);
        assertSame(domainService.getSessionStorage(), sessionStorageMock);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_sessionStorage_is_null() {
        new DomainService(null);
    }

    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_an_exception_when_domain_uri_is_null() {
        new DomainService(sessionStorageMock).forDomain((String) null);
    }

    @Test
    public void should_return_proper_DomainMetadataAdapter_instance() throws Exception {
        DomainMetadataAdapter retrieved = new DomainService(sessionStorageMock).forDomain(URI).metadata();

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof DomainMetadataAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        List<String> path = (List<String>) getInternalState(retrieved, "path");
        assertEquals(path, asList("domains", URI, "metadata"));
    }
}
