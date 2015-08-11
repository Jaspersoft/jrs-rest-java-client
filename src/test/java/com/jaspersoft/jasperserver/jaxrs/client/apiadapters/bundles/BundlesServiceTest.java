package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.bundles;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.serverInfo.ServerInfoService;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.RequestBuilder;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import org.codehaus.jettison.json.JSONObject;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;

import java.util.Locale;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

/**
 * @author Tetiana Iefimenko
 */
@PrepareForTest({JerseyRequest.class, ServerInfoService.class})
public class BundlesServiceTest extends PowerMockTestCase {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest<JSONObject> requestMock;
    @Mock
    private OperationResult<JSONObject> operationResultMock;

    private BundlesService service;

    @BeforeMethod
    public void before() {
        initMocks(this);
        service = new BundlesService(sessionStorageMock);
    }

    @Test
    public void should_return_proper_bundles_for_default_locale() throws Exception {
        //given
        String defaultLocale = Locale.getDefault().toString();
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(JSONObject.class), isA(String[].class), any(DefaultErrorHandler.class))).thenReturn(requestMock);
        doReturn(requestMock).when(requestMock).addParam(anyString(), anyString());
        doReturn(requestMock).when(requestMock).addHeader(anyString(), anyString());
        doReturn(requestMock).when(requestMock).setAccept(anyString());
        doReturn(operationResultMock).when(requestMock).get();
        //when
        OperationResult<JSONObject> bundles = service.forLocale(null).bundles();
        //then
        assertSame(bundles, operationResultMock);
        verify(requestMock).setAccept(MediaType.APPLICATION_JSON);
        verify(requestMock).addParam("expanded", "true");
        verify(requestMock).addHeader("Accept-Language", defaultLocale.replace('_', '-'));
        verify(requestMock).get();
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(JSONObject.class), eq(new String[]{"/bundles"}), any(DefaultErrorHandler.class));
        Locale locale = (Locale) Whitebox.getInternalState(service, "locale");
        assertEquals(locale.toString(), defaultLocale);
    }


    @Test
    public void should_return_proper_bundles_for_locale() throws Exception {
        //given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(JSONObject.class), isA(String[].class), any(DefaultErrorHandler.class))).thenReturn(requestMock);
        doReturn(requestMock).when(requestMock).addParam(anyString(), anyString());
        doReturn(requestMock).when(requestMock).addHeader(anyString(), anyString());
        doReturn(requestMock).when(requestMock).setAccept(anyString());
        doReturn(operationResultMock).when(requestMock).get();
        //when
        OperationResult<JSONObject> bundles = service.forLocale("de").bundles();
        //then
        assertSame(bundles, operationResultMock);
        verify(requestMock).setAccept(MediaType.APPLICATION_JSON);
        verify(requestMock).addParam("expanded", "true");
        verify(requestMock).addHeader("Accept-Language", "de");
        verify(requestMock).get();
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(JSONObject.class), eq(new String[]{"/bundles"}), any(DefaultErrorHandler.class));
        Locale locale = (Locale) Whitebox.getInternalState(service, "locale");
        assertEquals(locale.toString(), "de");
    }

    @Test
    public void should_return_proper_bundles_by_name_for_locale() throws Exception {
        //given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(JSONObject.class), isA(String[].class), any(DefaultErrorHandler.class))).thenReturn(requestMock);
        doReturn(requestMock).when(requestMock).addHeader(anyString(), anyString());
        doReturn(requestMock).when(requestMock).setAccept(anyString());
        doReturn(operationResultMock).when(requestMock).get();
        //when
        OperationResult<JSONObject> bundles = service.forLocale("de").bundles("jasperserver_messages");
        //then
        assertSame(bundles, operationResultMock);
        verify(requestMock).setAccept(MediaType.APPLICATION_JSON);
        verify(requestMock,never()).addParam("expanded", "true");
        verify(requestMock).addHeader("Accept-Language", "de");
        verify(requestMock).get();
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(JSONObject.class), eq(new String[]{"/bundles", "jasperserver_messages"}), any(DefaultErrorHandler.class));
    }

    @Test
    public void should_return_proper_bundles_by_name_for_default_locale() throws Exception {
        //given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock), eq(JSONObject.class), isA(String[].class), any(DefaultErrorHandler.class))).thenReturn(requestMock);
        doReturn(requestMock).when(requestMock).addHeader(anyString(), anyString());
        doReturn(requestMock).when(requestMock).setAccept(anyString());
        doReturn(operationResultMock).when(requestMock).get();
        //when
        OperationResult<JSONObject> bundles = service.forLocale(null).bundles("jasperserver_messages");
        //then
        assertSame(bundles, operationResultMock);
        verify(requestMock).setAccept(MediaType.APPLICATION_JSON);
        verify(requestMock,never()).addParam("expanded", "true");
        verify(requestMock).addHeader("Accept-Language", Locale.getDefault().toString().replace('_', '-'));
        verify(requestMock).get();
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(JSONObject.class), eq(new String[]{"/bundles", "jasperserver_messages"}), any(DefaultErrorHandler.class));
    }
}