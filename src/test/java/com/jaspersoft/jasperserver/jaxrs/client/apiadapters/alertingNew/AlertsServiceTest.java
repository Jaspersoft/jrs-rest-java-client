package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alertingNew;

import com.jaspersoft.jasperserver.dto.alerting.ClientAlertCalendar;
import com.jaspersoft.jasperserver.dto.alerting.ClientReportAlert;
import com.jaspersoft.jasperserver.dto.alerting.wrappers.ClientCalendarNameListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alerting.AlertsService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alerting.BatchAlertsOperationsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alerting.SingleAlertOperationsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alerting.calendar.SingleCalendarOperationsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link AlertsService}
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class AlertsServiceTest {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<ClientReportAlert> alertRequestMock;

    @Mock
    private JerseyRequest<ClientCalendarNameListWrapper> wrapperRequestMock;

    @Mock
    private OperationResult<ClientReportAlert> expectedAlertOperationResultMock;

    @Mock
    private OperationResult<ClientCalendarNameListWrapper> expectedWrapperOperationResultMock;

    @Mock
    private ClientReportAlert reportMock;

    @Mock
    private RestClientConfiguration configurationMock;

    @Mock
    private Callback<OperationResult<ClientCalendarNameListWrapper>, Object> callbackMock;

    @Mock
    private RequestExecution executionMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        mockitoSession.close();
        reset(sessionStorageMock, alertRequestMock, expectedAlertOperationResultMock,
                reportMock, configurationMock);
    }

    @Test(testName = "alerts")
    public void should_return_proper_adapter() {
        AlertsService service = new AlertsService(sessionStorageMock);

        BatchAlertsOperationsAdapter retrieved = service.alerts();

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof BatchAlertsOperationsAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
    }

    @Test(testName = "alert")
    public void should_return_proper_SingleJobOperationsAdapter() {
        AlertsService service = new AlertsService(sessionStorageMock);

        SingleAlertOperationsAdapter retrieved = service.alert(9056);

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof SingleAlertOperationsAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
        verify(spy(service), never()).alert(9056L);
    }

    @Test(testName = "allCalendars")
    public void should_return_proper_op_result_object() {
        AlertsService serviceSpy = spy(new AlertsService(sessionStorageMock));
        doReturn(expectedWrapperOperationResultMock).when(serviceSpy).calendar((ClientAlertCalendar.Type) null);

        OperationResult<ClientCalendarNameListWrapper> retrieved = serviceSpy.allCalendars();

        verify(serviceSpy, times(1)).allCalendars();
        verify(serviceSpy, times(1)).calendar((ClientAlertCalendar.Type) null);
        verify(serviceSpy, never()).calendar(ClientAlertCalendar.Type.holiday);
        verifyNoMoreInteractions(serviceSpy);
        assertSame(retrieved, expectedWrapperOperationResultMock);
    }

    @Test(testName = "asyncCalendar")
    public void should_return_RequestExecution_with_CalendarNameListWrapper_instance() {
        AlertsService serviceSpy = spy(new AlertsService(sessionStorageMock));
        doReturn(executionMock).when(serviceSpy).asyncCalendar(null, callbackMock);

        RequestExecution retrieved = serviceSpy.asyncCalendar(callbackMock);

        verify(serviceSpy, times(1)).asyncCalendar(null, callbackMock);
        verify(serviceSpy, never()).asyncCalendar(ClientAlertCalendar.Type.holiday, callbackMock);
        assertSame(retrieved, executionMock);
    }

    @Test(testName = "calendars_with_param")
    public void should_return_proper_op_result_object_when_param_is_not_null() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock),
                    eq(ClientCalendarNameListWrapper.class),
                    eq(new String[]{"alerts", "calendars"}))).thenReturn(wrapperRequestMock);
            doReturn(wrapperRequestMock).when(wrapperRequestMock).addParam("calendarType", "daily");
            doReturn(expectedWrapperOperationResultMock).when(wrapperRequestMock).get();

            AlertsService serviceSpy = spy(new AlertsService(sessionStorageMock));
            OperationResult<ClientCalendarNameListWrapper> retrieved = serviceSpy.calendar(ClientAlertCalendar.Type.daily);

            assertNotNull(retrieved);
            assertSame(retrieved, expectedWrapperOperationResultMock);
            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock),
                    eq(ClientCalendarNameListWrapper.class),
                    eq(new String[]{"alerts", "calendars"})), times(1));
            verify(wrapperRequestMock, times(1)).get();
            verify(wrapperRequestMock, times(1)).addParam("calendarType", "daily");
        }
    }

    @Test(testName = "calendars_with_param")
    public void should_return_proper_op_result_object_when_param_is_null() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock),
                    eq(ClientCalendarNameListWrapper.class),
                    eq(new String[]{"alerts", "calendars"}))).thenReturn(wrapperRequestMock);
            doReturn(expectedWrapperOperationResultMock).when(wrapperRequestMock).get();

            AlertsService serviceSpy = spy(new AlertsService(sessionStorageMock));
            OperationResult<ClientCalendarNameListWrapper> retrieved = serviceSpy.calendar((ClientAlertCalendar.Type) null);

            assertNotNull(retrieved);
            assertSame(retrieved, expectedWrapperOperationResultMock);
            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock),
                    eq(ClientCalendarNameListWrapper.class),
                    eq(new String[]{"alerts", "calendars"})), times(1));
            verify(wrapperRequestMock, times(1)).get();
            verify(wrapperRequestMock, never()).addParam(eq("calendarType"), Mockito.anyString());
        }
    }

    @Test(testName = "calendar")
    public void should_return_an_calendar_adapter() {
        final String calendarName = "testCalendar";
        AlertsService service = new AlertsService(sessionStorageMock);

        SingleCalendarOperationsAdapter retrieved = service.calendar(calendarName);

        assertNotNull(retrieved);
        assertTrue(retrieved instanceof SingleCalendarOperationsAdapter);
        assertSame(retrieved.getSessionStorage(), sessionStorageMock);
    }

    @Test(testName = "calendar", expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_invalid_calendar_name() {
        AlertsService service = new AlertsService(sessionStorageMock);
        service.calendar("");
    }

    @Test
    public void should_get_calendars_asynchronously() throws InterruptedException {
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock),
                    eq(ClientCalendarNameListWrapper.class),
                    eq(new String[]{"alerts", "calendars"}))).thenReturn(wrapperRequestMock);
            doReturn(wrapperRequestMock).when(wrapperRequestMock).addParam("calendarType", "annual");
            doReturn(expectedWrapperOperationResultMock).when(wrapperRequestMock).get();

            final Callback<OperationResult<ClientCalendarNameListWrapper>, Void> callback =
                    spy(new Callback<OperationResult<ClientCalendarNameListWrapper>, Void>() {
                        @Override
                        public Void execute(OperationResult<ClientCalendarNameListWrapper> data) {
                            newThreadId.set((int) Thread.currentThread().getId());
                            synchronized (this) {
                                this.notify();
                            }
                            return null;
                        }
                    });

            AlertsService serviceSpy = spy(new AlertsService(sessionStorageMock));
            RequestExecution retrieved = serviceSpy.asyncCalendar(ClientAlertCalendar.Type.annual, callback);

            synchronized (callback) {
                callback.wait(1000);
            }

            Assert.assertNotNull(retrieved);
            Assert.assertNotEquals(newThreadId.get(), currentThreadId);
            Mockito.verify(wrapperRequestMock).get();
            Mockito.verify(callback).execute(expectedWrapperOperationResultMock);
            Mockito.verify(wrapperRequestMock).addParam("calendarType", "annual");
        }
    }
}
