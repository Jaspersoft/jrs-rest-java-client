package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.bundles;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.serverInfo.ServerInfoService;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.util.Locale;
import java.util.Map;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONObject;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
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
        when(JerseyRequest.buildRequest(eq(sessionStorageMock), any(GenericType.class), isA(String[].class), any(DefaultErrorHandler.class))).thenReturn(requestMock);
        doReturn(requestMock).when(requestMock).addParam(anyString(), anyString());
        doReturn(requestMock).when(requestMock).addHeader(anyString(), anyString());
        doReturn(requestMock).when(requestMock).setAccept(anyString());
        doReturn(operationResultMock).when(requestMock).get();
        //when
        OperationResult<Map<String, Map<String, String>>> bundles = service.forLocale(null).allBundles();
        //then
        assertSame(bundles, operationResultMock);
        verify(requestMock).setAccept(MediaType.APPLICATION_JSON);
        verify(requestMock).addParam("expanded", "true");
        verify(requestMock).addHeader("Accept-Language", defaultLocale.replace('_', '-'));
        verify(requestMock).get();
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(new GenericType<Map<String, Map<String, String>>>() {
        }), eq(new String[]{"/bundles"}), any(DefaultErrorHandler.class));
        Locale locale = (Locale) Whitebox.getInternalState(service, "locale");
        assertEquals(locale.toString(), defaultLocale);
    }


    @Test
    public void should_return_proper_bundles_for_locale() throws Exception {
        //given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock),any(GenericType.class), isA(String[].class), any(DefaultErrorHandler.class))).thenReturn(requestMock);
        doReturn(requestMock).when(requestMock).addParam(anyString(), anyString());
        doReturn(requestMock).when(requestMock).addHeader(anyString(), anyString());
        doReturn(requestMock).when(requestMock).setAccept(anyString());
        doReturn(operationResultMock).when(requestMock).get();
        //when
        OperationResult<Map<String, Map<String, String>>> bundles = service.forLocale("de").allBundles();
        //then
        assertSame(bundles, operationResultMock);
        verify(requestMock).setAccept(MediaType.APPLICATION_JSON);
        verify(requestMock).addParam("expanded", "true");
        verify(requestMock).addHeader("Accept-Language", "de");
        verify(requestMock).get();
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(new GenericType<Map<String, Map<String, String>>>() {
        }), eq(new String[]{"/bundles"}), any(DefaultErrorHandler.class));
        Locale locale = (Locale) Whitebox.getInternalState(service, "locale");
        assertEquals(locale.toString(), "de");
    }

    @Test
    public void should_return_proper_bundles_by_name_for_locale() throws Exception {
        //given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock), any(GenericType.class), isA(String[].class), any(DefaultErrorHandler.class))).thenReturn(requestMock);
        doReturn(requestMock).when(requestMock).addHeader(anyString(), anyString());
        doReturn(requestMock).when(requestMock).setAccept(anyString());
        doReturn(operationResultMock).when(requestMock).get();
        //when
        OperationResult<Map<String, String>> bundle = service.forLocale("de").bundle("jasperserver_messages");
        //then
        assertSame(bundle, operationResultMock);
        verify(requestMock).setAccept(MediaType.APPLICATION_JSON);
        verify(requestMock,never()).addParam("expanded", "true");
        verify(requestMock).addHeader("Accept-Language", "de");
        verify(requestMock).get();
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock),  eq(new GenericType<Map<String, String>>() {
        }), eq(new String[]{"/bundles", "jasperserver_messages"}), any(DefaultErrorHandler.class));
    }

    @Test
    public void should_return_proper_bundles_by_name_for_default_locale() throws Exception {
        //given
        mockStatic(JerseyRequest.class);
        when(JerseyRequest.buildRequest(eq(sessionStorageMock),  any(GenericType.class), isA(String[].class), any(DefaultErrorHandler.class))).thenReturn(requestMock);
        doReturn(requestMock).when(requestMock).addHeader(anyString(), anyString());
        doReturn(requestMock).when(requestMock).setAccept(anyString());
        doReturn(operationResultMock).when(requestMock).get();
        //when
        OperationResult<Map<String, String>> bundle = service.forLocale(null).bundle("jasperserver_messages");
        //then
        assertSame(bundle, operationResultMock);
        verify(requestMock).setAccept(MediaType.APPLICATION_JSON);
        verify(requestMock,never()).addParam("expanded", "true");
        verify(requestMock).addHeader("Accept-Language", Locale.getDefault().toString().replace('_', '-'));
        verify(requestMock).get();
        verifyStatic(times(1));
        JerseyRequest.buildRequest(eq(sessionStorageMock), eq(new GenericType<Map<String, String>>() {
        }), eq(new String[]{"/bundles", "jasperserver_messages"}), any(DefaultErrorHandler.class));
    }
}