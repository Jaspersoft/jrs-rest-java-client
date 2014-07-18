package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.permissions;

import com.jaspersoft.jasperserver.dto.permissions.RepositoryPermissionListWrapper;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.MimeType;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MultivaluedMap;
import java.lang.reflect.Field;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
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

        // When
        PermissionResourceRequestAdapter adapter = new PermissionResourceRequestAdapter(sessionStorageMock, fakeUri);
        SinglePermissionRecipientRequestAdapter retrieved = adapter.permissionRecipient(PermissionRecipient.ROLE, "abc");

        Field recipientField = field(SinglePermissionRecipientRequestAdapter.class, "recipient");
        Field resourceUriField = field(SinglePermissionRecipientRequestAdapter.class, "resourceUri");

        String recipient = (String) recipientField.get(retrieved);
        String resourceUri = (String) resourceUriField.get(retrieved);

        // Than
        assertEquals(recipient, "role:%2Fabc");
        assertEquals(resourceUri, "resourceUri");
    }

    @Test(testName = "createOrUpdate")
    public void should_return_proper_OperationResult_instance() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(sessionStorageMock, RepositoryPermissionListWrapper.class, new String[]{"/permissions", fakeUri})).thenReturn(requestMock);
        when(sessionStorageMock.getConfiguration()).thenReturn(configurationMock);
        when(configurationMock.getContentMimeType()).thenReturn(MimeType.JSON);
        doReturn(expectedResultMock).when(requestMock).put(wrapperMock);

        // When
        PermissionResourceRequestAdapter adapter = new PermissionResourceRequestAdapter(sessionStorageMock, fakeUri);
        OperationResult<RepositoryPermissionListWrapper> retrieved = adapter.createOrUpdate(wrapperMock);

        // Than
        assertNotNull(retrieved);
        assertEquals(retrieved, expectedResultMock);
    }

    @Test(testName = "param")
    @SuppressWarnings("unchecked")
    public void should_set_field_of_adapter_and_invoke_param_method_twice() throws IllegalAccessException {

        // Given
        PermissionResourceRequestAdapter adapter = spy(new PermissionResourceRequestAdapter(sessionStorageMock, fakeUri));

        // When
        adapter.param(PermissionResourceParameter.EFFECTIVE_PERMISSIONS, "somePermissions");
        adapter.param(PermissionResourceParameter.RECIPIENT_ID, "id");

        Field paramsField = field(PermissionResourceRequestAdapter.class, "params");
        MultivaluedMap<String, String> retrieved = (MultivaluedMap<String, String>) paramsField.get(adapter);

        // Than
        assertTrue(retrieved.size() == 2);
        verify(adapter, times(2)).param(any(PermissionResourceParameter.class), anyString());
    }

    @Test(testName = "get")
    public void should_return_proper_op_result() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(sessionStorageMock, RepositoryPermissionListWrapper.class, new String[]{"/permissions", fakeUri})).thenReturn(requestMock);
        when(requestMock.get()).thenReturn(expectedResultMock);

        // When
        PermissionResourceRequestAdapter adapter = new PermissionResourceRequestAdapter(sessionStorageMock, fakeUri);
        OperationResult<RepositoryPermissionListWrapper> retrieved = adapter.get();

        // Than
        verify(requestMock, times(1)).get();
        assertSame(retrieved, expectedResultMock);
    }

    @Test(testName = "delete")
    public void should_delete_permission_and_return_op_result() {

        // Given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(sessionStorageMock, RepositoryPermissionListWrapper.class, new String[]{"/permissions", fakeUri})).thenReturn(requestMock);
        when(requestMock.delete()).thenReturn(expectedResultMock);

        // When
        PermissionResourceRequestAdapter adapter = new PermissionResourceRequestAdapter(sessionStorageMock, fakeUri);
        OperationResult retrieved = adapter.delete(); // why isn't generified? what comes as a result?

        // Than
        verify(requestMock, times(1)).delete();
        assertSame(retrieved, expectedResultMock);
    }

    @AfterMethod
    public void after() {
        Mockito.reset(sessionStorageMock, requestMock, wrapperMock, expectedResultMock, configurationMock);
    }
}