package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.permissions;

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermission;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

@PrepareForTest({SinglePermissionRecipientRequestAdapter.class, JerseyRequest.class})
public class SinglePermissionRecipientRequestAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage storageMock;

    @Mock
    private JerseyRequest<RepositoryPermission> requestMock;

    @Mock
    private OperationResult<RepositoryPermission> operationResultMock;

    @Mock
    private RepositoryPermission permissionMock;


    @BeforeMethod
    public void after() {
        initMocks(this);
    }

    @Test(testName = "get")
    public void should_return_a_proper_op_result_with_repo_permission() throws Exception {

        // Given
        SinglePermissionRecipientRequestAdapter adapterSpy = spy(new SinglePermissionRecipientRequestAdapter(storageMock, "resourceUri", "recipient"));
        doReturn(requestMock).when(adapterSpy, "getBuilder", RepositoryPermission.class);
        doReturn(operationResultMock).when(requestMock).get();

        // When
        OperationResult<RepositoryPermission> retrieved = adapterSpy.get();

        // Than
        verifyPrivate(adapterSpy, times(1)).invoke("getBuilder", eq(RepositoryPermission.class));
        verify(requestMock, times(1)).get();
        assertNotNull(retrieved);
    }

    @Test(testName = "createOrUpdate")
    public void createOrUpdate() throws Exception {

        // Given
        SinglePermissionRecipientRequestAdapter adapterSpy = spy(new SinglePermissionRecipientRequestAdapter(storageMock, "resourceUri", "recipient"));
        doReturn(requestMock).when(adapterSpy, "getBuilder", RepositoryPermission.class);
        doReturn(operationResultMock).when(requestMock).put(permissionMock);

        // When
        OperationResult<RepositoryPermission> retrieved = adapterSpy.createOrUpdate(permissionMock);

        // Than
        verifyPrivate(adapterSpy, times(1)).invoke("getBuilder", eq(RepositoryPermission.class));
        verify(requestMock, times(1)).put(permissionMock);
        assertNotNull(retrieved);
        assertSame(retrieved, operationResultMock);
    }

    @Test
    public void delete() throws Exception {

        // Given
        OperationResult opResultMock = PowerMockito.mock(OperationResult.class);
        SinglePermissionRecipientRequestAdapter adapterSpy = spy(new SinglePermissionRecipientRequestAdapter(storageMock, "resourceUri", "recipient"));
        doReturn(requestMock).when(adapterSpy, "getBuilder", Object.class);
        doReturn(opResultMock).when(requestMock).delete();

        // When
        OperationResult<RepositoryPermission> retrieved = adapterSpy.delete();

        // Than
        verifyPrivate(adapterSpy, times(1)).invoke("getBuilder", eq(Object.class));
        verify(requestMock, times(1)).delete();
        assertNotNull(retrieved);
        assertSame(retrieved, opResultMock);
    }

    @Test(testName = "getBuilder")
    public void should_invoke_private_method_only_once() throws Exception {

        // Given
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(storageMock), eq(RepositoryPermission.class), eq(new String[]{"/permissions", "resourceUri"}))).thenReturn(requestMock);
        PowerMockito.when(requestMock.get()).thenReturn(operationResultMock);
        SinglePermissionRecipientRequestAdapter spy = PowerMockito.spy(new SinglePermissionRecipientRequestAdapter(storageMock, "resourceUri", "recipient"));

        // When
        OperationResult<RepositoryPermission> retrieved = spy.get();

        // Than
        PowerMockito.verifyPrivate(spy, times(1)).invoke("getBuilder", RepositoryPermission.class);
        assertSame(retrieved, operationResultMock);
    }

    @AfterMethod
    public void before() {
        reset(requestMock, operationResultMock, storageMock, permissionMock);
    }
}