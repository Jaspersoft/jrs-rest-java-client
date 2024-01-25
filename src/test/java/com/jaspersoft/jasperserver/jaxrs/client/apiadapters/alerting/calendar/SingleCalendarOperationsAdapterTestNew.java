package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alerting.calendar;

import com.jaspersoft.jasperserver.dto.alerting.ClientAlertCalendar;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alerting.calendar.CalendarParameter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alerting.calendar.SingleCalendarOperationsAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.*;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.WithEntityOperationResult;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.powermock.reflect.Whitebox;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.testng.AssertJUnit.*;


/**
 * Unit tests for {@link SingleCalendarOperationsAdapter}
 */
@PrepareForTest({JerseyRequest.class, SingleCalendarOperationsAdapter.class, MultivaluedHashMap.class})
public class SingleCalendarOperationsAdapterTestNew extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<ClientAlertCalendar> requestMock;

    @Mock
    private JerseyRequest<Object> objRequestMock;

    @Mock
    private OperationResult<Object> delResultMock;

    @Mock
    private OperationResult<ClientAlertCalendar> operationResultMock;

    @Mock
    private ClientAlertCalendar reportAlertCalendarMock;

    @Mock
    private Response responseMock;

    @Mock
    private RequestBuilder<ClientAlertCalendar> builderMock;

    @Mock
    private WithEntityOperationResult<ClientAlertCalendar> withEntityOperationResultMock;

    @Mock
    private ClientAlertCalendar calendarEntityMock;

    @Mock
    private Callback<OperationResult<ClientAlertCalendar>, Object> callbackMock;

    @Mock
    private Callback<OperationResult<ClientAlertCalendar>, Object> callbackMock3;

    @Mock
    private Callback<OperationResult, Object> resultObjectCallbackMock;

    private MultivaluedHashMap<String, String> paramsSpy;

    @BeforeMethod
    public void before() {
        initMocks(this);
        paramsSpy = spy(new MultivaluedHashMap<String, String>());
    }

    @Test
    public void should_pass_proper_params_to_super_class() {

        // When
        SingleCalendarOperationsAdapter calendarOperationsAdapter =
                new SingleCalendarOperationsAdapter(sessionStorageMock, "testCalendarName");

        // Then
        assertSame(calendarOperationsAdapter.getSessionStorage(), sessionStorageMock);
        Object calendarName = Whitebox.getInternalState(calendarOperationsAdapter, "calendarName");
        Object params = Whitebox.getInternalState(calendarOperationsAdapter, "params");

        assertNotNull(params);
        assertSame(calendarName, "testCalendarName");

        assertTrue(instanceOf(String.class).matches(calendarName));
        assertTrue(instanceOf(MultivaluedHashMap.class).matches(params));


    }

    @Test
    @SuppressWarnings("unchecked")
    public void should_set_parameter_field_of_adapter() {

        // Given
        SingleCalendarOperationsAdapter adapter = new SingleCalendarOperationsAdapter(sessionStorageMock, "MyCal");

        // When
        adapter.parameter(CalendarParameter.REPLACE, "true");
        MultivaluedMap<String, String> params = (MultivaluedMap<String, String>) Whitebox.getInternalState(adapter, "params");

        // Then
        Assert.assertTrue(Boolean.valueOf(params.get("replace").get(0)));
    }

    @Test(enabled = true)
    public void parameter() throws Exception {

        // Given
        PowerMockito.whenNew(MultivaluedHashMap.class).withNoArguments().thenReturn(paramsSpy);
        SingleCalendarOperationsAdapter adapterSpy = Mockito.spy(new SingleCalendarOperationsAdapter(sessionStorageMock, "testCalendarName"));

        // When
        SingleCalendarOperationsAdapter retrieved = adapterSpy.parameter(CalendarParameter.UPDATE_TRIGGERS, "testValue");

        // Then
        verify(adapterSpy, times(1)).parameter(CalendarParameter.UPDATE_TRIGGERS, "testValue");
        verify(paramsSpy, times(1)).add(CalendarParameter.UPDATE_TRIGGERS.getName(), "testValue");
        verify(paramsSpy, never()).getFirst(anyString());

        Assert.assertSame(retrieved, adapterSpy);
        Assert.assertTrue(paramsSpy.size() == 1);
    }

    @Test
    public void get() throws Exception {

        // Given
          SingleCalendarOperationsAdapter adapterSpy = spy(new SingleCalendarOperationsAdapter(sessionStorageMock,
        "testCalendarName"));
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(ClientAlertCalendar.class),
                eq(new String[]{"alerts", "calendars", "testCalendarName"}))).thenReturn(requestMock);
        doReturn(operationResultMock).when(requestMock).get();

        // When
        OperationResult<ClientAlertCalendar> retrieved = adapterSpy.getCalendar();

        // Then
        verifyStatic(JerseyRequest.class, times(1));
        buildRequest(eq(sessionStorageMock), eq(ClientAlertCalendar.class),
                eq(new String[]{"alerts", "calendars", "testCalendarName"}));
        assertSame(retrieved, operationResultMock);
    }

    @Test
    public void delete() throws Exception {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock),
                eq(Object.class),
                eq(new String[]{"alerts", "calendars", "testCalendarName"}))).thenReturn(objRequestMock);
        doReturn(delResultMock).when(objRequestMock).delete();
        SingleCalendarOperationsAdapter adapterSpy = spy(new SingleCalendarOperationsAdapter(sessionStorageMock, "testCalendarName"));

        // When
        OperationResult retrieved = adapterSpy.delete();

        // Then
        verifyStatic(JerseyRequest.class, times(1));
        buildRequest(eq(sessionStorageMock), eq(Object.class), eq(new String[]{"alerts", "calendars", "testCalendarName"}));
        assertSame(retrieved, delResultMock);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void createNew() {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock),
                eq(ClientAlertCalendar.class),
                eq(new String[]{"alerts", "calendars", "testCalendarName"}))).thenReturn(requestMock);
        PowerMockito.when(requestMock.put(calendarEntityMock)).thenReturn(operationResultMock);
        SingleCalendarOperationsAdapter adapterSpy = spy(new SingleCalendarOperationsAdapter(sessionStorageMock, "testCalendarName"));

        // When
        OperationResult<ClientAlertCalendar> retrieved = adapterSpy.createNewCalendar(calendarEntityMock);

        // Then
        verifyStatic(JerseyRequest.class, times(1));
        buildRequest(eq(sessionStorageMock),
                eq(ClientAlertCalendar.class),
                eq(new String[]{"alerts", "calendars", "testCalendarName"}));
        assertSame(retrieved, operationResultMock);

        MultivaluedMap<String, String> retrievedParams = (MultivaluedMap<String, String>) Whitebox.getInternalState(adapterSpy, "params");
        Mockito.verify(requestMock).addParams(retrievedParams);
        Mockito.verify(requestMock).put(calendarEntityMock);
    }

    @Test
    public void asyncGet() throws Exception {

        // Given
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(
                buildRequest(
                        eq(sessionStorageMock),
                        eq(ClientAlertCalendar.class),
                        eq(new String[]{"alerts", "calendars", "testCalendarName"})))
                .thenReturn(requestMock);

        SingleCalendarOperationsAdapter adapterSpy = spy(new SingleCalendarOperationsAdapter(sessionStorageMock, "testCalendarName"));

        Callback<OperationResult<ClientAlertCalendar>, Void> callback = PowerMockito.spy(new Callback<OperationResult<ClientAlertCalendar>, Void>() {
            public Void execute(OperationResult<ClientAlertCalendar> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        PowerMockito.doReturn(operationResultMock).when(requestMock).get();
        PowerMockito.doNothing().when(callback).execute(operationResultMock);

        // When
        RequestExecution retrieved = adapterSpy.asyncGetCalendar(callback);

        // Wait
        synchronized (callback) {
            callback.wait(500);
        }

        // Then
        Assert.assertNotNull(retrieved);
        Assert.assertNotSame(currentThreadId, newThreadId.get());
    }

    @Test(enabled = true)
    public void asyncDelete() throws Exception {

        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(
                buildRequest(
                        eq(sessionStorageMock),
                        eq(Object.class),
                        eq(new String[]{"alerts", "calendars", "testCalendarName"})))
                .thenReturn(objRequestMock);

        SingleCalendarOperationsAdapter adapterSpy = spy(new SingleCalendarOperationsAdapter(sessionStorageMock, "testCalendarName"));
        PowerMockito.doReturn(delResultMock).when(objRequestMock).delete();
        PowerMockito.doReturn(new Object()).when(resultObjectCallbackMock).execute(delResultMock);

        // When
        adapterSpy.asyncDelete(resultObjectCallbackMock);

        // Then
        verifyStatic(JerseyRequest.class, times(1));
        JerseyRequest.buildRequest(
                eq(sessionStorageMock),
                eq(Object.class),
                eq(new String[]{"alerts", "calendars", "testCalendarName"}));

    }

    @Test
    public void asyncCreateNew() throws Exception {

        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.whenNew(MultivaluedHashMap.class).withNoArguments().thenReturn(paramsSpy);
        PowerMockito.when(
                buildRequest(
                        eq(sessionStorageMock),
                        eq(ClientAlertCalendar.class),
                        eq(new String[]{"alerts", "calendars", "testCalendarName"})))
                .thenReturn(requestMock);

        SingleCalendarOperationsAdapter adapterSpy = PowerMockito.spy(new SingleCalendarOperationsAdapter(sessionStorageMock, "testCalendarName"));

        PowerMockito.doReturn(operationResultMock).when(requestMock).put(calendarEntityMock);
        PowerMockito.doReturn(new Object()).when(callbackMock3).execute(operationResultMock);
        PowerMockito.doReturn(builderMock).when(requestMock).addParams(paramsSpy);
        // When
        adapterSpy.asyncCreateNewCalendar(calendarEntityMock, callbackMock3);

        // Then
        verifyStatic(JerseyRequest.class, times(1));
        JerseyRequest.buildRequest(
                eq(sessionStorageMock),
                eq(ClientAlertCalendar.class),
                eq(new String[]{"alerts", "calendars", "testCalendarName"}));
    }

    @Test
    public void should_delete_calendar_asynchronously() throws InterruptedException {

        /* Given */
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(buildRequest(eq(sessionStorageMock),
                eq(Object.class),
                eq(new String[]{"alerts", "calendars", "testCalendarName"}))).thenReturn(objRequestMock);

        PowerMockito.doReturn(delResultMock).when(objRequestMock).delete();

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult, Void> callback = PowerMockito.spy(new Callback<OperationResult, Void>() {
            @Override
            public Void execute(OperationResult data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        SingleCalendarOperationsAdapter adapterSpy = PowerMockito.spy(new SingleCalendarOperationsAdapter(sessionStorageMock, "testCalendarName"));

        /* When */
        RequestExecution retrieved = adapterSpy.asyncDelete(callback);

        /* Wait */
        synchronized (callback) {
            callback.wait(500);
        }

        /* Then */
        Assert.assertNotNull(retrieved);
        Assert.assertNotSame(currentThreadId, newThreadId.get());

        Mockito.verify(objRequestMock).delete();
        Mockito.verify(callback).execute(delResultMock);
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, requestMock, objRequestMock, delResultMock,
                operationResultMock, reportAlertCalendarMock, responseMock, withEntityOperationResultMock,
                calendarEntityMock);
    }
}