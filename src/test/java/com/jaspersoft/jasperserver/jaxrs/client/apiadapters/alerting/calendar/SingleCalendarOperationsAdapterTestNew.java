package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.alerting.calendar;

import com.jaspersoft.jasperserver.dto.alerting.ClientAlertCalendar;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
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

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertSame;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Unit tests for {@link SingleCalendarOperationsAdapter}
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class SingleCalendarOperationsAdapterTestNew {

    private static final String CALENDAR_NAME = "testCalendarName";
    private static final String[] CALENDAR_PATH = {"alerts", "calendars", CALENDAR_NAME};

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
    private ClientAlertCalendar calendarEntityMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        mockitoSession.close();
        Mockito.reset(sessionStorageMock, requestMock, objRequestMock, delResultMock,
                operationResultMock, calendarEntityMock);
    }

    private Object getInternalState(Object obj, String fieldName) throws Exception {
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(obj);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }

    @Test
    public void should_pass_proper_params_to_super_class() throws Exception {
        SingleCalendarOperationsAdapter adapter =
                new SingleCalendarOperationsAdapter(sessionStorageMock, CALENDAR_NAME);

        assertSame(adapter.getSessionStorage(), sessionStorageMock);
        Object calendarName = getInternalState(adapter, "calendarName");
        Object params = getInternalState(adapter, "params");

        assertNotNull(params);
        assertSame(CALENDAR_NAME, calendarName);
        assertTrue(instanceOf(String.class).matches(calendarName));
        assertTrue(instanceOf(MultivaluedHashMap.class).matches(params));
    }

    @Test
    public void should_set_parameter_field_of_adapter() throws Exception {
        SingleCalendarOperationsAdapter adapter =
                new SingleCalendarOperationsAdapter(sessionStorageMock, "MyCal");

        adapter.parameter(CalendarParameter.REPLACE, "true");

        MultivaluedMap<String, String> params =
                (MultivaluedMap<String, String>) getInternalState(adapter, "params");
        Assert.assertTrue(Boolean.parseBoolean(params.getFirst(CalendarParameter.REPLACE.getName())));
    }

    @Test
    public void parameter_should_add_to_params_and_return_self() throws Exception {
        SingleCalendarOperationsAdapter adapter =
                new SingleCalendarOperationsAdapter(sessionStorageMock, CALENDAR_NAME);

        SingleCalendarOperationsAdapter retrieved =
                adapter.parameter(CalendarParameter.UPDATE_TRIGGERS, "testValue");

        assertSame(retrieved, adapter);
        MultivaluedMap<String, String> params =
                (MultivaluedMap<String, String>) getInternalState(adapter, "params");
        Assert.assertEquals(params.size(), 1);
        Assert.assertNotNull(params.getFirst(CalendarParameter.UPDATE_TRIGGERS.getName()));
    }

    @Test
    public void get() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(ClientAlertCalendar.class),
                    eq(CALENDAR_PATH))).thenReturn(requestMock);
            doReturn(operationResultMock).when(requestMock).get();

            SingleCalendarOperationsAdapter adapter =
                    new SingleCalendarOperationsAdapter(sessionStorageMock, CALENDAR_NAME);
            OperationResult<ClientAlertCalendar> retrieved = adapter.getCalendar();

            assertSame(retrieved, operationResultMock);
            verify(requestMock, times(1)).get();
            mockedStatic.verify(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(ClientAlertCalendar.class),
                    eq(CALENDAR_PATH)), times(1));
        }
    }

    @Test
    public void delete() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(Object.class),
                    eq(CALENDAR_PATH))).thenReturn(objRequestMock);
            doReturn(delResultMock).when(objRequestMock).delete();

            SingleCalendarOperationsAdapter adapter =
                    new SingleCalendarOperationsAdapter(sessionStorageMock, CALENDAR_NAME);
            OperationResult retrieved = adapter.delete();

            assertSame(retrieved, delResultMock);
            verify(objRequestMock, times(1)).delete();
            mockedStatic.verify(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(Object.class),
                    eq(CALENDAR_PATH)), times(1));
        }
    }

    @Test
    public void createNew() throws Exception {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(ClientAlertCalendar.class),
                    eq(CALENDAR_PATH))).thenReturn(requestMock);
            doReturn(requestMock).when(requestMock).addParams(Mockito.any());
            doReturn(operationResultMock).when(requestMock).put(calendarEntityMock);

            SingleCalendarOperationsAdapter adapter =
                    new SingleCalendarOperationsAdapter(sessionStorageMock, CALENDAR_NAME);
            MultivaluedMap<String, String> internalParams =
                    (MultivaluedMap<String, String>) getInternalState(adapter, "params");
            OperationResult<ClientAlertCalendar> retrieved = adapter.createNewCalendar(calendarEntityMock);

            assertSame(retrieved, operationResultMock);
            verify(requestMock).addParams(internalParams);
            verify(requestMock).put(calendarEntityMock);
            mockedStatic.verify(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(ClientAlertCalendar.class),
                    eq(CALENDAR_PATH)), times(1));
        }
    }

    @Test
    public void asyncGet() throws InterruptedException {
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(ClientAlertCalendar.class),
                    eq(CALENDAR_PATH))).thenReturn(requestMock);
            doReturn(operationResultMock).when(requestMock).get();

            final Callback<OperationResult<ClientAlertCalendar>, Void> callback =
                    spy(new Callback<OperationResult<ClientAlertCalendar>, Void>() {
                        @Override
                        public Void execute(OperationResult<ClientAlertCalendar> data) {
                            newThreadId.set((int) Thread.currentThread().getId());
                            synchronized (this) {
                                this.notify();
                            }
                            return null;
                        }
                    });

            SingleCalendarOperationsAdapter adapter =
                    new SingleCalendarOperationsAdapter(sessionStorageMock, CALENDAR_NAME);
            RequestExecution retrieved = adapter.asyncGetCalendar(callback);

            synchronized (callback) {
                callback.wait(1000);
            }

            Assert.assertNotNull(retrieved);
            Assert.assertNotEquals(newThreadId.get(), currentThreadId);
            verify(callback).execute(operationResultMock);
        }
    }

    @Test
    public void asyncDelete() throws InterruptedException {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(Object.class),
                    eq(CALENDAR_PATH))).thenReturn(objRequestMock);
            doReturn(delResultMock).when(objRequestMock).delete();

            final Callback<OperationResult, Object> callback =
                    spy(new Callback<OperationResult, Object>() {
                        @Override
                        public Object execute(OperationResult data) {
                            synchronized (this) {
                                this.notify();
                            }
                            return null;
                        }
                    });

            SingleCalendarOperationsAdapter adapter =
                    new SingleCalendarOperationsAdapter(sessionStorageMock, CALENDAR_NAME);
            RequestExecution retrieved = adapter.asyncDelete(callback);

            synchronized (callback) {
                callback.wait(1000);
            }

            Assert.assertNotNull(retrieved);
            verify(objRequestMock).delete();
            verify(callback).execute(delResultMock);
            mockedStatic.verify(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(Object.class),
                    eq(CALENDAR_PATH)), times(1));
        }
    }

    @Test
    public void asyncCreateNew() throws InterruptedException {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(ClientAlertCalendar.class),
                    eq(CALENDAR_PATH))).thenReturn(requestMock);
            doReturn(requestMock).when(requestMock).addParams(Mockito.any());
            doReturn(operationResultMock).when(requestMock).put(calendarEntityMock);

            final Callback<OperationResult<ClientAlertCalendar>, Object> callback =
                    spy(new Callback<OperationResult<ClientAlertCalendar>, Object>() {
                        @Override
                        public Object execute(OperationResult<ClientAlertCalendar> data) {
                            synchronized (this) {
                                this.notify();
                            }
                            return null;
                        }
                    });

            SingleCalendarOperationsAdapter adapter =
                    new SingleCalendarOperationsAdapter(sessionStorageMock, CALENDAR_NAME);
            RequestExecution retrieved = adapter.asyncCreateNewCalendar(calendarEntityMock, callback);

            synchronized (callback) {
                callback.wait(1000);
            }

            Assert.assertNotNull(retrieved);
            verify(requestMock).addParams(Mockito.any(MultivaluedMap.class));
            verify(requestMock).put(calendarEntityMock);
            verify(callback).execute(operationResultMock);
            mockedStatic.verify(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(ClientAlertCalendar.class),
                    eq(CALENDAR_PATH)), times(1));
        }
    }

    @Test
    public void should_delete_calendar_asynchronously() throws InterruptedException {
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> buildRequest(
                    eq(sessionStorageMock),
                    eq(Object.class),
                    eq(CALENDAR_PATH))).thenReturn(objRequestMock);
            doReturn(delResultMock).when(objRequestMock).delete();

            final Callback<OperationResult, Void> callback =
                    spy(new Callback<OperationResult, Void>() {
                        @Override
                        public Void execute(OperationResult data) {
                            newThreadId.set((int) Thread.currentThread().getId());
                            synchronized (this) {
                                this.notify();
                            }
                            return null;
                        }
                    });

            SingleCalendarOperationsAdapter adapter =
                    new SingleCalendarOperationsAdapter(sessionStorageMock, CALENDAR_NAME);
            RequestExecution retrieved = adapter.asyncDelete(callback);

            synchronized (callback) {
                callback.wait(1000);
            }

            Assert.assertNotNull(retrieved);
            Assert.assertNotEquals(newThreadId.get(), currentThreadId);
            verify(objRequestMock).delete();
            verify(callback).execute(delResultMock);
        }
    }
}
