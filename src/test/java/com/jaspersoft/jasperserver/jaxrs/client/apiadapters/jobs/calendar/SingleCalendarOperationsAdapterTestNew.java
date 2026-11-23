package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.calendar;

import com.jaspersoft.jasperserver.dto.job.ClientJobCalendar;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.WithEntityOperationResult;
import java.util.concurrent.atomic.AtomicInteger;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertSame;
import static org.testng.AssertJUnit.assertTrue;


/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.jobs.calendar.SingleCalendarOperationsAdapter}
 */
public class SingleCalendarOperationsAdapterTestNew {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<ClientJobCalendar> requestMock;

    @Mock
    private JerseyRequest<Object> objRequestMock;

    @Mock
    private OperationResult<Object> delResultMock;

    @Mock
    private OperationResult<ClientJobCalendar> operationResultMock;

    @Mock
    private ClientJobCalendar reportJobCalendarMock;

    @Mock
    private Response responseMock;

    @Mock
    private RequestBuilder<ClientJobCalendar> builderMock;

    @Mock
    private WithEntityOperationResult<ClientJobCalendar> withEntityOperationResultMock;

    @Mock
    private ClientJobCalendar calendarEntityMock;

    @Mock
    private Callback<OperationResult<ClientJobCalendar>, Object> callbackMock;

    @Mock
    private Callback<OperationResult<ClientJobCalendar>, Object> callbackMock3;

    @Mock
    private Callback<OperationResult, Object> resultObjectCallbackMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_pass_proper_params_to_super_class() {

        // When
        SingleCalendarOperationsAdapter calendarOperationsAdapter =
                new SingleCalendarOperationsAdapter(sessionStorageMock, "testCalendarName");

        // Then
        assertSame(calendarOperationsAdapter.getSessionStorage(), sessionStorageMock);
        Object calendarName = getInternalState(calendarOperationsAdapter, "calendarName");
        Object params = getInternalState(calendarOperationsAdapter, "params");

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
        MultivaluedMap<String, String> params = (MultivaluedMap<String, String>) getInternalState(adapter, "params");

        // Then
        Assert.assertTrue(Boolean.valueOf(params.get("replace").get(0)));
    }

    @Test(enabled = true)
    public void parameter() throws Exception {

        // Given
        SingleCalendarOperationsAdapter adapterSpy = Mockito.spy(new SingleCalendarOperationsAdapter(sessionStorageMock, "testCalendarName"));

        // When
        SingleCalendarOperationsAdapter retrieved = adapterSpy.parameter(CalendarParameter.UPDATE_TRIGGERS, "testValue");

        // Then
        verify(adapterSpy, times(1)).parameter(CalendarParameter.UPDATE_TRIGGERS, "testValue");
        Assert.assertSame(retrieved, adapterSpy);

        MultivaluedMap<String, String> params = (MultivaluedMap<String, String>) getInternalState(adapterSpy, "params");
        Assert.assertTrue(params.containsKey(CalendarParameter.UPDATE_TRIGGERS.getName()));
    }

