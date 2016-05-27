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
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.internal.util.reflection.Whitebox.getInternalState;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyNoMoreInteractions;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link SingleAttributeAdapter}
 */
@PrepareForTest({SingleAttributeAdapter.class, JerseyRequest.class})
public class SingleAttributeAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<HypermediaAttribute> requestMock;

    @Mock
    private OperationResult<HypermediaAttribute> resultMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_pass_proper_session_storage_to_parent_class_and_set_own_fields() {

        // When
        SingleAttributeAdapter adapter = new SingleAttributeAdapter("MyCoolOrg", "Simon", sessionStorageMock, "State");
        ArrayList<String> uri = (ArrayList<String>) getInternalState(adapter, "path");

        // Then
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
        doReturn(requestMock).when(adapter, "buildRequest");
        doReturn(resultMock).when(requestMock).get();

        // When
        adapter.get();

        // Then
        verifyPrivate(adapter, times(1)).invoke("buildRequest");
        verify(requestMock, times(1)).get();
        verifyNoMoreInteractions(requestMock);
    }

    @Test
    public void should_return_attribute_asynchronously() throws Exception {

        // Given
        SingleAttributeAdapter adapter = spy(new SingleAttributeAdapter("MyCoolOrg", "Simon", sessionStorageMock, "State"));
        doReturn(requestMock).when(adapter, "buildRequest");
        doReturn(resultMock).when(requestMock).get();

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<HypermediaAttribute>, Void> callback = spy(new Callback<OperationResult<HypermediaAttribute>, Void>() {
            @Override
            public Void execute(OperationResult<HypermediaAttribute> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        doReturn(null).when(callback).execute(resultMock);

        // When
        RequestExecution retrieved = adapter.asyncGet(callback);

        synchronized (callback) {
            callback.wait(1000);
        }

        // Then
        verify(requestMock).get();
        verify(callback).execute(resultMock);
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
    }

    @Test
    public void should_delete_attribute_asynchronously() throws Exception {

        // Given
        SingleAttributeAdapter adapter = spy(new SingleAttributeAdapter("MyCoolOrg", "Simon", sessionStorageMock, "State"));
        doReturn(requestMock).when(adapter, "buildRequest");
        doReturn(resultMock).when(requestMock).delete();

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<HypermediaAttribute>, Void> callback = spy(new Callback<OperationResult<HypermediaAttribute>, Void>() {
            @Override
            public Void execute(OperationResult<HypermediaAttribute> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        doReturn(null).when(callback).execute(resultMock);

        // When
        RequestExecution retrieved = adapter.asyncDelete(callback);

        synchronized (callback) {
            callback.wait(1000);
        }

        // Then
        verify(requestMock).delete();
        verify(callback).execute(resultMock);
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
    }

    @Test
    public void should_create_or_update_attribute_for_user_asynchronously() throws Exception {

        // Given
        SingleAttributeAdapter adapter = spy(new SingleAttributeAdapter("MyCoolOrg", "Simon", sessionStorageMock, "State"));
        final HypermediaAttribute userAttributeMock = mock(HypermediaAttribute.class);
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<HypermediaAttribute>, Void> callback = spy(new Callback<OperationResult<HypermediaAttribute>, Void>() {
            @Override
            public Void execute(OperationResult<HypermediaAttribute> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        doReturn(requestMock).when(adapter, "buildRequest");
        doReturn(resultMock).when(requestMock).put(userAttributeMock);
        doReturn(null).when(callback).execute(resultMock);

        // When
        RequestExecution retrieved = adapter.asyncCreateOrUpdate(userAttributeMock, callback);

        synchronized (callback) {
            callback.wait(1000);
        }

        // Then
        verify(requestMock).put(userAttributeMock);
        verify(callback).execute(resultMock);
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
    }

    @Test
    public void should_invoke_private_method() {

        // Given
        final HypermediaAttribute userAttributeMock = mock(HypermediaAttribute.class);
        final SingleAttributeAdapter adapter = new SingleAttributeAdapter("MyCoolOrg", "Simon", sessionStorageMock, "State");
        RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(HypermediaAttribute.class),
                eq(new String[]{"organizations", "MyCoolOrg", "users", "Simon", "attributes", "State"}), any(DefaultErrorHandler.class)))
                .thenReturn(requestMock);
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(requestMock).when(requestMock).setContentType(anyString());
        doReturn(resultMock).when(requestMock).put(userAttributeMock);

        // When
        OperationResult<HypermediaAttribute> retrieved = adapter.createOrUpdate(userAttributeMock);

        // Then
        assertNotNull(retrieved);
        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock),
                eq(HypermediaAttribute.class),
                eq(new String[]{"organizations", "MyCoolOrg", "users", "Simon", "attributes", "State"}),
                any(DefaultErrorHandler.class));
        verify(requestMock, times(1)).put(userAttributeMock);
    }
    @Test
    public void should_set_headers_and_invoke_private_method() {

        // Given
        final SingleAttributeAdapter adapter = new SingleAttributeAdapter("MyCoolOrg", "Simon", sessionStorageMock, "State");

        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(HypermediaAttribute.class),
                eq(new String[]{"organizations", "MyCoolOrg", "users", "Simon", "attributes", "State"}), any(DefaultErrorHandler.class)))
                .thenReturn(requestMock);
        doReturn(requestMock).when(requestMock).setAccept(anyString());
        RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(requestMock).when(requestMock).addParam(anyString(), anyString());
        doReturn(resultMock).when(requestMock).get();

        // When
        OperationResult<HypermediaAttribute> retrieved = adapter.setIncludePermissions(true).get();

        // Then
        assertNotNull(retrieved);
        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock),
                eq(HypermediaAttribute.class),
                eq(new String[]{"organizations", "MyCoolOrg", "users", "Simon", "attributes", "State"}),
                any(DefaultErrorHandler.class));
        verify(requestMock, times(1)).get();
        verify(requestMock, times(1)).setAccept("application/hal+json");
        verify(requestMock, times(1)).addParam("_embedded", "permission");

    }

    @Test
    public void should_set_headers_and_invoke_private_method_for_user_in_root_by_default() {

        // Given
        final SingleAttributeAdapter adapter = new SingleAttributeAdapter(null, "Simon", sessionStorageMock, "State");

        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(HypermediaAttribute.class),
                eq(new String[]{"users", "Simon", "attributes", "State"}), any(DefaultErrorHandler.class)))
                .thenReturn(requestMock);
        doReturn(requestMock).when(requestMock).setAccept(anyString());
        RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(requestMock).when(requestMock).addParam(anyString(), anyString());
        doReturn(resultMock).when(requestMock).get();

        // When
        OperationResult<HypermediaAttribute> retrieved = adapter.setIncludePermissions(true).get();

        // Then
        assertNotNull(retrieved);
        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock),
                eq(HypermediaAttribute.class),
                eq(new String[]{"users", "Simon", "attributes", "State"}),
                any(DefaultErrorHandler.class));
        verify(requestMock, times(1)).get();
        verify(requestMock, times(1)).setAccept("application/hal+json");
        verify(requestMock, times(1)).addParam("_embedded", "permission");

    }

    @Test
    public void should_set_headers_and_invoke_private_method_for_user_in_root() {

        // Given
        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(HypermediaAttribute.class),
                eq(new String[]{"users", "Simon", "attributes", "State"}), any(DefaultErrorHandler.class)))
                .thenReturn(requestMock);
        doReturn(requestMock).when(requestMock).setAccept(anyString());
        RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
        doReturn(MimeType.JSON).when(configurationMock).getAcceptMimeType();
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(requestMock).when(requestMock).addParam(anyString(), anyString());
        doReturn(resultMock).when(requestMock).get();

        // When
        AttributesService service = new AttributesService(sessionStorageMock);
        OperationResult<HypermediaAttribute> retrieved = service
                .forOrganization("/")
                .forUser("Simon")
                .attribute("State")
                .setIncludePermissions(true).get();

        // Then
        assertNotNull(retrieved);
        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock),
                eq(HypermediaAttribute.class),
                eq(new String[]{"users", "Simon", "attributes", "State"}),
                any(DefaultErrorHandler.class));
        verify(requestMock, times(1)).get();
        verify(requestMock, times(1)).setAccept("application/hal+json");
        verify(requestMock, times(1)).addParam("_embedded", "permission");

    }

    @Test
    public void should_invoke_private_method_for_delete_method() {

        // Given
        final SingleAttributeAdapter adapter = new SingleAttributeAdapter("MyCoolOrg", "Simon", sessionStorageMock, "State");

        mockStatic(JerseyRequest.class);
        when(buildRequest(eq(sessionStorageMock), eq(HypermediaAttribute.class),
                eq(new String[]{"organizations", "MyCoolOrg", "users", "Simon", "attributes", "State"}),
                any(DefaultErrorHandler.class)))
                .thenReturn(requestMock);
        doReturn(resultMock).when(requestMock).delete();

        // When
        OperationResult<HypermediaAttribute> retrieved = adapter.delete();

        // Then
        assertNotNull(retrieved);
        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock),
                eq(HypermediaAttribute.class),
                eq(new String[]{"organizations", "MyCoolOrg", "users", "Simon", "attributes", "State"}),
                any(DefaultErrorHandler.class));
        verify(requestMock, times(1)).delete();
    }

    @Test
    public void should_delete_user_attribute() throws Exception {

        // Given
        SingleAttributeAdapter adapter = spy(new SingleAttributeAdapter("MyCoolOrg", "Simon", sessionStorageMock, "State"));
        doReturn(requestMock).when(adapter, "buildRequest");
        doReturn(resultMock).when(requestMock).delete();

        // When
        OperationResult retrieved = adapter.delete();

        // Then
        assertSame(retrieved, resultMock);
        verifyPrivate(adapter, times(1)).invoke("buildRequest");
    }


    @Test
    public void should_create_or_update_attribute_for_user() throws Exception {

        // Given
        final HypermediaAttribute userAttributeMock = mock(HypermediaAttribute.class);
        RestClientConfiguration configurationMock = mock(RestClientConfiguration.class);
        SingleAttributeAdapter adapter = spy(new SingleAttributeAdapter("MyCoolOrg", "Simon", sessionStorageMock, "State"));
        doReturn(requestMock).when(adapter, "buildRequest");
        doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();
        doReturn(requestMock).when(requestMock).setContentType(anyString());
        doReturn(resultMock).when(requestMock).put(userAttributeMock);

        // When
        OperationResult retrieved = adapter.createOrUpdate(userAttributeMock);

        // Then

        assertSame(retrieved, resultMock);
        verifyPrivate(adapter, times(1)).invoke("buildRequest");
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, requestMock, resultMock);
    }
}