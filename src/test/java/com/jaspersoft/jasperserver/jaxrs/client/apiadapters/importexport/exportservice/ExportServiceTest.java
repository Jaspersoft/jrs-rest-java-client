package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice;

import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.mockito.Mockito.reset;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link ExportService}
 */
public class ExportServiceTest {

    @Mock
    private SessionStorage storageMock;

    private ExportService service;
    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
        service = new ExportService(storageMock);
    }

    @AfterMethod
    public void after() throws Exception {
        mockitoSession.close();
        reset(storageMock);
        service = null;
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
    public void should_pass_param_to_parent_constructor() {
        assertSame(service.getSessionStorage(), storageMock);
    }

    @Test
    public void should_return_proper_ExportRequestAdapter_object() throws Exception {
        ExportRequestAdapter adapter = service.task("k4ks3ds");

        assertEquals(adapter.getSessionStorage(), storageMock);
        assertSame(getInternalState(adapter, "taskId"), "k4ks3ds");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_pass_invalid_param() {
        service.task("");
    }
}
