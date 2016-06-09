package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting;

import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionListWrapper;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MultivaluedHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertSame;

@PrepareForTest(JerseyRequest.class)
public class ReportsAndJobsSearchAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<ReportExecutionListWrapper> requestMock;

    @Mock
    private OperationResult<ReportExecutionListWrapper> resultMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.ReportsAndJobsSearchAdapter#asyncFind(com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}
     */
    public void should_return_RequestExecution_object_which_contains_a_Future_with_retrieved_ReportExecutionListWrapper_instance() throws InterruptedException {

        /* Given */
        final AtomicInteger newThreadId = new AtomicInteger();
        int currentThreadId = (int) Thread.currentThread().getId();

        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(ReportExecutionListWrapper.class), eq(new String[]{"reportExecutions"}))).thenReturn(requestMock);

        ReportsAndJobsSearchAdapter adapterSpy = spy(new ReportsAndJobsSearchAdapter(sessionStorageMock));
        Callback<OperationResult<ReportExecutionListWrapper>, Void> callback = spy(new Callback<OperationResult<ReportExecutionListWrapper>, Void>() {
            @Override
            public Void execute(OperationResult<ReportExecutionListWrapper> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        InOrder inOrder = Mockito.inOrder(requestMock);

        doReturn(resultMock).when(requestMock).get();
        doReturn(null).when(callback).execute(resultMock);

        /* When */
        RequestExecution retrieved = adapterSpy.asyncFind(callback);

        /* Wait */
        synchronized (callback) {
            callback.wait(1000);
        }

        /* Then */
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());

        verify(callback, times(1)).execute(resultMock);

        inOrder.verify(requestMock, times(1)).addParams(any(MultivaluedHashMap.class));
        inOrder.verify(requestMock, times(1)).get();
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.ReportsAndJobsSearchAdapter#parameter(com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.ReportAndJobSearchParameter, String)}
     */
    public void should_add_parameter_into_the_map_and_return_the_same_object() {

        /* Given */
        ReportsAndJobsSearchAdapter adapterSpy = spy(new ReportsAndJobsSearchAdapter(sessionStorageMock));

        /* When */
        ReportsAndJobsSearchAdapter retrieved = adapterSpy.parameter(ReportAndJobSearchParameter.JOB_ID, "id");
        MultivaluedHashMap<String, String> params = (MultivaluedHashMap<String, String>) Whitebox.getInternalState(adapterSpy, "params");

        /* Then */
        Assert.assertSame(retrieved, adapterSpy);
        Assert.assertTrue(params.size() == 1);
        Assert.assertEquals(params.getFirst(ReportAndJobSearchParameter.JOB_ID.getName()), "id");
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.ReportsAndJobsSearchAdapter#find()}
     */
    public void should_return_proper_ReportExecutionListWrapper_with_result_of_find_operation() {

        mockStatic(JerseyRequest.class);
        Mockito.when(buildRequest(eq(sessionStorageMock), eq(ReportExecutionListWrapper.class), eq(new String[]{"reportExecutions"}))).thenReturn(requestMock);
        Mockito.doReturn(resultMock).when(requestMock).get();

        ReportsAndJobsSearchAdapter adapter = new ReportsAndJobsSearchAdapter(sessionStorageMock);

        OperationResult<ReportExecutionListWrapper> retrieved = adapter.find();

        assertSame(retrieved, resultMock);
        Mockito.verify(requestMock).get();
        Mockito.verify(requestMock).addParams(any(MultivaluedHashMap.class));
        Mockito.verifyNoMoreInteractions(requestMock);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, requestMock, resultMock);
    }
}