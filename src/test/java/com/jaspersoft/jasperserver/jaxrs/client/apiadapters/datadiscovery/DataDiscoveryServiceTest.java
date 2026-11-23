package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.datadiscovery;

import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.mockito.Mockito.reset;
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
public class DataDiscoveryServiceTest {

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
        DataDiscoveryService service = new DataDiscoveryService(sessionStorageMock);
        SessionStorage retrieved = service.getSessionStorage();
        assertSame(retrieved, sessionStorageMock);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_sessionStorage_is_null() {
        new DataDiscoveryService(null);
    }

    @Test
    public void should_return_proper_DomainContextManager() throws Exception {
        DataDiscoveryService service = new DataDiscoveryService(sessionStorageMock);

        DomainContextManager retrieved = service.domainContext();

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof DomainContextManager);
        assertSame(getInternalState(retrieved, "sessionStorage"), sessionStorageMock);
    }

    @Test
    public void should_return_proper_TopicContextManager() throws Exception {
        DataDiscoveryService service = new DataDiscoveryService(sessionStorageMock);

        TopicContextManager retrieved = service.topicContext();

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof TopicContextManager);
        assertSame(getInternalState(retrieved, "sessionStorage"), sessionStorageMock);
    }

    @Test
    public void should_return_proper_DomElContextManager() throws Exception {
        DataDiscoveryService service = new DataDiscoveryService(sessionStorageMock);

        DomElContextManager retrieved = service.domElContext();

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof DomElContextManager);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
    }

    @Test
    public void should_return_proper_deridved_table_context_manager() throws Exception {
        DataDiscoveryService service = new DataDiscoveryService(sessionStorageMock);

        DerivedTableContextManager retrieved = service.derivedTableContext();

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof DerivedTableContextManager);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
    }
}
