package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.attributes;

import com.jaspersoft.jasperserver.dto.authority.hypermedia.HypermediaAttribute;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class SingleAttributeAdapterTest {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<HypermediaAttribute> requestMock;

    @Mock
    private OperationResult<HypermediaAttribute> resultMock;

    private AutoCloseable mocks;

    @BeforeMethod
    public void before() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_pass_proper_session_storage_to_parent_class_and_set_own_fields() throws Exception {
        SingleAttributeAdapter adapter = new SingleAttributeAdapter("MyCoolOrg", "Simon", sessionStorageMock, "State");

        Field field = SingleAttributeAdapter.class.getDeclaredField("path");
        field.setAccessible(true);
        ArrayList<String> uri = (ArrayList<String>) field.get(adapter);

        assertNotNull(adapter);
        assertEquals(uri.get(0), "organizations");
        assertEquals(uri.get(1), "MyCoolOrg");
        assertEquals(uri.get(2), "users");
        assertEquals(uri.get(3), "Simon");
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
    }

    @Test
    public void should_invoke_private_method_and_return_a_mock() throws Exception {
        // Given
        SingleAttributeAdapter adapter = spy(new SingleAttributeAdapter("MyCoolOrg", "Simon", sessionStorageMock, "State"));
        doReturn(requestMock).when(adapter).buildRequest();
        when(requestMock.get()).thenReturn(resultMock);
        // When
        adapter.get();
        // Then
        verify(adapter, times(1)).buildRequest();
        verify(requestMock, times(1)).get();
        verifyNoMoreInteractions(requestMock);
    }

    @Test
    public void should_return_attribute_asynchronously() throws Exception {
        SingleAttributeAdapter adapter = spy(new SingleAttributeAdapter("MyCoolOrg", "Simon", sessionStorageMock, "State"));

        doReturn(requestMock).when(adapter).buildRequest();
        when(requestMock.get()).thenReturn(resultMock);

        AtomicInteger newThreadId = new AtomicInteger();
        int currentThreadId = (int) Thread.currentThread().getId();

        Callback<OperationResult<HypermediaAttribute>, Void> callback = spy(new Callback<>() {
            @Override
            public Void execute(OperationResult<HypermediaAttribute> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        doNothing().when(callback).execute(resultMock);

        RequestExecution retrieved = adapter.asyncGet(callback);

        synchronized (callback) {
            callback.wait(1000);
        }

        verify(requestMock).get();
        verify(callback).execute(resultMock);
        assertNotNull(retrieved);
        assertNotEquals(currentThreadId, newThreadId.get());
    }

    @Test
    public void should_delete_attribute_asynchronously() throws Exception {
        SingleAttributeAdapter adapter = spy(new SingleAttributeAdapter("MyCoolOrg", "Simon", sessionStorageMock, "State"));

        doReturn(requestMock).when(adapter).buildRequest();
        when(requestMock.delete()).thenReturn(resultMock);

        AtomicInteger newThreadId = new AtomicInteger();
        int currentThreadId = (int) Thread.currentThread().getId();

        Callback<OperationResult<HypermediaAttribute>, Void> callback = spy(new Callback<>() {
            @Override
            public Void execute(OperationResult<HypermediaAttribute> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        doNothing().when(callback).execute(resultMock);

        RequestExecution retrieved = adapter.asyncDelete(callback);

        synchronized (callback) {
            callback.wait(1000);
        }

        verify(requestMock).delete();
        verify(callback).execute(resultMock);
        assertNotNull(retrieved);
        assertNotEquals(currentThreadId, newThreadId.get());
    }

    @Test
    public void should_create_or_update_attribute_for_user_asynchronously() throws Exception {
        SingleAttributeAdapter adapter = spy(new SingleAttributeAdapter("MyCoolOrg", "Simon", sessionStorageMock, "State"));
        HypermediaAttribute userAttributeMock = mock(HypermediaAttribute.class);

        doReturn(requestMock).when(adapter).buildRequest();
        when(requestMock.put(userAttributeMock)).thenReturn(resultMock);

        AtomicInteger newThreadId = new AtomicInteger();
        int currentThreadId = (int) Thread.currentThread().getId();

        Callback<OperationResult<HypermediaAttribute>, Void> callback = spy(new Callback<>() {
            @Override
            public Void execute(OperationResult<HypermediaAttribute> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        doNothing().when(callback).execute(resultMock);

        RequestExecution retrieved = adapter.asyncCreateOrUpdate(userAttributeMock, callback);

        synchronized (callback) {
            callback.wait(1000);
        }

        verify(requestMock).put(userAttributeMock);
        verify(callback).execute(resultMock);
        assertNotNull(retrieved);
        assertNotEquals(currentThreadId, newThreadId.get());
    }

    @Test
    public void should_invoke_private_method() {
        // Given
        final HypermediaAttribute userAttributeMock = mock(HypermediaAttribute.class);
        final SingleAttributeAdapter adapter = new SingleAttributeAdapter("MyCoolOrg", "Simon", sessionStorageMock, "State");
        RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
        try (MockedStatic<JerseyRequest> jerseyRequestMock = mockStatic(JerseyRequest.class)) {
            jerseyRequestMock.when(() -> JerseyRequest.buildRequest(
                    eq(sessionStorageMock),
                    eq(HypermediaAttribute.class),
                    eq(new String[]{"organizations", "MyCoolOrg", "users", "Simon", "attributes", "State"}),
                    any(DefaultErrorHandler.class)
            )).thenReturn(requestMock);
            when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
            when(configurationMock.getContentMimeType()).thenReturn(MimeType.JSON);
            when(requestMock.setContentType(anyString())).thenReturn(requestMock);
            when(requestMock.put(userAttributeMock)).thenReturn(resultMock);
            // When
            OperationResult<HypermediaAttribute> retrieved = adapter.createOrUpdate(userAttributeMock);
            // Then
            assertNotNull(retrieved);
            jerseyRequestMock.verify(() -> JerseyRequest.buildRequest(
                    eq(sessionStorageMock),
                    eq(HypermediaAttribute.class),
                    eq(new String[]{"organizations", "MyCoolOrg", "users", "Simon", "attributes", "State"}),
                    any(DefaultErrorHandler.class)
            ), times(1));
            verify(requestMock, times(1)).put(userAttributeMock);
        }
    }

    @Test
    public void should_set_headers_and_invoke_private_method() {
        // Given
        final SingleAttributeAdapter adapter = new SingleAttributeAdapter("MyCoolOrg", "Simon", sessionStorageMock, "State");
        RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
        try (MockedStatic<JerseyRequest> jerseyRequestMock = mockStatic(JerseyRequest.class)) {
            jerseyRequestMock.when(() -> JerseyRequest.buildRequest(
                    eq(sessionStorageMock),
                    eq(HypermediaAttribute.class),
                    eq(new String[]{"organizations", "MyCoolOrg", "users", "Simon", "attributes", "State"}),
                    any(DefaultErrorHandler.class)
            )).thenReturn(requestMock);
            when(requestMock.setAccept(anyString())).thenReturn(requestMock);
            when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
            when(configurationMock.getAcceptMimeType()).thenReturn(MimeType.JSON);
            when(requestMock.addParam(anyString(), anyString())).thenReturn(requestMock);
            when(requestMock.get()).thenReturn(resultMock);
            // When
            OperationResult<HypermediaAttribute> retrieved = adapter.setIncludePermissions(true).get();
            // Then
            assertNotNull(retrieved);
            jerseyRequestMock.verify(() -> JerseyRequest.buildRequest(
                    eq(sessionStorageMock),
                    eq(HypermediaAttribute.class),
                    eq(new String[]{"organizations", "MyCoolOrg", "users", "Simon", "attributes", "State"}),
                    any(DefaultErrorHandler.class)
            ), times(1));
            verify(requestMock, times(1)).get();
            verify(requestMock, times(1)).setAccept("application/hal+json");
            verify(requestMock, times(1)).addParam("_embedded", "permission");
        }
    }

    @Test
    public void should_set_headers_and_invoke_private_method_for_user_in_root_by_default() {
        // Given
        final SingleAttributeAdapter adapter = new SingleAttributeAdapter(null, "Simon", sessionStorageMock, "State");
        RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
        try (MockedStatic<JerseyRequest> jerseyRequestMock = mockStatic(JerseyRequest.class)) {
            jerseyRequestMock.when(() -> JerseyRequest.buildRequest(
                    eq(sessionStorageMock),
                    eq(HypermediaAttribute.class),
                    eq(new String[]{"users", "Simon", "attributes", "State"}),
                    any(DefaultErrorHandler.class)
            )).thenReturn(requestMock);
            when(requestMock.setAccept(anyString())).thenReturn(requestMock);
            when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
            when(configurationMock.getAcceptMimeType()).thenReturn(MimeType.JSON);
            when(requestMock.addParam(anyString(), anyString())).thenReturn(requestMock);
            when(requestMock.get()).thenReturn(resultMock);
            // When
            OperationResult<HypermediaAttribute> retrieved = adapter.setIncludePermissions(true).get();
            // Then
            assertNotNull(retrieved);
            jerseyRequestMock.verify(() -> JerseyRequest.buildRequest(
                    eq(sessionStorageMock),
                    eq(HypermediaAttribute.class),
                    eq(new String[]{"users", "Simon", "attributes", "State"}),
                    any(DefaultErrorHandler.class)
            ), times(1));
            verify(requestMock, times(1)).get();
            verify(requestMock, times(1)).setAccept("application/hal+json");
            verify(requestMock, times(1)).addParam("_embedded", "permission");
        }
    }

    @Test
    public void should_set_headers_and_invoke_private_method_for_user_in_root() {
        // Given
        RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
        try (MockedStatic<JerseyRequest> jerseyRequestMock = mockStatic(JerseyRequest.class)) {
            jerseyRequestMock.when(() -> JerseyRequest.buildRequest(
                    eq(sessionStorageMock),
                    eq(HypermediaAttribute.class),
                    eq(new String[]{"users", "Simon", "attributes", "State"}),
                    any(DefaultErrorHandler.class)
            )).thenReturn(requestMock);
            when(requestMock.setAccept(anyString())).thenReturn(requestMock);
            when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
            when(configurationMock.getAcceptMimeType()).thenReturn(MimeType.JSON);
            when(requestMock.addParam(anyString(), anyString())).thenReturn(requestMock);
            when(requestMock.get()).thenReturn(resultMock);
            // When
            AttributesService service = new AttributesService(sessionStorageMock);
            OperationResult<HypermediaAttribute> retrieved = service
                    .forOrganization("/") // org = "/"
                    .forUser("Simon")
                    .attribute("State")
                    .setIncludePermissions(true).get();
            // Then
            assertNotNull(retrieved);
            jerseyRequestMock.verify(() -> JerseyRequest.buildRequest(
                    eq(sessionStorageMock),
                    eq(HypermediaAttribute.class),
                    eq(new String[]{"users", "Simon", "attributes", "State"}),
                    any(DefaultErrorHandler.class)
            ), times(1));
            verify(requestMock, times(1)).get();
            verify(requestMock, times(1)).setAccept("application/hal+json");
            verify(requestMock, times(1)).addParam("_embedded", "permission");
        }
    }

    @Test
    public void should_invoke_private_method_for_delete_method() {
        // Given
        final SingleAttributeAdapter adapter = new SingleAttributeAdapter("MyCoolOrg", "Simon", sessionStorageMock, "State");
        try (MockedStatic<JerseyRequest> jerseyRequestMock = mockStatic(JerseyRequest.class)) {
            jerseyRequestMock.when(() -> JerseyRequest.buildRequest(
                    eq(sessionStorageMock),
                    eq(HypermediaAttribute.class),
                    eq(new String[]{"organizations", "MyCoolOrg", "users", "Simon", "attributes", "State"}),
                    any(DefaultErrorHandler.class)
            )).thenReturn(requestMock);
            when(requestMock.delete()).thenReturn(resultMock);
            // When
            OperationResult<HypermediaAttribute> retrieved = adapter.delete();
            // Then
            assertNotNull(retrieved);
            jerseyRequestMock.verify(() -> JerseyRequest.buildRequest(
                    eq(sessionStorageMock),
                    eq(HypermediaAttribute.class),
                    eq(new String[]{"organizations", "MyCoolOrg", "users", "Simon", "attributes", "State"}),
                    any(DefaultErrorHandler.class)
            ), times(1));
            verify(requestMock, times(1)).delete();
        }
    }

    @Test
    public void should_delete_user_attribute() {
        SingleAttributeAdapter adapter = spy(new SingleAttributeAdapter("MyCoolOrg", "Simon", sessionStorageMock, "State"));

        doReturn(requestMock).when(adapter).buildRequest();
        when(requestMock.delete()).thenReturn(resultMock);

        OperationResult<HypermediaAttribute> retrieved = adapter.delete();

        assertNotNull(retrieved);
        verify(requestMock).delete();
    }

    @Test
    public void should_create_or_update_attribute_for_user() {
        try (MockedStatic<JerseyRequest> mockedStatic = mockStatic(JerseyRequest.class)) {
            HypermediaAttribute userAttributeMock = mock(HypermediaAttribute.class);
            RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
            SingleAttributeAdapter adapter = new SingleAttributeAdapter("MyCoolOrg", "Simon", sessionStorageMock, "State");

            mockedStatic.when(() -> buildRequest(eq(sessionStorageMock), eq(HypermediaAttribute.class),
                    eq(new String[]{"organizations", "MyCoolOrg", "users", "Simon", "attributes", "State"}),
                    any(DefaultErrorHandler.class))).thenReturn(requestMock);

            when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
            when(configurationMock.getContentMimeType()).thenReturn(MimeType.JSON);
            when(requestMock.setContentType(anyString())).thenReturn(requestMock);
            when(requestMock.put(userAttributeMock)).thenReturn(resultMock);

            OperationResult<HypermediaAttribute> retrieved = adapter.createOrUpdate(userAttributeMock);

            assertNotNull(retrieved);
            mockedStatic.verify(() -> buildRequest(eq(sessionStorageMock), eq(HypermediaAttribute.class),
                    eq(new String[]{"organizations", "MyCoolOrg", "users", "Simon", "attributes", "State"}),
                    any(DefaultErrorHandler.class)), times(1));
            verify(requestMock, times(1)).put(userAttributeMock);
        }
    }

    @AfterMethod
    public void after() throws Exception {
        mocks.close();
        reset(sessionStorageMock, requestMock, resultMock);
    }
}
