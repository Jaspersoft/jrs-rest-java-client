package com.jaspersoft.jasperserver.jaxrs.client.core;

import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertNotNull;

public class ThreadPoolUtilTest {

    @Mock
    private ExecutorService serviceMock;

    @Mock
    private FutureTask futureMock;

    @Mock
    private Runnable runnableMock;

    @Mock
    private RequestExecution executionTaskMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @Test(priority = 1, enabled = false)
    public void should_invoke_static_method_of_class() {
        try (MockedStatic<Executors> mockedExecutors = Mockito.mockStatic(Executors.class)) {
            mockedExecutors.when(Executors::newCachedThreadPool).thenReturn(serviceMock);

            Mockito.doReturn(futureMock).when(serviceMock).submit(runnableMock);
            Mockito.doReturn(runnableMock).when(executionTaskMock).getTask();
            Mockito.doNothing().when(executionTaskMock).setFuture(futureMock);

            InOrder inOrder = Mockito.inOrder(executionTaskMock);

            /* When */
            ThreadPoolUtil.runAsynchronously(executionTaskMock);

            /* Then */
            mockedExecutors.verify(Executors::newCachedThreadPool, Mockito.times(1));

            inOrder.verify(executionTaskMock).getTask();
            inOrder.verify(executionTaskMock).setFuture(futureMock);

            verify(serviceMock).submit(runnableMock);
        }
    }

    @Test(priority = 2, enabled = true) // priority needs to avoid crash during execution of Reflection operations
    public void magic() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<ThreadPoolUtil> constructor = ThreadPoolUtil.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        ThreadPoolUtil instance = constructor.newInstance();

        assertNotNull(instance);

        constructor.setAccessible(false);
        constructor = null;
    }

    @AfterMethod
    public void after() throws Exception {
        reset(serviceMock, futureMock, executionTaskMock, runnableMock);
        mockitoSession.close();
    }
}
