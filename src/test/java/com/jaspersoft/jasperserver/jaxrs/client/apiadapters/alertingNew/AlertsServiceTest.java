package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alertingNew;

import com.jaspersoft.jasperserver.dto.alerting.ClientAlertCalendar;
import com.jaspersoft.jasperserver.dto.alerting.ClientReportAlert;
import com.jaspersoft.jasperserver.dto.alerting.wrappers.ClientCalendarNameListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alerting.AlertsService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alerting.BatchAlertsOperationsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alerting.SingleAlertOperationsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alerting.calendar.CalendarType;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alerting.calendar.SingleCalendarOperationsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.*;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link AlertsService}
 */
@PrepareForTest({JerseyRequest.class, AlertsService.class})
public class AlertsServiceTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private BatchAlertsOperationsAdapter expectedAdapterMock;

    @Mock
    private SingleAlertOperationsAdapter expectedAlertOperationsAdapter;

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

    @Mock
    private SingleCalendarOperationsAdapter expectedCalendarOperationsAdapterMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test(testName = "alerts")
    public void should_return_proper_adapter() throws Exception {

        // Given
        AlertsService serviceSpy = spy(new AlertsService(sessionStorageMock));
        whenNew(BatchAlertsOperationsAdapter.class)
                .withParameterTypes(SessionStorage.class)
                .withArguments(sessionStorageMock)
                .thenReturn(expectedAdapterMock);

        // When
        BatchAlertsOperationsAdapter retrieved = serviceSpy.alerts();

        // Then
        assertSame(retrieved, expectedAdapterMock);
    }

    @Test(testName = "alert")
    public void should_return_proper_SingleJobOperationsAdapter() throws Exception {

        // Given
        AlertsService serviceSpy = spy(new AlertsService(sessionStorageMock));
        whenNew(SingleAlertOperationsAdapter.class)
                .withParameterTypes(SessionStorage.class, String.class)
                .withArguments(sessionStorageMock, "9056")
                .thenReturn(expectedAlertOperationsAdapter);

        // When
        SingleAlertOperationsAdapter retrieved = serviceSpy.alert(9056);

        // Then
        verify(serviceSpy, times(1)).alert(9056);
        assertSame(retrieved, expectedAlertOperationsAdapter);
    }

    @Test(testName = "allCalendars")
    public void should_return_proper_op_result_object() {

        // Given
        AlertsService serviceSpy = spy(new AlertsService(sessionStorageMock));
        doReturn(expectedWrapperOperationResultMock).when(serviceSpy).calendar((ClientAlertCalendar.Type) null);

        // When
        OperationResult<ClientCalendarNameListWrapper> retrieved = serviceSpy.allCalendars();

        // Then
        verify(serviceSpy, times(1)).allCalendars();
        verify(serviceSpy, times(1)).calendar(((ClientAlertCalendar.Type) null));
        verify(serviceSpy, never()).calendar(ClientAlertCalendar.Type.holiday);
        verifyNoMoreInteractions(serviceSpy);

        assertSame(retrieved, expectedWrapperOperationResultMock);
    }

    @Test(testName = "asyncCalendar")
    public void should_return_RequestExecution_with_CalendarNameListWrapper_instance() {

        // Given
        AlertsService serviceSpy = spy(new AlertsService(sessionStorageMock));
        doReturn(executionMock).when(serviceSpy).asyncCalendar(null, callbackMock);

        // When
        RequestExecution retrieved = serviceSpy.asyncCalendar(callbackMock);

        // Then
        verify(serviceSpy, times(1)).asyncCalendar(null, callbackMock);
        verify(serviceSpy, never()).asyncCalendar(ClientAlertCalendar.Type.holiday, callbackMock);

        assertSame(retrieved, executionMock);
    }

    @Test(testName = "calendars_with_param")
    public void should_return_proper_op_result_object_when_param_is_not_null() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(ClientCalendarNameListWrapper.class),
                eq(new String[]{"alerts", "calendars"}))).thenReturn(wrapperRequestMock);
        when(wrapperRequestMock.get()).thenReturn(expectedWrapperOperationResultMock);
        AlertsService serviceSpy = spy(new AlertsService(sessionStorageMock));

        // When
        OperationResult<ClientCalendarNameListWrapper> retrieved = serviceSpy.calendar(ClientAlertCalendar.Type.daily);

        // Then
        assertNotNull(retrieved);
        assertSame(retrieved, expectedWrapperOperationResultMock);

        verifyStatic(JerseyRequest.class, times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(ClientCalendarNameListWrapper.class),
                eq(new String[]{"alerts", "calendars"}));

        verify(wrapperRequestMock, times(1)).get();
        verify(wrapperRequestMock, times(1)).addParam("calendarType", CalendarType.daily.toString().toLowerCase());
    }


    @Test(testName = "calendars_with_param")
    public void should_return_proper_op_result_object_when_param_is_null() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(ClientCalendarNameListWrapper.class),
                eq(new String[]{"alerts", "calendars"}))).thenReturn(wrapperRequestMock);
        when(wrapperRequestMock.get()).thenReturn(expectedWrapperOperationResultMock);
        AlertsService serviceSpy = spy(new AlertsService(sessionStorageMock));

        // When
        OperationResult<ClientCalendarNameListWrapper> retrieved = serviceSpy.calendar((ClientAlertCalendar.Type) null);

        // Then
        assertNotNull(retrieved);
        assertSame(retrieved, expectedWrapperOperationResultMock);

        verifyStatic(JerseyRequest.class, times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(ClientCalendarNameListWrapper.class),
                eq(new String[]{"alerts", "calendars"}));

        verify(wrapperRequestMock, times(1)).get();
        verify(wrapperRequestMock, never()).addParam("calendarType", CalendarType.daily.toString().toLowerCase());
    }


    @Test(testName = "calendar")
    public void should_return_an_calendar_adapter() throws Exception {

        // Given
        final String calendarName = "testCalendar";
        AlertsService serviceSpy = spy(new AlertsService(sessionStorageMock));
        whenNew(SingleCalendarOperationsAdapter.class)
                .withParameterTypes(SessionStorage.class, String.class)
                .withArguments(sessionStorageMock, calendarName)
                .thenReturn(expectedCalendarOperationsAdapterMock);

        // When
        SingleCalendarOperationsAdapter retrieved = serviceSpy.calendar(calendarName);

        // Then
        assertNotNull(retrieved);
        assertSame(retrieved, expectedCalendarOperationsAdapterMock);
    }

    @Test(testName = "calendar", expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_invalid_calendar_name() throws Exception {
        AlertsService serviceSpy = spy(new AlertsService(sessionStorageMock));
        serviceSpy.calendar("");
    }

    @Test
    public void should_get_calendars_asynchronously() throws InterruptedException {

        /* Given */
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(buildRequest(eq(sessionStorageMock),
                eq(ClientCalendarNameListWrapper.class),
                eq(new String[]{"alerts", "calendars"}))).thenReturn(wrapperRequestMock);

        PowerMockito.doReturn(expectedWrapperOperationResultMock).when(wrapperRequestMock).get();
        AlertsService serviceSpy = PowerMockito.spy(new AlertsService(sessionStorageMock));

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<ClientCalendarNameListWrapper>, Void> callback = PowerMockito.spy(new Callback<OperationResult<ClientCalendarNameListWrapper>, Void>() {
            @Override
            public Void execute(OperationResult<ClientCalendarNameListWrapper> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        PowerMockito.doNothing().when(callback).execute(expectedWrapperOperationResultMock);

        /* When */
        RequestExecution retrieved = serviceSpy.asyncCalendar(ClientAlertCalendar.Type.annual, callback);

        /* Wait */
        synchronized (callback) {
            callback.wait(1000);
        }

        /* Then */
        Assert.assertNotNull(retrieved);
        Assert.assertNotSame(currentThreadId, newThreadId.get());

        Mockito.verify(wrapperRequestMock).get();
        Mockito.verify(callback).execute(expectedWrapperOperationResultMock);
        Mockito.verify(wrapperRequestMock).addParam("calendarType", "annual");
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, expectedAdapterMock, expectedAlertOperationsAdapter,
                alertRequestMock, expectedAlertOperationResultMock, reportMock, configurationMock);
    }
}