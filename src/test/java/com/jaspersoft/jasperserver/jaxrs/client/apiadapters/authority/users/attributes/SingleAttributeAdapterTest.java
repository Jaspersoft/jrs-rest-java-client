package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes;

import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestExecution;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MultivaluedHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes.SingleAttributeAdapter}
 */
@PrepareForTest({JerseyRequest.class, SingleAttributeAdapter.class, MultivaluedHashMap.class})
public class SingleAttributeAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<ClientUserAttribute> requestMock;

    @Mock
    private JerseyRequest ungenerifiedRequestMock;


    @Mock
    private Callback<OperationResult<ClientUserAttribute>, Object> callbackMock;

    @Mock
    private Callback<OperationResult, Object> ungenerifiedCallbackMock;

    @Mock
    private Object resultMock;

    @Mock
    private OperationResult<ClientUserAttribute> operationResultMock;

    @Mock
    private RequestBuilder<ClientUserAttribute> requestBuilderMock;

    @Mock
    private ClientUserAttribute userAttributeMock;

    @Mock
    private OperationResult operationResultMock2;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void constructor() {
        new SingleAttributeAdapter(null, null);
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes.SingleAttributeAdapter#asyncGet(com.jaspersoft.jasperserver.jaxrs.client.core.Callback, String)}
     */
    @SuppressWarnings("unchecked")
    public void should_invoke_method_get_asynchronously_and_return_RequestExecution_object() throws Exception {

        /* Given */
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        SingleAttributeAdapter adapterSpy = PowerMockito.spy(new SingleAttributeAdapter(sessionStorageMock, new StringBuilder()));

        doReturn(requestMock).when(adapterSpy, "request");
        doReturn(operationResultMock).when(requestMock).get();

        Callback<OperationResult<ClientUserAttribute>, Void> callbackSpy = PowerMockito.spy(new Callback<OperationResult<ClientUserAttribute>, Void>() {
            public Void execute(OperationResult<ClientUserAttribute> data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        doReturn(null).when(callbackSpy).execute(operationResultMock);

        /* When */
        RequestExecution retrieved = adapterSpy.asyncGet(callbackSpy, "State");

        synchronized (callbackSpy) {
            callbackSpy.wait(100);
        }

        /* Than */
        assertNotNull(retrieved);
        verify(callbackSpy).execute(operationResultMock);
        assertNotSame(currentThreadId, newThreadId.get());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void asyncDelete() throws Exception {

        /* Given */
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        SingleAttributeAdapter adapterSpy = PowerMockito.spy(new SingleAttributeAdapter(sessionStorageMock, new StringBuilder()));

        doReturn(ungenerifiedRequestMock).when(adapterSpy, "request");
        doReturn(operationResultMock).when(ungenerifiedRequestMock).delete();

        Callback<OperationResult, Void> callbackSpy = PowerMockito.spy(new Callback<OperationResult, Void>() {
            public Void execute(OperationResult data) {
                newThreadId.set((int) Thread.currentThread().getId());
                synchronized (this) {
                    this.notify();
                }
                return null;
            }
        });

        doReturn(null).when(callbackSpy).execute(operationResultMock);

        /* When */
        RequestExecution retrieved = adapterSpy.asyncDelete(callbackSpy, "State");

        synchronized (callbackSpy) {
            callbackSpy.wait(100);
        }

        /* Than */
        assertNotNull(retrieved);
        verify(callbackSpy).execute(operationResultMock);
        verify(ungenerifiedRequestMock).delete();
        assertNotSame(currentThreadId, newThreadId.get());
    }


    @Test
    public void updateOrCreate() throws Exception {

        // Given
        SingleAttributeAdapter adapterSpy = PowerMockito.spy(new SingleAttributeAdapter(sessionStorageMock, new StringBuilder("/uri")));

        doReturn(requestMock).when(adapterSpy, "request");
        doReturn(operationResultMock2).when(requestMock).put(userAttributeMock);
        doReturn("State").when(userAttributeMock).getName();

        // When
        OperationResult retrieved = adapterSpy.updateOrCreate(userAttributeMock);

        // Than
        verifyPrivate(adapterSpy, times(1)).invoke("request");
        verify(requestMock, times(1)).put(userAttributeMock);
        Assert.assertEquals(Whitebox.getInternalState(adapterSpy, "attributeName"), "State");
        assertSame(operationResultMock2, retrieved);
    }


    @Test
    public void get() throws Exception {

        // Given
        SingleAttributeAdapter adapterSpy = PowerMockito.spy(new SingleAttributeAdapter(sessionStorageMock, new StringBuilder("/uri")));
        doReturn(requestMock).when(adapterSpy, "request");
        doReturn(operationResultMock).when(requestMock).get();
        doReturn("State").when(userAttributeMock).getName();

        // When
        OperationResult retrieved = adapterSpy.get("State");

        // Than
        verifyPrivate(adapterSpy, times(1)).invoke("request");
        verify(requestMock, times(1)).get();
        Assert.assertEquals(Whitebox.getInternalState(adapterSpy, "attributeName"), "State");
        assertSame(operationResultMock, retrieved);
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes.SingleAttributeAdapter#asyncUpdateOrCreate(com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute, com.jaspersoft.jasperserver.jaxrs.client.core.Callback, String)}
     */
    public void should_run_logic_of_update_method_asynchronously() throws Exception {

        /* Given */
        final String attributeName = "someAttributeName";
        final AtomicInteger newThreadId = new AtomicInteger();
        final int currentThreadId = (int) Thread.currentThread().getId();

        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(
                eq(sessionStorageMock),
                eq(ClientUserAttribute.class),
                eq(new String[]{"/uri", "/attributes", attributeName}),
                any(DefaultErrorHandler.class))).thenReturn(requestMock);
        SingleAttributeAdapter adapterSpy = PowerMockito.spy(new SingleAttributeAdapter(sessionStorageMock,
                new StringBuilder("/uri")));
        PowerMockito.doReturn(operationResultMock).when(requestMock).put(userAttributeMock);

        final Callback<OperationResult<ClientUserAttribute>, Void> callbackSpy =
                PowerMockito.spy(new Callback<OperationResult<ClientUserAttribute>, Void>() {
                    @Override
                    public Void execute(OperationResult<ClientUserAttribute> data) {
                        newThreadId.set((int) Thread.currentThread().getId());
                        synchronized (this) {
                            this.notify();
                        }
                        return null;
                    }
                });

        PowerMockito.doReturn(null).when(callbackSpy).execute(operationResultMock);

        /* When */
        RequestExecution retrieved = adapterSpy.asyncUpdateOrCreate(userAttributeMock, callbackSpy, attributeName);

        /* Wait */
        synchronized (callbackSpy) {
            callbackSpy.wait(1000);
        }

        /* Than */
        assertNotNull(retrieved);
        assertNotSame(currentThreadId, newThreadId.get());
        verify(callbackSpy, times(1)).execute(operationResultMock);
        verify(requestMock, times(1)).put(userAttributeMock);
    }

    @Test
    /**
     * for {@link com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes.SingleAttributeAdapter#delete(String)} and for {@link com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest#buildRequest()}
     */
    public void should_invoke_private_method_and_delete_attribute() {

        /* Given */
        final String attributeName = "someAttributeName";
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(
                eq(sessionStorageMock),
                eq(ClientUserAttribute.class),
                eq(new String[]{"/uri", "/attributes", attributeName}),
                any(DefaultErrorHandler.class))).thenReturn(requestMock);

        SingleAttributeAdapter adapterSpy = PowerMockito.spy(new SingleAttributeAdapter(sessionStorageMock,
                new StringBuilder("/uri")));

        /* When */
        adapterSpy.delete(attributeName);

        /* Than */
        PowerMockito.verifyStatic(times(1));
        JerseyRequest.buildRequest(
                eq(sessionStorageMock),
                eq(ClientUserAttribute.class),
                eq(new String[]{"/uri", "/attributes", attributeName}),
                any(DefaultErrorHandler.class));
    }


    @AfterMethod
    public void after() {
        reset(sessionStorageMock, requestMock, callbackMock, resultMock, operationResultMock,
                requestBuilderMock, userAttributeMock, operationResultMock2);
    }
}