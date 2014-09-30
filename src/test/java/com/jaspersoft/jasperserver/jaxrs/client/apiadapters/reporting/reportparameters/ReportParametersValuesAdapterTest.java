//package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportparameters;
//
//import com.jaspersoft.jasperserver.dto.reports.ReportParameters;
//import com.jaspersoft.jasperserver.dto.reports.inputcontrols.ReportInputControlsListWrapper;
//import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
//import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
//import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
//import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
//import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
//import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
//import com.jaspersoft.jasperserver.jaxrs.client.dto.reports.inputcontrols.InputControlStateListWrapper;
//import org.mockito.InOrder;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.testng.PowerMockTestCase;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.MultivaluedHashMap;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportparameters.ReportParametersUtils.toReportParameters;
//import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
//import static org.mockito.Matchers.any;
//import static org.mockito.Matchers.eq;
//import static org.mockito.Mockito.reset;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.MockitoAnnotations.initMocks;
//import static org.powermock.api.mockito.PowerMockito.doReturn;
//import static org.powermock.api.mockito.PowerMockito.mockStatic;
//import static org.powermock.api.mockito.PowerMockito.spy;
//import static org.powermock.api.mockito.PowerMockito.verifyStatic;
//import static org.powermock.api.mockito.PowerMockito.when;
//import static org.testng.Assert.assertNotNull;
//import static org.testng.Assert.assertNotSame;
//import static org.testng.Assert.assertSame;
//
///**
// * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportparameters.ReportParametersValuesAdapter}
// *
// * @author Alexander Krasnyanskiy
// */
//@PrepareForTest({JerseyRequest.class, ReportParametersValuesAdapter.class,
//        ReportInputControlsListWrapper.class, ReportParametersUtils.class})
//public class ReportParametersValuesAdapterTest extends PowerMockTestCase {
//
//    @Mock
//    private SessionStorage sessionStorageMock;
//
//    @Mock
//    private JerseyRequest<InputControlStateListWrapper> requestMock;
//
//    @Mock
//    private MultivaluedHashMap<String, String> paramsMock;
//
//    @Mock
//    private OperationResult<InputControlStateListWrapper> wrapperOperationResultMock;
//
//    @Mock
//    private ReportParameters reportParametersMock;
//
//    private String reportUnitUri = "some/abstract/report/unit/uri";
//    private String idsPathSegment = "idsPathSegment";
//
//    @BeforeMethod
//    public void before() {
//        initMocks(this);
//    }
//
//    @Test
//    /**
//     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportparameters.ReportParametersValuesAdapter#asyncGet(com.jaspersoft.jasperserver.jaxrs.client.core.Callback)}
//     */
//    public void should_return_wrapper_object_with_future_result_of_asynchronous_execution() throws Exception {
//
//        /* Given */
//        final AtomicInteger newThreadId = new AtomicInteger();
//        int currentThreadId = (int) Thread.currentThread().getId();
//
//        ReportParametersValuesAdapter adapterSpy = spy(new ReportParametersValuesAdapter(sessionStorageMock,
//                reportUnitUri, idsPathSegment, paramsMock));
//        doReturn(requestMock).when(adapterSpy, "prepareRequest");
//
//        mockStatic(ReportParametersUtils.class);
//        when(toReportParameters(paramsMock)).thenReturn(reportParametersMock);
//
//        Callback<OperationResult<InputControlStateListWrapper>, Void> callback =
//                spy(new Callback<OperationResult<InputControlStateListWrapper>, Void>() {
//            @Override
//            public Void execute(OperationResult<InputControlStateListWrapper> data) {
//                newThreadId.set((int) Thread.currentThread().getId());
//                synchronized (this) {
//                    this.notify();
//                }
//                return null;
//            }
//        });
//
//        doReturn(wrapperOperationResultMock).when(requestMock).post(reportParametersMock);
//        doReturn(null).when(callback).execute(wrapperOperationResultMock);
//
//        /* When */
//        RequestExecution retrieved = adapterSpy.asyncGet(callback);
//
//        /* Wait */
//        synchronized (callback) {
//            callback.wait(1000);
//        }
//
//        /* Than */
//        assertNotNull(retrieved);
//        assertNotSame(currentThreadId, newThreadId.get());
//
//        //verifyStatic(times(1));
//        //toReportParameters(paramsMock);
//
//        //verifyPrivate(adapterSpy, times(1)).invoke("prepareRequest");
//        verify(callback, times(1)).execute(wrapperOperationResultMock);
//    }
//
//    @Test
//    /**
//     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportparameters.ReportParametersValuesAdapter#get()} and for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.reporting.reportparameters.ReportParametersValuesAdapter#prepareRequest()}
//     */
//    public void should_fire_private_method_and_return_operation_result_with_InputControlStateListWrapper_instance() {
//
//        /* Given */
//        ReportParametersValuesAdapter adapterSpy = spy(new ReportParametersValuesAdapter(sessionStorageMock,
//                reportUnitUri, idsPathSegment, paramsMock));
//
//        // Setup
//        mockStatic(JerseyRequest.class, ReportParametersUtils.class);
//        when(buildRequest(
//                eq(sessionStorageMock),
//                eq(InputControlStateListWrapper.class),
//                eq(new String[]{"/reports", reportUnitUri, "/inputControls"}),
//                any(DefaultErrorHandler.class)))
//                    .thenReturn(requestMock);
//
//        when(toReportParameters(paramsMock))
//                    .thenReturn(reportParametersMock);
//
//        doReturn(wrapperOperationResultMock).when(requestMock).post(reportParametersMock);
//        InOrder inOrder = Mockito.inOrder(requestMock);
//
//        /* When */
//        OperationResult<InputControlStateListWrapper> retrieved = adapterSpy.get();
//
//        /* Than */
//        assertNotNull(retrieved);
//        assertSame(retrieved, wrapperOperationResultMock);
//
//        verifyStatic(times(1));
//        buildRequest(eq(sessionStorageMock),eq(InputControlStateListWrapper.class),eq(new String[]{"/reports",
//                reportUnitUri, "/inputControls"}),any(DefaultErrorHandler.class));
//
//        verifyStatic(times(1));
//        toReportParameters(paramsMock);
//
//        inOrder.verify(requestMock, times(1)).addPathSegment(idsPathSegment);
//        inOrder.verify(requestMock, times(1)).addPathSegment("values");
//        inOrder.verify(requestMock, times(1)).setContentType(MediaType.APPLICATION_XML);
//        inOrder.verify(requestMock, times(1)).setAccept(MediaType.APPLICATION_XML);
//        inOrder.verify(requestMock, times(1)).post(reportParametersMock);
//    }
//
//    @AfterMethod
//    public void after() {
//        reset(sessionStorageMock);
//    }
//}