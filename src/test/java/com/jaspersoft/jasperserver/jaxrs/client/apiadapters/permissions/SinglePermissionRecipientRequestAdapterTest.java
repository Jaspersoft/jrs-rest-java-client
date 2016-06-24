package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.permissions;

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermission;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
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

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

/**
* Unit tests for {@link SinglePermissionRecipientRequestAdapter}
*/
@PrepareForTest({SinglePermissionRecipientRequestAdapter.class, JerseyRequest.class})
public class SinglePermissionRecipientRequestAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage storageMock;

    @Mock
    private JerseyRequest<RepositoryPermission> requestMock;

    @Mock
    private OperationResult<RepositoryPermission> resultMock;

    @Mock
    private RepositoryPermission permissionMock;


    @BeforeMethod
    public void after() {
        initMocks(this);
    }

    @Test
    public void should_return_a_proper_op_result_with_repo_permission() throws Exception {

        // Given
        SinglePermissionRecipientRequestAdapter adapterSpy = spy(new SinglePermissionRecipientRequestAdapter(storageMock, "resourceUri", "recipient"));
        doReturn(requestMock).when(adapterSpy, "getBuilder", RepositoryPermission.class);
        doReturn(resultMock).when(requestMock).get();

        // When
        OperationResult<RepositoryPermission> retrieved = adapterSpy.get();

        // Then
        verifyPrivate(adapterSpy, times(1)).invoke("getBuilder", eq(RepositoryPermission.class));
        verify(requestMock, times(1)).get();
        assertNotNull(retrieved);
    }

    @Test
    public void should_create_or_update_RepositoryPermission_and_return_result_of_operation() throws Exception {

        // Given
        SinglePermissionRecipientRequestAdapter adapterSpy = spy(new SinglePermissionRecipientRequestAdapter(storageMock, "resourceUri", "recipient"));
        doReturn(requestMock).when(adapterSpy, "getBuilder", RepositoryPermission.class);
        doReturn(resultMock).when(requestMock).put(permissionMock);

        // When
        OperationResult<RepositoryPermission> retrieved = adapterSpy.createOrUpdate(permissionMock);

        // Then
        verifyPrivate(adapterSpy, times(1)).invoke("getBuilder", eq(RepositoryPermission.class));
        verify(requestMock, times(1)).put(permissionMock);
        assertNotNull(retrieved);
        assertSame(retrieved, resultMock);
    }

    @Test
    public void should_delete_RepositoryPermission_and_return_result() throws Exception {

        // Given
        OperationResult opResultMock = PowerMockito.mock(OperationResult.class);
        SinglePermissionRecipientRequestAdapter adapterSpy = spy(new SinglePermissionRecipientRequestAdapter(storageMock, "resourceUri", "recipient"));
        doReturn(requestMock).when(adapterSpy, "getBuilder", Object.class);
        doReturn(opResultMock).when(requestMock).delete();

        // When
        OperationResult<RepositoryPermission> retrieved = adapterSpy.delete();

        // Then
        verifyPrivate(adapterSpy, times(1)).invoke("getBuilder", eq(Object.class));
        verify(requestMock, times(1)).delete();
        assertNotNull(retrieved);
        assertSame(retrieved, opResultMock);
    }

    @Test
    public void should_invoke_private_method_only_once() throws Exception {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(storageMock),
                eq(RepositoryPermission.class),
                eq(new String[]{"permissions", "resourceUri"}))).thenReturn(requestMock);
        when(requestMock.get()).thenReturn(resultMock);
        SinglePermissionRecipientRequestAdapter spy = spy(new SinglePermissionRecipientRequestAdapter(storageMock, "resourceUri", "recipient"));

        // When
        OperationResult<RepositoryPermission> retrieved = spy.get();

        // Then
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(storageMock), eq(RepositoryPermission.class), eq(new String[]{"permissions", "resourceUri"}));

        // Verify that private print is called only once.
        verifyPrivate(spy, times(1)).invoke("getBuilder", RepositoryPermission.class);

        // Verify that addMatrixParam print is called with the specified parameters.
        verify(requestMock).addMatrixParam(eq("recipient"), eq("recipient"));

        assertSame(retrieved, resultMock);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_NPE_exception_when_session_is_null() throws Exception {

        // Given
        final SessionStorage NULL_STORAGE = null;
        SinglePermissionRecipientRequestAdapter spy = spy(new SinglePermissionRecipientRequestAdapter(NULL_STORAGE, "resourceUri", "recipient"));

        // When
        spy.get();

        // Then expect NPE
    }

    @Test
    public void should_retrieve_permission_asynchronously() throws InterruptedException {

        /* Given */
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(storageMock),
                eq(RepositoryPermission.class),
                eq(new String[]{"permissions", "resourceUri"}))).thenReturn(requestMock);
        PowerMockito.doReturn(resultMock).when(requestMock).get();
        SinglePermissionRecipientRequestAdapter adapterSpy = PowerMockito.spy(new SinglePermissionRecipientRequestAdapter(storageMock, "resourceUri", "recipient"));

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<RepositoryPermission>, Void> callback = PowerMockito.spy(new Callback<OperationResult<RepositoryPermission>, Void>() {
            @Override
            public Void execute(OperationResult<RepositoryPermission> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        PowerMockito.doReturn(null).when(callback).execute(resultMock);

        /* When */
        RequestExecution retrieved = adapterSpy.asyncGet(callback);

        synchronized (callback) {
            callback.wait(1000);
        }

        /* Then */
        Mockito.verify(requestMock).get();
        Mockito.verify(callback).execute(resultMock);
        Assert.assertNotNull(retrieved);
        Assert.assertNotSame(currentThreadId, newThreadId.get());
    }

    @Test
    public void should_delete_permission_asynchronously() throws InterruptedException {

        /* Given */
        JerseyRequest<Object> requestMock = (JerseyRequest<Object>) PowerMockito.mock(JerseyRequest.class);
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(storageMock),
                eq(Object.class),
                eq(new String[]{"permissions", "resourceUri"}))).thenReturn(requestMock);
        PowerMockito.doReturn(resultMock).when(requestMock).delete();
        SinglePermissionRecipientRequestAdapter adapterSpy = PowerMockito.spy(new SinglePermissionRecipientRequestAdapter(storageMock, "resourceUri", "recipient"));

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

        PowerMockito.doReturn(null).when(callback).execute(resultMock);

        /* When */
        RequestExecution retrieved = adapterSpy.asyncDelete(callback);

        synchronized (callback) {
            callback.wait(1000);
        }

        /* Then */
        Mockito.verify(requestMock).delete();
        Mockito.verify(callback).execute(resultMock);
        Assert.assertNotNull(retrieved);
        Assert.assertNotSame(currentThreadId, newThreadId.get());
    }

    @Test
    public void should_create_resource_asynchronously() throws InterruptedException {

        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(storageMock),
                eq(RepositoryPermission.class),
                eq(new String[]{"permissions", "resourceUri"}))).thenReturn(requestMock);
        PowerMockito.doReturn(resultMock).when(requestMock).put(permissionMock);

        SinglePermissionRecipientRequestAdapter adapterSpy = PowerMockito.spy(new SinglePermissionRecipientRequestAdapter(storageMock, "resourceUri", "recipient"));
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<RepositoryPermission>, Void> callback = PowerMockito.spy(new Callback<OperationResult<RepositoryPermission>, Void>() {
            @Override
            public Void execute(OperationResult<RepositoryPermission> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        PowerMockito.doReturn(null).when(callback).execute(resultMock);

        /* When */
        RequestExecution retrieved = adapterSpy.asyncCreateOrUpdate(permissionMock, callback);

        /* Wait */
        synchronized (callback) {
            callback.wait(1000);
        }

        /* Then */
        Assert.assertNotNull(retrieved);
        Assert.assertNotSame(currentThreadId, newThreadId.get());

        Mockito.verify(requestMock).put(permissionMock);
        Mockito.verify(requestMock).addMatrixParam(anyString(), anyString());
        Mockito.verify(callback).execute(resultMock);
    }

    @AfterMethod
    public void before() {
        reset(requestMock, resultMock, storageMock, permissionMock);
    }
}