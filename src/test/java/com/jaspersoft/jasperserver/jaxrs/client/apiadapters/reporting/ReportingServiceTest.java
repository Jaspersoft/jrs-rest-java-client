package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting;

import com.jaspersoft.jasperserver.dto.reports.ReportParameter;
import com.jaspersoft.jasperserver.dto.reports.ReportParameters;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionDescriptor;
import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.ReportExecutionRequest;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNotSame;

/**
 * Unit tests for {@link ReportingService}
 */
@PrepareForTest({JerseyRequest.class})
public class ReportingServiceTest extends PowerMockTestCase {

    @Mock
    SessionStorage sessionStorageMock;

    @Mock
    JerseyRequest<ReportExecutionDescriptor> requestMock;

    @Mock
    OperationResult<ReportExecutionDescriptor> operationResultMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_invoke_method_logic_asynchronously_and_retrieve_OperationResult_in_separate_thread() throws InterruptedException {

        /* Given */

        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(ReportExecutionDescriptor.class),
                eq(new String[]{"/reportExecutions"}))).thenReturn(requestMock);
        ReportingService serviceSpy = spy(new ReportingService(sessionStorageMock));

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        ReportExecutionRequest requestSpy = spy(new ReportExecutionRequest());
        List<ReportParameter> params = new ArrayList<ReportParameter>();

        params.add(new ReportParameter()
                .setName("name")
                .setValues(new ArrayList<String>() {{
                    add("value");
                }}));

        requestSpy.setParameters(new ReportParameters(params))
                .setReportUnitUri("/some/path")
                .setOutputFormat("pdf")
                .setAsync(true);

        doReturn(operationResultMock).when(requestMock).post(requestSpy);

        final Callback<OperationResult<ReportExecutionDescriptor>, Void> callback =
                spy(new Callback<OperationResult<ReportExecutionDescriptor>, Void>() {
                    @Override
                    public Void execute(OperationResult<ReportExecutionDescriptor> data) {
                        newThreadId.set((int) Thread.currentThread().getId());
                        synchronized (this) {
                            this.notify();
                        }
                        return null;
                    }
                });

        doReturn(null).when(callback).execute(operationResultMock);


        /* When */

        RequestExecution retrieved = serviceSpy.asyncNewReportExecutionRequest(requestSpy, callback);
        synchronized (callback) {
            callback.wait(1000);
        }

        /* Than */

        verify(requestMock).post(requestSpy);
        verify(callback).execute(operationResultMock);
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, requestMock, operationResultMock);
    }
}