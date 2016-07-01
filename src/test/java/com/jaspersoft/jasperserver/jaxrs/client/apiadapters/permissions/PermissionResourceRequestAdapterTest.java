package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.permissions;

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermissionListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.enums.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import javax.ws.rs.core.MultivaluedMap;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static java.util.Arrays.asList;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.support.membermodification.MemberMatcher.field;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * Unit tests for {@link PermissionResourceRequestAdapter}
 */
@PrepareForTest({JerseyRequest.class})
public class PermissionResourceRequestAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<RepositoryPermissionListWrapper> requestMock;

    @Mock
    private RepositoryPermissionListWrapper wrapperMock;

    @Mock
    private OperationResult<RepositoryPermissionListWrapper> expectedResultMock;

    @Mock
    private RestClientConfiguration configurationMock;

    private String fakeUri = "resourceUri";

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test(testName = "constructor")
    @SuppressWarnings("unchecked")
    public void should_set_fields_in_constructor_and_pass_session_to_parent_class() throws IllegalAccessException {

        // When
        PermissionResourceRequestAdapter adapter = new PermissionResourceRequestAdapter(sessionStorageMock, fakeUri);

        // Reflection hack
        Field resUri = field(PermissionResourceRequestAdapter.class, "resourceUri");
        Field params = field(PermissionResourceRequestAdapter.class, "params");
        String uri = (String) resUri.get(adapter);
        MultivaluedMap<String, String> paramsMap = (MultivaluedMap<String, String>) params.get(adapter);

        // Then
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
        assertNotNull(uri);
        assertNotNull(paramsMap);
    }

    @Test(testName = "permissionRecipient")
    public void should_return_proper_adapter() throws IllegalAccessException {

        // Given
        PermissionResourceRequestAdapter adapter = new PermissionResourceRequestAdapter(sessionStorageMock, fakeUri);

        // When
        SinglePermissionRecipientRequestAdapter retrieved = adapter.permissionRecipient(PermissionRecipient.ROLE, "abc");

        Field recipientField = field(SinglePermissionRecipientRequestAdapter.class, "recipient");
        Field pathField = field(SinglePermissionRecipientRequestAdapter.class, "path");

        String recipient = (String) recipientField.get(retrieved);
        ArrayList<String> path = (ArrayList<String>) pathField.get(retrieved);

        // Then
        assertEquals(recipient, "role:%2Fabc");
        assertEquals(path, asList("permissions", fakeUri));
    }

    @Test(testName = "updateOrCreate")
    public void should_return_proper_OperationResult_instance() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(sessionStorageMock,
                RepositoryPermissionListWrapper.class,
                new String[]{"permissions", fakeUri})).thenReturn(requestMock);
        when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getContentMimeType()).thenReturn(MimeType.JSON);
        doReturn(expectedResultMock).when(requestMock).put(wrapperMock);

        // When
        PermissionResourceRequestAdapter adapter = new PermissionResourceRequestAdapter(sessionStorageMock, fakeUri);
        OperationResult<RepositoryPermissionListWrapper> retrieved = adapter.createOrUpdate(wrapperMock);

        // Then
        verifyStatic(times(1));
        JerseyRequest.buildRequest(sessionStorageMock, RepositoryPermissionListWrapper.class, new String[]{"permissions", fakeUri});

        verify(requestMock, times(1)).setContentType("application/collection+json");
        verify(requestMock, times(1)).put(wrapperMock);
        verifyNoMoreInteractions(requestMock);

        assertNotNull(retrieved);
        assertEquals(retrieved, expectedResultMock);
    }

    @Test(testName = "param")
    @SuppressWarnings("unchecked")
    public void should_set_field_of_adapter_and_invoke_param_method_only_once() throws IllegalAccessException {

        // Given
        PermissionResourceRequestAdapter adapter = spy(new PermissionResourceRequestAdapter(sessionStorageMock, fakeUri));

        // When
        adapter.param(PermissionResourceParameter.EFFECTIVE_PERMISSIONS, "somePermissions");

        Field paramsField = field(PermissionResourceRequestAdapter.class, "params");
        MultivaluedMap<String, String> retrieved = (MultivaluedMap<String, String>) paramsField.get(adapter);

        // Then
        assertTrue(retrieved.size() == 1);
        verify(adapter, times(1)).param(PermissionResourceParameter.EFFECTIVE_PERMISSIONS, "somePermissions");
        verify(adapter, never()).param(PermissionResourceParameter.EFFECTIVE_PERMISSIONS, "wrongPermissions");
    }

    @Test(testName = "get")
    @SuppressWarnings("unchecked")
    public void should_return_proper_op_result() throws IllegalAccessException {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(sessionStorageMock,
                RepositoryPermissionListWrapper.class,
                new String[]{"permissions", fakeUri})).thenReturn(requestMock);
        when(requestMock.get()).thenReturn(expectedResultMock);

        // When
        PermissionResourceRequestAdapter adapter = new PermissionResourceRequestAdapter(sessionStorageMock, fakeUri);
        OperationResult<RepositoryPermissionListWrapper> retrieved = adapter.get();

        // Retrieving params field
        Field field = field(PermissionResourceRequestAdapter.class, "params");
        MultivaluedMap<String, String> paramsField = (MultivaluedMap<String, String>) field.get(adapter);

        // Then
        verify(requestMock, times(1)).get();
        verify(requestMock, times(1)).addParams(paramsField);
        verify(requestMock, never()).delete();
        verifyNoMoreInteractions(requestMock); // IMPORTANT: must be only two corresponding invocations and
        // no more on this Request object.
        assertSame(retrieved, expectedResultMock);
    }

    @Test(testName = "delete")
    public void should_delete_permission_and_return_op_result() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(sessionStorageMock,
                RepositoryPermissionListWrapper.class,
                new String[]{"permissions", fakeUri})).thenReturn(requestMock);
        when(requestMock.delete()).thenReturn(expectedResultMock);

        // When
        PermissionResourceRequestAdapter adapter = new PermissionResourceRequestAdapter(sessionStorageMock, fakeUri);
        OperationResult retrieved = adapter.delete(); // why isn't generified? what comes as a result?

        // Then
        // Verify that static print is called with the specified parameter.
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock),
                eq(RepositoryPermissionListWrapper.class),
                eq(new String[]{"permissions", fakeUri}));

        // Verify that instance print is called only once
        verify(requestMock, times(1)).delete();
        assertSame(retrieved, expectedResultMock);
    }


    @Test
    public void should_retrieve_resource_asynchronously() throws InterruptedException {

        /* Given */
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(buildRequest(eq(sessionStorageMock),
                eq(RepositoryPermissionListWrapper.class),
                eq(new String[]{"permissions", "resourceUri"}))).thenReturn(requestMock);
        PowerMockito.doReturn(expectedResultMock).when(requestMock).get();
        PermissionResourceRequestAdapter adapterSpy = PowerMockito.spy(new PermissionResourceRequestAdapter(sessionStorageMock, fakeUri));

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<RepositoryPermissionListWrapper>, Void> callback = PowerMockito.spy(new Callback<OperationResult<RepositoryPermissionListWrapper>, Void>() {
            @Override
            public Void execute(OperationResult<RepositoryPermissionListWrapper> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        PowerMockito.doReturn(null).when(callback).execute(expectedResultMock);

        /* When */
        RequestExecution retrieved = adapterSpy.asyncGet(callback);

        synchronized (callback) {
            callback.wait(1000);
        }

        /* Then */
        Mockito.verify(requestMock).get();
        Mockito.verify(callback).execute(expectedResultMock);
        Assert.assertNotNull(retrieved);
        Assert.assertNotSame(currentThreadId, newThreadId.get());
    }

    @Test
    public void should_delete_resource_asynchronously() throws InterruptedException {

        /* Given */
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(buildRequest(eq(sessionStorageMock),
                eq(RepositoryPermissionListWrapper.class),
                eq(new String[]{"permissions", "resourceUri"}))).thenReturn(requestMock);
        PowerMockito.doReturn(expectedResultMock).when(requestMock).delete();
        PermissionResourceRequestAdapter adapterSpy = PowerMockito.spy(new PermissionResourceRequestAdapter(sessionStorageMock, fakeUri));

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<RepositoryPermissionListWrapper>, Void> callback = PowerMockito.spy(new Callback<OperationResult<RepositoryPermissionListWrapper>, Void>() {
            @Override
            public Void execute(OperationResult<RepositoryPermissionListWrapper> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        PowerMockito.doReturn(null).when(callback).execute(expectedResultMock);

        /* When */
        RequestExecution retrieved = adapterSpy.asyncDelete(callback);

        synchronized (callback) {
            callback.wait(1000);
        }

        /* Then */
        Mockito.verify(requestMock).delete();
        Mockito.verify(callback).execute(expectedResultMock);
        Assert.assertNotNull(retrieved);
        Assert.assertNotSame(currentThreadId, newThreadId.get());
    }

    @Test
    public void should_create_resource_asynchronously() throws InterruptedException {

        /* Given */
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(buildRequest(eq(sessionStorageMock),
                eq(RepositoryPermissionListWrapper.class),
                eq(new String[]{"permissions", "resourceUri"}))).thenReturn(requestMock);
        PowerMockito.doReturn(expectedResultMock).when(requestMock).put(wrapperMock);

        PowerMockito.doReturn(configurationMock).when(sessionStorageMock).getConfiguration();
        PowerMockito.doReturn(MimeType.JSON).when(configurationMock).getContentMimeType();

        PermissionResourceRequestAdapter adapterSpy = PowerMockito.spy(new PermissionResourceRequestAdapter(sessionStorageMock, fakeUri));

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<RepositoryPermissionListWrapper>, Void> callback = PowerMockito.spy(new Callback<OperationResult<RepositoryPermissionListWrapper>, Void>() {
            @Override
            public Void execute(OperationResult<RepositoryPermissionListWrapper> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        PowerMockito.doReturn(null).when(callback).execute(expectedResultMock);

        /* When */
        RequestExecution retrieved = adapterSpy.asyncCreateOrUpdate(wrapperMock, callback);

        synchronized (callback) {
            callback.wait(1000);
        }

        /* Then */
        Assert.assertNotNull(retrieved);
        Assert.assertNotSame(currentThreadId, newThreadId.get());

        Mockito.verify(requestMock).put(wrapperMock);
        Mockito.verify(callback).execute(expectedResultMock);
        Mockito.verify(sessionStorageMock).getConfiguration();
        Mockito.verify(configurationMock).getContentMimeType();
    }

    @AfterMethod
    public void after() {
        Mockito.reset(sessionStorageMock, requestMock, wrapperMock, expectedResultMock, configurationMock);
    }
}