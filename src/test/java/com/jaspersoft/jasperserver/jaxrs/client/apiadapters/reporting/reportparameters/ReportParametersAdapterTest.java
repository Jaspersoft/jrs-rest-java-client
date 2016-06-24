package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportparameters;

import com.jaspersoft.jasperserver.dto.reports.ReportParameters;
import com.jaspersoft.jasperserver.dto.reports.inputcontrols.ReportInputControlsListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * @deprecated should be deleted after deleted deprecated {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportparameters.ReportParametersAdapter}.
 */
@PrepareForTest({JerseyRequest.class})
public class ReportParametersAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<ReportInputControlsListWrapper> requestMock;

    @Mock
    private OperationResult<ReportInputControlsListWrapper> operationResultMock;

    private String reportUnitUri = "_uri";

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_return_report_input_controls_asynchronously_and_in_wrapping_holder() throws InterruptedException {

        /** Given **/

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        mockStatic(JerseyRequest.class);
        when(buildRequest(
                        eq(sessionStorageMock),
                        eq(ReportInputControlsListWrapper.class),
                        eq(new String[]{"reports", reportUnitUri, "inputControls"}),
                        any(DefaultErrorHandler.class))
        ).thenReturn(requestMock);

        doReturn(operationResultMock)
                .when(requestMock)
                .post(anyObject());

        ReportParametersAdapter adapterSpy =
                spy(new ReportParametersAdapter(sessionStorageMock, reportUnitUri, "_segment"));

        adapterSpy.parameter("param1", "value1");
        adapterSpy.parameter("param2", "value2");

        final Callback<OperationResult<ReportInputControlsListWrapper>, Void> callbackSpy =
                spy(new Callback<OperationResult<ReportInputControlsListWrapper>, Void>() {
                    @Override
                    public Void execute(OperationResult<ReportInputControlsListWrapper> data) {
                        newThreadId.set((int) Thread.currentThread().getId());
                        synchronized (this) {
                            this.notify();
                        }
                        return null;
                    }
                });


        /** When **/
        RequestExecution retrieved = adapterSpy.asyncGet(callbackSpy);


        /** Wait **/
        synchronized (callbackSpy) {
            callbackSpy.wait(1000);
        }


        /** Then **/
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());

        verify(requestMock).setContentType(MediaType.APPLICATION_XML);
        verify(requestMock).setAccept(MediaType.APPLICATION_XML);
        verify(callbackSpy, times(1)).execute(operationResultMock);
    }

    @Test(enabled = false)
    public void should_return_ReportInputControlsListWrapper() {

        /** Given **/

        ArgumentCaptor<ReportParameters> parametersCaptor = ArgumentCaptor.forClass(ReportParameters.class);
        mockStatic(JerseyRequest.class);
        when(buildRequest(
                        eq(sessionStorageMock),
                        eq(ReportInputControlsListWrapper.class),
                        eq(new String[]{"/reports", reportUnitUri, "/inputControls"}),
                        any(DefaultErrorHandler.class))
        ).thenReturn(requestMock);

        doReturn(operationResultMock)
                .when(requestMock)
                .post(anyObject());

        ReportParametersAdapter adapterSpy = spy(new ReportParametersAdapter(sessionStorageMock, reportUnitUri, "_segment"));
        adapterSpy.parameter("p1", "v1");
        adapterSpy.parameter("p2", "v2");


        /** When **/
        OperationResult<ReportInputControlsListWrapper> retrieved = adapterSpy.get();


        /** Then **/
        PowerMockito.verifyStatic();
        buildRequest(
                eq(sessionStorageMock),
                eq(ReportInputControlsListWrapper.class),
                eq(new String[]{"/reports", reportUnitUri, "/inputControls"}),
                any(DefaultErrorHandler.class));

        Mockito.verify(requestMock).post(parametersCaptor.capture());
        assertEquals(parametersCaptor.getValue().getReportParameters().get(0).getName(), "p2");

        assertNotNull(retrieved);
        assertSame(retrieved, operationResultMock);
    }

    @Test
    public void should_return_proper_instance_of_ReportParametersValuesAdapter() {

        /* Given */
        ReportParametersAdapter adapterSpy = spy(new ReportParametersAdapter(sessionStorageMock, reportUnitUri, "_segment"));
        adapterSpy.parameter("p1", "v1");
        adapterSpy.parameter("p2", "v2");

        /* When */
        ReportParametersValuesAdapter retrieved = adapterSpy.values();

        /* Then */
        assertNotNull(retrieved);
        List<String> paramValues = retrieved.params.get("p1");
        assertTrue(paramValues.contains("v1"));
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, operationResultMock, requestMock);
    }
}