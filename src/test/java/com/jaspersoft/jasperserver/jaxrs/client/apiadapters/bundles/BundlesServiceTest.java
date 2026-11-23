package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.bundles;

import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

/**
 * @author Tetiana Iefimenko
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class BundlesServiceTest {

    @Mock
    private SessionStorage sessionStorageMock;
    @Mock
    private JerseyRequest requestMock;
    @Mock
    private OperationResult operationResultMock;

    private AutoCloseable mockitoSession;

    @BeforeMethod
    public void before() {
        mockitoSession = MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void after() throws Exception {
        reset(sessionStorageMock, requestMock, operationResultMock);
        mockitoSession.close();
    }

    private Locale getLocaleField(BundlesService service) throws Exception {
        Field field = BundlesService.class.getDeclaredField("locale");
        field.setAccessible(true);
        return (Locale) field.get(service);
    }

    @Test
    public void should_return_proper_bundles_for_default_locale() throws Exception {
        //given
        String defaultLocale = Locale.getDefault().toString();
        doReturn(Locale.getDefault()).when(sessionStorageMock).getUserLocale();
        doReturn(requestMock).when(requestMock).addParam(anyString(), anyString());
        doReturn(requestMock).when(requestMock).addHeader(anyString(), anyString());
        doReturn(requestMock).when(requestMock).setAccept(anyString());
        doReturn(operationResultMock).when(requestMock).get();
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), any(GenericType.class), isA(String[].class), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            //when
            BundlesService service = new BundlesService(sessionStorageMock);
            OperationResult<Map<String, Map<String, String>>> bundles = service.allBundles();
            //then
            assertSame(bundles, operationResultMock);
            verify(requestMock).setAccept(MediaType.APPLICATION_JSON);
            verify(requestMock).addParam("expanded", "true");
            verify(requestMock).addHeader("Accept-Language", defaultLocale.replace('_', '-'));
            verify(requestMock).get();
            mockedStatic.verify(() -> JerseyRequest.buildRequest(
                    eq(sessionStorageMock),
                    eq(new GenericType<Map<String, Map<String, String>>>() {}),
                    eq(new String[]{"bundles"}),
                    any(DefaultErrorHandler.class)), times(1));
            Locale locale = getLocaleField(service);
            assertEquals(locale.toString(), defaultLocale);
        }
    }

    @Test
    public void should_return_proper_bundles_for_string_locale() throws Exception {
        //given
        doReturn(requestMock).when(requestMock).addParam(anyString(), anyString());
        doReturn(requestMock).when(requestMock).addHeader(anyString(), anyString());
        doReturn(requestMock).when(requestMock).setAccept(anyString());
        doReturn(operationResultMock).when(requestMock).get();
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), any(GenericType.class), isA(String[].class), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            BundlesService service = new BundlesService(sessionStorageMock);
            //when
            OperationResult<Map<String, Map<String, String>>> bundles = service.forLocale("de").allBundles();
            //then
            assertSame(bundles, operationResultMock);
            verify(requestMock).setAccept(MediaType.APPLICATION_JSON);
            verify(requestMock).addParam("expanded", "true");
            verify(requestMock).addHeader("Accept-Language", "de");
            verify(requestMock).get();
            mockedStatic.verify(() -> JerseyRequest.buildRequest(
                    eq(sessionStorageMock),
                    eq(new GenericType<Map<String, Map<String, String>>>() {}),
                    eq(new String[]{"bundles"}),
                    any(DefaultErrorHandler.class)), times(1));
            Locale locale = getLocaleField(service);
            assertEquals(locale.toString(), "de");
        }
    }

    @Test
    public void should_return_proper_bundles_for_locale() throws Exception {
        //given
        doReturn(requestMock).when(requestMock).addParam(anyString(), anyString());
        doReturn(requestMock).when(requestMock).addHeader(anyString(), anyString());
        doReturn(requestMock).when(requestMock).setAccept(anyString());
        doReturn(operationResultMock).when(requestMock).get();
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), any(GenericType.class), isA(String[].class), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            BundlesService service = new BundlesService(sessionStorageMock);
            //when
            OperationResult<Map<String, Map<String, String>>> bundles = service.forLocale(Locale.GERMAN).allBundles();
            //then
            assertSame(bundles, operationResultMock);
            verify(requestMock).setAccept(MediaType.APPLICATION_JSON);
            verify(requestMock).addParam("expanded", "true");
            verify(requestMock).addHeader("Accept-Language", "de");
            verify(requestMock).get();
            mockedStatic.verify(() -> JerseyRequest.buildRequest(
                    eq(sessionStorageMock),
                    eq(new GenericType<Map<String, Map<String, String>>>() {}),
                    eq(new String[]{"bundles"}),
                    any(DefaultErrorHandler.class)), times(1));
            Locale locale = getLocaleField(service);
            assertEquals(locale.toString(), "de");
        }
    }

    @Test
    public void should_return_proper_bundles_by_name_for_string_locale() throws Exception {
        //given
        doReturn(requestMock).when(requestMock).addHeader(anyString(), anyString());
        doReturn(requestMock).when(requestMock).setAccept(anyString());
        doReturn(operationResultMock).when(requestMock).get();
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), any(GenericType.class), isA(String[].class), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            BundlesService service = new BundlesService(sessionStorageMock);
            //when
            OperationResult<Map<String, String>> bundle = service.forLocale("de").bundle("jasperserver_messages");
            //then
            assertSame(bundle, operationResultMock);
            verify(requestMock).setAccept(MediaType.APPLICATION_JSON);
            verify(requestMock, never()).addParam("expanded", "true");
            verify(requestMock).addHeader("Accept-Language", "de");
            verify(requestMock).get();
            mockedStatic.verify(() -> JerseyRequest.buildRequest(
                    eq(sessionStorageMock),
                    eq(new GenericType<Map<String, String>>() {}),
                    eq(new String[]{"bundles", "jasperserver_messages"}),
                    any(DefaultErrorHandler.class)), times(1));
        }
    }

    @Test
    public void should_return_proper_bundles_by_name_for_locale() throws Exception {
        //given
        doReturn(requestMock).when(requestMock).addHeader(anyString(), anyString());
        doReturn(requestMock).when(requestMock).setAccept(anyString());
        doReturn(operationResultMock).when(requestMock).get();
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), any(GenericType.class), isA(String[].class), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            BundlesService service = new BundlesService(sessionStorageMock);
            //when
            OperationResult<Map<String, String>> bundle = service.forLocale(Locale.US).bundle("jasperserver_messages");
            //then
            assertSame(bundle, operationResultMock);
            verify(requestMock).setAccept(MediaType.APPLICATION_JSON);
            verify(requestMock, never()).addParam("expanded", "true");
            verify(requestMock).addHeader("Accept-Language", "en-US");
            verify(requestMock).get();
            mockedStatic.verify(() -> JerseyRequest.buildRequest(
                    eq(sessionStorageMock),
                    eq(new GenericType<Map<String, String>>() {}),
                    eq(new String[]{"bundles", "jasperserver_messages"}),
                    any(DefaultErrorHandler.class)), times(1));
        }
    }

    @Test
    public void should_return_proper_bundles_by_name_for_default_locale() throws Exception {
        //given
        doReturn(Locale.getDefault()).when(sessionStorageMock).getUserLocale();
        doReturn(requestMock).when(requestMock).addHeader(anyString(), anyString());
        doReturn(requestMock).when(requestMock).setAccept(anyString());
        doReturn(operationResultMock).when(requestMock).get();
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), any(GenericType.class), isA(String[].class), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            BundlesService service = new BundlesService(sessionStorageMock);
            //when
            OperationResult<Map<String, String>> bundle = service.bundle("jasperserver_messages");
            //then
            assertSame(bundle, operationResultMock);
            verify(requestMock).setAccept(MediaType.APPLICATION_JSON);
            verify(requestMock, never()).addParam("expanded", "true");
            verify(requestMock).addHeader("Accept-Language", Locale.getDefault().toString().replace('_', '-'));
            verify(requestMock).get();
            mockedStatic.verify(() -> JerseyRequest.buildRequest(
                    eq(sessionStorageMock),
                    eq(new GenericType<Map<String, String>>() {}),
                    eq(new String[]{"bundles", "jasperserver_messages"}),
                    any(DefaultErrorHandler.class)), times(1));
        }
    }

    @Test
    public void should_not_invoke_adding_language_by_empty_locale() throws Exception {
        //given
        doReturn(requestMock).when(requestMock).addHeader(anyString(), anyString());
        doReturn(requestMock).when(requestMock).setAccept(anyString());
        doReturn(operationResultMock).when(requestMock).get();
        try (MockedStatic<JerseyRequest> mockedStatic = Mockito.mockStatic(JerseyRequest.class)) {
            mockedStatic.when(() -> JerseyRequest.buildRequest(eq(sessionStorageMock), any(GenericType.class), isA(String[].class), any(DefaultErrorHandler.class))).thenReturn(requestMock);
            BundlesService service = new BundlesService(sessionStorageMock);
            //when
            OperationResult<Map<String, String>> bundle = service.forLocale(new Locale("")).bundle("jasperserver_messages");
            //then
            assertSame(bundle, operationResultMock);
            verify(requestMock).setAccept(MediaType.APPLICATION_JSON);
            verify(requestMock, never()).addParam("expanded", "true");
            verify(requestMock, never()).addHeader("Accept-Language", Locale.getDefault().toString().replace('_', '-'));
            verify(requestMock).get();
            mockedStatic.verify(() -> JerseyRequest.buildRequest(
                    eq(sessionStorageMock),
                    eq(new GenericType<Map<String, String>>() {}),
                    eq(new String[]{"bundles", "jasperserver_messages"}),
                    any(DefaultErrorHandler.class)), times(1));
        }
    }

    @Test
    public void should_set_default_locale_if_string_locale_is_null() throws Exception {
        //given
        doReturn(Locale.getDefault()).when(sessionStorageMock).getUserLocale();
        BundlesService service = new BundlesService(sessionStorageMock);
        Locale defaultLocale = Locale.getDefault();
        //when
        service.forLocale((String) null);
        //then
        assertEquals(getLocaleField(service), defaultLocale);
    }

    @Test
    public void should_set_default_locale_if_instance_locale_is_null() throws Exception {
        //given
        doReturn(Locale.getDefault()).when(sessionStorageMock).getUserLocale();
        BundlesService service = new BundlesService(sessionStorageMock);
        Locale defaultLocale = Locale.getDefault();
        //when
        service.forLocale((Locale) null);
        //then
        assertEquals(getLocaleField(service), defaultLocale);
    }

    @Test
    public void should_locale_if_authentication_locale_is_different() throws Exception {
        //given
        doReturn(Locale.getDefault()).when(sessionStorageMock).getUserLocale();
        BundlesService service = new BundlesService(sessionStorageMock);
        Locale locale = new Locale("de");
        //when
        service.forLocale(locale);
        //then
        assertEquals(getLocaleField(service), locale);
    }
}
