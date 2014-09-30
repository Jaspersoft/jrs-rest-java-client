package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice;

import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import org.mockito.Mock;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.support.membermodification.MemberMatcher.field;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link ExportService}
 */
public class ExportServiceTest extends PowerMockTestCase {

    @Mock
    private SessionStorage storageMock;

    private ExportService service;

    @BeforeMethod
    public void after() {
        initMocks(this);
        service = new ExportService(storageMock);
    }

    @Test
    public void should_pass_param_to_parent_constructor() {
        assertSame(service.getSessionStorage(), storageMock);
    }

    @Test
    public void should_create_a_new_task_object_and_pass_session_into_the_constructor_of_task() {
        ExportTaskAdapter task = service.newTask();
        assertNotNull(task);
        assertSame(task.getSessionStorage(), storageMock);
    }

    @Test
    public void should_return_proper_ExportRequestAdapter_object() throws IllegalAccessException {
        ExportRequestAdapter adapter = service.task("k4ks3ds");
        Field field = field(ExportRequestAdapter.class, "taskId");
        String taskId = (String) field.get(adapter);

        assertEquals(adapter.getSessionStorage(), storageMock);
        assertSame(taskId, "k4ks3ds");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_pass_invalid_param() {
        service.task("");
    }

    @AfterMethod
    public void before() {
        reset(storageMock);
        service = null;
    }
}