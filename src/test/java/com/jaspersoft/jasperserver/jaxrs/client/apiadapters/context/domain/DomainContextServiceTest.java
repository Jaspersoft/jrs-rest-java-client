package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.context.domain;

import com.jaspersoft.jasperserver.dto.resources.ClientResourceLookup;
import com.jaspersoft.jasperserver.dto.resources.ClientSemanticLayerDataSource;
import com.jaspersoft.jasperserver.dto.resources.domain.ClientDomain;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.ContextMediaTypes;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.mockito.Mockito.reset;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * <p/>
 * <p/>
 *
 * @author tetiana.iefimenko
 * @version $Id$
 * @see
 */
public class DomainContextServiceTest {

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
        DomainContextService service = new DomainContextService(sessionStorageMock);
        SessionStorage retrieved = service.getSessionStorage();
        assertSame(retrieved, sessionStorageMock);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_sessionStorage_is_null() {
        new DomainContextService(null);
    }

    @Test
    public void should_return_proper_DomainContextManager_for_string_uri() throws Exception {
        DomainContextService service = new DomainContextService(sessionStorageMock);

        SingleDomainContextAdapter retrieved = service.context("URI");

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof SingleDomainContextAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertEquals(getInternalState(retrieved, "contextClass"), ClientResourceLookup.class);
        assertEquals(getInternalState(retrieved, "contextMimeType"), ContextMediaTypes.RESOURCE_LOOKUP_JSON);
        ClientResourceLookup contextObj = (ClientResourceLookup) getInternalState(retrieved, "context");
        assertEquals(contextObj.getUri(), "URI");
    }

    @Test
    public void should_return_proper_DomainContextManager_for_clientDomain() throws Exception {
        DomainContextService service = new DomainContextService(sessionStorageMock);
        ClientDomain clientDomain = new ClientDomain();

        SingleDomainContextAdapter retrieved = service.context(clientDomain);

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof SingleDomainContextAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertEquals(getInternalState(retrieved, "contextClass"), ClientDomain.class);
        assertEquals(getInternalState(retrieved, "contextMimeType"), ContextMediaTypes.DOMAIN_JSON);
        assertSame(getInternalState(retrieved, "context"), clientDomain);
    }

    @Test
    public void should_return_proper_DomainContextManager_for_clientSemanticLayerDataSource() throws Exception {
        DomainContextService service = new DomainContextService(sessionStorageMock);
        ClientSemanticLayerDataSource dataSource = new ClientSemanticLayerDataSource();

        SingleDomainContextAdapter retrieved = service.context(dataSource);

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof SingleDomainContextAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        assertEquals(getInternalState(retrieved, "contextClass"), ClientSemanticLayerDataSource.class);
        assertEquals(getInternalState(retrieved, "contextMimeType"), ContextMediaTypes.DOMAIN_DATA_SOURCE_JSON);
        assertSame(getInternalState(retrieved, "context"), dataSource);
    }
}
