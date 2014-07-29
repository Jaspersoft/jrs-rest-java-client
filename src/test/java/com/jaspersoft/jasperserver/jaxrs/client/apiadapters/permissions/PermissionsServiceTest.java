package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.permissions;

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermission;
import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermissionListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.support.membermodification.MemberMatcher.field;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.permissions.PermissionsService}
 */
@PrepareForTest({JerseyRequest.class})
public class PermissionsServiceTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<Object> requestMock;

    @Mock
    private RepositoryPermission permissionMock;

    @Mock
    private OperationResult resultMock;

    @Mock
    private RestClientConfiguration configurationMock;

    @Mock
    private RepositoryPermissionListWrapper wrapperMock;

    private String fakeUri = "uri";

    @BeforeMethod
    public void after() {
        initMocks(this);
    }

    @Test(testName = "resource")
    public void should_return_proper_adapter() throws IllegalAccessException {

        // Given
        PermissionsService service = new PermissionsService(sessionStorageMock);

        // When
        PermissionResourceRequestAdapter adapter = service.resource(fakeUri);
        SessionStorage retrieved = adapter.getSessionStorage();

        Field field = field(PermissionResourceRequestAdapter.class, "resourceUri");
        String resourceUri = (String) field.get(adapter);

        // Than
        assertSame(retrieved, sessionStorageMock);
        assertEquals(resourceUri, fakeUri);
    }

    @Test(testName = "resource", expectedExceptions = IllegalArgumentException.class)
    public void should_throw_an_exception_when_invalid_param_has_been_passed() throws IllegalAccessException {

        // When
        PermissionsService service = new PermissionsService(sessionStorageMock);

        // Than
        service.resource("");
    }

    @Test(testName = "createNew_with_RepositoryPermission_param")
    @SuppressWarnings("unchecked")
    public void should_create_new_permission() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Object.class), eq(new String[]{"/permissions"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);
        when(requestMock.post(permissionMock)).thenReturn(resultMock);

        // When
        PermissionsService service = new PermissionsService(sessionStorageMock);
        OperationResult retrieved = service.createNew(permissionMock);

        // Than
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Object.class), eq(new String[]{"/permissions"}), any(DefaultErrorHandler.class));

        assertSame(retrieved, resultMock);
        verify(requestMock, times(1)).post(permissionMock);
    }

    @Test (testName = "createNew_with_list_of_wrapped_RepositoryPermission")
    public void should_persist_list_of_permissions() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Object.class), eq(new String[]{"/permissions"}))).thenReturn(requestMock);
        when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getContentMimeType()).thenReturn(MimeType.JSON);
        doReturn(resultMock).when(requestMock).post(wrapperMock);

        // When
        PermissionsService service = new PermissionsService(sessionStorageMock);
        OperationResult retrieved = service.createNew(wrapperMock);

        // Than
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(Object.class), eq(new String[]{"/permissions"}));
        verify(requestMock, times(1)).setContentType("application/collection+json");
        verify(requestMock, times(1)).post(wrapperMock);
        assertSame(retrieved, resultMock);
    }

    @AfterMethod
    public void before() {
        reset(sessionStorageMock, wrapperMock, requestMock);
    }
}