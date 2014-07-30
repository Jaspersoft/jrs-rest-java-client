package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.authority.users.attributes;

import com.jaspersoft.jasperserver.dto.authority.ClientUserAttribute;
import com.jaspersoft.jasperserver.jaxrs.client.core.Callback;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.ThreadPoolUtil;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MultivaluedHashMap;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@PrepareForTest({JerseyRequest.class, ThreadPoolUtil.class, SingleAttributeAdapter.class,
        StringBuilder.class, MultivaluedHashMap.class})
public class SingleAttributeAdapterTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;

    @Mock
    private JerseyRequest<ClientUserAttribute> requestMock;

    @Mock
    private Callback<OperationResult<ClientUserAttribute>, Object> callbackMock;

    @Mock
    private Callback<OperationResult, Object> callbackMock2;

    @Mock
    private Object resultMock;

    @Mock
    private OperationResult<ClientUserAttribute> operationResultMock;

    @Mock
    private RequestBuilder<ClientUserAttribute> requestBuilderMock;

    @Mock
    private ClientUserAttribute userAttribute;

    @Mock
    private OperationResult operationResultMock2;

    @BeforeMethod
    public void before() {
        initMocks(this);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void constructor(){
        new SingleAttributeAdapter(null, null);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void asyncGet() throws Exception {

        // Given
        StringBuilder builderMock = PowerMockito.mock(StringBuilder.class);
        SingleAttributeAdapter adapterSpy = spy(new SingleAttributeAdapter(sessionStorageMock, builderMock));

        PowerMockito.doReturn(requestMock).when(adapterSpy, "request");
        PowerMockito.doReturn(operationResultMock).when(requestMock).get();
        PowerMockito.doReturn(resultMock).when(callbackMock).execute(operationResultMock);

        // When
        adapterSpy.asyncGet(callbackMock, "State"); // State = attribute name

        // Than
        verifyPrivate(adapterSpy, times(1)).invoke("request");
        verify(callbackMock, times(1)).execute(operationResultMock);
        PowerMockito.verifyNoMoreInteractions(callbackMock);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void asyncDelete() throws Exception {

        // Given
        StringBuilder builderMock = PowerMockito.mock(StringBuilder.class);
        SingleAttributeAdapter adapterSpy = spy(new SingleAttributeAdapter(sessionStorageMock, builderMock));

        PowerMockito.doReturn(requestMock).when(adapterSpy, "request");
        PowerMockito.doReturn(operationResultMock).when(requestMock).delete();
        PowerMockito.doReturn(resultMock).when(callbackMock).execute(operationResultMock);

        // When
        adapterSpy.asyncDelete(callbackMock2, "State"); // State = attribute name

        // Than
        verifyPrivate(adapterSpy, times(1)).invoke("request");
        verify(callbackMock2, times(1)).execute(operationResultMock);
        PowerMockito.verifyNoMoreInteractions(callbackMock2);
    }


    @Test
    @SuppressWarnings("unchecked")
    public void asyncUpdateOrCreate() throws Exception {

        // Given
        StringBuilder builderMock = PowerMockito.mock(StringBuilder.class);
        SingleAttributeAdapter adapterSpy = spy(new SingleAttributeAdapter(sessionStorageMock, builderMock));

        PowerMockito.doReturn(requestMock).when(adapterSpy, "request");
        PowerMockito.doReturn(operationResultMock).when(requestMock).put(userAttribute);
        PowerMockito.doReturn(resultMock).when(callbackMock).execute(operationResultMock);

        // When
        adapterSpy.asyncUpdateOrCreate(userAttribute, callbackMock, "State"); // State = attribute name

        // Than
        verifyPrivate(adapterSpy, times(1)).invoke("request");
        verify(callbackMock, times(1)).execute(operationResultMock);
        PowerMockito.verifyNoMoreInteractions(callbackMock);
    }


    @Test(testName = "private")
    public void request() throws Exception {
/*
        // Given
        PowerMockito.mockStatic(JerseyRequest.class);
        PowerMockito.when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientUserAttribute.class), eq(new String[]{"uri", "/attributes", "State"}), any(DefaultErrorHandler.class))).thenReturn(requestMock);

        StringBuilder builderMock = PowerMockito.mock(StringBuilder.class);
        PowerMockito.when(builderMock.toString()).thenReturn("uri");
        SingleAttributeAdapter adapterSpy = spy(new SingleAttributeAdapter(sessionStorageMock, builderMock));

        // When
        adapterSpy.delete("State");

        // Than
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(ClientUserAttribute.class), eq(new String[]{"uri", "/attributes", "State"}), any(DefaultErrorHandler.class));
        verifyPrivate(adapterSpy, times(1)).invoke("request");
    */}


    @Test
    public void updateOrCreate() throws Exception {
/*
        // Given
        StringBuilder builderMock = PowerMockito.mock(StringBuilder.class);
        PowerMockito.when(builderMock.toString()).thenReturn("uri");
        SingleAttributeAdapter adapterSpy = spy(new SingleAttributeAdapter(sessionStorageMock, builderMock));

        PowerMockito.doReturn(requestMock).when(adapterSpy, "request");
        PowerMockito.doReturn(operationResultMock2).when(requestMock).put(userAttribute);
        PowerMockito.doReturn("State").when(userAttribute).getName();

        // When
        OperationResult retrieved = adapterSpy.updateOrCreate(userAttribute);

        // Than
        verifyPrivate(adapterSpy, times(1)).invoke("request");
        verify(requestMock, times(1)).put(userAttribute);
        assertEquals(Whitebox.getInternalState(adapterSpy, "attributeName"), "State");
        assertSame(operationResultMock2, retrieved);
    */}


    @Test
    public void get() throws Exception {
/*
        // Given
        StringBuilder builderMock = PowerMockito.mock(StringBuilder.class);
        PowerMockito.when(builderMock.toString()).thenReturn("uri");
        SingleAttributeAdapter adapterSpy = spy(new SingleAttributeAdapter(sessionStorageMock, builderMock));

        PowerMockito.doReturn(requestMock).when(adapterSpy, "request");
        PowerMockito.doReturn(operationResultMock).when(requestMock).get();
        PowerMockito.doReturn("State").when(userAttribute).getName();

        // When
        OperationResult retrieved = adapterSpy.get("State");

        // Than
        verifyPrivate(adapterSpy, times(1)).invoke("request");
        verify(requestMock, times(1)).get();
        assertEquals(Whitebox.getInternalState(adapterSpy, "attributeName"), "State");
        assertSame(operationResultMock, retrieved);
        */
    }

    @AfterMethod
    public void after() {
        reset(sessionStorageMock, requestMock, callbackMock, resultMock, operationResultMock,
                requestBuilderMock, userAttribute, operationResultMock2);
    }
}