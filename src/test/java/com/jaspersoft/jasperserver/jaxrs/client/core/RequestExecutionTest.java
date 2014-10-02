package com.jaspersoft.jasperserver.jaxrs.client.core;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.Future;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertSame;

public class RequestExecutionTest {

    @Mock
    private Runnable runnableMock;

    @Mock
    private Future futureMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_return_not_null_future_after_it_was_setted() {

        /* Given */
        RequestExecution execution = Mockito.spy(new RequestExecution(runnableMock));
        Whitebox.setInternalState(execution, "future", futureMock);

        /* When */
        Future retrieved = execution.getFuture();

        /* Than */

        assertSame(retrieved, futureMock);
    }


    @Test
    public void should_return_not_null_task_after_setup_instance() {
        RequestExecution execution = new RequestExecution(runnableMock);
        assertSame(execution.getTask(), runnableMock);
    }

    @Test
    public void should_invoke_cancel_method_on_future_object() {

        /* Given */
        doReturn(true).when(futureMock).cancel(true);
        RequestExecution execution = new RequestExecution(runnableMock);

        /* When */
        execution.setFuture(futureMock);
        execution.cancel();

        /* Than */
        verify(futureMock).cancel(true);
    }

    @AfterMethod
    public void after() {
        reset(runnableMock);
    }
}