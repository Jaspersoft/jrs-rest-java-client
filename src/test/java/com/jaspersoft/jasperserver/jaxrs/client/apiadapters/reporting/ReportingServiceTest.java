package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting;

import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionRequest;
import java.util.TimeZone;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.String;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link ReportingService}
 */
@PrepareForTest({JerseyRequest.class, ReportingService.class})
public class ReportingServiceTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<ReportExecutionDescriptor> requestMock;

    @Mock
    private ReportExecutionRequest executionRequestMock;

    @Mock
    private OperationResult<ReportExecutionDescriptor> resultMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    /**
     * for {@link ReportingService#asyncNewReportExecutionRequest(ReportExecutionRequest, com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}
     */
    public void should_return_wrapped_ReportExecutionDescriptor() throws InterruptedException {

        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        int currentThreadId = (int) Thread.currentThread().getId();

        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock),
                eq(ReportExecutionDescriptor.class),
                eq(new String[]{"reportExecutions"}))).thenReturn(requestMock);

        ReportingService serviceSpy = PowerMockito.spy(new ReportingService(sessionStorageMock));
        Callback<OperationResult<ReportExecutionDescriptor>, Void> callback = spy(new Callback<OperationResult<ReportExecutionDescriptor>, Void>() {
            @Override
            public Void execute(OperationResult<ReportExecutionDescriptor> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        doReturn(resultMock).when(requestMock).post(executionRequestMock);
        doReturn(null).when(callback).execute(resultMock);

        // When
        RequestExecution retrieved = serviceSpy.asyncNewReportExecutionRequest(executionRequestMock, callback);

        // Wait
        synchronized (callback) {
            callback.wait(1000);
        }

        // Then
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
        verify(callback, times(1)).execute(resultMock);
        verify(requestMock, times(1)).post(executionRequestMock);
    }

    @Test
    /**
     * for {@link ReportingService#newReportExecutionRequest(ReportExecutionRequest)}
     */
    public void should_return_operation_result_with_proper_object() {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(ReportExecutionDescriptor.class),
                eq(new String[]{"reportExecutions"}))).thenReturn(requestMock);

        doReturn(resultMock).when(requestMock).post(executionRequestMock);
        ReportingService serviceSpy = PowerMockito.spy(new ReportingService(sessionStorageMock));

        // When
        OperationResult<ReportExecutionDescriptor> retrieved = serviceSpy.newReportExecutionRequest(executionRequestMock);

        // Then
        assertNotNull(retrieved);
        assertSame(retrieved, resultMock);

        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock), eq(ReportExecutionDescriptor.class), eq(new String[]{"reportExecutions"}));
    }

    @Test
    /**
     * for {@link ReportingService#newReportExecutionRequest(ReportExecutionRequest)}
     */
    public void should_set_time_zone_and_return_operation_result_with_proper_object() {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(ReportExecutionDescriptor.class),
                eq(new String[]{"reportExecutions"}))).thenReturn(requestMock);

        doReturn(TimeZone.getTimeZone("America/Los_Angeles")).when(executionRequestMock).getTimeZone();
        doReturn(requestMock).when(requestMock).addHeader("Accept-Timezone", "America/Los_Angeles");
        doReturn(resultMock).when(requestMock).post(executionRequestMock);
        ReportingService serviceSpy = PowerMockito.spy(new ReportingService(sessionStorageMock));

        // When
        OperationResult<ReportExecutionDescriptor> retrieved = serviceSpy.newReportExecutionRequest(executionRequestMock);

        // Then
        assertNotNull(retrieved);
        assertSame(retrieved, resultMock);

        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock), eq(ReportExecutionDescriptor.class), eq(new String[]{"reportExecutions"}));
        verify(requestMock).addHeader("Accept-Timezone", "America/Los_Angeles");
    }

    @Test
    /**
     * for {@link ReportingService#reportExecutionRequest(String)}
     */
    public void should_return_proper_builder_instance() throws Exception {

        // Given
        ReportExecutionRequestBuilder builderMock = mock(ReportExecutionRequestBuilder.class);
        whenNew(ReportExecutionRequestBuilder.class).withArguments(sessionStorageMock, "id").thenReturn(builderMock);
        ReportingService serviceSpy = PowerMockito.spy(new ReportingService(sessionStorageMock));

        // When
        ReportExecutionRequestBuilder retrieved = serviceSpy.reportExecutionRequest("id");

        // Then
        assertNotNull(retrieved);
        assertSame(retrieved, builderMock);
        verifyNew(ReportExecutionRequestBuilder.class).withArguments(sessionStorageMock, "id");
    }

    @Test
    /**
     * for {@link ReportingService#runningReportsAndJobs()}
     */
    public void should_return_proper_ReportsAndJobsSearch_instance() throws Exception {

        // Given
        ReportsAndJobsSearchAdapter adapterMock = mock(ReportsAndJobsSearchAdapter.class);
        whenNew(ReportsAndJobsSearchAdapter.class).withArguments(sessionStorageMock).thenReturn(adapterMock);
        ReportingService serviceSpy = PowerMockito.spy(new ReportingService(sessionStorageMock));

        // When
        ReportsAndJobsSearchAdapter retrieved = serviceSpy.runningReportsAndJobs();

        // Then
        assertNotNull(retrieved);
        assertSame(retrieved, adapterMock);
        verifyNew(ReportsAndJobsSearchAdapter.class).withArguments(sessionStorageMock);
    }

    @Test
    /**
     * for {@link ReportingService#report(String)}
     */
    public void should_return_proper_ReportsAdapter_instance() throws Exception {

        // Given
        ReportsAdapter adapterMock = mock(ReportsAdapter.class);
        whenNew(ReportsAdapter.class).withArguments(sessionStorageMock, "reportUnitUri").thenReturn(adapterMock);
        ReportingService serviceSpy = PowerMockito.spy(new ReportingService(sessionStorageMock));

        // When
        ReportsAdapter retrieved = serviceSpy.report("reportUnitUri");

        // Then
        assertNotNull(retrieved);
        assertSame(retrieved, adapterMock);
        verifyNew(ReportsAdapter.class).withArguments(sessionStorageMock, "reportUnitUri");
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, requestMock, executionRequestMock, resultMock);
    }
}