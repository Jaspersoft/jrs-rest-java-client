package com.jaspersoft.jasperserver.jaxrs.client.core;

import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
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
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertNotNull;

@PrepareForTest({Executors.class, ThreadPoolUtil.class})
public class ThreadPoolUtilTest extends PowerMockTestCase {

    @Mock
    private ExecutorService serviceMock;

    @Mock
    private FutureTask futureMock;

    @Mock
    private Runnable runnableMock;

    @Mock
    private RequestExecution executionTaskMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test(priority = 1, enabled = false)
    public void should_invoke_static_method_of_class() {

        PowerMockito.mockStatic(Executors.class);
        PowerMockito.when(Executors.newCachedThreadPool()).thenReturn(serviceMock);

        PowerMockito.doReturn(futureMock).when(serviceMock).submit(runnableMock);
        PowerMockito.doReturn(runnableMock).when(executionTaskMock).getTask();
        PowerMockito.doNothing().when(executionTaskMock).setFuture(futureMock);

        InOrder inOrder = Mockito.inOrder(executionTaskMock);

        /* When */
        ThreadPoolUtil.runAsynchronously(executionTaskMock);

        /* Than */
        PowerMockito.verifyStatic(Mockito.times(1));
        Executors.newCachedThreadPool();

        inOrder.verify(executionTaskMock).getTask();
        inOrder.verify(executionTaskMock).setFuture(futureMock);

        verify(serviceMock).submit(runnableMock);
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
    public void after() {
        reset(serviceMock, futureMock, executionTaskMock, runnableMock);
    }
}