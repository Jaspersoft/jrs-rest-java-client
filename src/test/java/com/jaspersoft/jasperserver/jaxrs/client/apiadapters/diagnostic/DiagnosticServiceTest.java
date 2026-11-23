package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.diagnostic;

import com.jaspersoft.jasperserver.dto.logcapture.CollectorSettings;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.MandatoryParameterNotFoundException;
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
public class DiagnosticServiceTest {

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
        DiagnosticService service = new DiagnosticService(sessionStorageMock);
        assertSame(service.getSessionStorage(), sessionStorageMock);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_in_parent_adapter() {
        new DiagnosticService(null);
    }

    @Test
    public void should_return_proper_single_adapter_by_collector_settings_instance() throws Exception {
        CollectorSettings collector = new CollectorSettings();

        SingleDiagnosticCollectorAdapter retrieved = new DiagnosticService(sessionStorageMock).forCollector(collector);

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof SingleDiagnosticCollectorAdapter);
        assertSame(getInternalState(retrieved, "sessionStorage"), sessionStorageMock);
        assertSame(getInternalState(retrieved, "collector"), collector);
    }

    @Test
    public void should_return_proper_single_adapter_by_collector_settings_id() throws Exception {
        SingleDiagnosticCollectorAdapter retrieved = new DiagnosticService(sessionStorageMock).forCollector("id");

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof SingleDiagnosticCollectorAdapter);
        assertSame(getInternalState(retrieved, "sessionStorage"), sessionStorageMock);
        CollectorSettings collector = (CollectorSettings) getInternalState(retrieved, "collector");
        assertEquals(collector.getId(), "id");
    }

    @Test
    public void should_invoke_proper_constructor_and_pass_proper_session_storage_instance() throws Exception {
        BatchDiagnosticCollectorsAdapter retrieved = new DiagnosticService(sessionStorageMock).allCollectors();

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof BatchDiagnosticCollectorsAdapter);
        assertSame(getInternalState(retrieved, "sessionStorage"), sessionStorageMock);
    }

    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_exception_when_collector_instance_is_null() {
        new DiagnosticService(sessionStorageMock).forCollector((CollectorSettings) null);
    }

    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_exception_when_collector_id_is_null() {
        new DiagnosticService(sessionStorageMock).forCollector((String) null);
    }

    @Test(expectedExceptions = MandatoryParameterNotFoundException.class)
    public void should_throw_exception_when_collector_id_is_empty() {
        new DiagnosticService(sessionStorageMock).forCollector("");
    }
}