    @Test
    public void get() throws Exception {

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            // Given
            SingleCalendarOperationsAdapter adapterSpy = spy(new SingleCalendarOperationsAdapter(sessionStorageMock,
                    "testCalendarName"));
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(ClientJobCalendar.class),
                    eq(new String[]{"jobs", "calendars", "testCalendarName"}))).thenReturn(requestMock);
            doReturn(operationResultMock).when(requestMock).get();

            // When
            OperationResult<ClientJobCalendar> retrieved = adapterSpy.getCalendar();

            // Then
            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(ClientJobCalendar.class),
                    eq(new String[]{"jobs", "calendars", "testCalendarName"})), times(1));
            assertSame(retrieved, operationResultMock);
        }
    }

    @Test
    public void delete() throws Exception {

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            // Given
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock),
                    eq(Object.class),
                    eq(new String[]{"jobs", "calendars", "testCalendarName"}))).thenReturn(objRequestMock);
            doReturn(delResultMock).when(objRequestMock).delete();
            SingleCalendarOperationsAdapter adapterSpy = spy(new SingleCalendarOperationsAdapter(sessionStorageMock, "testCalendarName"));

            // When
            OperationResult retrieved = adapterSpy.delete();

            // Then
            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(Object.class),
                    eq(new String[]{"jobs", "calendars", "testCalendarName"})), times(1));
            assertSame(retrieved, delResultMock);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void createNew() {

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            // Given
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock),
                    eq(ClientJobCalendar.class),
                    eq(new String[]{"jobs", "calendars", "testCalendarName"}))).thenReturn(requestMock);
            doReturn(operationResultMock).when(requestMock).put(calendarEntityMock);
            SingleCalendarOperationsAdapter adapterSpy = spy(new SingleCalendarOperationsAdapter(sessionStorageMock, "testCalendarName"));

            // When
            OperationResult<ClientJobCalendar> retrieved = adapterSpy.createNewCalendar(calendarEntityMock);

            // Then
            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock),
                    eq(ClientJobCalendar.class),
                    eq(new String[]{"jobs", "calendars", "testCalendarName"})), times(1));
            assertSame(retrieved, operationResultMock);

            MultivaluedMap<String, String> retrievedParams = (MultivaluedMap<String, String>) getInternalState(adapterSpy, "params");
            Mockito.verify(requestMock).addParams(retrievedParams);
            Mockito.verify(requestMock).put(calendarEntityMock);
        }
    }

    @Test
    public void asyncGet() throws Exception {

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            // Given
            final AtomicInteger newThreadId = new AtomicInteger();
            final int currentThreadId = (int) Thread.currentThread().getId();

            mockedStatic.when(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(ClientJobCalendar.class),
                    eq(new String[]{"jobs", "calendars", "testCalendarName"})))
                    .thenReturn(requestMock);

            SingleCalendarOperationsAdapter adapterSpy = spy(new SingleCalendarOperationsAdapter(sessionStorageMock, "testCalendarName"));

            Callback<OperationResult<ClientJobCalendar>, Void> callback = Mockito.spy(new Callback<OperationResult<ClientJobCalendar>, Void>() {
                public Void execute(OperationResult<ClientJobCalendar> data) {
                    newThreadId.set((int) Thread.currentThread().getId());
                    synchronized (this) {
                        this.notify();
                    }
                    return null;
                }
            });

            doReturn(operationResultMock).when(requestMock).get();
            doNothing().when(callback).execute(operationResultMock);

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
    }

    @Test(enabled = true)
    public void asyncDelete() throws Exception {

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(Object.class),
                    eq(new String[]{"jobs", "calendars", "testCalendarName"})))
                    .thenReturn(objRequestMock);

            SingleCalendarOperationsAdapter adapterSpy = spy(new SingleCalendarOperationsAdapter(sessionStorageMock, "testCalendarName"));
            doReturn(delResultMock).when(objRequestMock).delete();
            doReturn(new Object()).when(resultObjectCallbackMock).execute(delResultMock);

            // When
            adapterSpy.asyncDelete(resultObjectCallbackMock);

            // Then
            mockedStatic.verify(() -> JerseyRequest.buildRequest(
                    eq(sessionStorageMock),
                    eq(Object.class),
                    eq(new String[]{"jobs", "calendars", "testCalendarName"})), times(1));
        }
    }

    @Test
    public void asyncCreateNew() throws Exception {

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(ClientJobCalendar.class),
                    eq(new String[]{"jobs", "calendars", "testCalendarName"})))
                    .thenReturn(requestMock);

            SingleCalendarOperationsAdapter adapterSpy = Mockito.spy(new SingleCalendarOperationsAdapter(sessionStorageMock, "testCalendarName"));

            doReturn(operationResultMock).when(requestMock).put(calendarEntityMock);
            doReturn(new Object()).when(callbackMock3).execute(operationResultMock);
            // When
            adapterSpy.asyncCreateNewCalendar(calendarEntityMock, callbackMock3);

            // Then
            mockedStatic.verify(() -> JerseyRequest.buildRequest(
                    eq(sessionStorageMock),
                    eq(ClientJobCalendar.class),
                    eq(new String[]{"jobs", "calendars", "testCalendarName"})), times(1));
        }
    }

    @Test
    public void should_delete_calendar_asynchronously() throws InterruptedException {

        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            /* Given */
            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock),
                    eq(Object.class),
                    eq(new String[]{"jobs", "calendars", "testCalendarName"}))).thenReturn(objRequestMock);

            doReturn(delResultMock).when(objRequestMock).delete();

            final AtomicInteger newThreadId = new AtomicInteger();
            final int currentThreadId = (int) Thread.currentThread().getId();

            final Callback<OperationResult, Void> callback = Mockito.spy(new Callback<OperationResult, Void>() {
                @Override
                public Void execute(OperationResult data) {
                    newThreadId.set((int) Thread.currentThread().getId());
                    synchronized (this) {
                        this.notify();
                    }
                    return null;
                }
            });

            SingleCalendarOperationsAdapter adapterSpy = Mockito.spy(new SingleCalendarOperationsAdapter(sessionStorageMock, "testCalendarName"));

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
    }

    @AfterMethod
    public void after() throws Exception {
        reset(sessionStorageMock, requestMock, objRequestMock, delResultMock,
                operationResultMock, reportJobCalendarMock, responseMock, withEntityOperationResultMock,
                calendarEntityMock);
        mockitoSession.close();
    }

    @SuppressWarnings("unchecked")
    private static <T> T getInternalState(Object target, String fieldName) {
        try {
            Class<?> clazz = target.getClass();
            while (clazz != null) {
                try {
                    java.lang.reflect.Field field = clazz.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    return (T) field.get(target);
                } catch (NoSuchFieldException e) {
                    clazz = clazz.getSuperclass();
                }
            }
            throw new NoSuchFieldException(fieldName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
