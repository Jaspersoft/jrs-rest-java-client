package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes;

import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest.buildRequest;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.internal.util.reflection.Whitebox.getInternalState;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes.SingleAttributeInterfaceAdapter}
 */
@PrepareForTest({SingleAttributeInterfaceAdapter.class, JerseyRequest.class})
public class SingleAttributeInterfaceAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<ClientUserAttribute> requestMock;

    @Mock
    private OperationResult<ClientUserAttribute> resultMock;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test
    public void should_pass_proper_session_storage_to_parent_class_and_set_own_fields() {

        /* When */
        SingleAttributeInterfaceAdapter adapter = new SingleAttributeInterfaceAdapter(sessionStorageMock, "/organizations/MyCoolOrg/users/Simon", "State");

        /* Than */
        String uri = (String) getInternalState(adapter, "uri");
        String attributeName = (String) getInternalState(adapter, "attributeName");

        assertNotNull(adapter);
        assertEquals(uri, "/organizations/MyCoolOrg/users/Simon");
        assertEquals(attributeName, "State");
        assertSame(adapter.getSessionStorage(), sessionStorageMock);
    }

    @Test
    public void should_invoke_private_method_and_return_a_mock() throws Exception {

        /* Given */
        SingleAttributeInterfaceAdapter adapter = spy(new SingleAttributeInterfaceAdapter(sessionStorageMock, "/organizations/MyCoolOrg/users/Simon", "State"));
        doReturn(requestMock).when(adapter, "buildRequest");
        doReturn(resultMock).when(requestMock).get();

        /* When */
        adapter.get();

        /* Than */
        PowerMockito.verifyPrivate(adapter, times(1)).invoke("buildRequest");
        verify(requestMock, times(1)).get();
        Mockito.verifyNoMoreInteractions(requestMock);
    }

    @Test
    public void should_invoke_retrieving_logic_in_separate_thread_and_return_RequestExecution_object() throws Exception {

        // todo: проверить, почему не работать верификация вызова приватного метода
        // (PowerMockito.verifyPrivate(adapter, times(1)).invoke("buildRequest");)

        /* Given */
        SingleAttributeInterfaceAdapter adapter = spy(new SingleAttributeInterfaceAdapter(sessionStorageMock, "/organizations/MyCoolOrg/users/Simon", "State"));
        doReturn(requestMock).when(adapter, "buildRequest");
        doReturn(resultMock).when(requestMock).get();

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<ClientUserAttribute>, Void> callback = spy(new Callback<OperationResult<ClientUserAttribute>, Void>() {
            @Override
            public Void execute(OperationResult<ClientUserAttribute> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        doReturn(null).when(callback).execute(resultMock);

        /* When */
        RequestExecution retrieved = adapter.asyncGet(callback);

        synchronized (callback) {
            callback.wait(1000);
        }

        /* Than */
        verify(requestMock).get();
        verify(callback).execute(resultMock);
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
    }

    @Test
    public void should_delete_attribute_asynchronously() throws Exception {

        /* Given */
        SingleAttributeInterfaceAdapter adapter = spy(new SingleAttributeInterfaceAdapter(sessionStorageMock, "/organizations/MyCoolOrg/users/Simon", "State"));
        doReturn(requestMock).when(adapter, "buildRequest");
        doReturn(resultMock).when(requestMock).delete();

        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult, Void> callback = spy(new Callback<OperationResult, Void>() {
            @Override
            public Void execute(OperationResult data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        doReturn(null).when(callback).execute(resultMock);

        /* When */
        RequestExecution retrieved = adapter.asyncDelete(callback);

        synchronized (callback) {
            callback.wait(1000);
        }

        /* Than */
        verify(requestMock).delete();
        verify(callback).execute(resultMock);
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
    }

    @Test
    public void should_update_od_create_attribute_for_user() throws Exception {

        /* Given */
        SingleAttributeInterfaceAdapter adapter = spy(new SingleAttributeInterfaceAdapter(sessionStorageMock, "/organizations/MyCoolOrg/users/Simon", "State"));
        final ClientUserAttribute userAttributeMock = mock(ClientUserAttribute.class);
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        final Callback<OperationResult<ClientUserAttribute>, Void> callback = spy(new Callback<OperationResult<ClientUserAttribute>, Void>() {
            @Override
            public Void execute(OperationResult<ClientUserAttribute> data) {
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

        /* When */
        RequestExecution retrieved = adapter.asyncCreateOrUpdate(userAttributeMock, callback);

        synchronized (callback) {
            callback.wait(1000);
        }

        /* Than */
        verify(requestMock).put(userAttributeMock);
        verify(callback).execute(resultMock);
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
    }

    @Test
    public void should_create_or_update_attribute_for_user() {

        /* Given */
        final ClientUserAttribute userAttributeMock = Mockito.mock(ClientUserAttribute.class);
        final SingleAttributeInterfaceAdapter adapter = Mockito.spy(new SingleAttributeInterfaceAdapter(sessionStorageMock, "/organizations/MyCoolOrg/users/Simon", "State"));

        mockStatic(JerseyRequest.class);
        Mockito.when(buildRequest(eq(sessionStorageMock), eq(ClientUserAttribute.class),
                eq(new String[]{"/organizations/MyCoolOrg/users/Simon", "/attributes", "State"}), any(DefaultErrorHandler.class)))
                .thenReturn(requestMock);
        Mockito.doReturn(resultMock).when(requestMock).put(userAttributeMock);

        /* When */
        OperationResult retrieved = adapter.createOrUpdate(userAttributeMock);

        /* Than */
        assertNotNull(retrieved);
        verifyStatic(times(1));
        buildRequest(eq(sessionStorageMock), eq(ClientUserAttribute.class), eq(new String[]{"/organizations/MyCoolOrg/users/Simon", "/attributes", "State"}), any(DefaultErrorHandler.class));

        Mockito.verify(adapter, times(1)).createOrUpdate(userAttributeMock);
        Mockito.verify(requestMock, times(1)).put(userAttributeMock);
    }

    @Test
    public void should_delete_user_attribute() throws Exception {

        /* Given */
        SingleAttributeInterfaceAdapter adapter = spy(new SingleAttributeInterfaceAdapter(sessionStorageMock, "/organizations/MyCoolOrg/users/Simon", "State"));
        doReturn(requestMock).when(adapter, "buildRequest");
        doReturn(resultMock).when(requestMock).delete();

        /* When */
        OperationResult retrieved = adapter.delete();

        /* Than */
        assertSame(retrieved, resultMock);
        verifyPrivate(adapter, times(1)).invoke("buildRequest");
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, requestMock, resultMock);
    }
}