package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportparameters;

import com.jaspersoft.jasperserver.dto.reports.inputcontrols.ReportInputControl;
import com.jaspersoft.jasperserver.dto.reports.inputcontrols.ReportInputControlsListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
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
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportparameters.ReorderingReportParametersAdapter}
 *
 * @author Alexander Krasnyanskiy
 *
 *
 * @deprecated should be deleted after deleted deprecated {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportparameters.ReorderingReportParametersAdapter}.
 */
@PrepareForTest({JerseyRequest.class, ReorderingReportParametersAdapter.class, ReportInputControlsListWrapper.class})
public class ReorderingReportParametersAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<ReportInputControlsListWrapper> requestMock;

    @Mock
    private List<ReportInputControl> inputControlsMock;

    @Mock
    private ReportInputControlsListWrapper wrapperMock;

    @Mock
    private OperationResult<ReportInputControlsListWrapper> resultMock;

    private String reportUnitUri = "some/abstract/thumbnail/unit/uri";

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportparameters.ReorderingReportParametersAdapter#asyncReorder(java.util.List, com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}
     */
    public void should_do_reorder_of_IC_asynchronously_and_return_holder_with_future() throws Exception {

        /* Given */
        final AtomicInteger newThreadId = new AtomicInteger();
        int currentThreadId = (int) Thread.currentThread().getId();

        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock),
                eq(ReportInputControlsListWrapper.class),
                eq(new String[]{"reports", reportUnitUri, "inputControls"}))).thenReturn(requestMock);

        ReorderingReportParametersAdapter adapterSpy = spy(new ReorderingReportParametersAdapter(sessionStorageMock, reportUnitUri));
        Callback<OperationResult<ReportInputControlsListWrapper>, Void> callback = spy(new Callback<OperationResult<ReportInputControlsListWrapper>, Void>() {
            @Override
            public Void execute(OperationResult<ReportInputControlsListWrapper> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        whenNew(ReportInputControlsListWrapper.class).withArguments(inputControlsMock).thenReturn(wrapperMock);
        doReturn(resultMock).when(requestMock).put(wrapperMock);
        doReturn(null).when(callback).execute(resultMock);

        /* When */
        RequestExecution retrieved = adapterSpy.asyncReorder(inputControlsMock, callback);

        /* Wait */
        synchronized (callback) {
            callback.wait(1000);
        }

        /* Then */
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
        verify(requestMock, times(1)).put(wrapperMock);
        verifyNew(ReportInputControlsListWrapper.class, times(1)).withArguments(inputControlsMock);
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportparameters.ReorderingReportParametersAdapter#reorder(java.util.List)}
     */
    public void should_do_reorder_of_IC() throws Exception {

        /* Given */
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock),
                eq(ReportInputControlsListWrapper.class),
                eq(new String[]{"reports", reportUnitUri, "inputControls"}))).thenReturn(requestMock);

        whenNew(ReportInputControlsListWrapper.class).withArguments(inputControlsMock).thenReturn(wrapperMock);
        doReturn(resultMock).when(requestMock).put(wrapperMock);

        ReorderingReportParametersAdapter adapterSpy = spy(new ReorderingReportParametersAdapter(sessionStorageMock, reportUnitUri));

        /* When */
        OperationResult<ReportInputControlsListWrapper> retrieved = adapterSpy.reorder(inputControlsMock);

        /* Then */
        assertNotNull(retrieved);
        assertSame(retrieved, resultMock);

        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock),
                eq(ReportInputControlsListWrapper.class),
                eq(new String[]{"reports", reportUnitUri, "inputControls"}));

        verifyNew(ReportInputControlsListWrapper.class, times(1)).withArguments(inputControlsMock);
        verify(requestMock, times(1)).put(wrapperMock);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, requestMock, inputControlsMock, wrapperMock, resultMock);
    }

}